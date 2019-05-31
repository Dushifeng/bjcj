package cn.lovezsm.bjcj.service;

import cn.lovezsm.bjcj.algorithm.CalculateDeviceDensity;
import cn.lovezsm.bjcj.algorithm.LocalizeByFingerPrint;

import cn.lovezsm.bjcj.config.GlobeConf;
import cn.lovezsm.bjcj.entity.CalculateDeviceDensityReturnVal;
import cn.lovezsm.bjcj.entity.LocalizeReturnVal;
import cn.lovezsm.bjcj.task.*;
import cn.lovezsm.bjcj.utils.DataUtil;
import cn.lovezsm.bjcj.data.FingerPrint;

import cn.lovezsm.bjcj.utils.LogUtil;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.util.List;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;


@Service
@Scope("singleton")
public class LocationService {
    @Autowired
    @Qualifier("Scheduler")
    private Scheduler scheduler;
    @Autowired
    NettyTask nettyTask;
    @Autowired
    DataUtil dataUtil;
    @Autowired
    LogUtil logUtil;
    @Autowired
    GlobeConf globeConf;
    JobDataMap jobDataMapProcessRawDataTask;

    @Autowired
    CalculateDeviceDensity calculateDeviceDensity;

    @PostConstruct
    public void init(){

    }

    public synchronized void open() throws SchedulerException {


        jobDataMapProcessRawDataTask = new JobDataMap();
        jobDataMapProcessRawDataTask.put("dataUtils", dataUtil);
        jobDataMapProcessRawDataTask.put("apConf",globeConf.getApConf());
        jobDataMapProcessRawDataTask.put("logUtil",logUtil);
        Trigger triggerProcessRawDataTask = newTrigger()
                .withIdentity("ProcessRawDataTask")
                .startAt(futureDate(1, DateBuilder.IntervalUnit.SECOND))
                .usingJobData(jobDataMapProcessRawDataTask)
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(1)
                        .repeatForever())
                .build();
        scheduler.scheduleJob(newJob(ProcessRawDataTask.class).withIdentity("ProcessRawDataTask").build(), triggerProcessRawDataTask);

        JobDataMap jobDataMapLocationTask = new JobDataMap();
        jobDataMapLocationTask.put("apConf",globeConf.getApConf());
        jobDataMapLocationTask.put("algorithmConf",globeConf.getAlgorithmConf());
        jobDataMapLocationTask.put("fingerPrints",globeConf.getFingerPrints());
        jobDataMapLocationTask.put("dataUtils", dataUtil);
        jobDataMapLocationTask.put("gridMap",globeConf.getGridMap());
        jobDataMapLocationTask.put("log",logUtil);
        Trigger triggerLocationTask = newTrigger()
                .withIdentity("LocationTask")
                .startAt(futureDate(globeConf.getAlgorithmConf().getSliding_step_time(), DateBuilder.IntervalUnit.SECOND))
                .usingJobData(jobDataMapLocationTask)
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(globeConf.getAlgorithmConf().getSliding_step_time())
                        .repeatForever())
                .build();
        scheduler.scheduleJob(newJob(LocationTask.class).withIdentity("LocationTask").build(), triggerLocationTask);



        JobDataMap jobDataMapCleanUpTask = new JobDataMap();
        jobDataMapCleanUpTask.put("time",globeConf.getAlgorithmConf().getSliding_window_time());
        jobDataMapCleanUpTask.put("dataUtil", dataUtil);
        Trigger triggerCleanUpTask = newTrigger()
                .withIdentity("CleanUp")
                .startAt(futureDate(globeConf.getAlgorithmConf().getSliding_window_time(), DateBuilder.IntervalUnit.SECOND))
                .usingJobData(jobDataMapCleanUpTask)
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(1)
                        .repeatForever())
                .build();
        scheduler.scheduleJob(newJob(CleanRecordTask.class).withIdentity("CleanUpTask").build(), triggerCleanUpTask);



        JobDataMap jobDataMapOffsetTask = new JobDataMap();
        jobDataMapOffsetTask.put("dataUtil", dataUtil);
        Trigger triggerOffsetTask = newTrigger()
                .withIdentity("Offset")
                .startAt(futureDate(1, DateBuilder.IntervalUnit.HOUR))
                .usingJobData(jobDataMapCleanUpTask)
                .withSchedule(simpleSchedule()
                        .withIntervalInHours(1)
                        .repeatForever())
                .build();
        scheduler.scheduleJob(newJob(CleanOffsetTask.class).withIdentity("OffsetTask").build(), triggerOffsetTask);


    }


    public CalculateDeviceDensityReturnVal calculateDeviceDensity(List<LocalizeReturnVal> localizeReturnVals){
        return calculateDeviceDensity.doCalculate(localizeReturnVals);
    }

}
