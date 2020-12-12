package com.zy.spi.service.com.zy.spi;

import com.zy.spi.service.SPIService;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author: jogin
 * @date: 2020/9/13 9:58
 */
public class SPIApplication {

    public static void main(String[] args) {
        ServiceLoader<SPIService> loader = ServiceLoader.load(SPIService.class);
        Iterator<SPIService> iterator = loader.iterator();
        if (iterator != null){
            iterator.forEachRemaining(service -> System.out.println(service.fun((service.getClass().getSimpleName()))));
        }
    }
}
