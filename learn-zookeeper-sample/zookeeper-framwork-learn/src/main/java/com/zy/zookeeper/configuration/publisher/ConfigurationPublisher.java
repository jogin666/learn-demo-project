package com.zy.zookeeper.configuration.publisher;

import com.zy.zookeeper.configuration.entity.ConfigurationEntity;
import com.zy.zookeeper.configuration.observer.AbstractConfigurationObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: jogin
 * @date: 2020/9/13 9:29
 *
 * 发布/更新 配置信息
 */

public class ConfigurationPublisher {

    private static List<AbstractConfigurationObserver> observerList = new ArrayList<>();

    public static void addObserver(AbstractConfigurationObserver observer){
        observerList.add(observer);
    }

    public static void publish(ConfigurationEntity entity){
        observerList.forEach(observer -> observer.accept(entity));
    }
}
