package cn.lovezsm.bjcj.task;

import cn.lovezsm.bjcj.utils.DataUtil;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class CleanRecordTask extends QuartzJobBean {
    @Autowired
    DataUtil dataUtil;

    private int time;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
        time = dataMap.getInt("time");
        dataUtil.clearUpRecord(System.currentTimeMillis() - time * 1000);
    }
}
