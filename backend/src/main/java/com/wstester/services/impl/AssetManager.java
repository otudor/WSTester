package com.wstester.services.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.wstester.log.CustomLogger;
import com.wstester.model.Asset;
import com.wstester.services.definition.IAssetManager;
import com.wstester.util.ProjectProperties;

public class AssetManager implements IAssetManager {

	private CustomLogger log = new CustomLogger(AssetManager.class);
	
	@Override
	public void addAsset(Asset asset){
		
		try{
			log.info("Adding asset: " + asset);
			
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
	        log.info(asset.getId(), "Sent asset to asset queue");	
	        producer.send(message);
	        
	        session.close();
			connection.stop();
		}catch (Exception e){
			log.error("Can't create ActiveMQ instance: " + e.getMessage());
		}
	}
	
	@Override
	public void saveAsset(Asset asset, String content) {
		
		try {
			Files.write(Paths.get("assets/" + asset.getName()), content.getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			log.error("File does not exist");
			e.printStackTrace();
		}
	}
	
	@Override
	public String getAssetContent(String fileName) {
		
		String content = null;
		
		try {
			content = new String(Files.readAllBytes(Paths.get("assets/" + fileName)), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return content;
	}
	

	@Override
	public List<String> getCSVrow(String fileName, int column) throws IOException {
		
		List<String> columnRecords = new ArrayList<String>();
		
		File csvData = new File("assets/" + fileName);
		CSVParser parser = CSVParser.parse(csvData, StandardCharsets.UTF_8, CSVFormat.RFC4180);
		
		List<CSVRecord> records = parser.getRecords();
		for(CSVRecord record : records) {
			columnRecords.add(record.get(column - 1));
		}
		
		return columnRecords;
	}
	
	@Override
	public boolean waitUntilFileCopied(Asset asset){
		
		ProjectProperties properties = new ProjectProperties();
		Long timeout = properties.getLongProperty("assetCopyTimeout");
		while(!Files.isReadable(Paths.get("assets/" + asset.getName() + ".done")) && timeout > 0){
			try {
				timeout -= 1000;
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		try {
			if(timeout > 0){
				Files.delete(Paths.get("assets/" + asset.getName() + ".done"));
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
