package com.twx.learn.spi;

public interface CutAction {

    String exec(String streamPath);

    void clean();
}
