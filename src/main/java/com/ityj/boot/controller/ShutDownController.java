package com.ityj.boot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ShutDownController implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @GetMapping("/shutdown")
    public void shutDownContext() {
        ConfigurableApplicationContext ctx = (ConfigurableApplicationContext) applicationContext;
        log.warn("Context is shutdown...");
        ctx.close();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
