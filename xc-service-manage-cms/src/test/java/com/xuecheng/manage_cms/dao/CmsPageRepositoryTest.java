package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsPageParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName: CmsPageRepositoryTest
 * @Description: CmsPage测试类
 * @Author: shaodong from ane Email:15715790677@163.com
 * @Date: Create in 2019/4/19 16:18
 * @Version: 1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {

    @Autowired
    CmsPageRepository cmsPageRepository;

    @Test
    public void testFindAll(){
        int page = 1;
        int size = 10;
        Pageable pageable = PageRequest.of(page,size);
        Page<CmsPage> all =  cmsPageRepository.findAll(pageable);
        System.out.println(all);
    }

    //自定义条件查询
    @Test
    public void testFindAllByExample(){
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page,size);
        //条件值对象
        CmsPage cmsPage = new CmsPage();
        //要查询站点ID为5a751fab6abb5044e0d19ea1的页面
//        cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");
        //设置模板ID
//        cmsPage.setTemplateId("5a925be7b00ffc4b3c1578b5");
        //设置页面别名
        cmsPage.setPageAliase("轮播");
        //条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();

        //模糊匹配
        exampleMatcher = exampleMatcher.withMatcher("pageAliase",ExampleMatcher.GenericPropertyMatchers.contains());
        //ExampleMatcher.GenericPropertyMatchers.contains() 包含关键字
        //ExampleMatcher.GenericPropertyMatchers.startWith() 前缀匹配

        //定义Example
        Example<CmsPage> example =  Example.of(cmsPage,exampleMatcher);
        Page<CmsPage> all =  cmsPageRepository.findAll(example,pageable);
        List<CmsPage> content = all.getContent();
        System.out.println(content);
    }



    //添加
    @Test
    public void testInsert(){
    //定义实体类
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId("s01");
        cmsPage.setTemplateId("t01");
        cmsPage.setPageName("测试页面");
        //cmsPage.setPageCreateTime(new Date());
        List<CmsPageParam> cmsPageParams = new ArrayList<>();
        CmsPageParam cmsPageParam = new CmsPageParam();
        cmsPageParam.setPageParamName("param1");
        cmsPageParam.setPageParamValue("value1");
        cmsPageParams.add(cmsPageParam);
        cmsPage.setPageParams(cmsPageParams);
        cmsPageRepository.save(cmsPage);
        System.out.println(cmsPage);
    }

    //删除
    @Test
    public void testDelete() {
        cmsPageRepository.deleteById("5b17a2c511fe5e0c409e5eb3");
    }

    //修改
    @Test
    public void testUpdate(){
        //查询对象
          Optional<CmsPage> optionalCmsPage =  cmsPageRepository.findById("5a754adf6abb500ad05688d9");
          if(optionalCmsPage.isPresent()){
              //设置修改值
              CmsPage cmsPage = optionalCmsPage.get();
              //修改
              cmsPage.setPageAliase("test01");
              CmsPage save = cmsPageRepository.save(cmsPage);
              System.out.println(save);
          }





    }
}
