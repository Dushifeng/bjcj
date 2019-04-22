package cn.lovezsm.bjcj.utils;

import cn.lovezsm.bjcj.data.DataFilter;
import cn.lovezsm.bjcj.data.FingerPrint;
import cn.lovezsm.bjcj.entity.LocalizeReturnVal;
import cn.lovezsm.bjcj.entity.Message;
import cn.lovezsm.bjcj.entity.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
@Scope("singleton")
public class DataUtil {

    private BlockingQueue<Message> dataqueue = new LinkedBlockingQueue<Message>();
    private BlockingQueue<Record> recordqueue = new LinkedBlockingQueue<>();
    public BlockingQueue<LocalizeReturnVal> returnVals = new LinkedBlockingQueue<>();

    private final Logger log= LoggerFactory.getLogger(DataUtil.class);


    @Autowired
    DataFilter dataFilter;

//    public FloorPlan<String,Object> analyzeData(String rawData){
//        FloorPlan<String,Object> ans = new HashMap<>();
//
//        ans.put("companyName",rawData.substring(0, 4));
//        ans.put("protocolControl",rawData.substring(4, 8));
//        ans.put("encryption",rawData.substring(8, 40));
//        ans.put("apMac",rawData.substring(46, 58));
//        List<Message> messages = new ArrayList<>();
//        ans.put("messages",messages);
//        int messageIndex = 58;
//        while (messageIndex < rawData.length() - 1) {
//            String tag = rawData.substring(messageIndex, messageIndex + 2);
//            if (!tag.equals("00")) {
//                int len = Integer.parseInt(rawData.substring(messageIndex + 2, messageIndex + 6), 16);
//                messageIndex += (len * 2 + 6);
////                System.out.println("出现了一条异常信息:"+tag);
//                continue;
//            }
//            messageIndex += 6;
//            String devMac = rawData.substring(messageIndex, messageIndex + 12);
//            if (!dataFilter.isOKMessage(devMac)) {
//                messageIndex += 34;
//                continue;
//            }
//            int f = Integer.parseInt(rawData.substring(messageIndex + 16, messageIndex + 18), 16);
//
//            messageIndex += 32;
//            int rssiVal = Integer.parseInt(rawData.substring(messageIndex, messageIndex + 2), 16) - 256;
//            messageIndex += 2;
//            Message message = new Message(tag, devMac, f, rssiVal, (String) ans.get("apMac"));
//            messages.add(message);
//        }
//
//        return ans;
//
//    }


 public List<Message> analyzeData(String rawData,Long scanTime){
        if(scanTime==null){
            scanTime = System.currentTimeMillis();
        }

//        if (rawData.contains(":")){
//            scanTime = Long.parseLong(rawData.split(":")[0]);
//            rawData = rawData.split(":")[1];
//        }

        List<Message> messages = new ArrayList<>();
        String apMac= rawData.substring(46, 58);
        int messageIndex = 58;
        while (messageIndex < rawData.length() - 1) {
            String tag = rawData.substring(messageIndex, messageIndex + 2);
            if (!tag.equals("00")) {
                int len = Integer.parseInt(rawData.substring(messageIndex + 2, messageIndex + 6), 16);
                messageIndex += (len * 2 + 6);
//                System.out.println("出现了一条异常信息:"+tag);
                continue;
            }
            messageIndex += 6;
            String devMac = rawData.substring(messageIndex, messageIndex + 12);
            if (!dataFilter.isOKMessage(devMac)) {
                messageIndex += 34;
                continue;
            }
            int f = Integer.parseInt(rawData.substring(messageIndex + 16, messageIndex + 18), 16);

            messageIndex += 32;
            int rssiVal = Integer.parseInt(rawData.substring(messageIndex, messageIndex + 2), 16) - 256;
            messageIndex += 2;
            Message message = new Message(scanTime,tag, devMac, f, rssiVal, apMac);
            messages.add(message);
        }

        return messages;

    }

    public List<LocalizeReturnVal> getLocVal(){
        List<LocalizeReturnVal> ans = new ArrayList();
        Map<String,LocalizeReturnVal> map = new HashMap<>();
        for(LocalizeReturnVal returnVal:returnVals){
            if(!map.containsKey(returnVal.getDevMac())){
                map.put(returnVal.getDevMac(),returnVal);
            }else {
                LocalizeReturnVal val = map.get(returnVal.getDevMac());
                if(val.getFrequency()==2){
                    val.setX(val.getX()*0.6+returnVal.getX()*0.4);
                    val.setY(val.getY()*0.6+returnVal.getY()*0.4);
                }else {
                    val.setX(val.getX()*0.4+returnVal.getX()*0.6);
                    val.setY(val.getY()*0.4+returnVal.getY()*0.6);
                }
            }
        }

        ans.addAll(map.values());

        return ans;
    }

    public synchronized void updateLocVal(LocalizeReturnVal localizeReturnVal){
        if(returnVals.isEmpty()){
            returnVals.add(localizeReturnVal);
            return;
        }
        for (LocalizeReturnVal item:returnVals){
            if(item.getDevMac().equals(localizeReturnVal.getDevMac())){
                if(item.getFrequency()==localizeReturnVal.getFrequency()){
                    returnVals.remove(item);
                }
            }
        }
        returnVals.add(localizeReturnVal);
    }

    public void putData(List<Message> data){
        if (dataqueue.addAll(data)){
//                log.info("add data successful:"+data.size());
        }else {
            log.info("add data failed...");
        }
    }

    public void putRecord(List<Record> record){
//        System.out.println("添加了:"+record.size()+"条");
        recordqueue.addAll(record);
    }

    public List<Message> collectData(){
        List<Message> ans = new ArrayList<>();
        dataqueue.drainTo(ans);
        return ans;
    }

    public List<Record> collectInfo(){
        List<Record> ans = new ArrayList<>();
        ans.addAll(recordqueue);
        return ans;
    }
    public void clearUpMessage(Long time){
        if(dataqueue.size()==0){
            return;
        }
        if(time==null){
            dataqueue.clear();
            return;
        }
        while (!dataqueue.isEmpty()){
            Message cur = dataqueue.peek();
            if(cur.getTime()>time){
                return;
            }
            dataqueue.poll();
        }
    }

    public void clearUpLocVal(Long time){
        if(returnVals.size()==0){
            return;
        }
        if(time==null){
            returnVals.clear();
            return;
        }
        while (!returnVals.isEmpty()){
            LocalizeReturnVal cur = returnVals.peek();
            if(cur.getUpdateTime()>time){
                return;
            }
            returnVals.poll();
        }
    }

    public void clearUpRecord(Long time){
        if(recordqueue.size()==0){
            return;
        }
        if(time==null){
            recordqueue.clear();
            return;
        }
        int num =0;
        while (!recordqueue.isEmpty()){
            Record cur = recordqueue.peek();
            if(cur.getScanTime()>time){
                System.out.println("清理了"+num+"条数据");
                return;
            }
            recordqueue.poll();
            num++;
        }

    }

    public List<Record> getAlgorithmData(Long time){
        List<Record> records = new ArrayList<>();
        for(Record record:recordqueue){
            if(record.getScanTime()>=time&&record.getUpdateTime()==null){
                records.add(record);
            }
        }
        return records;
    }


}
