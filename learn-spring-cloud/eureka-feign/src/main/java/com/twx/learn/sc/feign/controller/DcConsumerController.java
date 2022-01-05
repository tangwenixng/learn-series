package com.twx.learn.sc.feign.controller;

import com.twx.learn.sc.feign.client.DcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tangwx@soyuan.com.cn
 * @date 2020/11/18 下午1:51
 */
@RestController
@RequestMapping("/dc")
public class DcConsumerController {
    private DcClient dcClient;

    @Autowired
    public DcConsumerController(DcClient dcClient) {
        this.dcClient = dcClient;
    }

    @GetMapping("/consumer")
    public String index() {
        return "feign_" + dcClient.consumer();
    }
}
