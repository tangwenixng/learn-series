package com.twx.learn.sentinel.common.exceptions;

/**
 * @author tangwx@soyuan.com.cn
 * @date 2020/6/17 15:13
 */
public class AccessLimitException extends RuntimeException{
    private String msg;

    public AccessLimitException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
