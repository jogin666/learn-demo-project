package com.zy.zookeeper.configuration.observer;

import com.zy.zookeeper.configuration.entity.ConfigurationEntity;
import com.zy.zookeeper.configuration.publisher.ConfigurationPublisher;

/**
 * @author: jogin
 * @date: 2020/9/13 18:35
 */
public class MysqlConfigurationObserver extends AbstractConfigurationObserver {

    @Override
    public void accept(ConfigurationEntity entity) {
        System.out.println(entity);
    }

    @Override
    public void observe() {
        ConfigurationPublisher.addObserver(this);
    }
}
