package org.vinci.busfinder.loader.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;


@Component
public class AgencyLoaderListener implements JobExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(AgencyLoaderListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("==========================================================");
        log.info("[Agency Job] Job started at: "+ jobExecution.getStartTime());
        log.info("[Agency Job] Status of the Job: "+jobExecution.getStatus());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("[Agency Job] Job Ended at: " + jobExecution.getEndTime());
            log.info("[Agency Job] Status of the Job: " + jobExecution.getStatus());
        }
    }
}
