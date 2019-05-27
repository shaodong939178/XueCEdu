package com.xuecheng.manage_cms.dao;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.manage_cms.service.PageService;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @ClassName: GridFsTemplateTest
 * @Description: GridFs存取文件测试
 * @Author: shaodong from ane Email:15715790677@163.com
 * @Date: Create in 2019/5/24 14:20
 * @Version: 1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class pageServiceTest {

   @Autowired
    PageService pageService;

   @Test
    public void  testgetPageHtml(){
       String pageHtml = pageService.getPageHtml("5a795ac7dd573c04508f3a56");
       System.out.println(pageHtml);
   }
}
