package com.wstester.client.mongo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class MongoDBClient {

	private MongoClient mongoClient;
	private DB db;
	
	public MongoDBClient(String hostname, String port, String dbName, String user, String pass) throws UnknownHostException{
		
		// construct the server address
		ServerAddress addr = new ServerAddress(hostname, Integer.parseInt(port));
		
		// construct the credential object
		List<MongoCredential> credentialsList = new ArrayList<MongoCredential>();
		MongoCredential credential = MongoCredential.createMongoCRCredential(user, dbName, pass.toCharArray());
		credentialsList.add(credential);
		
		// construct the client and get the DB
		mongoClient = new MongoClient(addr, credentialsList);
		this.db = mongoClient.getDB(dbName);
	}
	
	public JSONArray select(String collection, HashMap<String, String> query){
		
		DBCollection table = db.getCollection("customer");
		
		DBObject object = new BasicDBObject();
		object.putAll(query);
		
		DBCursor results = table.find(object);
		JSONArray jsonResults = new JSONArray();
		while(results.hasNext()){
			jsonResults.put(results.next().toMap());
		}
		
		results.close();
		
		return jsonResults;
	}
	
	public void insert(String collection, HashMap<String, String> query){
		
		DBCollection table = db.getCollection(collection);
		
		DBObject object = new BasicDBObject();
		object.putAll(query);
		
		table.insert(object);
	}
	
	public void close(){
		
		mongoClient.close();
	}
}
