//package io.nexgrid.bizcoretemplate.domain.access_statistics.batch.monthlybatchjob;
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
//public class MonthlyStatisticsBatchItemReader implements ItemReader<List<AccessStatisticsDto>> {
//
//    private final AccessStatisticsRepository accessStatisticsRepository;
//    private boolean isRun = true;
//
//    @Override
//    public List<AccessStatisticsDto> read() {
//        String seqId = MDC.get("seqId");
//
//        if (isRun) {
//            String previousMonth = DateUtil.getPreviousMonth();
//            List<AccessStatisticsDto> previousMonthAccessStatistics = accessStatisticsRepository.getAccessStatisticsByStatisticsType(previousMonth, StatisticsType.DAILY);
//            log.info("[{}] 마리아DB에서 AccessStatistics 엔티티에서 전월 월별 통계 데이터를 가져옵니다. previousMonthAccessStatistics size = {}", seqId, previousMonthAccessStatistics.size());
//            isRun = false;
//
//            return previousMonthAccessStatistics;
//        } else {
//            isRun = true;
//            return null;
//        }
//
//
//    }
//}