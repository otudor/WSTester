package com.wstester.dispatcher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
	 
	 @Bean(name="stepResult")
	 public ResponseCallback getStepResult(){
		 return new ResponseCallback();
	 }
}
