package com.twx.hutool;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.lang.Console;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class TimeIntervalTest {
    /**
     * 计时
     * @throws InterruptedException
     */
    @Test
    public void common() throws InterruptedException {
        TimeInterval timer = DateUtil.timer();
        TimeUnit.SECONDS.sleep(1);
        Console.log("{}",timer.intervalRestart());
        TimeUnit.SECONDS.sleep(1);
        Console.log("{}",timer.interval());
    }
}
