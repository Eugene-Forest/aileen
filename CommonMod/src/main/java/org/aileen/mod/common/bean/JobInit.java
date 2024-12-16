package org.aileen.mod.common.bean;

import org.aileen.mod.common.quartz.TutorJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author qaz22
 * {@code @date} 2024/11/10
 * {@code @project} tutor-selenium
 */

@Component
public class JobInit implements ApplicationRunner {

    private static final String ID = "SUMMERDAY";

    @Autowired
    private Scheduler scheduler;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        JobDetail jobDetail = JobBuilder.newJob(TutorJob.class)
                .withIdentity(ID + " 01")
                .withDescription("JobInit_")
                .storeDurably()
                .build();
        CronScheduleBuilder scheduleBuilder =
                CronScheduleBuilder.cronSchedule("0/5 * * * * ? *");
        // 创建任务触发器
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(ID + " 01Trigger")
                .withSchedule(scheduleBuilder)
                .startNow() //立即執行一次任務
                .build();
        // 手动将触发器与任务绑定到调度器内
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
