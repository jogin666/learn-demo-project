package com.zy.zookeeper.test;

import com.zy.zookeeper.lock.ContributedLock;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: jogin
 * @date: 2020/9/6 16:45
 */
public class ContributedLockTest {


    public static void main(String[] args){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 每秒打印信息
        running(() -> {
            for (int i = 0; i < 999999999; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String format = dateTimeFormatter.format(LocalDateTime.now());
                System.out.println(format);
            }
        },1);
        // 线程测试
        running(() -> {
            try{
                ContributedLock distributedLock = new ContributedLock();
                if (distributedLock.acquireLock()){
                    System.out.println("执行业务逻辑......");
                }
                delayOperation();
                distributedLock.releaseLock();
                distributedLock.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        },5);
    }

    public static void running(Runnable runnable,int appNumber){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(30, 30,
                0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
        for (int i = 0; i < appNumber; i++) {
            threadPoolExecutor.execute(runnable::run);
        }
        threadPoolExecutor.shutdown();
    }

    public static void delayOperation(){
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    interface Runnable{
        void run();
    }
}
