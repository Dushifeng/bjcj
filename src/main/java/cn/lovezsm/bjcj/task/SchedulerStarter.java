package cn.lovezsm.bjcj.task;

import cn.lovezsm.bjcj.netty.UDPServer;
import org.quartz.DateBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
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

    @Override
    public void run(String... args) throws Exception {
        nettyTask.start(16662);
    }
}
