package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * @ClassName: ExceptionCast
 * @Description: 异常抛出类
 * @Author: shaodong from ane Email:15715790677@163.com
 * @Date: Create in 2019/5/13 14:38
 * @Version: 1.0
 **/
public class ExceptionCast {
    //使用静态方法抛出自定义异常
    public static void  cast(ResultCode resultCode){
        throw new CustomException(resultCode);
    }
}
