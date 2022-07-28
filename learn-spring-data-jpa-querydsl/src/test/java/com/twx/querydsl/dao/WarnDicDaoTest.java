package com.twx.querydsl.dao;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.twx.querydsl.dto.WarnDicDto;
import com.twx.querydsl.entity.QWarnDic;
import com.twx.querydsl.entity.WarnDic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WarnDicDaoTest {

    @Autowired
    WarnDicDao warnDicDao;

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Test
    public void findByType() {
        int actual = warnDicDao.findByType("warn_level").size();
        assertEquals(3, actual);
    }

    /**
     * 单表: 这种方式直接使用jpa的方式即可
     */
    @Test
    public void queryDsl1() {
        QWarnDic warnDic = QWarnDic.warnDic;
        int count = 0;
        Iterator<WarnDic> wd = warnDicDao.findAll(warnDic.type.eq("warn_level")).iterator();
        for (; wd.hasNext(); ) {
            wd.next();
            count++;
        }
        assertEquals(3, count);
    }

    /**
     * 单表：只需要某些字段;拿到Tuple后需要自己去做映射
     */
    @Test
    public void queryDsl12() {
        QWarnDic warnDic = QWarnDic.warnDic;
        List<Tuple> tuples = jpaQueryFactory.select(warnDic.name, warnDic.code, warnDic.type)
                .from(warnDic)
                .where(warnDic.type.eq("warn_level").and(warnDic.code.eq("first_warn")))
                .fetch();
        assertEquals(1, tuples.size());
    }

    /**
     * 单表: 使用Bean投影
     */
    @Test
    public void queryDslProjectionBean() {
        QWarnDic warnDic = QWarnDic.warnDic;
        List<WarnDicDto> wds = jpaQueryFactory.select(Projections.bean(
                        WarnDicDto.class, warnDic.code, warnDic.name, warnDic.type
                ))
                .from(warnDic)
                .where(warnDic.type.eq("warn_level"))
                .fetch();
        assertEquals(3, wds.size());
    }
    /**
     * 单表: 使用fields投影
     */
    @Test
    public void queryDslProjectionFields() {
        QWarnDic warnDic = QWarnDic.warnDic;
        List<WarnDicDto> wds = jpaQueryFactory.select(Projections.fields(
                        WarnDicDto.class, warnDic.name, warnDic.code, warnDic.type
                ))
                .from(warnDic)
                .where(warnDic.type.eq("warn_level"))
                .fetch();
        assertEquals(3, wds.size());
    }

    @Test
    public void jpaQueryFactoryIsNULL() {
        assertNotNull(jpaQueryFactory);
    }
}