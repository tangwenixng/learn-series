package com.twx.learn.service.impl;

import com.twx.learn.entity.User;
import com.twx.learn.mapper.UserMapper;
import com.twx.learn.service.DemoService;
import com.twx.learn.service.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoServiceImpl implements DemoService {
    @Autowired
    private ICompanyService cs;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void component() {
        System.out.println(("----- query master ------"));
        List<User> userList = userMapper.selectList(null);
        userList.stream().forEach(System.out::println);
        System.out.println("----- query slave_1 ------");
        cs.list().stream().forEach(System.out::println);
    }
}
