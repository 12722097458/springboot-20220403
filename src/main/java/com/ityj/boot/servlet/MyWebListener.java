package com.ityj.boot.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

//@WebListener
@Slf4j
public class MyWebListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("MyWebListener.contextInitialized()...");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("MyWebListener.contextDestroyed()...");
    }
}
