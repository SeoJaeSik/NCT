//package io.nexgrid.bizcoretemplate.domain.access_statistics.batch.monthlybatchjob;
//
//import io.nexgrid.bizcoretemplate.domain.access_statistics.AccessStatistics;
//import io.nexgrid.bizcoretemplate.domain.access_statistics.enums.LoginType;
//import io.nexgrid.bizcoretemplate.domain.access_statistics.enums.StatisticsType;
//import io.nexgrid.bizcoretemplate.domain.access_statistics.repository.AccessStatisticsRepository;
//import io.nexgrid.bizcoretemplate.util.DateUtil;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.item.Chunk;
//import org.springframework.batch.item.ItemWriter;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//@Slf4j
//@RequiredArgsConstructor
//public class MonthlyStatisticsBatchItemWriter implements ItemWriter<Map<String, Integer>> {
//
//    private final AccessStatisticsRepository accessStatisticsRepository;
//
//    @Override
//    public void write(Chunk<? extends Map<String, Integer>> chunkMap) {
//        List<AccessStatistics> accessStatisticsList = new ArrayList<>();
//
//        for (Map<String, Integer> map : chunkMap) {
//            map.entrySet().stream()
//                    .map(entry -> AccessStatistics.builder()
//                            .statisticsType(StatisticsType.MONTHLY)
//                            .loginType(LoginType.TOTAL)
//                            .accessResource(entry.getKey())
//                            .accessCount(entry.getValue())
//                            .statisticsStamp(DateUtil.getPreviousMonth())
//                            .build())
//                    .forEach(accessStatisticsList::add);
//        }
//
//        accessStatisticsRepository.saveAll(accessStatisticsList);
//    }
//
//}