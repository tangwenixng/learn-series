package com.twx.learn.sc.eurekaclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.eureka.CloudEurekaClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * @author tangwx@soyuan.com.cn
 * @date 2020/11/18 上午11:16
 */
@RestController
public class DcController {

    @Autowired
    EurekaDiscoveryClient discoveryClient;

    @Value("${server.port}")
    private int port;

    @GetMapping("/dc")
    public String dc() {
        List<String> services = discoveryClient.getServices();
        System.out.println("services=>"+services);
        return "client port =>"+port+" =>"+services.toString();
    }
}
