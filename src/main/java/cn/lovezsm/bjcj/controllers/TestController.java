package cn.lovezsm.bjcj.controllers;



import cn.lovezsm.bjcj.config.AlgorithmConf;
import cn.lovezsm.bjcj.repository.APConfRepository;
import cn.lovezsm.bjcj.repository.AlgorithmRepository;
import cn.lovezsm.bjcj.repository.LogRepository;
import cn.lovezsm.bjcj.repository.MessageRepository;
import org.bson.types.ObjectId;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import web.ConfData;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Controller
public class TestController {

    @Autowired
    APConfRepository apConfRepository;
    @Autowired
    AlgorithmRepository algorithmRepository;
    @Autowired
    LogRepository logRepository;
    @Autowired
    MessageRepository messageRepository;

    Map<String, MongoRepository> repositoryMap;

    @PostConstruct
    public void init(){
        repositoryMap = new HashMap<>();
        repositoryMap.put("apConfRepository",apConfRepository);
        repositoryMap.put("algorithmRepository",algorithmRepository);
        repositoryMap.put("logRepository",logRepository);
        repositoryMap.put("messageRepository",messageRepository);
    }

    @Autowired
    @Qualifier("Scheduler")
    private Scheduler scheduler;


    @GetMapping("update")
    @ResponseBody
    public void update(List<ConfData> data){


    }




    @GetMapping("/")
    public String root(){
        return "starter";
    }

//    @GetMapping("start")
//    @ResponseBody
//    public void start(){
//        System.out.println("start...");
//        try {
//            scheduler.start();
//            Trigger trigger = newTrigger()
//                    .withIdentity("t1")
//                    .startAt(futureDate(5, DateBuilder.IntervalUnit.SECOND))
//                    .withSchedule(simpleSchedule()
//                            .withIntervalInSeconds(5)
//                            .repeatForever())
//                    .build();
////          scheduler.scheduleJob(newJob(SchedulerTask.class).withIdentity("t1").build(), trigger);
//
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//        }
//    }

    //Simple Trigger

    @GetMapping("stop")
    @ResponseBody
    public void stop(){
        System.out.println("stop...");
        try {
            scheduler.deleteJob(new JobKey("t1"));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
//
//    /**
//     * getGridList
//     *
//     */
//    int fingerPrintNo =1;
//
//    String gridStr = "1 21 30 43 48 60 93 99 110 120 154 170 173 176 183 187 202 215 229 101 97 48 181 232";
//    List<FloorPlan<String, String>> infos = new ArrayList<>();
//    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//    Date startTime;
//    Date endTime;
//
//    String[] arr;
//    @Autowired
//    FingerPrintConf fingerPrint;
//    {
//
//
//        arr = gridStr.split(" ");
//        for (String s : arr) {
//            FloorPlan<String, String> map = new HashMap<>();
//            map.put("key", s);
//            map.put("value", "0");
//            infos.add(map);
//        }
//
//
//    }
//
//    @GetMapping("/")
//    public String index() {
//        System.out.println("come..");
//        return "test2";
//    }
//
//    @ResponseBody
//    @GetMapping("/getGridInfo")
//    public List<FloorPlan<String, String>> getGridInfo() {
//        return infos;
//    }
//
//    @ResponseBody
//    @GetMapping("/modifyLattId")
//    public Integer modifyLattId(int id) {
//        UDPServerHandler.latticeId = id;
//        System.out.println("晶格id改为:" + UDPServerHandler.latticeId);
//        return UDPServerHandler.latticeId;
//    }
//
//    @ResponseBody
//    @GetMapping("/start")
//    public boolean start() {
//        System.out.println("晶格id开始:" + UDPServerHandler.latticeId);
//        UDPServerHandler.startTag = true;
//        startTime = new Date();
//
////        try {
////            FileWriter fileWriter = new FileWriter("log.log", true);
////            fileWriter.append(dateFormat.format(new Date()) + "\t晶格id开始:" + UDPServerHandler.latticeId + "\r\n");
////            fileWriter.close();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//
//        return true;
//    }
//
//    @Autowired
//    DataUtils dateUtils;
//
//    @ResponseBody
//    @GetMapping("/stop")
//    public boolean stop() throws IOException {
//
//        if(fingerPrint.getAvgPath().equals("C:/data/Fingerprint_avg.dat")){
//            fingerPrintNo=1;
//        }else {
//            fingerPrintNo=2;
//        }
//
//        System.out.println("晶格id结束:" + UDPServerHandler.latticeId);
//        endTime = new Date();
//
//        List<LocalizeReturnVal> culList = new ArrayList<>();
//        dateUtils.returnVals.drainTo(culList);
//        dateUtils.returnVals.clear();
//
//        Pos girdPos = new Pos(fingerPrint.getPosX()[UDPServerHandler.latticeId-1],fingerPrint.getPosY()[UDPServerHandler.latticeId-1]);
//
//
//        FloorPlan<String,List<LocalizeReturnVal>> recs = new HashMap<>();
//        for(LocalizeReturnVal localize:culList){
////            culPoslist.add(new Pos(localize.getX(),localize.getY()));
////            sum += DataUtils.euclideanDistance(girdPos.getX(),girdPos.getY(),localize.getX(),localize.getY());
//            if(!recs.containsKey(localize.getDevMac())){
//                List<LocalizeReturnVal> vals = new ArrayList<>();
//                recs.put(localize.getDevMac(),vals);
//            }
//            recs.get(localize.getDevMac()).add(localize);
//        }
//        FileWriter fileWriter = new FileWriter(UDPServerHandler.latticeId+".log",true);
//        for(FloorPlan.Entry<String,List<LocalizeReturnVal>> entry:recs.entrySet()){
//            List<LocalizeReturnVal> vals = entry.getValue();
//            List<Pos> culPoslist = new ArrayList<>();
//            float error = 0f;
//            float sum = 0f;
//            for(LocalizeReturnVal val:vals){
//                culPoslist.add(new Pos(val.getX(),val.getY()));
//                sum+=DataUtils.euclideanDistance(girdPos.getX(),girdPos.getY(),val.getX(),val.getY());
//            }
//            error = sum/vals.size();
//            TestOutputCase testOutputCase = new TestOutputCase(fingerPrintNo,10,UDPServerHandler.latticeId,girdPos,culPoslist,startTime,endTime,error,entry.getKey());
//            String content = JSON.toJSONString(testOutputCase);
//            fileWriter.append(content);
//        }
//        fileWriter.close();
//
//
////        try {
////            FileWriter fileWriter = new FileWriter("log.log", true);
////            fileWriter.append(dateFormat.format(new Date()) + "\t晶格id结束:" + UDPServerHandler.latticeId + "\r\n");
////            fileWriter.close();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//
//        UDPServerHandler.startTag = false;
//
//        for (FloorPlan<String, String> map : infos) {
//            if (map.get("key").equals(UDPServerHandler.latticeId + "")) {
//                map.put("key", UDPServerHandler.latticeId + "");
//                map.put("value", "1");
//            }
//        }
//        return true;
//    }


}
