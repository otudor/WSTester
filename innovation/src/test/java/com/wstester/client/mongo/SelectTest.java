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

public class SelectTest {

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
	public void selectOneRow() throws JSONException{
		
		HashMap<String, String> query = new HashMap<String, String>();
		String name = "HAC";
		String key = "name";
		query.put(key, name);
		
		JSONArray result = client.select("customer", query );
		
		assertEquals(1, result.length());
		assertEquals(name , result.getJSONObject(0).get(key));
	}
	
	@Test
	public void selectMoreRows() throws JSONException{
		
		HashMap<String, String> query = new HashMap<String, String>();
		String name = "ROFLMAO";
		String key = "name";
		query.put(key, name);
		
		JSONArray result = client.select("customer", query );
		
		assertEquals(3, result.length());
		for(int i=0; i<result.length(); i++)
			assertEquals(name , result.getJSONObject(i).get(key));
	}
}
