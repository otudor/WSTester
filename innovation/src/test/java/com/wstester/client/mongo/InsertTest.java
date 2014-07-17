package com.wstester.client.mongo;

import static org.junit.Assert.assertEquals;

import java.net.UnknownHostException;
import java.util.HashMap;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.wstester.client.mongo.MongoDBClient;

public class InsertTest {

static MongoDBClient client;
	
	@BeforeClass
	public static void setUp() throws UnknownHostException{
		
		client = new MongoDBClient("localhost", "27017", "test", "appuser", "apppass");
	}
	
	@AfterClass
	public static void tearDown(){
		
		client.close();
	}
	
	@Test
	public void insertOneRow() throws JSONException{
		
		HashMap<String, String> query = new HashMap<String, String>();
		String name = "Ana";
		String keyName = "name";
		String id = "100";
		String keyId = "id";
		query.put(keyName, name);
		query.put(keyId, id);
		
		JSONArray resultBefore = client.select("customer", query);
		int rowsBefore = resultBefore.length();
		
		client.insert("customer", query );
		
		JSONArray resultAfter = client.select("customer", query);
		
		assertEquals(rowsBefore + 1, resultAfter.length());
		for(int i=0; i<resultBefore.length(); i++){
			assertEquals(name , resultAfter.getJSONObject(i).get(keyName));
			assertEquals(id , resultAfter.getJSONObject(i).get(keyId));
		}
	}
}
