package com.twx.learnmybatisplus.controller;

import com.twx.learnmybatisplus.db.mysql.TestMapper;
import com.twx.learnmybatisplus.entity.mysql.Dog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired
    private TestMapper testMapper;

    @GetMapping("/index")
    public List<Dog> index() {
        List<Dog> dogs = testMapper.findDogs();
        return dogs;
    }
}
