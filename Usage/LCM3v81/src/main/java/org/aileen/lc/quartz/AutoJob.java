package org.aileen.lc.quartz;

import mod.quartz.QuartzInfo;
import mod.quartz.QuartzUtils;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class AutoJob implements ApplicationRunner {

    private final Scheduler scheduler;
    private static final Logger logger = LoggerFactory.getLogger(AutoJob.class);

    public AutoJob(Scheduler scheduler) {
        this.scheduler = scheduler;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        createTestJob();
        logger.info("AutoJob run");
    }

    private void createTestJob(){
        try {
            // 检查任务是否已经存在
            if (scheduler.checkExists(new JobKey("testJob"))) {
                logger.info("任务 testJob 已存在，跳过创建");
                return;
            }
        }catch ( SchedulerException e){
            logger.error("任务 testJob 创建失败", e);
        }

        QuartzInfo quartzInfo = new QuartzInfo();
        quartzInfo.setJobName("testJob");
        // 使用 LocalDateTime 创建日期（Java 8+ 推荐方式）
        LocalDateTime startDateTime = LocalDateTime.of(2025, 8, 19, 17, 29, 0);
        quartzInfo.setStartTime(Date.from(startDateTime.atZone(ZoneId.systemDefault()).toInstant()));

        LocalDateTime endDateTime = LocalDateTime.of(2025, 8, 19, 17, 35, 0);
        quartzInfo.setEndTime(Date.from(endDateTime.atZone(ZoneId.systemDefault()).toInstant()));
        //时间范围内每隔20秒执行一次
        quartzInfo.setCronExpression("*/10 * * * * ?");
        QuartzUtils.createScheduleJob(scheduler, TestLogJob.class, quartzInfo);
    }
}
