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
import org.vinci.busfinder.loader.listener.StopTimesLoaderListener;
import org.vinci.busfinder.model.StopTimes;
import org.vinci.busfinder.model.key.StopTimesKey;
import org.vinci.busfinder.repository.StopTimesRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class StopTimesJobConfig extends BaseJobConfig {

    private static final Logger log = LoggerFactory.getLogger(StopTimesJobConfig.class);

    @Value("${dbloader.config.input.stoptimes-file-name}")
    private String inputFileName;

    @Autowired
    StopTimesRepository stopTimesRepository;

    @Bean
    public FlatFileItemReader<StopTimes> stopTimesReader() {
        Path path = Paths.get(super.inputPath + inputFileName);
        if(!Files.exists(path))
            throw new RuntimeException("[Error] Input file not found: " + super.inputPath + inputFileName);

        return new FlatFileItemReaderBuilder<StopTimes>()
                .linesToSkip(1)
                .name("stoptimes-reader")
                .resource(new FileSystemResource(super.inputPath + inputFileName))
                .delimited()
                .delimiter(",")
                .names("trip_id","arrival_time","departure_time","stop_id","stop_sequence","stop_headsign","pickup_type","drop_off_type","shape_dist_traveled")
                .fieldSetMapper(fieldSet -> {
                    StopTimes st = new StopTimes();
                    StopTimesKey stk = new StopTimesKey();
                    stk.setStopId(fieldSet.readInt("stop_id"));
                    stk.setTripId(fieldSet.readInt("trip_id"));
                    stk.setStopSeq(fieldSet.readInt("stop_sequence"));

                    st.setId(stk);
                    st.setArrivalTime(fieldSet.readString("arrival_time"));
                    st.setDepartureTime(fieldSet.readString("departure_time"));
                    st.setStopHeadsign(fieldSet.readString("stop_headsign"));
                    st.setPickupType(fieldSet.readInt("pickup_type"));
                    st.setDropOffType(fieldSet.readInt("drop_off_type"));
                    st.setShapeDistTraveled(fieldSet.readString("shape_dist_traveled"));

                    return st;
                })
                .build();
    }

    @Bean
    public ItemWriter<StopTimes> stopTimesWriter(){
        return stopTimes -> {
            stopTimes.forEach(a -> log.info("Saving StopTimes Records: " + a.toString()));
            stopTimesRepository.saveAll(stopTimes);
        };
    }

    @Bean
    public Step stopTimesStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("load_stoptimes_item", jobRepository)
                .<StopTimes,StopTimes>chunk(2, transactionManager)
                .reader(stopTimesReader())
                .writer(stopTimesWriter())
                .build();
    }

    @Bean
    public Job stopTimesJob(JobRepository jobRepository, Step stopTimesStep, StopTimesLoaderListener listener){
        return new JobBuilder("load_stoptimes_table", jobRepository)
                .listener(listener)
                .start(stopTimesStep)
                .build();
    }

}
