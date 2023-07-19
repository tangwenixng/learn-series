package com.twx.learn.example;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.twx.learn.example.ZkUtils.serverID;
import static com.twx.learn.example.ZkUtils.zk;

public class Master implements Watcher {
    private static final Logger log = LoggerFactory.getLogger(Master.class);

    private List<String> workersCache;
    /**
     * 创建master回调
     */
    private AsyncCallback.StringCallback createMasterCb = (rc, path, ctx, name) -> {
        switch (KeeperException.Code.get(rc)) {
            case CONNECTIONLOSS:
                createMaster();
                break;
            case OK:
                log.info("{} become [>>>MASTER<<<]!", ctx);
                if (Role.work == true) {
                    //如果之前该节点是worker
                    new Worker().deleteMySelf();
                }
                Role.master = true;
                Role.work = false;
                //get workers
                getWorkers();
                //get tasks
                getTasks();
                break;
            case NODEEXISTS:
                //注册work
                log.info("Obtain Master failure...");
                new Worker().register();
                break;
            default:
                //something error
                log.error("Something error!");
                break;
        }
    };
    public void createMaster() {
        zk.create(ZkPaths.MASTER, serverID.getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL,
                createMasterCb,
                serverID);
    }

    /**
     * 获取workers列表
     */
    AsyncCallback.ChildrenCallback getWorkerChildrenCb = new AsyncCallback.ChildrenCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx, List<String> children) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    getWorkers();
                    break;
                case OK:
                    log.info("Successfully get a list of workers: {} workers", children.size());
                    if (workersCache == null) {
                        workersCache = new ArrayList<>(children.size());
                        workersCache.addAll(workersCache);
                    }else {
                        //宕机的worker;
                        List<String> crashWorker = workersCache.stream().filter(w -> !children.contains(w)).collect(Collectors.toList());
                        //todo 重新分配任务
                        workersCache = children;
                    }
                    break;
                default:
                    //something error
                    log.error("Something error!");
                    break;
            }
        }
    };
    public void getWorkers() {
        zk.getChildren(ZkPaths.WORKERS, this, getWorkerChildrenCb, null);
    }

    /**
     * 获取任务列表
     */
    AsyncCallback.ChildrenCallback getTaskChildrenCb = (rc, path, ctx, children) -> {
        switch (KeeperException.Code.get(rc)) {
            case CONNECTIONLOSS:
                getTasks();
                break;
            case OK:
                log.info("Master get tasks:  {}",children);
                if (children != null) {
                    assignTasks(children);
                }
                break;
            default:
                //something error
                log.error("Something error!");
                break;
        }
    };
    public void getTasks() {
        zk.getChildren(ZkPaths.TASKS, this, getTaskChildrenCb, serverID);
    }


    public void assignTasks(List<String> tasks) {
        for (String task : tasks) {
            getTaskData(task);
        }
    }

    /**
     * 获取任务回调
     */
    AsyncCallback.DataCallback taskDataCb = new AsyncCallback.DataCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    getTaskData((String) ctx);
                    break;
                case OK:
                    //随机选择一个worker,然后将此任务分配过去
                    if (workersCache != null && workersCache.size() > 0) {
                        int workerIdx = new Random().nextInt(workersCache.size());
                        log.info("workersCache size: {}, workerIdx: {}",workersCache.size(), workerIdx);
                        String worker = workersCache.get(workerIdx);
                        String assignPath = ZkPaths.ASSIGN+"/"+worker+"/"+(String) ctx;
                        createAssignment(assignPath, data);
                    }
                    break;
                default:
                    //something error
                    log.error("Something error!");
                    break;
            }
        }
    };
    public void getTaskData(String task) {
        zk.getData(ZkPaths.TASKS+"/"+task, false, taskDataCb, task);
    }


    AsyncCallback.StringCallback createAssignmentCb = (rc, path, ctx, name) -> {
        switch (KeeperException.Code.get(rc)) {
            case CONNECTIONLOSS:
                createAssignment(path,(byte[])ctx);
                break;
            case OK:
                log.info("Task assign successfully: {}", path);
                String hasAssignTaskPath = ZkPaths.TASKS+path.substring(path.lastIndexOf("/"));
                deleteHasAssignTask(hasAssignTaskPath);
                break;
            default:
                //something error
                log.error("Something error!");
                break;
        }
    };
    public void createAssignment(String path, byte[] data) {
        log.info("master assign task to: {}", path);
        zk.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT, createAssignmentCb, data);
    }


    public void deleteHasAssignTask(String path) {
        try {
            log.info("delete assign success node: {}",path);
            zk.delete(path, -1);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        } catch (KeeperException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void process(WatchedEvent event) {
        log.info("Master WatchedEvent: {}",event);
        String path = event.getPath();
        if (event.getType().equals(Event.EventType.NodeChildrenChanged)) {
            if (path.equals(ZkPaths.TASKS)) {
                //有新任务来了,重新拉取
                getTasks();
            } else if (path.equals(ZkPaths.WORKERS)) {
                //worker有变化
                getWorkers();
            }
        }
    }
}
