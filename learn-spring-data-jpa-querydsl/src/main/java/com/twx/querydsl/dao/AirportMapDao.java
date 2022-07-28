package com.twx.querydsl.dao;

import com.twx.querydsl.entity.AirportMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportMapDao extends JpaRepository<AirportMap,Integer> {
}
