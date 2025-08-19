package mod.quartz;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 定时任务工具类
 */
public class QuartzUtils {

    private static final Logger logger = LoggerFactory.getLogger(QuartzUtils.class);

    /**
     * 创建定时任务
     *
     * @param scheduler  定时器
     * @param quartzInfo 定时任务信息
     */
    public static void createScheduleJob(Scheduler scheduler, Class<? extends Job> jobClass, QuartzInfo quartzInfo) {
        try {
            //创建定时任务信息
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(quartzInfo.getJobName()).build();
            //设置定时任务执行方式
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzInfo.getCronExpression()).withMisfireHandlingInstructionDoNothing();
            //触发
            TriggerBuilder<CronTrigger> triggerBuilder = TriggerBuilder.newTrigger().withIdentity(quartzInfo.getJobName()).withSchedule(scheduleBuilder);
            // 设置开始时间（如果有的话）
            if (quartzInfo.getStartTime() != null) {
                triggerBuilder.startAt(quartzInfo.getStartTime());
            }
            // 设置结束时间（如果有的话）
            if (quartzInfo.getEndTime() != null) {
                triggerBuilder.endAt(quartzInfo.getEndTime());
            }
            //job数据
            Map<String, String> jobDataMap = quartzInfo.getJobDataMap();
            if (jobDataMap != null && !jobDataMap.isEmpty()) {
                for (Map.Entry<String, String> entry : jobDataMap.entrySet()) {
                    triggerBuilder.usingJobData(entry.getKey(), entry.getValue());
                }
            }
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
     * @param quartzInfo 定时任务信息
     */
    public static void updateScheduleJob(Scheduler scheduler, QuartzInfo quartzInfo) {
        try {
            // 获取任务对应的触发器
            TriggerKey triggerKey = TriggerKey.triggerKey(quartzInfo.getJobName());
            //设置定时任务执行方式
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzInfo.getCronExpression())
                    .withMisfireHandlingInstructionDoNothing();
            //重新构建任务触发器
            TriggerBuilder<CronTrigger> triggerBuilder = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(scheduleBuilder);
            // 设置开始时间（如果有的话）
            if (quartzInfo.getStartTime() != null) {
                triggerBuilder.startAt(quartzInfo.getStartTime());
            }
            // 设置结束时间（如果有的话）
            if (quartzInfo.getEndTime() != null) {
                triggerBuilder.endAt(quartzInfo.getEndTime());
            }
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
     * @param quartzInfo 定时任务信息
     */
    public static void deleteScheduleJob(Scheduler scheduler, QuartzInfo quartzInfo) {
        try {
            scheduler.deleteJob(new JobKey(quartzInfo.getJobName()));
        } catch (SchedulerException e) {
            logger.error("deleteScheduleJob error: " + e.getMessage());
        }
    }
}
