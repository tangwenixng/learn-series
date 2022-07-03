package com.twx.learn.spi;

public class SoyuanCutAction implements CutAction{

    public SoyuanCutAction() {
        System.out.println("loader SoyuanCutAction...");
    }

    @Override
    public String exec(String streamPath) {
        System.out.println("SoyuanCutAction receive=>"+streamPath);
        return "cut "+streamPath+" success!";
    }

    @Override
    public void clean() {
        System.out.println("execute clean action...");
    }
}
