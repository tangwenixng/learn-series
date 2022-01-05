package com.twx.learnmybatisplus.db.mysql;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.twx.learnmybatisplus.entity.mysql.Dog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMapperTest {

    @Autowired
    private TestMapper testMapper;

    @Test
    public void findDogs() {
        List<Dog> dogs = testMapper.findDogs();
        System.out.println(dogs.get(0).getTime());


        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = null;
        try {
            jsonStr = mapper.writeValueAsString(dogs.get(0));
            System.out.println(jsonStr);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


//        Gson gson = new Gson();
//        String res = gson.toJson(dogs.get(0));
//        System.out.println(res);
    }
}