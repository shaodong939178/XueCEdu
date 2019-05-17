package com.xuecheng.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.sun.javafx.collections.MappingChange;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @ClassName: ExceptionCatch
 * @Description: 异常捕获类
 * @Author: shaodong from ane Email:15715790677@163.com
 * @Date: Create in 2019/5/13 14:40
 * @Version: 1.0
 **/
@ControllerAdvice//控制器增强
//
public class ExceptionCatch {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionCatch.class);

    //定义一个map，配置异常类型所对应的错误代码
    //使用EXCEPTIONS存放异常和错误代码的映射，ImmutableMap的特点是一旦创建不可更改，且线程安全
    private static ImmutableMap<Class<? extends Throwable>,ResultCode> EXCEPTIONS;
    //使用builder来构建一个异常类型和错误代码异常
    protected static ImmutableMap.Builder<Class<? extends Throwable>,ResultCode> builder =
            ImmutableMap.builder();
    //捕获CusntomException异常
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult customExeception(CustomException e){
        LOGGER.error("catch exception:{}",e.getMessage());
        ResultCode resultCode = e.getResultCode();
        return new ResponseResult(resultCode);

    }

    //捕获Exception此类异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult Exeception(Exception e){
        LOGGER.error("catch exception:{}",e.getMessage());
        if(EXCEPTIONS == null){
            EXCEPTIONS = builder.build();//EXCEPTIONS构建成功
        }
        //从EXCEPTIONS这个Mao当中找异常对应的错误代码，如果找到了就将错误代码
        //响应给客户，如果找不到就给用户返回99999异常
        ResultCode resultCode = EXCEPTIONS.get(e.getClass());
        if(resultCode !=null){
            return  new ResponseResult(resultCode);
        }else{
            //返回9999异常
            return new ResponseResult(CommonCode.SERVER_ERROR);
        }


    }

    static {
        //在这里加入一些基础的异常类型判断
        builder.put(HttpMessageNotReadableException.class,CommonCode.INVALID_PARAM);
    }
}
