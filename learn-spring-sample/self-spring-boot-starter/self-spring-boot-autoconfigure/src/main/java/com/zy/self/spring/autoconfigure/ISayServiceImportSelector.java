package com.zy.self.spring.autoconfigure;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.function.Predicate;

/**
 * @Author ZY
 * @Date 2020/12/12 14:01
 * @Version 1.0
 */
public class ISayServiceImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{"com.zy.self.spring.template.SecondISayTemplate"};
    }
}
