package com.wstester.services.definition;

import org.springframework.context.support.AbstractXmlApplicationContext;

import com.wstester.services.common.Stateful;

@Stateful
public interface ICamelContextManager extends IService{

	void startCamelContext();
	
	boolean isStarted();
	
	void closeCamelContext();
	
	AbstractXmlApplicationContext getCamelContext();
}
