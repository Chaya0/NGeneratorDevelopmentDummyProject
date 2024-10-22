package com.lms.config.quartz;

import java.io.IOException;
import java.util.*;

import org.apache.commons.lang3.ArrayUtils;
import org.quartz.Trigger;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

@Configuration
public class QuartzConfiguration {
	
	private final ApplicationContext applicationContext;
	
	public QuartzConfiguration(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Bean
	public SpringBeanJobFactory springBeanJobFactory() {
		AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		return jobFactory;
	}

	@Bean
	public SchedulerFactoryBean scheduler(Trigger... triggers) throws IOException {
		SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
		
		schedulerFactory.setOverwriteExistingJobs(true);
		schedulerFactory.setAutoStartup(true);
		schedulerFactory.setQuartzProperties(quartzProperties());
		schedulerFactory.setJobFactory(springBeanJobFactory());
		schedulerFactory.setWaitForJobsToCompleteOnShutdown(true);
		
		if (ArrayUtils.isNotEmpty(triggers)) {
			schedulerFactory.setTriggers(triggers);
		}
		return schedulerFactory;
	}

	@Bean
	public Properties quartzProperties() throws IOException {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
		propertiesFactoryBean.afterPropertiesSet();
		return propertiesFactoryBean.getObject();
	}

}
