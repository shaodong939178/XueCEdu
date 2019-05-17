package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * @ClassName: CustomException
 * @Description: 自定义异常
 * @Author: shaodong from ane Email:15715790677@163.com
 * @Date: Create in 2019/5/13 14:35
 * @Version: 1.0
 **/
public class CustomException extends RuntimeException {
    private ResultCode resultCode;

    public CustomException(ResultCode resultCode) {
        //异常信息为错误代码+异常信息
        super("errorCode:"+resultCode.code()+"errorMessage:"+resultCode.message());
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return this.resultCode;
    }
}
