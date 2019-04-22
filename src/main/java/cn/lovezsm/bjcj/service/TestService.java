package cn.lovezsm.bjcj.service;

import cn.lovezsm.bjcj.config.GlobeConf;
import cn.lovezsm.bjcj.data.TestPageReport;
import cn.lovezsm.bjcj.entity.Message;
import cn.lovezsm.bjcj.utils.DataUtil;
import cn.lovezsm.bjcj.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.util.*;

@Service
public class TestService {
    @Autowired
    GlobeConf globeConf;

    @Autowired
    DataUtil dataUtil;

    public List<TestPageReport> separationRawFile(MultipartFile file) {
        Map<String,TestPageReport> reportMap = new HashMap<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String line = null;
            while((line=bufferedReader.readLine())!=null){
                Long scanTime = null;
                String raw = "";
                if (line.contains(":")){
                    scanTime = Long.parseLong(line.split(":")[0]);
                    raw = line.split(":")[1];
                }else {
                    raw = line;
                }
                String apMac= raw.substring(46, 58);
                if(!reportMap.containsKey(apMac)){
                    TestPageReport testPageReport = new TestPageReport();
                    reportMap.put(apMac,testPageReport);
                    testPageReport.setMac(apMac);
                }
                TestPageReport report = reportMap.get(apMac);
                List<Message> messages = dataUtil.analyzeData(raw, scanTime);
                report.setAllRawNum(report.getAllRawNum()+1);
                report.setAllMessageNum(report.getAllMessageNum()+messages.size());
                report.getMessages().addAll(messages);
                Map<String, Integer> channelCount = report.getChannelCount();
                if(messages.size()==0){
                    report.setEmptyMessageNum(report.getEmptyMessageNum()+1);
                }else {
                    Set<String> macSet = new HashSet<>();
                    for(Message message:messages){
                        macSet.add(message.getDevMac());
                        if(!channelCount.containsKey(message.getFrequency()+"")){
                            channelCount.put(message.getFrequency()+"",1);
                        }else {
                            channelCount.put(message.getFrequency()+"",channelCount.get(message.getFrequency()+"")+1);
                        }
                    }
                    report.setIndependentMacNum(report.getIndependentMacNum()+macSet.size());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(reportMap.values());
    }


    public TestPageReport getSingleMacFileReport(MultipartFile multipartFile) {
        String mac = FileUtil.getFileNameNoEx(multipartFile.getOriginalFilename());
        TestPageReport testPageReport = new TestPageReport();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()));
            String line = null;
            int allRawNum = 0;
            int allMessageNum = 0;
            int emptyMessageNum = 0;
            Map<String,Integer> macMap = new HashMap<>();
            Map<String,Integer> channelCount = new HashMap<>();
            while ((line = bufferedReader.readLine())!=null){
                allRawNum++;
                String raw = "";
                Long scanTime = null;
                if (line.contains(":")){
                    scanTime = Long.parseLong(line.split(":")[0]);
                    raw = line.split(":")[1];
                }else {
                    raw = line;
                }

                if(raw.length()<=58){
                    emptyMessageNum++;
                }else {
                    List<Message> messages = dataUtil.analyzeData(raw,scanTime);

                    testPageReport.getMessages().addAll(messages);
                    for(Message message:messages){
                        //devMac统计
                        String devMac = message.getDevMac();
//                        if(!devAllow.contains(devMac)){
//                            continue;
//                        }
                        allMessageNum++;
                        if(!macMap.containsKey(devMac)){
                            macMap.put(devMac,0);
                        }
                        macMap.put(devMac,macMap.get(devMac)+1);

                        //不同频率统计
                        if(!channelCount.containsKey(message.getFrequency()+"")){
                            channelCount.put(message.getFrequency()+"",0);
                        }
                        channelCount.put(message.getFrequency()+"",channelCount.get(message.getFrequency()+"")+1);
                    }
                }
            }
            testPageReport.setAllMessageNum(allMessageNum);
            testPageReport.setAllRawNum(allRawNum);
            testPageReport.setChannelCount(channelCount);
            testPageReport.setMac(mac);
            testPageReport.setId(globeConf.getApConf().getApId(mac)+1);
            testPageReport.setEmptyMessageNum(emptyMessageNum);
            testPageReport.setIndependentMacNum(macMap.size());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return testPageReport;
    }

}
