package com.twx.learn.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

public class MainApp {
    public static void main(String[] args) {
        ServiceLoader<CutAction> serviceLoader = ServiceLoader.load(CutAction.class);
        Iterator<CutAction> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            CutAction action = iterator.next();
            String res = action.exec("Prd_stream/gy1");
            System.out.println("res=>"+res);
        }
    }
}
