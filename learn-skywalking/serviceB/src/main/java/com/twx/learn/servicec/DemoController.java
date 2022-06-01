package com.twx.learn.servicec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/sb")
public class DemoController {

    @Autowired
    private RestTemplate template;

    @GetMapping("/")
    public String index() {
        String result = template.getForObject("http://localhost:1356/sc/", String.class);
        return "this is serviceB-->"+result;
    }
}
