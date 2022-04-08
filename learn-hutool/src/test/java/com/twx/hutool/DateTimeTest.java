package com.twx.hutool;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.Month;
import org.junit.Assert;
import org.junit.Test;

public class DateTimeTest {
    @Test
    public void common() {
        DateTime dateTime = new DateTime("2017-01-05 12:34:23", DatePattern.NORM_DATETIME_FORMAT);

        //年，结果：2017
        int year = dateTime.year();
        Assert.assertEquals(2017,year);

        //月份，结果：Month.JANUARY
        Month month = dateTime.monthEnum();

        //日，结果：5
        int day = dateTime.dayOfMonth();
        Assert.assertEquals(5,day);
    }
}
