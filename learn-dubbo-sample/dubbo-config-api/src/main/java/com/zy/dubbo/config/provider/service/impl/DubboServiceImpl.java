package com.zy.dubbo.config.provider.service.impl;

import com.zy.dubbo.service.DubboService;

/**
 * @author: jogin
 * @date: 2020/9/12 17:35
 */
public class DubboServiceImpl implements DubboService {

    @Override
    public String say(String name) {
        return "Hello " + name;
    }
}
