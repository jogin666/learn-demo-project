package com.zy.zookeeper.id;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author: jogin
 * @date: 2020/9/6 15:27
 */
public class GlobalUniqueIdGenerator {

    private static final String ZK_IP = "";
    private static ZooKeeper zk;
    private static final String ID_GENERATE_PATH = "/app/Id";
    private static final String GENERATE_ID_VALUE = "";

    private static CountDownLatch counter = new CountDownLatch(1);

    public static void init() {
        try{
            if (zk == null){  //防止并发问题
                synchronized (GlobalUniqueIdGenerator.class){
                    if (zk == null){
                        zk=new ZooKeeper(ZK_IP,5000,new GenerateUniqueIdWatcher());
                        counter.await();
                    }
                }
            }
        }catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public static String generateId(){
        String id = null;
        try {
            if (zk == null){
                init();
            }
            // id = /app/Id/generate/Id00000000 [注：递增]
            String path = zk.create(ID_GENERATE_PATH, GENERATE_ID_VALUE.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            id=path.substring(ID_GENERATE_PATH.length());
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        return id;
    }

    public static void close(){
        try {
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class GenerateUniqueIdWatcher implements Watcher {
        @Override
        public void process(WatchedEvent watchedEvent) {
            try{
                if (watchedEvent.getType() == Event.EventType.None){
                    Event.KeeperState state = watchedEvent.getState();
                    if (state == Event.KeeperState.SyncConnected){
                        counter.countDown();
                        System.out.println("连接成功");
                    }else if (state == Event.KeeperState.AuthFailed){
                        System.out.println("认证失败");
                    }else if (state == Event.KeeperState.Disconnected){
                        System.out.println("断开连接");
                    }else if (state == Event.KeeperState.Expired){
                        System.out.println("连接超时");
                        init();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
