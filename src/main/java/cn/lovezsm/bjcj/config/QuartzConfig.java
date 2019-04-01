package cn.lovezsm.bjcj.config;

import org.quartz.Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfig {

    @Bean(name = "SchedulerFactory")
    public SchedulerFactoryBean schedulerFactoryBean(){
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        return factoryBean;
    }

    @Bean(name = "Scheduler")
    public Scheduler scheduler(){
        return schedulerFactoryBean().getScheduler();
    }

}
