package com.twx.learn.asspringbootautoconfigure;

import com.twx.learn.IAsCamara;
import org.springframework.beans.factory.annotation.Autowired;

public class BjAsCamera implements IAsCamara {

    @Autowired
    @Override
    public String name() {
        return "BjAsCamera";
    }
}
