package com.wstester.asset;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wstester.model.Asset;

public class AssetManager {

	private AbstractXmlApplicationContext camelContext;
	
	public AssetManager(){
		
	}
	
	public void addAsset(Asset asset){
		
		try{
			camelContext = new ClassPathXmlApplicationContext("camel/CamelAssetContext.xml");
			
			// Create a ConnectionFactory
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
	
			// Create a Connection
			Connection connection = connectionFactory.createConnection();
			connection.start();
	
			// Create a Session
			Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
	
			// Create the destination (Topic or Queue)
			Destination destination = session.createQueue("assetQueue");
	
			// Create a MessageProducer from the Session to the Topic or Queue
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			
			// Create a messages
	        ObjectMessage message = session.createObjectMessage(asset);
	        
	        // Tell the producer to send the message
	        System.out.println("Sent asset: "+  asset.getID());	
	        producer.send(message);
	        
	        session.close();
			connection.close();
		}catch (Exception e){
			
		}
	}
	
	public void close(){
		camelContext.close();
	}
}
