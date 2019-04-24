package cn.lovezsm.bjcj.task;

import cn.lovezsm.bjcj.utils.DataUtil;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class CleanOffsetTask extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
        DataUtil dataUtil = (DataUtil) dataMap.get("dataUtil");
        dataUtil.saveAndClearUpOffsetMap();
    }
}
