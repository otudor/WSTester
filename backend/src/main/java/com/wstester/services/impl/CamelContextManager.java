package com.wstester.services.impl;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wstester.log.CustomLogger;
import com.wstester.services.definition.ICamelContextManager;

public class CamelContextManager implements ICamelContextManager {

	private ClassPathXmlApplicationContext camelContext ;
	private CustomLogger log = new CustomLogger(CamelContextManager.class);
	
	@Override
	public void startCamelContext() {
		
		if(!isStarted()){
			camelContext = new ClassPathXmlApplicationContext("camel/CamelContext.xml");
			camelContext.start();
		}
		else {
			log.info("Camel context already started");
		}
	}
	
	@Override
	public boolean isStarted(){
		
		if(camelContext != null && camelContext.isRunning()){
			return true;
		}
		
		return false;
	}
	
	@Override
	public void closeCamelContext() {
		if(camelContext == null)
			return;
		
		camelContext.close();
		
		while(camelContext.isRunning()){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
