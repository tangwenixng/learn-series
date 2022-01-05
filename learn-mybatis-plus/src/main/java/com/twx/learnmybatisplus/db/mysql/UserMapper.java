package com.twx.learnmybatisplus.db.mysql;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.twx.learnmybatisplus.entity.mysql.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User>{

}
