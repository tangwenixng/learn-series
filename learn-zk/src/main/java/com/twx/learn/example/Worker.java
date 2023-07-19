package com.twx.learn.example;

import com.sun.jmx.snmp.SnmpNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;

import java.util.List;

import static com.twx.learn.example.ZkUtils.serverID;
import static com.twx.learn.example.ZkUtils.zk;
@Slf4j
public class Worker implements Watcher {

    /**
     * 注册worker回调
     */
    AsyncCallback.StringCallback workerRegisterCb = (rc, path, ctx, name) -> {
        switch (KeeperException.Code.get(rc)) {
            case CONNECTIONLOSS:
                register();
                break;
            case OK:
                Role.work = true;
                log.info("[{}] become >>>Worker<<<<!", ctx);
                //create znode: [/app/assign/serverId]
                //prepare receive task
                createAssignServerId();
                watchMaster();
                break;
            case NODEEXISTS:
                log.warn("znode: {}已经存在!", path);
                watchMaster();
                break;
            default:
                //something error
                log.error("Something error!");
                break;
        }
    };
    public void register() {
        log.info("Now this Node is registering Worker...");
        zk.create(ZkPaths.WORKERS+"/"+serverID,
                serverID.getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL,
                workerRegisterCb, serverID);
    }

    AsyncCallback.StringCallback createAssignServerIdCb = new AsyncCallback.StringCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    createAssignServerId();
                    break;
                case OK:
                    log.info("Successfully Create path: {}", path);
                    getAssignTasks();
                    break;
                case NODEEXISTS:
                    log.warn("znode: {}已经存在!", path);
                    break;
                default:
                    //something error
                    log.error("Something error!");
                    break;
            }
        }
    };
    public void createAssignServerId() {
        zk.create(ZkPaths.ASSIGN + "/" + serverID, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT, createAssignServerIdCb, null);
    }

    /**
     * 检测master是否存在
     */
    AsyncCallback.StatCallback masterExistCb = (rc, path, ctx, stat) -> {
        switch (KeeperException.Code.get(rc)) {
            case CONNECTIONLOSS:
                watchMaster();
                break;
            case OK:
                log.info("watcher master success!");
                break;
            default:
                //something error
                log.error("Something error!");
                break;

        }
    };
    public void watchMaster() {
        zk.exists(ZkPaths.MASTER, this, masterExistCb, "");
    }

    AsyncCallback.VoidCallback deleteMySelfCb = new AsyncCallback.VoidCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    deleteMySelf();
                    break;
                case OK:
                    log.info("delete worker: {}", path);
                    break;
                default:
                    //something error
                    log.error("Something error!");
                    break;

            }
        }
    };
    public void deleteMySelf() {
        zk.delete(ZkPaths.WORKERS + "/" + serverID, -1, deleteMySelfCb, null);
    }


    AsyncCallback.ChildrenCallback getAssignTasksCb = new AsyncCallback.ChildrenCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx, List<String> children) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    getAssignTasks();
                    break;
                case OK:
                    if (children != null && children.size()>0) {
                        log.info("Worker Get Assign Task: {}",children);
                    }
                    break;
                default:
                    //something error
                    log.error("Something error!");
                    break;

            }
        }
    };
    public void getAssignTasks() {
        zk.getChildren(ZkPaths.ASSIGN + "/" + serverID, this, getAssignTasksCb, null);
    }

    @Override
    public void process(WatchedEvent event) {
        Event.EventType type = event.getType();
        if (type.equals(Event.EventType.NodeDeleted)) {
            if (event.getPath().equals(ZkPaths.MASTER)) {
                log.info("检测到{}被删除,将重新选举Master!", event.getPath());
                new Master().createMaster();
            }
        } else if (type.equals(Event.EventType.NodeChildrenChanged)) {
            if (event.getPath().equals(ZkPaths.ASSIGN + "/" + serverID)) {
                getAssignTasks();
            }
        }
    }
}
