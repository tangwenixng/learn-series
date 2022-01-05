package com.twx.learnmybatisplus.db.mysql;

import com.twx.learnmybatisplus.entity.mysql.Dog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestMapper {

    List<Dog> findDogs();
}
