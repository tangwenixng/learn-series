package com.twx.querydsl.dao;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.twx.querydsl.dto.WarnTypeDto;
import com.twx.querydsl.entity.QWarnDic;
import com.twx.querydsl.entity.QWarnType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class WarnTypeDaoTest {
    @Autowired
    WarnTypeDao warnTypeDao;

    @Autowired
    JPAQueryFactory queryFactory;

    @Test
    public void multiTable() {
        QWarnType warnType = QWarnType.warnType;
        QWarnDic warnDic = QWarnDic.warnDic;
        List<Tuple> list = queryFactory.select(warnType.name, warnType.typeCode, warnDic.name)
                .from(warnType)
                .innerJoin(warnDic)
                .on(warnType.behaviorId.eq(warnDic.id))
//                .where(warnType.id.eq(9))
                .fetch();
        list.forEach(System.out::println);
        assertEquals(17, list.size());
    }

    /**
     * 测试两个表有相同名字的字段
     */
    @Test
    public void multiTableProjectBean() {
        QWarnType warnType = QWarnType.warnType;
        QWarnDic warnDic = QWarnDic.warnDic;
        List<WarnTypeDto> list = queryFactory.select(Projections.bean(
                        WarnTypeDto.class, warnType.name, warnType.typeCode, warnDic.name.as("dicName")
                ))
                .from(warnType)
                .innerJoin(warnDic)
                .on(warnType.behaviorId.eq(warnDic.id))
                .fetch();
        assertEquals(17, list.size());
    }

    /**
     * 通过构造器注入有相同名称的列
     */
    @Test
    public void multiTableProjectConstruct() {
        QWarnType warnType = QWarnType.warnType;
        QWarnDic warnDic = QWarnDic.warnDic;
        List<WarnTypeDto> list = queryFactory.select(Projections.constructor(
                        WarnTypeDto.class, warnType.name, warnType.typeCode, warnDic.name
                ))
                .from(warnType)
                .innerJoin(warnDic)
                .on(warnType.behaviorId.eq(warnDic.id))
                .fetch();
        assertEquals(17, list.size());
    }

}