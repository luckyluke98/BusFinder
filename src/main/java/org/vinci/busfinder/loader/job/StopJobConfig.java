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
import org.vinci.busfinder.dto.ShapeDto;
import org.vinci.busfinder.dto.StopDto;
import org.vinci.busfinder.loader.listener.ShapeLoaderListener;
import org.vinci.busfinder.loader.listener.StopLoaderListener;
import org.vinci.busfinder.repository.ShapeRepository;
import org.vinci.busfinder.repository.StopRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Configuration
public class StopJobConfig extends BaseJobConfig {

    private static final Logger log = LoggerFactory.getLogger(StopJobConfig.class);

    @Value("${dbloader.config.input.stop-file-name}")
    private String inputFileName;

    @Autowired
    StopRepository stopRepository;

    @Bean
    public FlatFileItemReader<StopDto> stopReader() {
        Path path = Paths.get(super.inputPath + inputFileName);
        if(!Files.exists(path))
            throw new RuntimeException("[Error] Input file not found: " + super.inputPath + inputFileName);

        return new FlatFileItemReaderBuilder<StopDto>()
                .linesToSkip(1)
                .name("stop-reader")
                .resource(new FileSystemResource(super.inputPath + inputFileName))
                .delimited()
                .delimiter(",")
                .names("stop_id","stop_code","stop_name","stop_desc","stop_lat","stop_lon","zone_id","stop_url",
                        "location_type","parent_station","stop_timezone","wheelchair_boarding")
                .fieldSetMapper(fieldSet -> {
                    StopDto s = new StopDto();
                    s.setStopId(fieldSet.readInt("stop_id"));
                    s.setStopCode(fieldSet.readInt("stop_code"));
                    s.setStopName(fieldSet.readString("stop_name"));
                    s.setStopDescription(fieldSet.readString("stop_desc"));
                    s.setStopLat(fieldSet.readString("stop_lat"));
                    s.setStopLong(fieldSet.readString("stop_lon"));
                    s.setZoneId(fieldSet.readString("zone_id"));
                    s.setStopUrl(fieldSet.readString("stop_url"));
                    s.setLocationType(fieldSet.readString("location_type"));
                    s.setParentStation(fieldSet.readString("parent_station"));
                    s.setStopTimezone(fieldSet.readString("stop_timezone"));
                    s.setWheelchairBoarding(fieldSet.readString("wheelchair_boarding"));
                    return s;
                })
                .build();
    }

    @Bean
    public ItemWriter<StopDto> stopWriter(){
        return stops -> {
            stops.forEach(s -> log.info("Saving Stop Records: " + s.toString()));
            stopRepository.saveAllRaw((List<StopDto>) stops.getItems());
        };
    }

    @Bean
    public Step stopStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("load_stop_item", jobRepository)
                .<StopDto,StopDto>chunk(2, transactionManager)
                .reader(stopReader())
                .writer(stopWriter())
                .build();
    }

    @Bean
    public Job stopJob(JobRepository jobRepository, Step stopStep, StopLoaderListener listener){
        return new JobBuilder("load_stop_table", jobRepository)
                .listener(listener)
                .start(stopStep)
                .build();
    }

}
