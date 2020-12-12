package com.zy.zookeeper.configuration.observer;

import com.zy.zookeeper.configuration.entity.ConfigurationEntity;

/**
 * @author: jogin
 * @date: 2020/9/13 9:31
 *
 * 配置信息 观察者
 */
public abstract class AbstractConfigurationObserver {

    public abstract void accept(ConfigurationEntity entity);

    public abstract void observe();
}
