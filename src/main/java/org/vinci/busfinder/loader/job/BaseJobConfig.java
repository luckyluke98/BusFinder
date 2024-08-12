package org.vinci.busfinder.loader.job;

import org.springframework.beans.factory.annotation.Value;

public class BaseJobConfig {

    @Value("${dbloader.config.input-path}")
    protected String inputPath;

    @Value("${dbloader.config.verbose}")
    protected Boolean verbose;



}
