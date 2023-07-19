package com.twx.learn.example;

import com.twx.learn.example.watch.DefaultWatcher;
import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class ZkUtils {
    private static final Logger log = LoggerFactory.getLogger(ZkUtils.class);

    public static ZooKeeper zk;
    public static String serverID;

    static {
        try {
            serverID = Integer.toHexString(new Random().nextInt());
            log.info("My Server Id is {}", serverID);
            zk = new ZooKeeper("127.0.0.1:2181", 15000, new DefaultWatcher());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void init() {
        create(ZkPaths.WORKERS);
        create(ZkPaths.TASKS);
        create(ZkPaths.ASSIGN);
    }



    public static List<String> getChildren(String path) {
        try {
            return zk.getChildren(path, false);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void create(String path){
        try {
            String actuallyPath = zk.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT);
            log.info("create znode: [{}] success", actuallyPath);
        } catch (KeeperException e) {
            log.warn(e.getMessage());
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static void create(String path, String data, AsyncCallback.StringCallback cb, Object ctx) {
        zk.create(path, data.getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT,
                cb,
                ctx);
    }


    public static void close() {
        try {
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
