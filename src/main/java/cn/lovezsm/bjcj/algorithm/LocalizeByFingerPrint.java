package cn.lovezsm.bjcj.algorithm;

import cn.lovezsm.bjcj.data.FingerPrint;
import cn.lovezsm.bjcj.data.GridMap;
import cn.lovezsm.bjcj.entity.LocalizeReturnVal;
import cn.lovezsm.bjcj.utils.AlgorithmUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * %% 指纹定位算法
 * %%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 * % 输入
 * % rss: p维数组，某个设备的实时RSS采样
 * % k:   WKNN方法的k值，取6~10;
 * % rssMean: n*p数组，每行表示一个晶格的RSS均值；每列表示一个AP；
 * % rssStd:  n*p数组，每行表示一个晶格的RSS标准差；每列表示一个AP；
 * % posGrid: n*2数组，每行表示一个晶格的二维坐标；
 * % areaGrid: n*1数组，每个元素表示对应晶格的面积（如果所有晶格面积都相同，该数组可置为[]）
 * %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 * % 输出
 * % 位置估计坐标
 * % 向量：k个最佳参考位置序号
 * % 向量：k个最佳参考位置归一后的似然值
 *
 * %% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 * % 注意
 * % 晶格个数n既包括监控区域，也包括非监控区域
 * % k<n
 * % rssStd中每一个元素均为正数
 * % rss每个元素表示对应AP的RSS值，如果对应AP没有探测到该设备，则取值-200
 */

@Component
@Scope("singleton")
public class LocalizeByFingerPrint {

    public static LocalizeReturnVal doCalculate(Double[] rssi, int k, FingerPrint fingerPrint, GridMap gridMap){

        if(fingerPrint.isStandardization()){
            //标准化rssi
            AlgorithmUtil.VectorStandardization(rssi);
        }
        int gridNum = gridMap.getPosX().length;
        LocalizeReturnVal returnVal = new LocalizeReturnVal();
        int apNum = rssi.length;
        double fFusion[] = AlgorithmUtil.getMatrix(gridNum,1);
        for(int i =0;i<apNum;i++){
            //计算该AP在每一个晶格探测到该设备的概率值
            double[] fFusionTemp = AlgorithmUtil.getMatrix(gridNum,0);
            for (int ng =0;ng<gridNum;ng++){
                // (1/(sqrt(2*pi)*rssStd(num_grid, j )))*exp((rss(j)-rssMean(num_grid,j))^2/(-2*rssStd(num_grid,j)^2));
                fFusionTemp[ng] = (1/(Math.sqrt(2*Math.PI)*fingerPrint.getStd()[ng][i]))*Math.exp((rssi[i]-fingerPrint.getAvg()[ng][i])*(rssi[i]-fingerPrint.getAvg()[ng][i])/(-2*fingerPrint.getStd()[ng][i]*fingerPrint.getStd()[ng][i]));
               //累乘概率值，得到该设备在每一个晶格的似然值
                fFusion[ng] = fFusion[ng]*fFusionTemp[ng];
            }
        }
        int[] fusionIdx =  AlgorithmUtil.getSortedIndex(fFusion,k);
        //根据晶格面积修正似然值
        for(int i = 0;i<gridNum;i++){
            fFusion[i]=fFusion[i]*gridMap.getPosH()[i]*gridMap.getPosW()[i];
        }

        AlgorithmUtil.normalized(fFusion);
        //使用WKNN算法，计算位置估计
        double x = AlgorithmUtil.multiplication(gridMap.getPosX(),fFusion);
        double y = AlgorithmUtil.multiplication(gridMap.getPosY(),fFusion);
        returnVal.setX(x);
        returnVal.setY(y);

        int idxCandidate[] = Arrays.copyOf(fusionIdx,k);
        returnVal.setIdxCandidate(idxCandidate);
        double probCandidate[] = new double[k];
        for(int i=0;i<idxCandidate.length;i++){
            probCandidate[i] = fFusion[idxCandidate[i]];
        }


        returnVal.setProbCandidate(probCandidate);

        return returnVal;
    }


}
