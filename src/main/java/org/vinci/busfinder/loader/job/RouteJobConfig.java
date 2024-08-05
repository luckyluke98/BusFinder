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
import org.vinci.busfinder.dto.RouteDto;
import org.vinci.busfinder.loader.listener.AgencyLoaderListener;
import org.vinci.busfinder.loader.listener.RouteLoaderListener;
import org.vinci.busfinder.repository.RouteRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Configuration
public class RouteJobConfig extends BaseJobConfig {
    private static final Logger log = LoggerFactory.getLogger(RouteJobConfig.class);

    @Value("${dbloader.config.input.route-file-name}")
    private String inputFileName;

    @Autowired
    RouteRepository routeRepository;

    @Bean
    public FlatFileItemReader<RouteDto> routeReader() {
        Path path = Paths.get(super.inputPath + inputFileName);
        if(!Files.exists(path))
            throw new RuntimeException("[Error] Input file not found: " + super.inputPath + inputFileName);

        return new FlatFileItemReaderBuilder<RouteDto>()
                .linesToSkip(1)
                .name("route-reader")
                .resource(new FileSystemResource(super.inputPath + inputFileName))
                .delimited()
                .delimiter(",")
                .names("route_id","agency_id","route_short_name","route_long_name","route_desc","route_type","route_url","route_color","route_text_color")
                .fieldSetMapper(fieldSet -> {
                    RouteDto r = new RouteDto();
                    r.setRouteId(fieldSet.readInt("route_id"));
                    r.setAgencyId(fieldSet.readString("agency_id"));
                    r.setRouteShortName(fieldSet.readString("route_short_name"));
                    r.setRouteLongName(fieldSet.readString("route_long_name"));
                    r.setRouteDesc(fieldSet.readString("route_desc"));
                    r.setRouteType(fieldSet.readString("route_type"));
                    r.setRouteUrl(fieldSet.readString("route_url"));
                    r.setRouteColor(fieldSet.readString("route_color"));
                    r.setRouteTextColor(fieldSet.readString("route_text_color"));
                    return r;
                })
                .build();
    }

    @Bean
    public ItemWriter<RouteDto> routeWriter(){
        return routes -> {
            routes.forEach(r -> log.info("Saving Route Records: " + r.toString()));
            routeRepository.saveAllRaw((List<RouteDto>) routes.getItems());
        };
    }

    @Bean
    public Step routeStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("load_route_item", jobRepository)
                .<RouteDto,RouteDto>chunk(2, transactionManager)
                .reader(routeReader())
                .writer(routeWriter())
                .build();
    }

    @Bean
    public Job routeJob(JobRepository jobRepository, Step routeStep, RouteLoaderListener listener){
        return new JobBuilder("load_route_table", jobRepository)
                .listener(listener)
                .start(routeStep)
                .build();
    }
}
