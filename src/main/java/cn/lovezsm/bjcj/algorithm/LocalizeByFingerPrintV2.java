package cn.lovezsm.bjcj.algorithm;

import cn.lovezsm.bjcj.config.AlgorithmConf;
import cn.lovezsm.bjcj.config.GlobeConf;
import cn.lovezsm.bjcj.data.FingerPrint;
import cn.lovezsm.bjcj.data.GridMap;
import cn.lovezsm.bjcj.entity.LocalizeReturnVal;
import cn.lovezsm.bjcj.utils.AlgorithmUtil;
import cn.lovezsm.bjcj.utils.DataUtil;
import cn.lovezsm.bjcj.utils.SpringUtil;

import java.util.Arrays;

public class LocalizeByFingerPrintV2 extends Localizer {

    AlgorithmConf algorithmConf;
    DataUtil dataUtil;
    LocalizeByFingerPrint localizeByFingerPrint;
    public LocalizeByFingerPrintV2(String mac,Double[] rssi, int k, FingerPrint fingerPrint, GridMap gridMap,String vision) {
        super(mac,rssi, k, fingerPrint, gridMap,vision);
        GlobeConf conf = SpringUtil.getBean(GlobeConf.class);
        algorithmConf = conf.getAlgorithmConf();
        dataUtil = SpringUtil.getBean(DataUtil.class);
        localizeByFingerPrint = new LocalizeByFingerPrint(mac,rssi,k,fingerPrint,gridMap,"v1");
    }


    @Override
    public LocalizeReturnVal doCalculate() {
        int count = AlgorithmUtil.checkNotNullValCount(rssi);
        if(algorithmConf.isOffsetOpen()&&count>=algorithmConf.getOffsetNotNullVal()){
            double offsetVal = CalculateOffset.doCalculate(rssi, k, fingerPrint, gridMap);

            if(Math.abs(offsetVal)<12){
                if(dataUtil.containsKeyForOffsetMap(this.mac)){
                    double oldOffsetVal = dataUtil.getOffsetValByMac(this.vision,this.mac);
                    dataUtil.updateOffsetMap(this.vision,this.mac,oldOffsetVal*0.7+offsetVal*0.3);
                }else {
                    dataUtil.updateOffsetMap(this.vision,this.mac,offsetVal);
                }
            }
        }

        if(dataUtil.containsKeyForOffsetMap(this.mac)){
            double offset = dataUtil.getOffsetValByMac(this.vision,this.mac);
            for(int i=0;i<rssi.length;i++){
                if(rssi[i]!=null&&rssi[i]!=-100){
                    rssi[i] = rssi[i]-offset;
                }
            }
        }
        System.out.println(Arrays.toString(rssi));
        this.returnVal = localizeByFingerPrint.doCalculate();
        return returnVal;
    }
}
