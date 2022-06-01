package com.twx.learn.servicec;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sc")
public class DemoController {

    @GetMapping("/")
    public String index() {
        return "this is serviceC";
    }
}
