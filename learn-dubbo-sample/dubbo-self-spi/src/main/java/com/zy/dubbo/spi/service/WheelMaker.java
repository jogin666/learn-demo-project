package com.zy.dubbo.spi.service;

import com.alibaba.dubbo.common.extension.SPI;
import com.zy.dubbo.spi.annotation.SelfSPIAdaptive;
import com.zy.dubbo.spi.car.Wheel;


/**
 * @author: jogin
 * @date: 2020/10/24 13:38
 */

@SelfSPIAdaptive
@SPI
public interface WheelMaker {

    Wheel makeWheel(String name);
}
