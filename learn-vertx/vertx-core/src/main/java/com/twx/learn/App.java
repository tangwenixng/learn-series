package com.twx.learn;

import com.twx.learn.verticle.MyVerticle;
import com.twx.learn.verticle.WorkVerticle;
import io.vertx.core.*;
import io.vertx.core.file.FileProps;
import io.vertx.core.file.FileSystem;
import io.vertx.core.json.JsonObject;

import java.util.function.Function;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        VertxOptions options = new VertxOptions();
        options.setWorkerPoolSize(10);

        Vertx vertx = Vertx.vertx(options);
        vertx.setPeriodic(1000, id -> System.out.println(Thread.currentThread().getName() + " timer fired!"));
        //这种方式创建的是Standard Verticle；
        //Standard Verticle会分配给一个Event Loop线程执行，并且保证所有的代码都是在相同 Event Loop中执行。
        vertx.deployVerticle(new MyVerticle(), res -> {
            if (res.succeeded()) {
                System.out.println("Deployment MyVerticle id is: " + res.result());
            } else {
                System.out.println("Deployment MyVerticle failed!");
            }
        });
        JsonObject config = JsonObject.of("name", "tim", "directory", "/blah");
        //通过setConfig给verticle传递配置,setWorker设置verticle类型为Worker Verticle
        DeploymentOptions workerVerticleOption = new DeploymentOptions().setWorker(true).setInstances(2).setConfig(config);
        //这种方式创建的是Worker Verticle；
        //Worker Verticle设计用于调用阻塞式代码，它不会阻塞任何 Event Loop。
        //Worker Verticle实例绝对不会同时被多个线程执行， 但它可以在不同时间由不同线程执行。
        vertx.deployVerticle("com.twx.learn.verticle.WorkVerticle", workerVerticleOption, res -> {
            if (res.succeeded()) {
                System.out.println("Deployment WorkVerticle id is: " + res.result());
            } else {
                System.out.println("Deployment WorkVerticle failed!");
            }
        });

        FileSystem fs = vertx.fileSystem();
        Future<FileProps> future = fs.props("/Users/twx/Downloads/ali-check-style.xml");
        future.onComplete(result -> {
            if (result.succeeded()) {
                FileProps fileProps = result.result();
                System.out.println("文件大小: " + fileProps.size());
            } else {
                System.out.println("文件不存在: " + result.cause().getMessage());
            }
        });

        vertx.getOrCreateContext().runOnContext(event -> System.out.println(
                Thread.currentThread().getName()+" This will be executed asynchronously in the same context"));

        //集群模式的Vert.x对象
        /*Vertx.clusteredVertx(options, result -> {
            if (result.succeeded()) {
                Vertx vertxCore = result.result();
            }else {

            }
        });*/
    }
}
