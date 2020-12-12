package com.zy.zookeeper.test;

import com.zy.zookeeper.id.GlobalUniqueIdGenerator;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author: jogin
 * @date: 2020/9/13 19:09
 */
public class GlobalUniqueGeneratorTest {

    @Test
    public void testGenerated() throws InterruptedException, IOException {
        ExecutorService threadPool = Executors.newFixedThreadPool(6);
        for (int i = 0;i < 50; i++){
            threadPool.submit(()-> System.out.println(GlobalUniqueIdGenerator.generateId()));
        }
        threadPool.awaitTermination(5000, TimeUnit.MILLISECONDS);
        threadPool.shutdown();
        GlobalUniqueIdGenerator.close();
    }

}
