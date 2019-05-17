package com.xuecheng.manage_cms.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @ClassName: CmsConfigRepositortTets
 * @Description:
 * @Author: shaodong from ane Email:15715790677@163.com
 * @Date: Create in 2019/5/14 14:42
 * @Version: 1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsConfigRepositortTest {

    @Autowired
    RestTemplate restTemplate;
    @Test
    public void testRestTemplate(){
        ResponseEntity<Map> forEntity = restTemplate.getForEntity(
                "http://localhost:31001/cms/config/getmodel/5a791725dd573c3574ee333f",
                Map.class);
        Map body = forEntity.getBody();
        System.out.println(body);
    }
}
