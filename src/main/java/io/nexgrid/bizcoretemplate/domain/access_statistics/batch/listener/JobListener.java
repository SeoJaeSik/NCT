//package io.nexgrid.bizcoretemplate.domain.access_statistics.batch.listener;
//
//import io.nexgrid.bizcoretemplate.util.LogUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.MDC;
//import org.springframework.batch.core.JobExecution;
//import org.springframework.batch.core.JobExecutionListener;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//public class JobListener implements JobExecutionListener {
//
//    long startTime;
//    long endTime;
//    long timeDiff;
//
//    @Override
//    public void beforeJob(JobExecution jobExecution) {
//        String jobName = getJobName(jobExecution);
//        String seqId = LogUtil.generateSeqId();
//
//        // MDC: 스레드-세이프한 로깅 컨텍스트
//        MDC.put("seqId", seqId);
//        log.info("[{}] ========== {} 동작 START ==========", seqId, jobName);
//        startTime = System.currentTimeMillis();
//    }
//
//    @Override
//    public void afterJob(JobExecution jobExecution) {
//        String jobName = getJobName(jobExecution);
//        String seqId = MDC.get("seqId");
//
//        endTime = System.currentTimeMillis();
//        log.info("[{}] ========== {} 동작 END ==========", seqId, jobName);
//
//        timeDiff = (endTime - startTime);
//        log.info("[{}] ========== {} 소요 시간 = {}ms ==========", seqId, jobName, timeDiff);
//        log.info("");
//
//        MDC.remove("seqId");
//    }
//
//    // 배치 JOB의 이름을 한글로 변환해 가져온다.
//    private String getJobName(JobExecution jobExecution) {
//        String jobName = jobExecution.getJobInstance().getJobName();
//
//        return switch (jobName) {
//            case "hourlyStatisticsBatchJob" -> "시간별 접속 통계 배치 프로그램";
//            case "dailyStatisticsBatchJob" -> "일별 접속 통계 배치 프로그램";
//            case "monthlyStatisticsBatchJob" -> "월별 접속 통계 배치 프로그램";
//            default -> jobName;
//        };
//    }
//}
