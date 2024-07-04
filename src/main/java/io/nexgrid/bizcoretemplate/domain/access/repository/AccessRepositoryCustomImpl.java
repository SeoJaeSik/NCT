//package io.nexgrid.bizcoretemplate.domain.access.repository;
//
//import io.nexgrid.bizcoretemplate.domain.access.Access;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Repository
//@RequiredArgsConstructor
//public class AccessRepositoryCustomImpl implements AccessRepositoryCustom {
//
//    private final MongoTemplate mongoTemplate;
//
//    // 몽고DB에서 accessResource를 distinct로 가져온다.
//    @Override
//    public List<String> findDistinctAccessResource() {
//        return mongoTemplate.getCollection("access")
//                .distinct("accessResource", String.class)
//                .into(new ArrayList<>());
//    }
//
//    // 몽고DB에서 모든 path에 대한 시간별 접근 횟수를 가져온다.
//    @Override
//    public Integer countHourlyAccessByAccessResource(String accessResource, String accessDay, String accessHour) {
//        Query countQuery = new Query();
//        countQuery.addCriteria(Criteria.where("accessResource").is(accessResource)
//                .and("accessDay").is(accessDay)
//                .and("accessHour").is(accessHour));
//
//        return (int) mongoTemplate.count(countQuery, Access.class);
//    }
//
//}
