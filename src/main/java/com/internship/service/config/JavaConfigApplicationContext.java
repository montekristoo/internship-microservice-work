package com.internship.service.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.AbstractRefreshableApplicationContext;

import java.io.IOException;

public class JavaConfigApplicationContext extends AbstractRefreshableApplicationContext {
    public JavaConfigApplicationContext() {
        super();
    }

    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException, IOException {

    }

    @Override
    public void refresh() throws BeansException, IllegalStateException {
        super.refresh();
    }
}
