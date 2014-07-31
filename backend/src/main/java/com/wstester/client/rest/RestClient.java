package com.wstester.client.rest;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.wstester.client.Client;

public class RestClient extends Client{

	private ClientResponse response;
	private com.sun.jersey.api.client.Client client;
	private WebResource resource;
	private String endpoint;
	
	public RestClient(String serverURL){
		
		this.endpoint = serverURL;
	}
	
	//TODO construct the login method
	public void login(String username, String password){
		
	}
	
	/**
	 * Use this method when a REST request is made
	 * @param request - any HHTP request from the API
	 * @return the {@link com.sun.jersey.api.client.ClientResponse response} from the server 
	 */
	public ClientResponse sendRestRequest(Request request){
		
		client = com.sun.jersey.api.client.Client.create();
		resource = client.resource(endpoint);
		request.setClient(resource);
		
		if(request instanceof PostRequest || request instanceof GetRequest){
			response = request.sendRequest();
		}
		else{
			throw new IllegalArgumentException("Request type " + request.getClass().getName() + " unkown!");
		}
		
		return response;
	}
	
	public void close(){

		if(client != null)
			client.destroy();
	}
}
