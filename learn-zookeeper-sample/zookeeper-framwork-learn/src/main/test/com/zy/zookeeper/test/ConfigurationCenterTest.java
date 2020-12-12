package com.zy.zookeeper.test;

import com.zy.zookeeper.configuration.ConfigurationCenter;
import com.zy.zookeeper.configuration.observer.MysqlConfigurationObserver;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @author: jogin
 * @date: 2020/9/13 18:38
 */
public class ConfigurationCenterTest {

    @Test
    public void testConfiguration() throws IOException {
        MysqlConfigurationObserver observer = new MysqlConfigurationObserver();
        observer.observe();
        ConfigurationCenter center = new ConfigurationCenter();
        center.publishConfiguration();
        System.in.read();
        center.close();
    }
}
