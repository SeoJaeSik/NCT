//package io.nexgrid.bizcoretemplate.domain.access_statistics.batch.dailybatchjob;
//
//import io.nexgrid.bizcoretemplate.domain.access_statistics.dto.AccessStatisticsDto;
//import io.nexgrid.bizcoretemplate.domain.access_statistics.enums.StatisticsType;
//import io.nexgrid.bizcoretemplate.domain.access_statistics.repository.AccessStatisticsRepository;
//import io.nexgrid.bizcoretemplate.util.DateUtil;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.MDC;
//import org.springframework.batch.item.ItemReader;
//
//import java.util.List;
//
//@Slf4j
//@RequiredArgsConstructor
//public class DailyStatisticsBatchItemReader implements ItemReader<List<AccessStatisticsDto>> {
//
//    private final AccessStatisticsRepository accessStatisticsRepository;
//    private boolean isRun = true;
//
//    @Override
//    public List<AccessStatisticsDto> read() {
//        String seqId = MDC.get("seqId");
//
//        if (isRun) {
//            String yesterday = DateUtil.getYesterday();
//            List<AccessStatisticsDto> yesterdayAccessStatistics = accessStatisticsRepository.getAccessStatisticsByStatisticsType(yesterday, StatisticsType.HOURLY);
//            log.info("[{}] 마리아DB에서 AccessStatistics 엔티티에서 전날 일별 통계 데이터를 가져옵니다. yesterdayAccessStatistics size = {}", seqId, yesterdayAccessStatistics.size());
//            isRun = false;
//
//            return yesterdayAccessStatistics;
//        } else {
//            isRun = true;
//            return null;
//        }
//
//
//    }
//}