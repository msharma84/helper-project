package com;

import org.springframework.context.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BootApplication{
  
	private static ApplicationContext applicationContext;

	public static void main(String[] args) {
		applicationContext =  SpringApplication.run(SreMonitoringToolApplication.class, args);
		String[] allBeanNames = applicationContext.getBeanDefinitionNames();
        for(String beanName : allBeanNames) {
            System.out.println("Bean name ->"+beanName);
        }
     }
}

