package com.twx.learnmybatisplus.entity.mysql;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@TableName("t_manager")
@Data
public class User {

    private int id;
    private String username;
    private String name;
    private String password;
    private String salt;
    private String phone;
    private String tips;

}
