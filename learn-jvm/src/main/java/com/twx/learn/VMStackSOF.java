package com.twx.learn;

/**
 * -Xss128k
 */
public class VMStackSOF {
    private int length = 1;

    public void stackLeak() {
        length++;
        stackLeak();
    }

    public static void main(String[] args) {
        VMStackSOF sof = new VMStackSOF();
        try{
            sof.stackLeak();
        }catch (Throwable e) {
            System.out.println(sof.length);
            throw e;
        }

    }
}
