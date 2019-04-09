package cn.lovezsm.bjcj.utils;

import cn.lovezsm.bjcj.config.APConf;
import cn.lovezsm.bjcj.config.GlobeConf;
import cn.lovezsm.bjcj.config.LogConf;
import cn.lovezsm.bjcj.entity.LocalizeReturnVal;
import cn.lovezsm.bjcj.entity.Message;
import cn.lovezsm.bjcj.entity.Record;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
@Scope("singleton")
public class LogUtil {

    @Autowired
    LogConf logConf;


    public synchronized void log(String content, String fileName){

        if(logConf.isOpen() == false) return;
        File file = new File(logConf.getDicPath()+File.separator+fileName);
        FileWriter fileWriter = null;
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            fileWriter = new FileWriter(file,true);
            fileWriter.append(content);
            fileWriter.append("\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void logRaw(List<Message> messages){
//        if(logConf.isOpen() == false) return;
//        for (Message message:messages){
////                rawFW.append(message.getTime()+","+message.getApMac()+","+message.getDevMac()+",-1,"+(apConf.getApId(message.getApMac())+1)+","+message.getFrequency()+","+message.getRssi());
//            //(1553134245,'70:47:60:02:A3:0C','24:09:95:B2:90:9C',1,6,-66),
//            String content = "("+message.getTime()+",'"+message.getApMac()+"','"+message.getDevMac()+"',"+logConf.getGridId()+","+message.getFrequency()+","+message.getRssi()+"),";
//            this.log(content,logConf.getRawPath());
//        }
    }

    public synchronized void logRecord(List<Record> records){
//        if(logConf.isOpen() == false) return;
//        for (Record record:records){
//            this.log(record.toString(),logConf.getRecordPath());
//        }
    }


    public synchronized void logResult(List<LocalizeReturnVal> returnVals){
//        if(logConf.isOpen() == false) return;
//        String json = JSON.toJSONString(returnVals);
//        this.log(json,logConf.getLocResultPath());
    }

    public LogConf getLogConf() {
        return logConf;
    }
}
