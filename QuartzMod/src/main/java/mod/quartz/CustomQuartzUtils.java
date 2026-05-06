package mod.quartz;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

/**
 * 定时任务工具类
 */
public class CustomQuartzUtils {

    private static final Logger logger = LoggerFactory.getLogger(CustomQuartzUtils.class);

    /**
     * 创建定时任务
     *
     * @param scheduler  定时器
     * @param customQuartzInfo 定时任务信息
     */
    public static void createScheduleJob(Scheduler scheduler, Class<? extends Job> jobClass, CustomQuartzInfo customQuartzInfo) {
        try {
            //创建定时任务信息
            JobBuilder jobBuilder = JobBuilder.newJob(jobClass)
                    .withIdentity(customQuartzInfo.getJobName(), customQuartzInfo.getGroupName());
            //设置定时任务执行方式
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(customQuartzInfo.getCronExpression()).withMisfireHandlingInstructionDoNothing();
            //触发
            TriggerBuilder<CronTrigger> triggerBuilder = TriggerBuilder.newTrigger()
                    .withIdentity(customQuartzInfo.getJobName(), customQuartzInfo.getGroupName())
                    .withSchedule(scheduleBuilder);
            // 设置开始时间（如果有的话）
            if (customQuartzInfo.getStartTime() != null) {
                triggerBuilder.startAt(customQuartzInfo.getStartTime());
            }
            // 设置结束时间（如果有的话）
            if (customQuartzInfo.getEndTime() != null) {
                triggerBuilder.endAt(customQuartzInfo.getEndTime());
            }
            //job数据
            Map<String, String> jobDataMap = customQuartzInfo.getJobDataMap();
            if (jobDataMap != null && !jobDataMap.isEmpty()) {
                for (Map.Entry<String, String> entry : jobDataMap.entrySet()) {
                    jobBuilder.usingJobData(entry.getKey(), entry.getValue());
                }
            }
            jobBuilder.usingJobData("currentCount", 0);
            //add count
            triggerBuilder.usingJobData("maxCount", Optional.ofNullable(customQuartzInfo.getCount()).orElse(-1));
            JobDetail jobDetail = jobBuilder.build();
            CronTrigger trigger = triggerBuilder.build();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            logger.error("createScheduleJob error: " + e.getMessage());
        }
    }

    /**
     * 更新定时任务
     *
     * @param scheduler  定时器
     * @param customQuartzInfo 定时任务信息
     */
    public static void updateScheduleJob(Scheduler scheduler, CustomQuartzInfo customQuartzInfo) {
        try {
            // 获取任务对应的触发器
            TriggerKey triggerKey = TriggerKey.triggerKey(customQuartzInfo.getJobName(), customQuartzInfo.getGroupName());
            //设置定时任务执行方式
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(customQuartzInfo.getCronExpression())
                    .withMisfireHandlingInstructionDoNothing();
            //重新构建任务触发器
            TriggerBuilder<CronTrigger> triggerBuilder = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(scheduleBuilder);
            // 设置开始时间（如果有的话）
            if (customQuartzInfo.getStartTime() != null) {
                triggerBuilder.startAt(customQuartzInfo.getStartTime());
            }
            // 设置结束时间（如果有的话）
            if (customQuartzInfo.getEndTime() != null) {
                triggerBuilder.endAt(customQuartzInfo.getEndTime());
            }
            triggerBuilder.usingJobData("maxCount", Optional.ofNullable(customQuartzInfo.getCount()).orElse(-1));
            CronTrigger trigger = triggerBuilder.build();
            //重置Job
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            logger.error("updateScheduleJob error: " + e.getMessage());
        }
    }

    /**
     * 删除定时任务
     *
     * @param scheduler  定时器
     * @param customQuartzInfo 定时任务信息
     */
    public static void deleteScheduleJob(Scheduler scheduler, CustomQuartzInfo customQuartzInfo) {
        try {
            scheduler.deleteJob(new JobKey(customQuartzInfo.getJobName(), customQuartzInfo.getGroupName()));
        } catch (SchedulerException e) {
            logger.error("deleteScheduleJob error: " + e.getMessage());
        }
    }
}
