package com.xuecheng.manage_cms.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.dao.CmsConfigRepository;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.dao.CmsTemplataRepository;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;


/**
 * @ClassName: PageService
 * @Description: 页面查询方法
 * @Author: shaodong from ane Email:15715790677@163.com
 * @Date: Create in 2019/4/24 11:08
 * @Version: 1.0
 **/
@Service
public class PageService {
    @Autowired
    CmsPageRepository cmsPageRepository;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    CmsTemplataRepository cmsTemplataRepository;
    @Autowired
    GridFSBucket gridFSBucket;
    @Autowired
    GridFsTemplate gridFsTemplate;
    @Autowired
    CmsConfigRepository cmsConfigRepository;

    //根据id查询CmsConfig
    public CmsConfig getConfigById(String id){
        Optional<CmsConfig> optional = cmsConfigRepository.findById(id);
        if(optional.isPresent()){
            CmsConfig cmsConfig = optional.get();
            return cmsConfig;
        }
        return null;
    }

    /**
     * 页面列表分页查询
     *
     * @param page             当前页码
     * @param size             页面显示个数
     * @param queryPageRequest 查询条件
     * @return 页面列表
     */
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) {
        if (queryPageRequest == null) {
            queryPageRequest = new QueryPageRequest();
        }
        //实现自定义条件查询
        //定义条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("pageAliase",
                        ExampleMatcher.GenericPropertyMatchers.contains());

        //条件值对象
        CmsPage cmsPage = new CmsPage();
        //设置条件值(siteId)
        if (StringUtils.isNoneEmpty(queryPageRequest.getSiteId())) {
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        //设置模板ID
        if (StringUtils.isNoneEmpty(queryPageRequest.getTemplateId())) {
            cmsPage.setPageAliase(queryPageRequest.getTemplateId());
        }
        //设置页面别名
        if (StringUtils.isNotEmpty(queryPageRequest.getPageAliase())) {
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }
        //创建example实例
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);
        if (page <= 0) {
            page = 1;
        }
        page = page - 1;
        if (size <= 0) {
            size = 20;
        }
        //分页对象
        Pageable pageable = new PageRequest(page, size);
        //实现自定义条件查询
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);
        QueryResult<CmsPage> cmsPageQueryResult = new QueryResult<CmsPage>();
        cmsPageQueryResult.setList(all.getContent());//数据列表
        cmsPageQueryResult.setTotal(all.getTotalElements());//数据记录数
        return new QueryResponseResult(CommonCode.SUCCESS, cmsPageQueryResult);
    }

    //新增页面
    public CmsPageResult add(CmsPage cmsPage) {
        if (cmsPage == null) {
            //抛出异常，非法参数异常，指定异常信息内容
            ExceptionCast.cast(CommonCode.FAIL);
        }
        //校验页面名称、站点ID、页面webpath的唯一性
        //根据页面名称、站点ID、页面webpath去cms_page查询集合，如果查到说明此页面一存在
        //如果查不到，新增。
        CmsPage cmsPage1 = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if (cmsPage1 != null) {
            //抛出异常，页面已经存在
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }
        //调用dao的新增页面
        cmsPage.setPageId(null);//MongoDB自动生成
        cmsPageRepository.save(cmsPage);
        return new CmsPageResult(CommonCode.SUCCESS, cmsPage);
    }

    //根据ID查询页面
    public CmsPage getById(String id) {
        if (id == null) {
            ExceptionCast.cast(CommonCode.FAIL);
        }
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if (optional.isPresent()) {
            CmsPage cmsPage = optional.get();
            return cmsPage;
        }
        return null;
    }

    //更新页面信息
    public CmsPageResult update(String id, CmsPage cmsPage) {
        CmsPage cmsPage1 = this.getById(id);
        if (cmsPage1 != null) {
            //设置页面别名
            cmsPage1.setPageAliase(cmsPage.getPageAliase());
            //设置页面所属站点
            cmsPage1.setSiteId(cmsPage.getSiteId());
            //设置模板id
            cmsPage1.setTemplateId(cmsPage.getTemplateId());
            //设置页面名称
            cmsPage1.setPageName(cmsPage.getPageName());
            //设置页面访问路径
            cmsPage1.setPageWebPath(cmsPage.getPageWebPath());
            //设置页面物理路径
            cmsPage1.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            //更新dataUrl
            cmsPage1.setDataUrl(cmsPage.getDataUrl());
            //执行更新
            CmsPage save = cmsPageRepository.save(cmsPage1);
            if (save != null) {
                //返回成功
                CmsPageResult cmsPageResult = new CmsPageResult(CommonCode.SUCCESS, save);
                return cmsPageResult;
            }
        }
        //返回失败
        return new CmsPageResult(CommonCode.FAIL, null);
    }

    //根据ID删除页面
    public ResponseResult delete(String id) {
        //先查询页面是否存在
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if (optional.isPresent()) {
            cmsPageRepository.deleteById(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    //页面静态化
    public String getPageHtml(String pageId) {
        //获取数据模型
        Map model = this.getMoldeByPageId(pageId);
        if (model == null) {
            //数据模型获取不到
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }
        //获取页面模板
        String template = this.getTemplateByPageId(pageId);
        if (StringUtils.isEmpty(template)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        //执行静态化
        String generateHtml = this.generateHtml(template, model);
        if (StringUtils.isEmpty(generateHtml)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        }
        return generateHtml;
    }

    //执行静态化
    public String generateHtml(String template, Map model) {
        try {
            //创建配置对象
            Configuration configuration = new Configuration(Configuration.getVersion());
            //模板加载器
            StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
            stringTemplateLoader.putTemplate("template", template);
            //配置模板加载器
            configuration.setTemplateLoader(stringTemplateLoader);
            //获取模板
            Template template1 = configuration.getTemplate("template");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template1, model);
            return html;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取页面模板
    public String getTemplateByPageId(String pageId) {
        //根据pageId获取页面信息
        CmsPage cmsPage = this.getById(pageId);
        if (cmsPage == null) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXITS);
        }
        //获取页面模板ID
        String templateId = cmsPage.getTemplateId();
        if (StringUtils.isEmpty(templateId)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        //查询模板信息
        Optional<CmsTemplate> optional = cmsTemplataRepository.findById(templateId);
        if (optional.isPresent()) {
            CmsTemplate cmsTemplate = optional.get();
            //获取模板文件id
            String templateFileId = cmsTemplate.getTemplateFileId();
            //GridFs获取
            //根据ObjectId查询文件
            GridFSFile fsFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
            //打开下载流对象
            GridFSDownloadStream gridFSDownloadStream =
                    gridFSBucket.openDownloadStream(fsFile.getObjectId());
            //创建GridFsResource ,用于获取对象
            GridFsResource gridFsResource = new GridFsResource(fsFile, gridFSDownloadStream);
            //获取流中的数据
            try {
                String s = IOUtils.toString(gridFsResource.getInputStream(), "utf-8");
                return s;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //获取数据模型
    private Map getMoldeByPageId(String pageId) {
        //根据pageId获取页面信息
        CmsPage cmsPage = this.getById(pageId);
        if (cmsPage == null) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXITS);
        }
        //获取页面dataUrl
        String dataUrl = cmsPage.getDataUrl();
        if (StringUtils.isEmpty(dataUrl)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        }
        //通过restTemplate请求dataUrl获取数据
        ResponseEntity<Map> forEntity = restTemplate.getForEntity(dataUrl, Map.class);
        Map body = forEntity.getBody();
        return body;
    }
}
