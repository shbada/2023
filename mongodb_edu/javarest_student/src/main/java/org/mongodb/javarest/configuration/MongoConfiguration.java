package org.mongodb.javarest.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mapping.model.SnakeCaseFieldNamingStrategy;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
public class MongoConfiguration {

    @Autowired
    private Environment env;

    @Autowired
    public MongoClient mongoClient;

    @Autowired
    public MongoDatabaseFactory mongoDatabaseFactory;

    @Bean
    public MongoTransactionManager transactionManager() {
        return new MongoTransactionManager(mongoDatabaseFactory);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        MappingMongoConverter converter = mappingMongoConverter();
        // 스프링에서 자동으로 설정하는 _class 필드 사용하지 않게끔 설정
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        converter.afterPropertiesSet();
        return new MongoTemplate(mongoDatabaseFactory, converter);
    }

    private MappingMongoConverter mappingMongoConverter() {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDatabaseFactory);
        MongoMappingContext mongoMappingContext = new MongoMappingContext();
        mongoMappingContext.setFieldNamingStrategy(new SnakeCaseFieldNamingStrategy());

        return new MappingMongoConverter(dbRefResolver, mongoMappingContext);
    }

    protected String getDatabaseName() {
        return env.getProperty("spring.data.mongodb.database");
    }

    @Bean
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(
                env.getProperty("spring.data.mongodb.uri")
        );

        MongoClientSettings mongoClientSettings =
                MongoClientSettings.builder()
                        .applyConnectionString(connectionString)
                        .build();

        return MongoClients.create(mongoClientSettings);
    }
}
