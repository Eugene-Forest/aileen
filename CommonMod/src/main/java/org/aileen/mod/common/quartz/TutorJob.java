package org.aileen.mod.common.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *  定时任务的定义
 * @author qaz22
 * {@code @date} 2024/11/10
 * {@code @project} tutor-selenium
 */
@Slf4j
public class TutorJob extends QuartzJobBean {


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        String now = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
        log.warn(context.getJobDetail().getDescription() + " --当前的时间: " + now);
    }
}

