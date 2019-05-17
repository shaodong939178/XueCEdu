package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName: CmsConfigControllerApi
 * @Description:
 * @Author: shaodong from ane Email:15715790677@163.com
 * @Date: Create in 2019/5/14 14:17
 * @Version: 1.0
 **/
@Api(value="cms配置管理接口",description = "cms配置管理接口，提供数据模型的管理、查询接口")
public interface CmsConfigControllerApi {
    @ApiOperation("根据id查询CMS配置信息")
    public CmsConfig getmodel(String id);
}

