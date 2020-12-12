package com.zy.dubbo.spi;

import com.zy.dubbo.spi.annotation.SelfSPIAdaptive;
import com.zy.dubbo.spi.car.Wheel;
import com.zy.dubbo.spi.service.WheelMaker;
import com.zy.dubbo.spi.service.adaptor.WheelMakerAdaptor;

import java.util.Random;

/**
 * @Author ZY
 * @Date 2020/12/12 17:41
 * @Version 1.0
 */
public class SelfDubboSpiApplication {

    public static void main(String[] args) {
        //.......do something
        for (int i = 0; i < 10; i++) {
            fun();
        }
    }

    static void fun() {
        Random random = new Random();
        int choice = random.nextInt(10);
        System.out.println(choice);
        if (WheelMaker.class.isAnnotationPresent(SelfSPIAdaptive.class)) {
            WheelMaker maker = new WheelMakerAdaptor();
            String name = "";
            if (choice % 2 == 1) {
                name = "bus";
            } else{
                name = "car";
            }
            Wheel wheel = maker.makeWheel(name);
            System.out.println(wheel.getName());
        }
    }
}
