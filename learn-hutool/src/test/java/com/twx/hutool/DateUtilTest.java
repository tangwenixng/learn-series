package com.twx.hutool;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class DateUtilTest {
    @Test
    public void common() {
        //当前时间
        Date date = DateUtil.date();
        System.out.println(date);
        //当前时间
        Date date2 = DateUtil.date(Calendar.getInstance());
        System.out.println(date2);
        //当前时间
        Date date3 = DateUtil.date(System.currentTimeMillis());
        System.out.println(date3);
        //当前时间字符串，格式：yyyy-MM-dd HH:mm:ss
        String now = DateUtil.now();
        System.out.println(now);
        //当前日期字符串，格式：yyyy-MM-dd
        String today= DateUtil.today();
        System.out.println(today);


    }

    @Test
    public void parse() {
        String dateStr = "2017-03-01";
        Date d1 = DateUtil.parse(dateStr);
        System.out.println(d1);

        String dateStr1 = "2017-03-01";
        Date d2 = DateUtil.parse(dateStr1, "yyyy-MM-dd");
        System.out.println(d2);
    }

    @Test
    public void format() {
        String dateStr = "2017-03-01";
        Date date = DateUtil.parse(dateStr);

        //结果 2017/03/01
        String format = DateUtil.format(date, "yyyy/MM/dd");

        //常用格式的格式化，结果：2017-03-01
        String formatDate = DateUtil.formatDate(date);

        //结果：2017-03-01 00:00:00
        String formatDateTime = DateUtil.formatDateTime(date);

        //结果：00:00:00
        String formatTime = DateUtil.formatTime(date);
    }

    /**
     * 日期时间差
     */
    @Test
    public void between() {
        String dateStr1 = "2017-03-01 22:33:23";
        Date date1 = DateUtil.parse(dateStr1);

        String dateStr2 = "2017-04-01 23:33:23";
        Date date2 = DateUtil.parse(dateStr2);

        //相差一个月，31天
        long betweenDay = DateUtil.between(date1, date2, DateUnit.DAY);
        Assert.assertEquals(31,betweenDay);
    }
}
