package com.twx.learn.learnspringbootnacos;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/config")
@RestController
public class ConfigController {

    @NacosValue(value = "${app.token:12343}", autoRefreshed = true)
    private String token;

    @GetMapping("/")
    public String config() {
        return token;
    }
}
