package mod.quartz;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public abstract class CountControlledJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(CountControlledJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // 获取任务的名称和组名
        JobKey jobKey = context.getJobDetail().getKey();

        // 从 JobDataMap 中获取最大执行次数设置
        int maxCount = context.getMergedJobDataMap().getInt("maxCount");
        boolean isLimited = maxCount > 0;

        // 从 JobDataMap 中获取当前已执行次数
        int currentCount = context.getMergedJobDataMap().getInt("currentCount");
        currentCount = currentCount + 1; // 增加执行次数
        logger.info("Job {} 执行第 {} 次", jobKey, currentCount);

        // 在这里执行您的业务逻辑
        doBusinessLogic(context);

        context.getJobDetail().getJobDataMap().put("currentCount", currentCount);
        if (isLimited) {
            // 检查是否达到最大执行次数
            if (currentCount >= maxCount) {
                logger.info("Job {} 已达到最大执行次数 {}, 将停止执行", jobKey, maxCount);

                try {
                    // 取消任务调度
                    context.getScheduler().deleteJob(jobKey);
                } catch (Exception e) {
                    logger.error("删除任务 {} 失败: {}", jobKey, e.getMessage());
                    throw new JobExecutionException(e);
                }
            }
        }
    }

    /**
     * 在这里实现您的具体业务逻辑
     */
    public abstract void doBusinessLogic(JobExecutionContext context);
}
