package com.zy.self.enable.analyser.annotation;

import com.zy.self.enable.analyser.registrar.AnalyserBeanRegistrar;

import java.lang.annotation.*;

/**
 * @Author ZY
 * @Date 2020/12/12 15:10
 * @Version 1.0
 */

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SelfImport(AnalyserBeanRegistrar.class)
public @interface EnableSelfAnalyserConfig {
}
