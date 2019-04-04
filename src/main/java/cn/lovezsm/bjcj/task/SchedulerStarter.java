package cn.lovezsm.bjcj.task;

import cn.lovezsm.bjcj.config.APConf;
import cn.lovezsm.bjcj.config.AlgorithmConf;
import cn.lovezsm.bjcj.data.FingerPrint;
import cn.lovezsm.bjcj.netty.UDPServer;
import cn.lovezsm.bjcj.utils.DataUtils;
import cn.lovezsm.bjcj.utils.LogUtil;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Component
@Order(2)
public class SchedulerStarter implements CommandLineRunner {

    @Autowired
    @Qualifier("Scheduler")
    private Scheduler scheduler;

    @Autowired
    NettyTask nettyTask;

    @Autowired
    DataUtils dataUtils;
    @Autowired
    APConf apConf;
    @Autowired
    LogUtil logUtil;
    @Autowired
    AlgorithmConf algorithmConf;
    @Autowired
    FingerPrint fingerPrint;
    @Override
    public void run(String... args) throws Exception {
        scheduler.start();

        JobDataMap jobDataMapProcessRawDataTask = new JobDataMap();
        jobDataMapProcessRawDataTask.put("dataUtils",dataUtils);
        jobDataMapProcessRawDataTask.put("apConf",apConf);
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
        jobDataMapLocationTask.put("apNum",apConf.getApnum());
        jobDataMapLocationTask.put("K",algorithmConf.getK());
        jobDataMapLocationTask.put("fingerPrint",fingerPrint);
        jobDataMapLocationTask.put("dataUtils",dataUtils);
        jobDataMapLocationTask.put("stepTime",algorithmConf.getSliding_step_time());
        Trigger triggerLocationTask = newTrigger()
                .withIdentity("LocationTask")
                .startAt(futureDate(algorithmConf.getSliding_step_time(), DateBuilder.IntervalUnit.SECOND))
                .usingJobData(jobDataMapLocationTask)
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(algorithmConf.getSliding_step_time())
                        .repeatForever())
                .build();
        scheduler.scheduleJob(newJob(LocationTask.class).withIdentity("LocationTask").build(), triggerLocationTask);

    }
}
