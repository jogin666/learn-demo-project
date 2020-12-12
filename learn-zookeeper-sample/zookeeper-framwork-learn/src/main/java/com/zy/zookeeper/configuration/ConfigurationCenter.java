package com.zy.zookeeper.configuration;


import com.zy.zookeeper.configuration.entity.ConfigurationEntity;
import com.zy.zookeeper.configuration.publisher.ConfigurationPublisher;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * @author: jogin
 * @date: 2020/9/13 9:32
 */
public class ConfigurationCenter {

    private final String ZK_IP = "127.0.0.1:2181";
    private ZooKeeper zk;
    private final String CONFIGURATION_PATH= "/app/configuration/mysql/";

    private CountDownLatch counter = new CountDownLatch(1);

    public void init() {
        try{
            if (zk == null){
                synchronized (this){
                    if (zk == null){
                        zk=new ZooKeeper(ZK_IP,5000,new ConfigurationWatcher());
                        counter.await();

                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void publishConfiguration(){
        try{
            if (zk == null){
                init();
            }
            byte[] urlBytes = zk.getData(CONFIGURATION_PATH+"url",true,null);
            byte[] usernameBytes = zk.getData(CONFIGURATION_PATH+"username",true,null);
            byte[] passwordBytes = zk.getData(CONFIGURATION_PATH + "password", true, null);
            ConfigurationEntity configurationEntity=new ConfigurationEntity(new String(urlBytes),new String(usernameBytes),new String(passwordBytes));
            ConfigurationPublisher.publish(configurationEntity);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    class ConfigurationWatcher implements Watcher {
        @Override
        public void process(WatchedEvent watchedEvent) {
            try{
                if (watchedEvent.getType() == Event.EventType.None){
                    Event.KeeperState state = watchedEvent.getState();
                    if (state == Event.KeeperState.SyncConnected){
                        System.out.println("连接成功");
                        counter.countDown();
                    }else if (state == Event.KeeperState.AuthFailed){
                        System.out.println("认证失败");
                    }else if (state == Event.KeeperState.Disconnected){
                        System.out.println("断开连接");
                    }else if (state == Event.KeeperState.Expired){
                        System.out.println("连接超时");
                        init();
                    }
                }else if (watchedEvent.getType() == Event.EventType.NodeDataChanged){
                    publishConfiguration();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void close(){
        try {
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
