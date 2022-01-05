package com.twx.learn.sc.ribbon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author tangwx@soyuan.com.cn
 * @date 2020/11/18 下午1:51
 */
@RestController
@RequestMapping("/dc")
public class DcConsumerController {
    private RestTemplate restTemplate;

    @Autowired
    public DcConsumerController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/consumer")
    public String index() {
        //Chooses a ServiceInstance from the LoadBalancer for the specified service.
        String url = "http://eureka-client/dc";
        return "consumer_"+restTemplate.getForObject(url,String.class);
    }
}
