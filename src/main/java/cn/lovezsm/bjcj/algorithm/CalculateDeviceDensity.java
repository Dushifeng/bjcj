package cn.lovezsm.bjcj.algorithm;

import cn.lovezsm.bjcj.config.GlobeConf;
import cn.lovezsm.bjcj.data.FingerPrint;
import cn.lovezsm.bjcj.entity.CalculateDeviceDensityReturnVal;
import cn.lovezsm.bjcj.entity.LocalizeReturnVal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * version:3
 *
 * %% 	设备密度估计算法
 * % 功能描述：已知指纹库、部署区域的参考位置坐标等信息，给定一段时间内探测的RSS、MAC地址等信息，估计各个参考位置的设备密度
 * %%%%%%%%%%%%%%%%%%%%%%%%%%%
 * %	输入：
 * % rssSlidingWindow：r*p数组，每行表示一个设备在同一个时刻被p个AP探测的RSS值，每列表示一个AP
 * %（滑动窗口中的数据已按时间排序，即第一个数据是最早的，最后一个数据是最新的）
 * % macSlidingWindow：r*1数组，每行表示rssSlidingWindow中每行对应的设备MAC
 * % k:   WKNN方法的k值，取6~10
 * % rssMean: n*p数组，每行表示一个晶格的RSS均值；每列表示一个AP；
 * % rssStd:  n*p数组，每行表示一个晶格的RSS标准差；每列表示一个AP；
 * % posGrid: n*2数组，每行表示一个晶格的二维坐标；
 * % areaGrid: n*1数组，每个元素表示对应晶格的面积（如果所有晶格面积都相同，该数组可置为[]）
 * %%%%%%%%%%%%%%%%%%%%%%%%%%%%
 * %	输出：
 * % 设备密度数组
 * %% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 * % 注意
 * % 晶格个数n既包括监控区域，也包括非监控区域
 * % k<n
 * % rssStd中每一个元素均为正数
 * % rssSlidingWindow每行中的每个元素表示对应AP的RSS值，如果对应AP没有探测到该设备，则取值-200
 *
 */
@Component
public class CalculateDeviceDensity {
    @Autowired
    GlobeConf globeConf;

    public CalculateDeviceDensityReturnVal doCalculate(List<LocalizeReturnVal> records){

        double[] deviceDensity = new double[globeConf.getGridMap().getGridNum()];
        double[] newDensity = new double[globeConf.getGridMap().getGridNum()];
        for(LocalizeReturnVal lr:records){
            if(lr == null){
                continue;
            }
            List<Integer> idxCandidate = lr.getIdxCandidate();
            List<Double> probCandidate = lr.getProbCandidate();
            for(int i=0;i<idxCandidate.size();i++){
                int idx = idxCandidate.get(i);
                deviceDensity[idx] = deviceDensity[idx]+probCandidate.get(i);
                if(lr.getUpdateTime()>=System.currentTimeMillis()-globeConf.getAlgorithmConf().getSliding_step_time()*1000){
                    newDensity[idx] = deviceDensity[idx]+probCandidate.get(i);
                }
            }
        }
        CalculateDeviceDensityReturnVal cddr = new CalculateDeviceDensityReturnVal();
        cddr.setDeviceDensity(deviceDensity);
        cddr.setNewDensity(newDensity);
        cddr.setPosX(globeConf.getGridMap().getPosX());
        cddr.setPosY(globeConf.getGridMap().getPosY());
        return cddr;
    }

}
