package org.vinci.busfinder.loader.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;
import org.vinci.busfinder.loader.listener.AgencyLoaderListener;
import org.vinci.busfinder.model.Agency;
import org.vinci.busfinder.repository.AgencyRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class AgencyJobConfig extends BaseJobConfig {

    private static final Logger log = LoggerFactory.getLogger(AgencyJobConfig.class);

    @Value("${dbloader.config.input.agency-file-name}")
    private String inputFileName;

    @Autowired
    AgencyRepository agencyRepository;

    @Bean
    public FlatFileItemReader<Agency> agencyReader() {
        Path path = Paths.get(super.inputPath + inputFileName);
        if(!Files.exists(path))
            throw new RuntimeException("[Error] Input file not found: " + super.inputPath + inputFileName);

        return new FlatFileItemReaderBuilder<Agency>()
                .linesToSkip(1)
                .name("agency-reader")
                .resource(new FileSystemResource(super.inputPath + inputFileName))
                .delimited()
                .delimiter(",")
                .names("agency_id","agency_name","agency_url","agency_timezone","agency_lang","agency_phone","agency_fare_url")
                .fieldSetMapper(fieldSet -> {
                    Agency a = new Agency();
                    a.setAgencyId(fieldSet.readString("agency_id"));
                    a.setAgencyName(fieldSet.readString("agency_name"));
                    a.setAgencyUrl(fieldSet.readString("agency_url"));
                    a.setAgencyTimeZone(fieldSet.readString("agency_timezone"));
                    a.setAgencyLang(fieldSet.readString("agency_lang"));
                    a.setAgencyPhone(fieldSet.readString("agency_phone"));
                    a.setAgencyFareUrl(fieldSet.readString("agency_fare_url"));
                    return a;
                })
                .build();
    }

    @Bean
    public ItemWriter<Agency> agencyWriter(){
        return agencies -> {
            if (verbose) {
                agencies.forEach(a -> log.info("Saving Agency Records: " + a.toString()));
            }
            agencyRepository.saveAll(agencies);
        };
    }

    @Bean
    public Step agencyStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("load_agency_item", jobRepository)
                .<Agency,Agency>chunk(2, transactionManager)
                .reader(agencyReader())
                .writer(agencyWriter())
                .build();
    }

    @Bean
    public Job agencyJob(JobRepository jobRepository, Step agencyStep, AgencyLoaderListener listener){
        return new JobBuilder("load_agency_table", jobRepository)
                .listener(listener)
                .start(agencyStep)
                .build();
    }

}
