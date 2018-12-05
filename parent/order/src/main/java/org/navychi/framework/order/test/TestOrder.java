//package org.navychi.framework.order.test;
//
//import java.beans.PropertyDescriptor;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.PropertyValues;
//import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
//import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
//import org.springframework.beans.factory.support.RootBeanDefinition;
//import org.springframework.stereotype.Component;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Component
//public class TestOrder extends InstantiationAwareBeanPostProcessorAdapter implements MergedBeanDefinitionPostProcessor {
//
//    @Override
//    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean,
//            String beanName) throws BeansException {
//        log.info("TestOrder postProcessPropertyValues");
//        return pvs;
//    }
//
//    @Override
//    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        log.info("TestOrder postProcessAfterInitialization");
//        return bean;
//    }
//
//    @Override
//    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        log.info("TestOrder postProcessBeforeInitialization");
//        return bean;
//    }
//
//    @Override
//    public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {
//        log.info("TestOrder postProcessMergedBeanDefinition");
//    }
//}
