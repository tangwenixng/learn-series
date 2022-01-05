package com.twx.learn.sentinel.controller;

import com.twx.learn.sentinel.service.IProjectMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author tangwx@soyuan.com.cn
 * @date 2020/4/15 15:25
 */
@RestController
@RequestMapping("/project")
public class IndexController {

    @Autowired
    private IProjectMetaService service;

    @GetMapping("/test")
    public String test(@RequestParam("key")String key) {
        return service.test(key);
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRqo rqo) {
        service.register(rqo.getKey(), rqo.getCount());
        return "注册成功";
    }

    public static class RegisterRqo {
        String key;
        Integer count;

        public RegisterRqo() {
        }

        public String getKey() {
            return key;
        }

        public Integer getCount() {
            return count;
        }
    }
}
