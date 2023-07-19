package com.twx.learn;

import io.vertx.core.*;
import io.vertx.core.file.FileProps;
import io.vertx.core.file.FileSystem;

import java.util.function.Function;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        VertxOptions options = new VertxOptions();
        options.setWorkerPoolSize(10);

        Vertx vertx = Vertx.vertx(options);
        vertx.setPeriodic(1000, id -> System.out.println(Thread.currentThread().getName()+" timer fired!"));

        FileSystem fs = vertx.fileSystem();
        Future<FileProps> future = fs.props("/Users/twx/Downloads/ali-check-style.xml");
        future.onComplete(result -> {
            if (result.succeeded()) {
                FileProps fileProps = result.result();
                System.out.println("文件大小: "+fileProps.size());
            }else {
                System.out.println("文件不存在: "+result.cause().getMessage());
            }
        });

        //集群模式的Vert.x对象
        /*Vertx.clusteredVertx(options, result -> {
            if (result.succeeded()) {
                Vertx vertxCore = result.result();
            }else {

            }
        });*/
    }
}
