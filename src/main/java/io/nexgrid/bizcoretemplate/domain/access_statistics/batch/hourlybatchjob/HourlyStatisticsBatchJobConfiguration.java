//package io.nexgrid.bizcoretemplate.domain.access_statistics.batch.hourlybatchjob;
//
//import io.nexgrid.bizcoretemplate.domain.access.repository.AccessRepository;
//import io.nexgrid.bizcoretemplate.domain.access_statistics.repository.AccessStatisticsRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobExecutionListener;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import java.util.List;
//import java.util.Map;
//
//@Configuration
//@RequiredArgsConstructor
//public class HourlyStatisticsBatchJobConfiguration {
//
//    private final JobRepository jobRepository;
//    private final AccessRepository accessRepository;
//    private final JobExecutionListener jobListener;
//    private final PlatformTransactionManager transactionManager;
//    private final AccessStatisticsRepository accessStatisticsRepository;
//
//
//    @Bean
//    public Job hourlyStatisticsBatchJob() {
//        return new JobBuilder("hourlyStatisticsBatchJob", jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .start(hourlyStatisticsStep())
//                .listener(jobListener)
//                .build();
//    }
//
//    @Bean
//    public Step hourlyStatisticsStep() {
//        return new StepBuilder("hourlyStatisticsStep", jobRepository)
//                .chunk(1, transactionManager)
//                .reader(hourlyStatisticsItemReader())
//                .processor((ItemProcessor<? super Object, ?>) hourlyStatisticsItemProcessor())
//                .writer((ItemWriter<? super Object>) hourlyStatisticsItemWriter())
//                .allowStartIfComplete(true)
//                .build();
//    }
//
//    @Bean
//    public ItemReader<List<String>> hourlyStatisticsItemReader() {
//        return new HourlyStatisticsBatchItemReader(accessRepository);
//    }
//    @Bean
//    public ItemProcessor<? super List<String>, Map<String, Integer>> hourlyStatisticsItemProcessor() {
//        return new HourlyStatisticsBatchItemProcessor(accessRepository);
//    }
//
//    @Bean
//    public ItemWriter<?> hourlyStatisticsItemWriter() {
//        return new HourlyStatisticsBatchItemWriter(accessStatisticsRepository);
//    }
//
//}
