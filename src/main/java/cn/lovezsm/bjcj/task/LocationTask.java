package cn.lovezsm.bjcj.task;

import cn.lovezsm.bjcj.algorithm.LocalizeByFingerPrint;
import cn.lovezsm.bjcj.data.FingerPrint;
import cn.lovezsm.bjcj.entity.LocalizeReturnVal;
import cn.lovezsm.bjcj.entity.Record;
import cn.lovezsm.bjcj.netty.UDPServerHandler;
import cn.lovezsm.bjcj.utils.AlgorithmUtils;
import cn.lovezsm.bjcj.utils.DataUtils;
import cn.lovezsm.bjcj.utils.LogUtil;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LocationTask extends QuartzJobBean {


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();

        int apNum = dataMap.getInt("apNum");
        int k = dataMap.getInt("K");
        FingerPrint fingerPrint = (FingerPrint) dataMap.get("fingerPrint");
        DataUtils dataUtils = (DataUtils) dataMap.get("dataUtils");
        int stepTime = dataMap.getInt("stepTime");

        List<Record> records = dataUtils.getAlgorithmData(System.currentTimeMillis()-stepTime*1000);
        Map<String,List<List<Double>>> map = new HashMap<>();
        if(records.size()==0|| UDPServerHandler.startTag==false){
            return;
        }
        //预处理，平均
        for(Record record:records){
            if(!map.containsKey(record.getDevMac())) {
                List<List<Double>> vl = new ArrayList<>();
                for(int i=0;i<apNum;i++){
                    vl.add(new ArrayList<Double>());
                }
                map.put(record.getDevMac(),vl);
            }
            List<List<Double>> vl = map.get(record.getDevMac());
            Double[] rssi = record.getRssi();
            for(int i=0;i<apNum;i++){
                if(rssi[i]!=null){
                    vl.get(i).add(rssi[i]);
                }
            }
            map.put(record.getDevMac(),vl);
            record.setUpdateTime(System.currentTimeMillis());
        }
        Map<String,Double[]> temp = new HashMap<>();
        for(Map.Entry<String,List<List<Double>>> entry:map.entrySet()){
            Double[] val = new Double[apNum];
            List<List<Double>> value = entry.getValue();
            for(int i =0;i< apNum;i++){
                List<Double> vals = value.get(i);
                if(vals.size()>0){
                    double sum = 0;
                    for(double v:vals){
                        sum+=v;
                    }
                    val[i] = sum/vals.size();
                }else {
//                    val[i] = -110f;
                }
            }
            temp.put(entry.getKey(),val);
        }

        Map<String, LocalizeReturnVal> result = new HashMap<>();
        List<LocalizeReturnVal> lrs = new ArrayList<>();
        for(Map.Entry<String,Double[]> item:temp.entrySet()){
//            LocalizeReturnVal returnVal = LocalizeByFingerPrint.doCalculate(item.getValue(), k,fingerPrint.getPosX(),fingerPrint.getPosY(),fingerPrint.getAreaGrid(),fingerPrint.getAvg(),fingerPrint.getStd());
//            returnVal.setDevMac(item.getKey());
//            System.out.println(item.getKey()+" : "+returnVal);
//            lrs.add(returnVal);
//            dataUtils.returnVals.add(returnVal);
//            result.put(item.getKey(),returnVal);
//            lrs.add(returnVal);
        }

//        logUtil.logResult(lrs);
        for (LocalizeReturnVal val:lrs){
//            double error = AlgorithmUtils.euclideanDistance(val.getX(), val.getY(), fingerPrint.getPosX()[logConf.gridId - 1], fingerPrint.getPosY()[logConf.gridId - 1]);
//            String content = "{" + "x=" + new BigDecimal(val.getX()).setScale(2, RoundingMode.UP).doubleValue() + ", y=" + new BigDecimal(val.getY()).setScale(2, RoundingMode.UP).doubleValue() + ", idxCandidate=" + val.getIdxCandidate() + ", probCandidate=" + val.getProbCandidate() + ", error="+ error +'}';
//            LogUtil.log(content,val.getDevMac()+"_"+logConf.gridId+"_"+"local.log");
        }
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for(Record record:records){
//                    LocalizeReturnVal returnVal = result.get(record.getDevMac());
//                    returnVal.setUpdateTime(record.getScanTime());
//                    record.setLocalizeReturnVal(returnVal);
//                }
//                List<Record> records1 = dataUtils.collectInfo();
//                FloorPlan<String,LocalizeReturnVal> ml = new HashMap<>();
//                List<LocalizeReturnVal> ct = new ArrayList<>();
//                for(Record record:records1){
//                    ml.put(record.getUpdateTime(),record.getLocalizeReturnVal());
//                }
//                for(FloorPlan.Entry<String,LocalizeReturnVal> entry:ml.entrySet()){
//                    ct.add(entry.getValue());
//                }
//                cddr = cdd.doCalculate(ct);
//            }
//        });
//        thread.start();
    }
}
