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
import org.vinci.busfinder.dto.CalendarDto;
import org.vinci.busfinder.loader.listener.CalendarLoaderListener;
import org.vinci.busfinder.repository.CalendarRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Configuration
public class CalendarJobConfig extends BaseJobConfig {

    private static final Logger log = LoggerFactory.getLogger(CalendarJobConfig.class);

    @Value("${dbloader.config.input.calendar-file-name}")
    private String inputFileName;

    @Autowired
    CalendarRepository calendarRepository;

    @Bean
    public FlatFileItemReader<CalendarDto> calendarReader() {
        Path path = Paths.get(super.inputPath + inputFileName);
        if(!Files.exists(path))
            throw new RuntimeException("[Error] Input file not found: " + super.inputPath + inputFileName);

        return new FlatFileItemReaderBuilder<CalendarDto>()
                .linesToSkip(1)
                .name("calendar-reader")
                .resource(new FileSystemResource(super.inputPath + inputFileName))
                .delimited()
                .delimiter(",")
                .names("service_id","monday","tuesday","wednesday","thursday","friday","saturday","sunday","start_date","end_date")
                .fieldSetMapper(fieldSet -> {
                    CalendarDto c = new CalendarDto();
                    c.setServiceId(fieldSet.readString("service_id"));
                    c.setMonday(fieldSet.readBoolean("monday"));
                    c.setTuesday(fieldSet.readBoolean("tuesday"));
                    c.setWednesday(fieldSet.readBoolean("wednesday"));
                    c.setThursday(fieldSet.readBoolean("thursday"));
                    c.setFriday(fieldSet.readBoolean("friday"));
                    c.setSaturday(fieldSet.readBoolean("saturday"));
                    c.setSunday(fieldSet.readBoolean("sunday"));
                    c.setStartDate(fieldSet.readString("start_date"));
                    c.setEndDate(fieldSet.readString("end_date"));
                    return c;
                })
                .build();
    }

    @Bean
    public ItemWriter<CalendarDto> calendarWriter(){
        return calendars -> {
            calendars.forEach(c -> log.info("Saving Calendar Records: " + c.toString()));
            calendarRepository.saveAllRaw((List<CalendarDto>) calendars.getItems());
        };
    }

    @Bean
    public Step calendarStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("load_calendar_item", jobRepository)
                .<CalendarDto,CalendarDto>chunk(2, transactionManager)
                .reader(calendarReader())
                .writer(calendarWriter())
                .build();
    }

    @Bean
    public Job calendarJob(JobRepository jobRepository, Step calendarStep, CalendarLoaderListener listener){
        return new JobBuilder("load_calendar_table", jobRepository)
                .listener(listener)
                .start(calendarStep)
                .build();
    }

}
