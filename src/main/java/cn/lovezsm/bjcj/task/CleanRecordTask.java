package cn.lovezsm.bjcj.task;

import cn.lovezsm.bjcj.utils.DataUtils;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class CleanRecordTask extends QuartzJobBean {
    @Autowired
    DataUtils dataUtils;

    private int time;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
        time = dataMap.getInt("time");
        dataUtils.clearUpRecord(System.currentTimeMillis() - time * 1000);
    }
}
