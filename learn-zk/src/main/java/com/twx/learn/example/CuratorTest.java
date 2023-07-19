package com.twx.learn.example;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.net.InetAddress;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CuratorTest {
    public static void main(String[] args) throws Exception {
        System.setProperty("java.security.auth.login.config",
                "/Users/twx/code-space/personal/learn-series/learn-zk/src/main/resources/jaas.conf");

        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework cf = CuratorFrameworkFactory.newClient("172.26.1.22:2181", retryPolicy);
        cf.start();

        String id = "client#" + InetAddress.getLocalHost().getHostAddress();
        LeaderLatch leaderLatch = new LeaderLatch(cf, "/m1", id);
        LeaderLatchListener leaderLatchListener = new LeaderLatchListener() {
            @Override
            public void isLeader() {
                System.out.println("[LeaderLatch]我是主节点, id={}" + leaderLatch.getId());
            }

            @Override
            public void notLeader() {
                System.out.println("[LeaderLatch]我不是主节点, id={}" + leaderLatch.getId());
            }
        };
        leaderLatch.addListener(leaderLatchListener);
        leaderLatch.start();

//        cf.create().forPath("/twx3", "abcd".getBytes());

        TimeUnit.SECONDS.sleep(30);
        cf.close();
    }
}
