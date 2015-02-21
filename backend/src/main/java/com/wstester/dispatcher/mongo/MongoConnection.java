package com.wstester.dispatcher.mongo;

import org.apache.camel.Exchange;
import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.wstester.model.MongoService;
import com.wstester.model.MongoStep;
import com.wstester.model.Server;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IProjectFinder;

public class MongoConnection {

	@Autowired
	private Mongo mongo;
	
	public String getConnection(String body, @Header(Exchange.SLIP_ENDPOINT) String previous) {

		if(previous == null)
			return "mongodb://mongoConnection?database=none&collection=none&dynamicity=true";
		else
			return null;
	}
	
	public void setConnectionBean(MongoStep step) throws Exception{
		
		MongoClientURI uri = new MongoClientURI(getURI(step));
		mongo = new MongoClient(uri);
	}

	private String getURI(MongoStep step) throws Exception {
		
		IProjectFinder projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
		
		MongoService service = (MongoService) projectFinder.getServiceById(step.getServiceId());
		Server server = projectFinder.getServerById(step.getServerId());
		
		return "mongodb://" + service.getUser() + ":" + service.getPassword() + "@" + server.getIp() + ":" + service.getPort() + "/" + service.getDbName();
	}
}
