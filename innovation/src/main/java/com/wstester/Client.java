package com.wstester;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.wstester.api.GetRequest;
import com.wstester.api.PostRequest;
import com.wstester.api.Request;

public class Client {

	private ClientResponse response;
	private com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
	private WebResource resource;
	
	public Client(String serverURL){
		
		resource = client.resource(serverURL);
		
	}
	
	//TODO construct the login method
	public void login(String username, String password){
		
	}
	
	public ClientResponse sendRequest(Request request){
		
		request.setClient(resource);
		
		if(request instanceof PostRequest || request instanceof GetRequest){
			response = request.sendRequest();
		}
		else{
			throw new IllegalArgumentException("Request type " + request.getClass().getName() + " unkown!");
		}
		
		return response;
	}
}
