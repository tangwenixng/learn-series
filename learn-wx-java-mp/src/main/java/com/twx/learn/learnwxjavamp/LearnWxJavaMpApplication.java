package com.twx.learn.learnwxjavamp;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class LearnWxJavaMpApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearnWxJavaMpApplication.class, args);
    }
}
