package cn.lovezsm.bjcj.task;

import cn.lovezsm.bjcj.config.APConf;
import cn.lovezsm.bjcj.entity.Message;
import cn.lovezsm.bjcj.entity.Record;
import cn.lovezsm.bjcj.utils.DataUtil;
import cn.lovezsm.bjcj.utils.LogUtil;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProcessRawDataTask extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
        DataUtil dataUtil = (DataUtil) dataMap.get("dataUtils");
        APConf apConf = (APConf) dataMap.get("apConf");
        LogUtil logUtil = (LogUtil) dataMap.get("logUtil");
        List<Message> dl = dataUtil.collectData();
        if (dl == null || dl.size() == 0) {
            System.err.println("没有采集到数据");
            return;
        }

        Map<String, Integer> valueMap = new Hashtable<>();
        Map<String, Integer> countMap = new Hashtable<>();
        for (Message message : dl) {
            if (valueMap.containsKey(message.getApMac() + "_" + message.getDevMac() + "_"+message.getFrequency())) {
                Integer val = valueMap.get(message.getApMac() + "_" + message.getDevMac()+ "_"+message.getFrequency());
                Integer count = countMap.get(message.getApMac() + "_" + message.getDevMac()+ "_"+message.getFrequency());
                valueMap.put(message.getApMac() + "_" + message.getDevMac()+ "_"+message.getFrequency(), val + message.getRssi());
                countMap.put(message.getApMac() + "_" + message.getDevMac()+ "_"+message.getFrequency(), ++count);
            } else {
                valueMap.put(message.getApMac() + "_" + message.getDevMac()+ "_"+message.getFrequency(), message.getRssi());
                countMap.put(message.getApMac() + "_" + message.getDevMac()+ "_"+message.getFrequency(), 1);
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
            int frequency = Integer.parseInt(keyArray[2]);
            Double avgRssi = (value * 1.0d) / count;


            if (!rssiMap.containsKey(devMac)) {
                Double[] rssiArray = new Double[apConf.getInfo().size()*apConf.getCoefficientNum()];
                rssiMap.put(devMac, rssiArray);
            }
            Double[] rssiArray = rssiMap.get(devMac);
            int frequencyCoefficient = apConf.getFrequencyCoefficient(frequency);
            int rssi_loc = frequencyCoefficient*apConf.getApnum()+apConf.getApId(apMac);
            rssiArray[rssi_loc] = avgRssi;
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
        dataUtil.putRecord(records);
    }
}

