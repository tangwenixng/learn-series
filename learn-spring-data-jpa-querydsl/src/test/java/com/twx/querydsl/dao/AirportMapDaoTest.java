package com.twx.querydsl.dao;

import com.twx.querydsl.entity.AirportMap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AirportMapDaoTest {

    @Autowired
    AirportMapDao airportMapDao;

    @Test
    public void findAll() {
        List<AirportMap> list = airportMapDao.findAll();
        assertEquals(1, list.size());
        assertEquals(2, list.get(0).getId());
    }

    @Test
    public void findById() {
        assertEquals(true, airportMapDao.findById(2).isPresent());
    }

}