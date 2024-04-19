package io.nexgrid.bizcoretemplate.domain.access_statistics.batch.hourlybatchjob;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class HourlyStatisticsBatchJobConfiguration {

    private final JobRepository jobRepository;


    @Bean
    public Job hourlyStatisticsBatchJob() {
        return new JobBuilder("hourlyStatisticsBatchJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start()
                .listener()
                .build();
    }



}
