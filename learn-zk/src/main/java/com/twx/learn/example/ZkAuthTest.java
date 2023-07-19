package com.twx.learn.example;

import com.twx.learn.example.watch.DefaultWatcher;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;

public class ZkAuthTest {
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        System.setProperty("java.security.auth.login.config",
                "/Users/twx/code-space/personal/learn-series/learn-zk/src/main/resources/jaas.conf");

        ZooKeeper zk = new ZooKeeper("172.26.1.22:2181", 15000, new DefaultWatcher());

        String actuallyPath = zk.create("/twx1", "abc".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
        System.out.println(actuallyPath);
    }
}
