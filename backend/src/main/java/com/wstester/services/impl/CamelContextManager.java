package com.wstester.services.impl;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.wstester.services.definition.ICamelContextManager;

public class CamelContextManager implements ICamelContextManager {

	private static ClassPathXmlApplicationContext camelContext ;
	
	@Override
	public void startCamelContext() {
		
		camelContext = new ClassPathXmlApplicationContext("camel/CamelContext.xml");
		camelContext.start();
	}
	
	@Override
	public void closeCamelContext() {
		camelContext.close();
		
		while(camelContext.isActive()){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
