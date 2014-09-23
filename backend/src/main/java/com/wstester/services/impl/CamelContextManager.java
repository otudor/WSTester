package com.wstester.services.impl;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Connection;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CamelContextManager {

	ClassPathXmlApplicationContext camelContext ;
	
	public  void startCamelContext (Session session , Connection connection) throws JMSException{
		
		camelContext = new ClassPathXmlApplicationContext("camel/CamelContext.xml");
		camelContext.start();
		
		// Create a ConnectionFactory
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

		// Create a Connection
		connection = connectionFactory.createConnection();
		connection.start();

		// Create a Session
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}
	
	public void startAssetContext(Session session , Connection connection){
		
	}
	
	public void closeCamelContext(Session session , Connection connection) throws JMSException{
		
			connection.close();
			session.close();
			camelContext.close();
	}
}
