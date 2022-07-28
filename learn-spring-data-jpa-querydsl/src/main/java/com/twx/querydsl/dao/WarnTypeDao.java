package com.twx.querydsl.dao;

import com.twx.querydsl.entity.WarnType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WarnTypeDao extends JpaRepository<WarnType,Integer>, QuerydslPredicateExecutor<WarnType> {
}
