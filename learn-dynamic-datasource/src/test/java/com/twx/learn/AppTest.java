package com.twx.learn;

import com.twx.learn.service.DemoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Unit test for simple App.
 */
@SpringBootTest
public class AppTest 
{
    @Autowired
    private DemoService demoService;

    @Test
    public void testDemo() {
        demoService.component();
    }
}
