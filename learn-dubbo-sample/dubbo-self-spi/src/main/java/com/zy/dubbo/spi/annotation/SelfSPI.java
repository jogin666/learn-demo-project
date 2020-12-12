package com.zy.dubbo.spi.annotation;

import java.lang.annotation.*;

/**
 * @Author ZY
 * @Date 2020/12/12 17:40
 * @Version 1.0
 */


@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SelfSPI {
}
