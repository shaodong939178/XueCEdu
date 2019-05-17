package com.xuecheng.manage_cms.connection;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Test;


/**
 * @ClassName: MongoDBConnectionTest
 * @Description:
 * @Author: shaodong from ane Email:15715790677@163.com
 * @Date: Create in 2019/4/19 16:28
 * @Version: 1.0
 **/
public class MongoDBConnectionTest {

    @Test
    public void testConnection(){
        //创建mongodb 客户端
        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        //或者采用连接字符串
        //MongoClientURI connectionString = new MongoClientURI("mongodb://root:root@localhost:27017");
        //MongoClient mongoClient = new MongoClient(connectionString);
        //连接数据库

        MongoDatabase database = mongoClient.getDatabase("test1");
        // 连接collection
        MongoCollection<org.bson.Document> collection = database.getCollection("student");
        //查询第一个文档
        Document myDoc = (Document) collection.find().first();
        //得到文件内容 json串
        String json = myDoc.toJson();
        System.out.println(json);

    }
}
