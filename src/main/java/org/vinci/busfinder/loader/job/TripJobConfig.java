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
import org.vinci.busfinder.loader.listener.TripLoaderListener;
import org.vinci.busfinder.model.Trip;
import org.vinci.busfinder.repository.TripRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class TripJobConfig extends BaseJobConfig {

    private static final Logger log = LoggerFactory.getLogger(TripJobConfig.class);

    @Value("${dbloader.config.input.trip-file-name}")
    private String inputFileName;

    @Autowired
    TripRepository tripRepository;

    @Bean
    public FlatFileItemReader<Trip> tripReader() {
        Path path = Paths.get(super.inputPath + inputFileName);
        if(!Files.exists(path))
            throw new RuntimeException("[Error] Input file not found: " + super.inputPath + inputFileName);

        return new FlatFileItemReaderBuilder<Trip>()
                .linesToSkip(1)
                .name("trip-reader")
                .resource(new FileSystemResource(super.inputPath + inputFileName))
                .delimited()
                .delimiter(",")
                .names("route_id","service_id","trip_id","trip_headsign","trip_short_name","direction_id","block_id",
                        "shape_id","wheelchair_accessible")
                .fieldSetMapper(fieldSet -> {
                    Trip t = new Trip();
                    t.setRouteId(fieldSet.readInt("route_id"));
                    t.setServiceId(fieldSet.readString("service_id"));
                    t.setTripId(fieldSet.readInt("trip_id"));
                    t.setTripHeadsign(fieldSet.readString("trip_headsign"));
                    t.setTripShortName(fieldSet.readString("trip_short_name"));
                    t.setDirectionId(fieldSet.readString("direction_id"));
                    t.setBlockId(fieldSet.readString("block_id"));
                    t.setShapeId(fieldSet.readString("shape_id"));
                    t.setWheelchairAccessible(fieldSet.readString("wheelchair_accessible"));
                    return t;
                })
                .build();
    }

    @Bean
    public ItemWriter<Trip> tripWriter(){
        return trips -> {
            if (verbose) {
                trips.forEach(s -> log.info("Saving Trip Records: " + s.toString()));
            } else {
                log.info("Saving Trip Records...");
            }
            tripRepository.saveAll(trips);
        };
    }

    @Bean
    public Step tripStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("load_trip_item", jobRepository)
                .<Trip,Trip>chunk(2, transactionManager)
                .reader(tripReader())
                .writer(tripWriter())
                .build();
    }

    @Bean
    public Job tripJob(JobRepository jobRepository, Step tripStep, TripLoaderListener listener){
        return new JobBuilder("load_trip_table", jobRepository)
                .listener(listener)
                .start(tripStep)
                .build();
    }

}
