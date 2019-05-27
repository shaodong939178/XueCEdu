package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @ClassName: CmsTemplataRepository
 * @Description:
 * @Author: shaodong from ane Email:15715790677@163.com
 * @Date: Create in 2019/5/24 15:05
 * @Version: 1.0
 **/
public interface CmsTemplataRepository  extends MongoRepository<CmsTemplate , String> {
}
