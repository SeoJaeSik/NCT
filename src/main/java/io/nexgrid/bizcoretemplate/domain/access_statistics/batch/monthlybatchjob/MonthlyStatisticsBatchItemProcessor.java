//package io.nexgrid.bizcoretemplate.domain.access_statistics.batch.monthlybatchjob;
//
//import io.nexgrid.bizcoretemplate.domain.access_statistics.dto.AccessStatisticsDto;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.item.ItemProcessor;
//
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Slf4j
//@RequiredArgsConstructor
//public class MonthlyStatisticsBatchItemProcessor implements ItemProcessor<List<AccessStatisticsDto>, Map<String, Integer>> {
//
//    @Override
//    public Map<String, Integer> process(List<AccessStatisticsDto> items) {
//        return items.stream()
//                .collect(Collectors.toMap(
//                        AccessStatisticsDto::access_resource, // 키
//                        AccessStatisticsDto::access_count,  // 밸류
//                        Integer::sum                                // 동일한 키가 있을 경우 밸류 합산
//                ));
//    }
//}