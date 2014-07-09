package com.wstester.client.rs;

import static org.junit.Assert.assertEquals;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.wstester.Client;
import com.wstester.api.PostRequest;
import com.wstester.api.Request;
import com.wstester.server.Main;

public class PostRequestTest {

	private static HttpServer server;
	private static Client client;
 
    @BeforeClass
    public static void setUp() throws Exception {
        server = Main.startRestServer();
        client = new Client("http://localhost:9998");
    }
 
    @AfterClass
    public static void tearDown() throws Exception {
        server.stop();
    }
    
    @Test
    public void stringRequest(){
    	
    	Request request = new PostRequest();
    	request.setPath("customer", "insertCustomer");
    	String name = "Crix";
    	((PostRequest)request).setRequestEntity(name);
    	
		ClientResponse response = client.sendRestRequest(request);
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(name, response.getEntity(String.class));
    }
    
    @Test
    public void jsonRequest() throws JSONException{
    	
    	Request request = new PostRequest();
    	request.setPath("customer", "insertCustomerByJson");
    	JSONObject name = new JSONObject();
    	name.put("name", "Crix");
    	((PostRequest)request).setRequestEntity(name);
    	
    	ClientResponse response = client.sendRestRequest(request);
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(name.get("name"), response.getEntity(JSONObject.class).get("name"));
    }
}
