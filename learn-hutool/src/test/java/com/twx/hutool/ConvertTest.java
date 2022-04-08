package com.twx.hutool;

import cn.hutool.core.convert.Convert;
import static org.junit.Assert.*;

import cn.hutool.core.date.Month;
import cn.hutool.core.lang.TypeReference;
import org.junit.Test;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ConvertTest {

    @Test
    public void toStr() {
        int a = 1;
        //aStr为"1"
        String aStr = Convert.toStr(a);
        System.out.println(aStr);

        long[] b = {1, 2, 3, 4, 5};
        //bStr为："[1, 2, 3, 4, 5]"
        String bStr = Convert.toStr(b);
        System.out.println(bStr);
    }

    @Test
    public void toIntArray() {
        String[] b = { "1", "2", "3", "4" };
        //结果为Integer数组
        Integer[] intArray = Convert.toIntArray(b);

        long[] c = {1,2,3,4,5};
        //结果为Integer数组
        Integer[] intArray2 = Convert.toIntArray(c);
    }

    @Test
    public void composeTest() {
        byte[] bytes = Convert.hexToBytes("9e1243");
        assertEquals("[-98, 18, 67]",Convert.toStr(bytes));

        long res = Convert.convertTime(1, TimeUnit.SECONDS, TimeUnit.MILLISECONDS);
        assertEquals(1000,res);

        Object[] a = { "a", "你", "好", "", 1 };
        List<String> list = Convert.convert(new TypeReference<List<String>>() {}, a);
        assertEquals("[a, 你, 好, , 1]",list.toString());
    }
}
