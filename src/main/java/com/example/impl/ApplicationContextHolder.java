package com.example.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextHolder implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationContextHolder.class);

    private static ApplicationContext ctx;

    public static ApplicationContext getApplicationContext() {

        logger.debug("ApplicationContext method (ApplicationContextHolder) invoked;");

        return ctx;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {

        logger.debug("setApplicationContext method (ApplicationContextHolder) invoked;");

        try {
            ApplicationContextHolder.ctx = applicationContext;
        } catch (BeansException e) {

            logger.error(e.getMessage(), e);
        }
    }
}