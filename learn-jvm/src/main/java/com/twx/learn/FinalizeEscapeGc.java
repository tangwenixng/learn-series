package com.twx.learn;

public class FinalizeEscapeGc {
    public static FinalizeEscapeGc SAVE_HOOK = null;

    public void isAlive() {
        System.out.println("Yes!I'm alive!");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("trigger finalize!");
        //重新赋值引用
        SAVE_HOOK = this;
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK = new FinalizeEscapeGc();

        SAVE_HOOK = null;
        System.gc();
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        }else {
            System.out.println("I'm dead");
        }
        System.out.println("*******");

        SAVE_HOOK = null;
        System.gc();
        //finalize只会被触发一次
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        }else {
            System.out.println("I'm dead");
        }

    }
}
