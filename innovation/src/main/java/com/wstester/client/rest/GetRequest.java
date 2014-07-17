package com.wstester.client.rest;

import javax.ws.rs.core.Cookie;
import com.sun.jersey.api.client.ClientResponse;

public class GetRequest extends Request {

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
		
		// send get request
		if (builder != null)
			return builder.get(ClientResponse.class);
		else
			return resource.get(ClientResponse.class);
	}

}
