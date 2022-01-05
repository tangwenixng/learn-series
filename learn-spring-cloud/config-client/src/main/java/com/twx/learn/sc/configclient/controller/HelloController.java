package com.twx.learn.sc.configclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tangwx@soyuan.com.cn
 * @date 2020/11/18 下午3:41
 */
@RefreshScope
@RestController
public class HelloController {
    @Value("${info.foo}")
    private String fooProperty;

    @GetMapping("/hello")
    public String index() {
        return "Using [" + fooProperty + "] from config server";
    }
}
