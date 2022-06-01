package com.twx.learn.servicec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/sb")
public class DemoController {
    private static final Logger log = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private RestTemplate template;

    @GetMapping("/")
    public String index() {
        log.info("I'm serviceB,receive request...");
        String result = template.getForObject("http://localhost:1356/sc/", String.class);
        return "this is serviceB-->"+result;
    }
}
