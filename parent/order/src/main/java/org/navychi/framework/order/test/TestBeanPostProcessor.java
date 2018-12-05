package org.navychi.framework.order.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;

@Slf4j
public class TestBeanPostProcessor implements BeanDefinitionRegistryPostProcessor, EnvironmentAware,
        ResourceLoaderAware, BeanClassLoaderAware {

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        log.info("TestBeanPostProcessor {}", "setBeanClassLoader");
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        log.info("TestBeanPostProcessor {}", "postProcessBeanDefinitionRegistry");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        log.info("TestBeanPostProcessor {}", "postProcessBeanFactory");
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        log.info("TestBeanPostProcessor {}", "setResourceLoader");
    }

    @Override
    public void setEnvironment(Environment environment) {
        log.info("TestBeanPostProcessor {}", "setEnvironment");
    }
}
