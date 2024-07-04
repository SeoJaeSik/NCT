//package io.nexgrid.bizcoretemplate.domain.access_statistics.batch.hourlybatchjob;
//
//import io.nexgrid.bizcoretemplate.domain.access.repository.AccessRepository;
//import io.nexgrid.bizcoretemplate.util.DateUtil;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.item.ItemProcessor;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Slf4j
//@RequiredArgsConstructor
//public class HourlyStatisticsBatchItemProcessor implements ItemProcessor<List<String>, Map<String, Integer>> {
//
//    private final AccessRepository accessRepository;
//
//    @Override
//    public Map<String, Integer> process(List<String> items) {
//        Map<String, Integer> accessMap = new HashMap<>();
//        String day = calculateDay();
//        String hour = DateUtil.getOneHourAgo();
//
//        items.forEach(
//                item -> {
//                    Integer count = accessRepository.countHourlyAccessByAccessResource(item, day, hour);
//                    accessMap.put(item, count);
//                }
//        );
//
//        return accessMap;
//    }
//
//    // 날짜가 넘어가는 시점을 고려해 날짜를 계산한다
//    private String calculateDay() {
//        String day = DateUtil.getDay();
//        String hour = DateUtil.getOneHourAgo();
//
//        if ("23".equals(hour)) {
//            day = DateUtil.getYesterday();
//        }
//
//        return day;
//    }
//
//}
