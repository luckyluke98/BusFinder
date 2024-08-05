package org.vinci.busfinder.loader;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@PropertySource("classpath:db_loader.properties")
public class DbFileLoader implements CommandLineRunner {

    @Value("${dbloader.config.load}")
    private Boolean load;

    @Autowired
    private JobLauncher jobLauncher;

    @Qualifier("agencyJob")
    @Autowired
    private Job load_agency_table;

    @Qualifier("calendarJob")
    @Autowired
    private Job load_calendar_table;

    @Qualifier("routeJob")
    @Autowired
    private Job load_route_table;

    @Qualifier("shapeJob")
    @Autowired
    private Job load_shape_table;

    @Qualifier("stopJob")
    @Autowired
    private Job load_stop_table;

    @Override
    public void run(String... args) throws Exception {
        if (Boolean.TRUE.equals(load)) {
            jobLauncher.run(load_agency_table, new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters());
            jobLauncher.run(load_calendar_table, new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters());
            jobLauncher.run(load_route_table, new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters());
            //jobLauncher.run(load_shape_table, new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters());
            jobLauncher.run(load_stop_table, new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters());
        }
    }
}
