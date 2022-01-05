package com.twx.learn.sentinel.common.exceptions.handler;

import com.twx.learn.sentinel.common.exceptions.AccessLimitException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author tangwx@soyuan.com.cn
 * @date 2020/6/17 15:16
 */
@ControllerAdvice
public class SyExceptionHandler {

    @ExceptionHandler(AccessLimitException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public String index(AccessLimitException ale) {
        return ale.getMsg();
    }
}
