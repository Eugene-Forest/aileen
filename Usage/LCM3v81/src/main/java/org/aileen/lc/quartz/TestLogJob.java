package org.aileen.lc.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLogJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(TestLogJob.class);
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.warn("TestLogJob");
    }
}
