package org.vinci.busfinder.loader.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class TripLoaderListener implements JobExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(AgencyLoaderListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("==========================================================");
        log.info("[Trip Job] Job started at: "+ jobExecution.getStartTime());
        log.info("[Trip Job] Status of the Job: "+jobExecution.getStatus());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("[Trip Job] Job Ended at: "+ jobExecution.getEndTime());
        log.info("[Trip Job] Status of the Job: "+jobExecution.getStatus());
    }
}