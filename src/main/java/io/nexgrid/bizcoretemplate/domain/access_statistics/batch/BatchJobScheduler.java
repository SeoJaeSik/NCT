//package io.nexgrid.bizcoretemplate.domain.access_statistics.batch;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersInvalidException;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
//import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
//import org.springframework.batch.core.repository.JobRestartException;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class BatchJobScheduler {
//
//    private final JobLauncher jobLauncher;
//    private final Job hourlyStatisticsBatchJob;
//    private final Job dailyStatisticsBatchJob;
//    private final Job monthlyStatisticsBatchJob;
//
//
//    @Scheduled(cron = "${spring.batch.hourly-statistics-job-cron}")
//    public void runHourlyStatisticsBatchJob() {
//        runJob(hourlyStatisticsBatchJob);
//    }
//
//    @Scheduled(cron = "${spring.batch.daily-statistics-job-cron}")
//    public void runDailyStatisticsBatchJob() {
//        runJob(dailyStatisticsBatchJob);
//    }
//
//    @Scheduled(cron = "${spring.batch.monthly-statistics-job-cron}")
//    public void runMonthlyStatisticsBatchJob() {
//        runJob(monthlyStatisticsBatchJob);
//    }
//
//    private void runJob(Job job) {
//        try {
//            jobLauncher.run(job, new JobParameters());
//        } catch (JobExecutionAlreadyRunningException e) {
//            throw new RuntimeException(e);
//        } catch (JobRestartException e) {
//            throw new RuntimeException(e);
//        } catch (JobInstanceAlreadyCompleteException e) {
//            throw new RuntimeException(e);
//        } catch (JobParametersInvalidException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//}
