package com.xuecheng.manage_cms.dao;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
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
public class GridFsTemplateTest {

    @Autowired
    GridFsTemplate gridFsTemplate;
    @Autowired
    GridFSBucket gridFSBucket;
    //存文件
    @Test
    public void testGridFs() throws FileNotFoundException {
        //要存的文件
        File file = new File("d:/轮播图.ftl");
        //定义输入流
        FileInputStream fileInputStream = new FileInputStream(file);
        //像GridFs中存文件
        ObjectId objectId = gridFsTemplate.store(fileInputStream, "banner.ftl");
        //得到文件
        String fileId  = objectId.toString();
        System.out.println(fileId);
    }
    //取文件
    @Test
    public void testGetFile() throws IOException {
        //根据文件id查询File
        GridFSFile gridFsFile = gridFsTemplate.findOne(
                Query.query(Criteria.where("_id").is("5a962bf8b00ffc514038fafa")));
        //打开下载流
        GridFSDownloadStream gridFSDownloadStream =
                gridFSBucket.openDownloadStream(gridFsFile.getObjectId());
        //创建gridFsResource，用于获取对象（File）
        GridFsResource gridFsResource = new GridFsResource(gridFsFile,gridFSDownloadStream);
        //获取流中的数据
        String s = IOUtils.toString(gridFsResource.getInputStream(), "utf-8");
        System.out.println(s);
    }

    //删除文件
    @Test
    public void testDelFile() throws IOException {
//根据文件id删除fs.files和fs.chunks中的记录
        gridFsTemplate.delete(Query.query(Criteria.where("_id").is("5ce79081b2585131d04a6022")));
    }
}
