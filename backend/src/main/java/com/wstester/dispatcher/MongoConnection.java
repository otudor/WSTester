package com.wstester.dispatcher;

import java.net.UnknownHostException;

import org.apache.camel.Exchange;
import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.wstester.model.MongoService;
import com.wstester.model.MongoStep;
import com.wstester.model.Server;

public class MongoConnection {

	@Autowired
	private Mongo mongo;
	
	public String getConnection(String body, @Header(Exchange.SLIP_ENDPOINT) String previous) throws Exception{

		if(previous == null)
			return "mongodb://myDb?database=none&collection=none&dynamicity=true";
		else
			return null;
	}
	
	public void setConnectionBean(MongoStep step) throws UnknownHostException{
		
		MongoClientURI uri = new MongoClientURI(getURI(step));
		mongo = new MongoClient(uri);
	}

	private String getURI(MongoStep step) {
		
		MongoService service = (MongoService) step.getService();
		Server server = step.getServer();
		
		return "mongodb://" + service.getUser() + ":" + service.getPassword() + "@" + server.getIp() + ":" + service.getPort() + "/" + service.getDbName();
	}
}
