package cn.lovezsm.bjcj.task;

import cn.lovezsm.bjcj.config.APConf;
import cn.lovezsm.bjcj.entity.Message;
import cn.lovezsm.bjcj.entity.Record;
import cn.lovezsm.bjcj.utils.DataUtils;
import cn.lovezsm.bjcj.utils.LogUtil;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.util.*;

@Component
public class ProcessRawDataTask extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("ProcessRawDataTask working...."+System.currentTimeMillis());
        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
        DataUtils dataUtils = (DataUtils) dataMap.get("dataUtils");
        APConf apConf = (APConf) dataMap.get("apConf");
        LogUtil logUtil = (LogUtil) dataMap.get("logUtil");
        List<Message> dl = dataUtils.collectData();
        if (dl == null || dl.size() == 0) {
            System.err.println("没有采集到数据");
            return;
        }

        Map<String, Integer> valueMap = new Hashtable<>();
        Map<String, Integer> countMap = new Hashtable<>();

        for (Message message : dl) {
            if (valueMap.containsKey(message.getApMac() + "_" + message.getDevMac())) {
                Integer val = valueMap.get(message.getApMac() + "_" + message.getDevMac());
                Integer count = countMap.get(message.getApMac() + "_" + message.getDevMac());
                valueMap.put(message.getApMac() + "_" + message.getDevMac(), val + message.getRssi());
                countMap.put(message.getApMac() + "_" + message.getDevMac(), ++count);
            } else {
                valueMap.put(message.getApMac() + "_" + message.getDevMac(), message.getRssi());
                countMap.put(message.getApMac() + "_" + message.getDevMac(), 1);
            }
        }
        long scanTime = dl.get(0).getTime();
        Iterator<String> keyIter = valueMap.keySet().iterator();
        Map<String, Double[]> rssiMap = new Hashtable<>();

        while (keyIter.hasNext()) {
            String key = keyIter.next();
            Integer count = countMap.get(key);
            Integer value = valueMap.get(key);

            String[] keyArray = key.split("_");
            String apMac = keyArray[0];
            String devMac = keyArray[1];

            Double avgRssi = (value * 1.0d) / count;



            if (rssiMap.containsKey(devMac)) {
                Double[] rssiArray = rssiMap.get(devMac);
                rssiArray[apConf.getApId(apMac)] = avgRssi;
            } else {
                Double[] rssiArray = new Double[apConf.getInfo().size()];
                rssiArray[apConf.getApId(apMac)] = avgRssi;
                rssiMap.put(devMac, rssiArray);
            }
        }

        Iterator<String> rssiIter = rssiMap.keySet().iterator();
        List<Record> records = new ArrayList<>();
        while (rssiIter.hasNext()) {
            String devMac = rssiIter.next();
            Record record = new Record();
            record.setDevMac(devMac);
            record.setRssi(rssiMap.get(devMac));
            record.setScanTime(scanTime);
            records.add(record);
        }

//        logUtil.logRecord(records);
        for(Record record:records){
//            logUtil.log(record.toString(),record.getDevMac()+"_"+logUtil.getLogConf().getGridId()+"_"+"record.log");
        }
        System.out.println("ProcessRawDataTask over...."+records.size());
        dataUtils.putRecord(records);
    }


}
