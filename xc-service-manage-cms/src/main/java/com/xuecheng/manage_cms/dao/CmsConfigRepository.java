package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @ClassName: CmsConfigRepository
 * @Description:
 * @Author: shaodong from ane Email:15715790677@163.com
 * @Date: Create in 2019/5/14 14:18
 * @Version: 1.0
 **/
public interface CmsConfigRepository extends
        MongoRepository<CmsConfig,String> {

}
