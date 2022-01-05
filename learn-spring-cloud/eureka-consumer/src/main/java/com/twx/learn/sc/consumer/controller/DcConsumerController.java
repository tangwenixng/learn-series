package com.twx.learn.sc.consumer.controller;

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
    private LoadBalancerClient lbClient;
    private RestTemplate restTemplate;

    @Autowired
    public DcConsumerController(LoadBalancerClient lbClient, RestTemplate restTemplate) {
        this.lbClient = lbClient;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/consumer")
    public String index() {
        //Chooses a ServiceInstance from the LoadBalancer for the specified service.
        ServiceInstance client = lbClient.choose("eureka-client");
        String url = "http://" + client.getHost() + ":" + client.getPort() + "/dc";
        System.out.println(url);
        return "consumer_"+client.getPort()+"_"+restTemplate.getForObject(url,String.class);
    }
}
