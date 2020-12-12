package com.zy.dubbo.provider.servie.impl;

import com.zy.dubbo.service.DubboService;

/**
 * @author: jogin
 * @date: 2020/9/12 18:45
 */
public class DubboServiceImpl implements DubboService {

    @Override
    public String say(String name) {
        return "hello " + name;
    }
}
