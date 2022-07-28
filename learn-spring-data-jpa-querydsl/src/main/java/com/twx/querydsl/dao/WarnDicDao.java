package com.twx.querydsl.dao;

import com.twx.querydsl.entity.WarnDic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarnDicDao extends JpaRepository<WarnDic,Integer>, QuerydslPredicateExecutor<WarnDic> {
    List<WarnDic> findByType(String type);
}
