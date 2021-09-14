package com.luxoft.library.utils.jmx.app;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import java.util.Date;

@ManagedResource(objectName = "com.luxoft.utils.jmx.app:category=MBeans,name=AppControllingMBean", description = "App Controlling Bean")
@Component
public class AppControllingMBean {

    private final ApplicationContext context;

    public AppControllingMBean(ApplicationContext context) {
        this.context = context;
    }

    @ManagedOperation
    public void stopApplication() {
        SpringApplication.exit(context);
    }

    @ManagedOperation
    public void getApplicationLifeTime() {
        long startupDate = context.getStartupDate();
        long time = new Date().getTime();
        System.out.println("Application life time is " + (time - startupDate) / 1000 + " seconds");
    }
}
