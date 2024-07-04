//package io.nexgrid.bizcoretemplate.domain.access_statistics.batch.monthlybatchjob;
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
//public class MonthlyStatisticsBatchJobConfiguration {
//
//    private final JobRepository jobRepository;
//    private final JobExecutionListener jobListener;
//    private final PlatformTransactionManager transactionManager;
//    private final AccessStatisticsRepository accessStatisticsRepository;
//
//
//    @Bean
//    public Job monthlyStatisticsBatchJob() {
//        return new JobBuilder("monthlyStatisticsBatchJob", jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .start(monthlyStatisticsStep())
//                .listener(jobListener)
//                .build();
//    }
//
//    @Bean
//    public Step monthlyStatisticsStep() {
//        return new StepBuilder("monthlyStatisticsStep", jobRepository)
//                .chunk(1, transactionManager)
//                .reader(monthlyStatisticsItemReader())
//                .processor((ItemProcessor<? super Object, ?>) monthlyStatisticsItemProcessor())
//                .writer((ItemWriter<? super Object>) monthlyStatisticsItemWriter())
//                .allowStartIfComplete(true)
//                .build();
//    }
//
//    @Bean
//    public ItemReader<List<AccessStatisticsDto>> monthlyStatisticsItemReader() {
//        return new MonthlyStatisticsBatchItemReader(accessStatisticsRepository);
//    }
//
//    @Bean
//    public ItemProcessor<? super List<AccessStatisticsDto>, Map<String, Integer>> monthlyStatisticsItemProcessor() {
//        return new MonthlyStatisticsBatchItemProcessor();
//    }
//
//    @Bean
//    public ItemWriter<?> monthlyStatisticsItemWriter() {
//        return new MonthlyStatisticsBatchItemWriter(accessStatisticsRepository);
//    }
//
//}
