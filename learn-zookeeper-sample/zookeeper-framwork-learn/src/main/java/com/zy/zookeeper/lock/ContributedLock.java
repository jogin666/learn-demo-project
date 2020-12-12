package com.zy.zookeeper.lock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author: jogin
 * @date: 2020/9/6 15:50
 */
public class ContributedLock {

    private static final String ZK_IP = "127.0.0.1:2181";
    private static final String LOCK_ROOT_PATH ="/app/root_lock";
    private static final String LOCK_PREFIX = "lock_";
    private static ZooKeeper zk;
    private String lockPath;

    private static Object mutex = new Object(); // 锁

    private Watcher watcher=(watchedEvent)->{
        if (watchedEvent.getType() == Watcher.Event.EventType.NodeDeleted){
            synchronized (mutex){
                mutex.notifyAll();
            }
        }
    };

    private CountDownLatch counter = new CountDownLatch(1);

    public void init() {
        try{
            if (zk == null){
                synchronized (this){
                    if (zk == null){
                        zk = new ZooKeeper(ZK_IP,5000,(watchedEvent)->{
                            if (watchedEvent.getState() == Watcher.Event.KeeperState.SyncConnected){
                                System.out.println(Thread.currentThread().getName()+" 连接ZK成功");
                                counter.countDown();
                            }
                        });
                        counter.await();
                    }
                }
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public boolean acquireLock() throws IOException, KeeperException, InterruptedException {
        if (zk == null){
            init();
        }
        createLock();
        return attemptLock();
    }

    public boolean releaseLock() throws KeeperException, InterruptedException {
        return deleteLock();
    }

    public void close(){
        try {
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean attemptLock() throws InterruptedException, IOException, KeeperException {
        List<String> pathList = zk.getChildren(LOCK_ROOT_PATH, false);
        pathList.sort(String::compareTo);
//        Collections.sort(pathList);
        int ranking = pathList.indexOf(lockPath.substring(LOCK_ROOT_PATH.length()+1));
        if (ranking == 0){
            System.out.printf(Thread.currentThread().getName()+" 获取锁成功，[%s]\n",lockPath);
            return true;
        }
        // 监视前一个节点
        String path = pathList.get(ranking - 1);
        Stat stat = zk.exists(LOCK_ROOT_PATH+"/"+path, watcher);
        if (stat == null){  // 前一个节点释放了锁
            attemptLock();
        }else{ // 前一个节点在等待锁 或者 是正在执行
            synchronized (mutex){
                System.out.println(Thread.currentThread().getName() + " 进入等待状态");
                mutex.wait();
            }
            System.out.println(Thread.currentThread().getName() + " 被唤醒");
            attemptLock(); //被唤醒后，尝试获取锁
        }
        return true;
    }

    private void createLock() throws KeeperException, InterruptedException {
        if (zk == null){
            init();
        }
        Stat stat = zk.exists(LOCK_ROOT_PATH, false);
        if (stat == null){
            zk.create(LOCK_ROOT_PATH,new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        lockPath = zk.create(LOCK_ROOT_PATH + "/" + LOCK_PREFIX, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.printf(Thread.currentThread().getName()+"创建锁 [%s]\n",lockPath);
    }

    private boolean deleteLock() throws KeeperException, InterruptedException {
        zk.delete(lockPath,-1);
        System.out.printf(Thread.currentThread().getName()+" 释放锁成功，[%s]\n",lockPath);
        return true;
    }
}
