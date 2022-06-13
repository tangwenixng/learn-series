package com.twx.learn.asapplication;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("Start MyBeanFactoryPostProcessor...");
        /*Iterator<String> names = beanFactory.getBeanNamesIterator();
        for (; names.hasNext(); ) {
            System.out.println(names.next());
        }*/
        System.out.println("End MyBeanFactoryPostProcessor...");
    }
}
