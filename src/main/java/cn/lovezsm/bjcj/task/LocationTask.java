package cn.lovezsm.bjcj.task;


import cn.lovezsm.bjcj.algorithm.LocalizeByFingerPrint;
import cn.lovezsm.bjcj.algorithm.LocalizeByFingerPrintV2;
import cn.lovezsm.bjcj.algorithm.Localizer;
import cn.lovezsm.bjcj.config.APConf;
import cn.lovezsm.bjcj.config.AlgorithmConf;
import cn.lovezsm.bjcj.data.FingerPrint;
import cn.lovezsm.bjcj.data.GridMap;
import cn.lovezsm.bjcj.entity.LocalizeReturnVal;
import cn.lovezsm.bjcj.entity.Record;

import cn.lovezsm.bjcj.utils.AlgorithmUtil;
import cn.lovezsm.bjcj.utils.DataUtil;

import cn.lovezsm.bjcj.utils.LogUtil;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class LocationTask extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
        AlgorithmConf algorithmConf = (AlgorithmConf) dataMap.get("algorithmConf");
        APConf apConf = (APConf) dataMap.get("apConf");

        List<FingerPrint> fingerPrints = (List<FingerPrint>) dataMap.get("fingerPrints");
        GridMap gridMap = (GridMap) dataMap.get("gridMap");
        DataUtil dataUtil = (DataUtil) dataMap.get("dataUtils");
        LogUtil logUtil = (LogUtil) dataMap.get("log");
        int stepTime = algorithmConf.getSliding_step_time();
        int k = algorithmConf.getK();
        List<Record> records = dataUtil.getAlgorithmData(System.currentTimeMillis()-stepTime*1000);
        Map<String,Double[]> valueMap = new HashMap<>();
        Map<String,Integer[]> countMap = new HashMap<>();
        FingerPrint fingerPrint = new FingerPrint();
        FingerPrint fingerPrint_2_4 = new FingerPrint();
        Double[][] std = new Double[gridMap.getGridNum()][apConf.getApnum()*fingerPrints.size()];
        Double[][] avg = new Double[gridMap.getGridNum()][apConf.getApnum()*fingerPrints.size()];;
        for(int t=0;t<fingerPrints.size();t++){
            if(fingerPrints.get(t)==null){
                continue;
            }
            Double[][] t_std = fingerPrints.get(t).getStd();
            Double[][] t_avg = fingerPrints.get(t).getAvg();
            for(int i=0;i<gridMap.getGridNum();i++){
                for (int j=0;j<apConf.getApnum();j++){
                    std[i][apConf.getApnum()*t+j] = t_std[i][j];
                    avg[i][apConf.getApnum()*t+j] = t_avg[i][j];
                }
            }
            if(fingerPrints.get(t).getName().equals("2G")){
                fingerPrint_2_4 = fingerPrints.get(t);
            }
        }

        fingerPrint.setAvg(avg);
        fingerPrint.setStd(std);
        fingerPrint.setStandardization(false);

        for(Record record:records){
            String key = record.getDevMac();
            if(valueMap.containsKey(key)){
                Double[] doubles = valueMap.get(key);
                Integer[] integers = countMap.get(key);
                for(int i =0;i<doubles.length;i++){
                    if(record.getRssi()[i]!=null){
                        doubles[i] = doubles[i]+record.getRssi()[i];
                        integers[i]++;
                    }
                }
            }else{
                int length = record.getRssi().length;
                Double[] doubles = new Double[length];
                Integer[] integers = new Integer[length];
                for (int i=0;i<length;i++){
                    if(record.getRssi()[i]!=null){
                        doubles[i]=record.getRssi()[i];
                        integers[i] = 1;
                    }else {
                        doubles[i]=0d;
                        integers[i] = 0;
                    }
                }
                valueMap.put(key,doubles);
                countMap.put(key,integers);
            }
        }
//        Map<String,Double[]> culMap = new HashMap<>();
        Iterator<String> keyIter = valueMap.keySet().iterator();
        while (keyIter.hasNext()){
            String devMac = keyIter.next();
            Double[] rssi = valueMap.get(devMac);
            Integer[] counts = countMap.get(devMac);
            for(int i =0;i<rssi.length;i++){
                if(counts[i]>0){
                    rssi[i] = rssi[i]/counts[i];
                }else {
                    rssi[i] = -100d;
                }
            }

            int count = AlgorithmUtil.checkNotNullValCount(rssi);
            if(count<algorithmConf.getNotNullValMinCount()){
                continue;
            }

            Double[] rssi_2_4 = new Double[rssi.length/10];
            for(int i =0;i<rssi_2_4.length;i++){
                rssi_2_4[i] = rssi[i];
            }


//            Localizer localize1 = new LocalizeByFingerPrint(devMac,rssi_2_4,k,fingerPrint_2_4,gridMap,"v1");
//            LocalizeReturnVal val1 = localize1.doCalculate();
//            Localizer localize2 = new LocalizeByFingerPrintV2(devMac,rssi_2_4,k,fingerPrint_2_4,gridMap,"v2");
//            LocalizeReturnVal val2 = localize2.doCalculate();
            Localizer localize3 = new LocalizeByFingerPrintV2(devMac,rssi,k,fingerPrint,gridMap,"v3");
            LocalizeReturnVal val3 = localize3.doCalculate();

            if(val3==null||Double.isNaN(val3.getX())||Double.isNaN(val3.getY())){
                continue;
            }
            val3.setUpdateTime(System.currentTimeMillis());
            val3.setDevMac(devMac);
            dataUtil.updateLocVal(val3);
        }
        System.out.println("个数："+valueMap.keySet().size());
//            dataUtil.updateLocVal(val);
    }

}
