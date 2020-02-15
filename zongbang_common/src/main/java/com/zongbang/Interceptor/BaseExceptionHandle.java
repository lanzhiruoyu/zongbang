package com.zongbang.Interceptor;

import com.zongbang.pojo.Result;
import com.zongbang.pojo.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: zongbang_parent
 * @description: 全局拦截器
 * @author: LiaoHui
 * @create: 2020-02-05 19:37
 **/
@ControllerAdvice
public class BaseExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result error(Exception e){
        return new Result(false, StatusCode.ERROR,e.getMessage());
    }
}
