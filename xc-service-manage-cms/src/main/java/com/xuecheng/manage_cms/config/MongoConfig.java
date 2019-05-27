package com.xuecheng.manage_cms.config;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: MongoConfig
 * @Description: MongoDB配置类
 * @Author: shaodong from ane Email:15715790677@163.com
 * @Date: Create in 2019/5/23 13:40
 * @Version: 1.0
 **/
@Configuration//相当于bean
public class MongoConfig {
    @Value("${spring.data.mongodb.database}")
    String db;

    @Bean
    public GridFSBucket getGridFsBucket(MongoClient mongoClient){
        MongoDatabase database = mongoClient.getDatabase(db);
        GridFSBucket bucket = GridFSBuckets.create(database);
        return bucket;
    }
}
