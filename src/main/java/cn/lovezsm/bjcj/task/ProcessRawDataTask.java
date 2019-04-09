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
        int f=2;
        for (Message message : dl) {
            if(message.getFrequency()<15){
                f = 2;
            }else {
                f = 5;
            }
            if (valueMap.containsKey(message.getApMac() + "_" + message.getDevMac() + "_"+f)) {
                Integer val = valueMap.get(message.getApMac() + "_" + message.getDevMac()+ "_"+f);
                Integer count = countMap.get(message.getApMac() + "_" + message.getDevMac()+ "_"+f);
                valueMap.put(message.getApMac() + "_" + message.getDevMac()+ "_"+f, val + message.getRssi());
                countMap.put(message.getApMac() + "_" + message.getDevMac()+ "_"+f, ++count);
            } else {
                valueMap.put(message.getApMac() + "_" + message.getDevMac()+ "_"+f, message.getRssi());
                countMap.put(message.getApMac() + "_" + message.getDevMac()+ "_"+f, 1);
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



            if (rssiMap.containsKey(devMac+"_"+frequency)) {
                Double[] rssiArray = rssiMap.get(devMac+"_"+frequency);
                rssiArray[apConf.getApId(apMac)] = avgRssi;
            } else {
                Double[] rssiArray = new Double[apConf.getInfo().size()];
                rssiArray[apConf.getApId(apMac)] = avgRssi;
                rssiMap.put(devMac+"_"+frequency, rssiArray);
            }
        }

        Iterator<String> rssiIter = rssiMap.keySet().iterator();
        List<Record> records = new ArrayList<>();
        while (rssiIter.hasNext()) {
            String[] split = rssiIter.next().split("_");
            String devMac = split[0];
            int frequency = Integer.parseInt(split[1]);
            Record record = new Record();
            record.setDevMac(devMac);
            record.setRssi(rssiMap.get(devMac+"_"+frequency));
            record.setScanTime(scanTime);
            record.setFrequency(frequency);
            records.add(record);
        }
//        logUtil.logRecord(records);
        for(Record record:records){
            logUtil.log(record.toString(),record.getDevMac()+"_"+logUtil.getLogConf().getGridId()+"_"+logUtil.getLogConf().getX()+"_"+logUtil.getLogConf().getY()+"_"+"record.log");
            System.out.println(record);
        }
        dataUtil.putRecord(records);
    }
}

