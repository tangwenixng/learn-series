package com.twx.learn;

import java.util.ArrayList;
import java.util.List;

/**
 * -Xms100m -Xmx100m -XX:+UseSerialGC
 */
public class JConsoleDemo {
    public static void main(String[] args) throws InterruptedException {
        fill(1000);
    }

    static class OOMObject {
        /**
         * 64k
         */
        public byte[] holder = new byte[64 * 1024];
    }

    public static void fill(int num) throws InterruptedException {
        List<HeapOOM.OOMObject> list = new ArrayList<>();

        for (int i = 0; i < num; i++) {
            Thread.sleep(50);
            list.add(new HeapOOM.OOMObject());
        }
        System.gc();
    }
}
