package com.twx.learn.learnspringexpansion.importbean;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {
    private ResourceLoader resourceLoader;
    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        boolean person = registry.containsBeanDefinition("person");
        if (!person) {
            System.out.println("Create Person Bean...");
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(Person.class)
                            .addPropertyValue("name", "twx")
                            .addPropertyValue("age", 22);
            registry.registerBeanDefinition("person", builder.getBeanDefinition());
//            GenericBeanDefinition bd = new GenericBeanDefinition();
//            bd.setBeanClass(Person.class);
//            bd.getPropertyValues().add("name", "twx");
//            bd.getPropertyValues().add("age", 22);
//            registry.registerBeanDefinition("person",bd);
        }
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
