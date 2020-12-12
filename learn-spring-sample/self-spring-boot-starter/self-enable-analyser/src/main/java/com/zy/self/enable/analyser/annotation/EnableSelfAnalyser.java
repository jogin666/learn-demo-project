package com.zy.self.enable.analyser.annotation;

import java.lang.annotation.*;

/**
 * @Author ZY
 * @Date 2020/12/12 15:09
 * @Version 1.0
 */

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableSelfAnalyserConfig
public @interface EnableSelfAnalyser {
}
