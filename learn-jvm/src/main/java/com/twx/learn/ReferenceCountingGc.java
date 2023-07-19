package com.twx.learn;

/**
 *  -verbose:gc -XX:+PrintGCDetails
 */
public class ReferenceCountingGc {
    public Object instance = null;

    private static final int _1MB = 1024 * 1024;
    private byte[] bigSize = new byte[2 * _1MB];

    public static void testGc() {
        ReferenceCountingGc a = new ReferenceCountingGc();
        ReferenceCountingGc b = new ReferenceCountingGc();
        a.instance = b;
        b.instance = a;

        a=null;
        b=null;

        System.gc();
    }

    public static void main(String[] args) {
        testGc();
    }
}
