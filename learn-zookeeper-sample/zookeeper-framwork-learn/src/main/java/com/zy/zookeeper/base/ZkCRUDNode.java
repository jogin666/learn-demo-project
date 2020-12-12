package com.zy.zookeeper.base;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * @Author ZY
 * @Date 2020/12/12 18:25
 * @Version 1.0
 */
public class ZkCRUDNode {

    private ZooKeeper zk;

    public void before() throws IOException, InterruptedException {
        CountDownLatch count=new CountDownLatch(1);
        zk=new ZooKeeper("localhost",5000,watchedEvent -> {
            if (watchedEvent.getState()== Watcher.Event.KeeperState.SyncConnected){
                count.countDown();
            }
        });
        System.out.println(zk.getSessionId());
        count.await();
    }

    public void after() throws InterruptedException {
        zk.close();
    }

    public void create1() throws KeeperException, InterruptedException {
        String str="node";
        String rs = zk.create("/create/node", str.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(rs);
    }

    public void create2() throws KeeperException, InterruptedException {
        ArrayList<ACL> acls=new ArrayList<>();
        Id id=new Id("ip","ipValue");
        acls.add(new ACL(ZooDefs.Perms.ALL,id));
        zk.create("/create/node1","node2".getBytes(),acls,CreateMode.EPHEMERAL);
    }

    public void create3() throws KeeperException, InterruptedException {
        zk.addAuthInfo("digest","zy:123456".getBytes());
        zk.create("/create/node2","node2".getBytes(),ZooDefs.Ids.CREATOR_ALL_ACL,CreateMode.PERSISTENT);
    }

    public void create4(){
        zk.create("/create/node3","node3".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT,
                (AsyncCallback.StringCallback) (rc, path, name, ctx) -> {
                    System.out.println(rc + " " + path + " " + name +  " " + ctx);
                },"context");
    }

    //
    public void setData1() throws KeeperException, InterruptedException {
        Stat stat = zk.setData("/create/node1", "nodeValue".getBytes(), -1);
    }

    public void setData2(){
        zk.setData("/create/node2", "node2Value".getBytes(), -1, (
                AsyncCallback.StatCallback) (rc, path, ctx, stat) -> {
            // 讲道理，要判空
            System.out.println(rc + " " + path + " " + stat.getVersion() +  " " + ctx);
        },"context");
    }

    public void delete1() throws KeeperException, InterruptedException {
        zk.delete("/create/node1",1);
    }

    public void delete2(){
        zk.delete("/create/node2",1,(rc,path,ctx)->{ System.out.println(rc + " " + path + " " + ctx);},"context");
    }

    public void getData() throws KeeperException, InterruptedException {
        /**
         * arg1 路径
         * arg2 是否使用连接时的监听器
         * arg3 节点数据
         */
        byte[] data = zk.getData("/path", false, new Stat());
    }

    //添加监听器
    public void addWatcher() throws KeeperException, InterruptedException {
        byte[] data = zk.getData("/path", watchedEvent -> {
            try{
                //数据节点发生变化的使用
                Watcher.Event.EventType type = watchedEvent.getType();
                if (type == Watcher.Event.EventType.NodeChildrenChanged) {
                }

                //会话通知状态的使用
                Watcher.Event.KeeperState state = watchedEvent.getState();
                if (state == Watcher.Event.KeeperState.AuthFailed) {

                }
            }finally {
                try{
                    zk.getData("/path",watchedEvent1 -> { },null );
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Stat());
    }
}
