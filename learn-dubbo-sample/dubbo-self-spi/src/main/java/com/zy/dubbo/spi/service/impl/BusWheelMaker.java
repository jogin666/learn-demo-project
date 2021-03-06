package com.zy.dubbo.spi.service.impl;

import com.zy.dubbo.spi.car.Wheel;
import com.zy.dubbo.spi.service.WheelMaker;

/**
 * @Author ZY
 * @Date 2020/12/12 17:32
 * @Version 1.0
 */
public class BusWheelMaker implements WheelMaker {

    @Override
    public Wheel makeWheel(String name) {
        Wheel wheel = new Wheel();
        wheel.setName("bus wheel");
        return wheel;
    }
}
