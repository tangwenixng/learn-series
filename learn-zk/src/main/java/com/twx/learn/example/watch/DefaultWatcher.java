package com.twx.learn.example.watch;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

@Slf4j
public class DefaultWatcher implements Watcher {
    @Override
    public void process(WatchedEvent event) {
        log.info("DefaultWatcher: {}", event);
    }
}
