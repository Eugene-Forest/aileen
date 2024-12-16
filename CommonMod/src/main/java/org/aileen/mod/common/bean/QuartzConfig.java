package org.aileen.mod.common.bean;


import org.aileen.mod.common.quartz.TutorJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 定时任务描述及任务触发规则
 * @author qaz22
 * {@code @date} 2024/11/10
 * {@code @project} tutor-selenium
 */
@Configuration
public class QuartzConfig {
    private static final String ID = "TUTOR_JOB";

    @Bean
    public JobDetail jobDetail1() {
        return JobBuilder.newJob(TutorJob.class)
                .withIdentity(ID + "_01")
                .withDescription("QuartzConfig_")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger trigger1() {
        // 简单的调度计划的构造器
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(5) // 频率
                .repeatForever(); // 次数

        return TriggerBuilder.newTrigger()
                .forJob(jobDetail1())
                .withIdentity(ID + " 01Trigger")
                .withSchedule(scheduleBuilder)
                .build();
    }


}
