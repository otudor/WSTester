package com.wstester.rest;

import javax.ws.rs.core.Cookie;

import com.sun.jersey.api.client.ClientResponse;

public class PostRequest extends Request {

	private Object requestEntity;
	
	public void setRequestEntity(Object request){
		this.requestEntity = request;
	}
	
	public Object getRequestEntity(){
		return this.requestEntity;
	}
	
	@Override
	public ClientResponse sendRequest() {
		
		// set path
		if (getPath().size() > 0)
			for (String path : getPath()) {
				resource = resource.path(path);
			}

		// set query parameters
		if (getParams() != null)
			resource = resource.queryParams(getParams());

		// set header
		if (getHeader() != null && !getHeader().isEmpty()){
			for(String key : getHeader().keySet())
				for(String value : getHeader().get(key))
					if(builder != null)
						builder = builder.header(key, value);
					else
						builder = resource.header(key, value);
		}
		
		// set cookies
		if (getCookies() != null)
			for(Cookie cookie : getCookies())
				if(builder != null)
					builder = builder.cookie(cookie);
				else
					builder = resource.cookie(cookie);

		// send post request
		if (builder != null)
			return builder.post(ClientResponse.class, requestEntity);
		else
			return resource.post(ClientResponse.class, requestEntity);
	}
}
