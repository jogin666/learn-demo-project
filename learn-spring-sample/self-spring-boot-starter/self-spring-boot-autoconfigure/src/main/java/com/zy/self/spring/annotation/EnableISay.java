package com.zy.self.spring.annotation;

import java.lang.annotation.*;

/**
 * @Author ZY
 * @Date 2020/12/12 13:54
 * @Version 1.0
 */
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableISayConfigRegistrar
@EnableISayConfigSelector
public @interface EnableISay {
}
