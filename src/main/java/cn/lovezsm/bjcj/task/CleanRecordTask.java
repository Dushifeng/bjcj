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

    private int time;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();

        time = dataMap.getInt("time");
        DataUtil dataUtil = (DataUtil) dataMap.get("dataUtil");
        long t = System.currentTimeMillis() - (time+1) * 1000;
        dataUtil.clearUpMessage(t);
        dataUtil.clearUpRecord(t);
        dataUtil.clearUpLocVal(t);

    }
}
