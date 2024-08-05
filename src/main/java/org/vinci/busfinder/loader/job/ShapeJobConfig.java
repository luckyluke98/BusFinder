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
import org.vinci.busfinder.loader.listener.ShapeLoaderListener;
import org.vinci.busfinder.repository.ShapeRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Configuration
public class ShapeJobConfig extends BaseJobConfig {
    private static final Logger log = LoggerFactory.getLogger(ShapeJobConfig.class);

    @Value("${dbloader.config.input.shape-file-name}")
    private String inputFileName;

    @Autowired
    ShapeRepository shapeRepository;

    @Bean
    public FlatFileItemReader<ShapeDto> shapeReader() {
        Path path = Paths.get(super.inputPath + inputFileName);
        if(!Files.exists(path))
            throw new RuntimeException("[Error] Input file not found: " + super.inputPath + inputFileName);

        return new FlatFileItemReaderBuilder<ShapeDto>()
                .linesToSkip(1)
                .name("shape-reader")
                .resource(new FileSystemResource(super.inputPath + inputFileName))
                .delimited()
                .delimiter(",")
                .names("shape_id","shape_pt_lat","shape_pt_lon","shape_pt_sequence","shape_dist_traveled")
                .fieldSetMapper(fieldSet -> {
                    ShapeDto s = new ShapeDto();
                    s.setShapeId(fieldSet.readString("shape_id"));
                    s.setShapePtLat(fieldSet.readString("shape_pt_lat"));
                    s.setShapePtLon(fieldSet.readString("shape_pt_lon"));
                    s.setShapePtSeq(fieldSet.readInt("shape_pt_sequence"));
                    s.setShapeDistTraveled(fieldSet.readString("shape_dist_traveled"));
                    return s;
                })
                .build();
    }

    @Bean
    public ItemWriter<ShapeDto> shapeWriter(){
        return shapes -> {
            //shapes.forEach(s -> log.info("Saving Shape Records: " + s.toString()));
            shapeRepository.saveAllRaw((List<ShapeDto>) shapes.getItems());
        };
    }

    @Bean
    public Step shapeStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("load_shape_item", jobRepository)
                .<ShapeDto,ShapeDto>chunk(2, transactionManager)
                .reader(shapeReader())
                .writer(shapeWriter())
                .build();
    }

    @Bean
    public Job shapeJob(JobRepository jobRepository, Step shapeStep, ShapeLoaderListener listener){
        return new JobBuilder("load_shape_table", jobRepository)
                .listener(listener)
                .start(shapeStep)
                .build();
    }

}
