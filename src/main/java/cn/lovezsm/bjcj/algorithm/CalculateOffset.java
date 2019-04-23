package cn.lovezsm.bjcj.algorithm;

import cn.lovezsm.bjcj.data.FingerPrint;
import cn.lovezsm.bjcj.data.GridMap;
import cn.lovezsm.bjcj.entity.LocalizeReturnVal;
import cn.lovezsm.bjcj.utils.AlgorithmUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CalculateOffset {

    public static double doCalculate(Double[] rssi, int k, FingerPrint fingerPrint, GridMap gridMap) {
        double avgOffset = 0d;
        double[] posX = gridMap.getPosX();
        double[] posY = gridMap.getPosY();
        int apNum = rssi.length;
        int gridNum = gridMap.getGridNum();
        double[] fFusion = AlgorithmUtil.getMatrix(gridNum, 1);
        double[] offset = AlgorithmUtil.getMatrix(gridNum, 0);
        Double[][] std = fingerPrint.getStd();
        Double[][] avg = fingerPrint.getAvg();
        for(int i=0;i<gridNum;i++){
            double a=0,b=0,c=0;
            for (int j=0;j<apNum;j++){
                if(rssi[j]==-100||rssi[j]==null){
                    continue;
                }
                double t =(std[i][j]*std[i][j]);
                a=a+1/2/t;
                b=b+(avg[i][j]-rssi[j])/t;
                c=c+(avg[i][j]-rssi[j])*(avg[i][j]-rssi[j])/2/t+Math.log(std[i][j]);

            }
            fFusion[i] = Math.exp(-(4*a*c-b*b)/a/4);
            offset[i] = -b/2/a;
        }
        AlgorithmUtil.getSortedIndex(fFusion,k);
        //根据晶格面积修正似然值
        for(int i = 0;i<gridNum;i++){
            fFusion[i]=fFusion[i]*gridMap.getPosH()[i]*gridMap.getPosW()[i];
        }
        AlgorithmUtil.normalized(fFusion);
        avgOffset = AlgorithmUtil.multiplication(offset,fFusion);
        return avgOffset;
    }
}
