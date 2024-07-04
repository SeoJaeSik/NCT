//package io.nexgrid.bizcoretemplate.domain.access_statistics.batch.dailybatchjob;
//
//import io.nexgrid.bizcoretemplate.domain.access_statistics.dto.AccessStatisticsDto;
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
//public class DailyStatisticsBatchJobConfiguration {
//
//    private final JobRepository jobRepository;
//    private final JobExecutionListener jobListener;
//    private final PlatformTransactionManager transactionManager;
//    private final AccessStatisticsRepository accessStatisticsRepository;
//
//
//    @Bean
//    public Job dailyStatisticsBatchJob() {
//        return new JobBuilder("dailyStatisticsBatchJob", jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .start(dailyStatisticsStep())
//                .listener(jobListener)
//                .build();
//    }
//
//    @Bean
//    public Step dailyStatisticsStep() {
//        return new StepBuilder("dailyStatisticsStep", jobRepository)
//                .chunk(1, transactionManager)
//                .reader(dailyStatisticsItemReader())
//                .processor((ItemProcessor<? super Object, ?>) dailyStatisticsItemProcessor())
//                .writer((ItemWriter<? super Object>) dailyStatisticsItemWriter())
//                .allowStartIfComplete(true)
//                .build();
//    }
//
//    @Bean
//    public ItemReader<List<AccessStatisticsDto>> dailyStatisticsItemReader() {
//        return new DailyStatisticsBatchItemReader(accessStatisticsRepository);
//    }
//
//    @Bean
//    public ItemProcessor<? super List<AccessStatisticsDto>, Map<String, Integer>> dailyStatisticsItemProcessor() {
//        return new DailyStatisticsBatchItemProcessor();
//    }
//
//    @Bean
//    public ItemWriter<?> dailyStatisticsItemWriter() {
//        return new DailyStatisticsBatchItemWriter(accessStatisticsRepository);
//    }
//
//}
