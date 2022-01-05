package com.twx.learnmybatisplus.db.mysql;

import com.twx.learnmybatisplus.entity.mysql.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void findById() throws Exception {
        Assert.assertNotNull(userMapper);
        User user = userMapper.selectById(1);
        System.out.println(user);

        user.setName("管理员");
        user.setTips("发斯蒂芬地点");
        userMapper.updateById(user);
    }

}