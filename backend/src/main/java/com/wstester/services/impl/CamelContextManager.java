package com.wstester.services.impl;

import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wstester.services.definition.ICamelContextManager;

public class CamelContextManager implements ICamelContextManager {

	private AbstractXmlApplicationContext camelContext;

	@Override
	public void startContext() {
		camelContext = new ClassPathXmlApplicationContext("camel/CamelAssetContext.xml");
	}

	@Override
	public void stopContext() {
		camelContext.close();
	}

}
