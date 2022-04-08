package com.twx.netty.file.download.client;

/**
 * @author tangwx@soyuan.com.cn
 * @date 2021/12/21 上午9:45
 */
public class ClientApp {
    public static void main(String[] args) throws InterruptedException {
       NettyClient client = NettyClient.getInstance();

        for (int i = 0; i < 1; i++) {
            long start = System.currentTimeMillis();
            client.download("/Users/twx/Documents/test"+i+".mp4","/Users/twx/Documents/jinjie_265.mp4");
            long end = System.currentTimeMillis();
            System.out.println("consumer "+ (end-start));
        }
//        client.download("/Users/twx/Documents/test1.mp4","/Users/twx/Documents/jinjie_265.mp4");
        //connectionClient.sendInSingleChannel("/Users/twx/Movies/stop.avi");


//        TimeUnit.SECONDS.sleep(3);
//        System.out.println("下载第二个...");
//
//        fdClient.setSavePath("/Users/twx/Downloads/netty-test2.avi");
//        connectionClient.sendInSingleChannel("/Users/twx/Movies/stop.avi");
    }
}
