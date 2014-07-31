package com.wstester.dispatcher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	 @Bean(name="dispatcher")
	 public Dispatcher getDispacher() {
	        return new DispatcherImpl();
	 }
	 
	 @Bean(name="stepResult")
	 public StepResult getStepResult(){
		 return new StepResult();
	 }
}
