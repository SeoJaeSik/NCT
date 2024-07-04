//package io.nexgrid.bizcoretemplate.config;
//
//import com.mongodb.ConnectionString;
//import com.mongodb.MongoClientSettings;
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoClients;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
//
//@Configuration
//@RequiredArgsConstructor
//public class MongoDBConfig {
//
//    private final Environment env;
//
//    @Bean
//    public MongoClient mongoClient() {
//        ConnectionString connection = new ConnectionString(env.getProperty("spring.data.mongodb.uri"));
//
//        MongoClientSettings settings = MongoClientSettings.builder()
//                .applyConnectionString(connection)
//                .applyToConnectionPoolSettings(builder ->
//                        builder
//                            .minSize(Integer.parseInt(env.getProperty("spring.data.mongodb.min-size")))
//                            .maxSize(Integer.parseInt(env.getProperty("spring.data.mongodb.max-size")))
//                )
//                .build();
//
//        return MongoClients.create(settings);
//    }
//
//    @Bean
//    public MongoTemplate mongoTemplate() {
//        return new MongoTemplate(new SimpleMongoClientDatabaseFactory(mongoClient(), env.getProperty("spring.data.mongodb.database")));
//    }
//
//}
