package com.wstester.api;

import java.util.ArrayList;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

public abstract class Request {

	protected WebResource resource;
	protected Builder builder;
	private ArrayList<String> paths = new ArrayList<String>();
	private MultivaluedMap<String, String> params;
	private MultivaluedMap<String, String> header;
	private ArrayList<Cookie> cookies;
	
	public void setClient(WebResource resource){
		this.resource = resource;
	}
	
	public void setParams(MultivaluedMap<String, String> params){
		this.params = params;
	}
	
	public MultivaluedMap<String, String> getParams(){
		return this.params;
	}
	
	public void setPath(String... path){
		for(int i=0; i<path.length; i++)
			this.paths.add(path[i]);
	}
	
	public ArrayList<String> getPath(){
		return paths;
	}
	
	public void setCookies(ArrayList<Cookie> cookies){
		this.cookies = cookies;
	}
	
	public ArrayList<Cookie> getCookies(){
		return this.cookies;
	}
	
	public void setHeader(MultivaluedMap<String, String> header){
		this.header = header;
	}
	
	public MultivaluedMap<String, String> getHeader(){
		return this.header;
	}
	
	public abstract ClientResponse sendRequest();
}
