package cn.lovezsm.bjcj.task;

import cn.lovezsm.bjcj.algorithm.LocalizeByFingerPrint;
import cn.lovezsm.bjcj.config.AlgorithmConf;
import cn.lovezsm.bjcj.data.FingerPrint;
import cn.lovezsm.bjcj.data.GridMap;
import cn.lovezsm.bjcj.entity.LocalizeReturnVal;
import cn.lovezsm.bjcj.entity.Record;
import cn.lovezsm.bjcj.netty.UDPServerHandler;
import cn.lovezsm.bjcj.utils.AlgorithmUtil;
import cn.lovezsm.bjcj.utils.DataUtil;
import cn.lovezsm.bjcj.utils.FileUtil;
import cn.lovezsm.bjcj.utils.LogUtil;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.io.Reader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Component
public class LocationTask extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
        AlgorithmConf algorithmConf = (AlgorithmConf) dataMap.get("algorithmConf");
        int apNum = dataMap.getInt("apNum");

        List<FingerPrint> fingerPrints = (List<FingerPrint>) dataMap.get("fingerPrints");
        GridMap gridMap = (GridMap) dataMap.get("gridMap");
        DataUtil dataUtil = (DataUtil) dataMap.get("dataUtils");
        LogUtil logUtil = (LogUtil) dataMap.get("log");
        int stepTime = algorithmConf.getSliding_step_time();
        int k = algorithmConf.getK();
        List<Record> records = dataUtil.getAlgorithmData(System.currentTimeMillis()-stepTime*1000);
        Map<String,Double[]> valueMap = new HashMap<>();
        Map<String,Integer[]> countMap = new HashMap<>();
        FingerPrint fingerPrint_2G = fingerPrints.get(0);
        System.out.println(fingerPrint_2G.getName());
        FingerPrint fingerPrint_5G = fingerPrints.get(1);


        for(Record record:records){
            String key = record.getDevMac()+"_"+record.getFrequency();
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
                Double[] doubles = new Double[apNum];
                Integer[] integers = new Integer[apNum];
                for (int i=0;i<apNum;i++){
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
            String key = keyIter.next();
            Double[] doubles = valueMap.get(key);
            Integer[] integers = countMap.get(key);
            for(int i =0;i<apNum;i++){
                if(integers[i]>0){
                    doubles[i] = doubles[i]/integers[i];
                }else {
                    doubles[i] = -100d;
                }
            }
            int count = checkNotNullValCount(doubles);
            if(count<algorithmConf.getNotNullValMinCount()){
                continue;
            }
//            culMap.put(key,doubles);
            String devMac = key.split("_")[0];
            Integer frequency = Integer.parseInt(key.split("_")[1]);
            LocalizeReturnVal val;
            if(frequency==2){
                val = LocalizeByFingerPrint.doCalculate(doubles, k, fingerPrint_2G, gridMap);
            }else {
                val = LocalizeByFingerPrint.doCalculate(doubles, k, fingerPrint_5G, gridMap);
            }
            long time = System.currentTimeMillis();
            val.setFrequency(frequency);
            val.setUpdateTime(time);
            double error = -1d;
            String content="";
            Object[] pro = val.getProbCandidate().toArray();
            for (int i =0;i<pro.length;i++){
                if(Double.isNaN((Double)pro[i])||Double.isInfinite((Double)pro[i])){
                    System.out.println("error");
                }
                pro[i] = new BigDecimal((Double) pro[i]).setScale(2, RoundingMode.UP).doubleValue();
            }

            if(logUtil.getLogConf().getX()!=-1d&&logUtil.getLogConf().getY()!=-1d){
                error = AlgorithmUtil.euclideanDistance(val.getX(), val.getY(), logUtil.getLogConf().getX(),logUtil.getLogConf().getY());
                if(Double.isNaN(error)||Double.isInfinite(error)){
                    error = 0d;
                }else {
                    error = new BigDecimal(error).setScale(2, RoundingMode.UP).doubleValue();
                }
                error = new BigDecimal(error).setScale(2, RoundingMode.UP).doubleValue();

                if(error>7){
                    System.out.println("exception...");
                    StringBuffer sb = new StringBuffer();
                    for (Record record:records){
                        if(record.getDevMac().equals(devMac)){
                            sb.append(record.getFrequency()+";");
                            sb.append(Arrays.toString(record.getRssi()));
                        }
                    }
                    logUtil.log(time+","+devMac+","+error+","+val.getX()+","+val.getY()+","+sb.toString()+","+Arrays.toString(doubles),"exceptionData.log");
                }
                content =   time + ","+ devMac+ ","+ frequency + ","+ new BigDecimal(val.getX()).setScale(2, RoundingMode.UP).doubleValue() + "," + new BigDecimal(val.getY()).setScale(2, RoundingMode.UP).doubleValue() + "," + FileUtil.toString(val.getIdxCandidate().toArray()) + "," + FileUtil.toString(pro) + ","+ new BigDecimal(Math.abs(logUtil.getLogConf().getX()-val.getX())).setScale(2, RoundingMode.UP).doubleValue() + ","+ new BigDecimal(Math.abs(logUtil.getLogConf().getY()-val.getY())).setScale(2, RoundingMode.UP).doubleValue()+ ","+ error;
            }else if(logUtil.getLogConf().getGridId()!=-1){
                int id = logUtil.getLogConf().getGridId()-1;
                double grid_x = gridMap.getPosX()[id];
                double grid_y = gridMap.getPosY()[id];
                error = AlgorithmUtil.euclideanDistance(val.getX(),val.getY(),grid_x,grid_y);
                if(Double.isNaN(error)||Double.isInfinite(error)){
                    error = 0d;
                }else {
                    error = new BigDecimal(error).setScale(2, RoundingMode.UP).doubleValue();
                }
                content =  time + ","+ devMac+ ","+ frequency + ","+ new BigDecimal(val.getX()).setScale(2, RoundingMode.UP).doubleValue() + "," + new BigDecimal(val.getY()).setScale(2, RoundingMode.UP).doubleValue() + "," + FileUtil.toString(val.getIdxCandidate().toArray()) + "," + FileUtil.toString(pro) + ","+ new BigDecimal(Math.abs(grid_x-val.getX())).setScale(2, RoundingMode.UP).doubleValue() + ","+ new BigDecimal(Math.abs(grid_y-val.getY())).setScale(2, RoundingMode.UP).doubleValue() + ","+ error;
            }
            //locx,locy,
            logUtil.log(content,devMac+logUtil.getLogConf().getLogPath()+"local.log");

            val.setDevMac(devMac);
            val.setUpdateTime(System.currentTimeMillis());
            System.out.println("Mac:"+devMac+"   frequency:"+frequency+"   "+val);
            dataUtil.updateLocVal(val);
        }

//        Map<String,List<List<Double>>> map = new HashMap<>();
//        if(records.size()==0|| UDPServerHandler.startTag==false){-------------------------------------------------------------------
//            return;
//        }
//        //预处理，平均
//        for(Record record:records){
//            if(!map.containsKey(record.getDevMac())) {
//                List<List<Double>> vl = new ArrayList<>();
//                for(int i=0;i<apNum;i++){
//                    vl.add(new ArrayList<Double>());
//                }
//                map.put(record.getDevMac(),vl);
//            }
//            List<List<Double>> vl = map.get(record.getDevMac());
//            Double[] rssi = record.getRssi();
//            for(int i=0;i<apNum;i++){
//                if(rssi[i]!=null){
//                    vl.get(i).add(rssi[i]);
//                }
//            }
//            map.put(record.getDevMac(),vl);
//            record.setUpdateTime(System.currentTimeMillis());
//        }
//        Map<String,Double[]> temp = new HashMap<>();
//        for(Map.Entry<String,List<List<Double>>> entry:map.entrySet()){
//            Double[] val = new Double[apNum];
//            List<List<Double>> value = entry.getValue();
//            for(int i =0;i< apNum;i++){
//                List<Double> vals = value.get(i);
//                if(vals.size()>0){
//                    double sum = 0;
//                    for(double v:vals){
//                        sum+=v;
//                    }
//                    val[i] = sum/vals.size();
//                }else {
////                    val[i] = -110f;
//                }
//            }
//            temp.put(entry.getKey(),val);
//        }
//
//        Map<String, LocalizeReturnVal> result = new HashMap<>();
//        List<LocalizeReturnVal> lrs = new ArrayList<>();
//        for(Map.Entry<String,Double[]> item:temp.entrySet()){
////            LocalizeReturnVal returnVal = LocalizeByFingerPrint.doCalculate(item.getValue(), k,fingerPrint.getPosX(),fingerPrint.getPosY(),fingerPrint.getAreaGrid(),fingerPrint.getAvg(),fingerPrint.getStd());
////            returnVal.setDevMac(item.getKey());
////            System.out.println(item.getKey()+" : "+returnVal);
////            lrs.add(returnVal);
////            dataUtils.returnVals.add(returnVal);
////            result.put(item.getKey(),returnVal);
////            lrs.add(returnVal);
//        }

//        logUtil.logResult(lrs);
//        for (LocalizeReturnVal val:lrs){
//            double error = AlgorithmUtil.euclideanDistance(val.getX(), val.getY(), fingerPrint.getPosX()[logConf.gridId - 1], fingerPrint.getPosY()[logConf.gridId - 1]);
//            String content = "{" + "x=" + new BigDecimal(val.getX()).setScale(2, RoundingMode.UP).doubleValue() + ", y=" + new BigDecimal(val.getY()).setScale(2, RoundingMode.UP).doubleValue() + ", idxCandidate=" + val.getIdxCandidate() + ", probCandidate=" + val.getProbCandidate() + ", error="+ error +'}';
//            LogUtil.log(content,val.getDevMac()+"_"+logConf.gridId+"_"+"local.log");
        }

    private int checkNotNullValCount(Double[] doubles) {
        int count=0;
        for (Double d:doubles){
            if(d!=-100){
                count++;
            }
        }
        return count;
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
//    }
}
