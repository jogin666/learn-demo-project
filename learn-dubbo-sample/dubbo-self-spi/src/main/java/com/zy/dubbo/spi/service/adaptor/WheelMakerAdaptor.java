package com.zy.dubbo.spi.service.adaptor;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.zy.dubbo.spi.car.Wheel;
import com.zy.dubbo.spi.service.WheelMaker;


/**
 * @author: jogin
 * @date: 2020/10/24 13:42
 */
public class WheelMakerAdaptor implements WheelMaker {

    @Override
    public Wheel makeWheel(String name) {
        if (name==null || name.isEmpty()){
            throw new RuntimeException();
        }
        WheelMaker wheelMaker= ExtensionLoader.getExtensionLoader(WheelMaker.class).getExtension(name);
        return wheelMaker.makeWheel(name);
    }
}
