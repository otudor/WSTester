package com.wstester.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
import com.wstester.services.definition.IAssetManager;

public class AssetManager implements IAssetManager {

	private AbstractXmlApplicationContext camelContext;
	
	/**
	 * <br><br>
	 * Method responsible to create a new Asset entity through JMS(Async) mechanism
	 * </br>
	 * @param the asset
	 */
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
	
	@Override
	public void saveAsset(Asset asset) {
		
	}
	
	public String getAssetContent(Asset asset) {
		
		String content = null;
		
		try {
			content = new String(Files.readAllBytes(Paths.get("assets/" + asset.getName())));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return content;
	}
	
	public void waitUntilFileCopied(Asset asset){
		
		while(!Files.isReadable(Paths.get("assets/" + asset.getName() + ".done"))){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			Files.delete(Paths.get("assets/" + asset.getName() + ".done"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close(){
		camelContext.close();
	}

}
