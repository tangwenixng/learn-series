package com.twx.learn.servicec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/sa")
public class DemoController {

    @Autowired
    private RestTemplate template;

    @GetMapping("/")
    public String index() {
        String result = template.getForObject("http://localhost:1355/sb/", String.class);
        return "this is serviceA-->"+result;
    }
}
