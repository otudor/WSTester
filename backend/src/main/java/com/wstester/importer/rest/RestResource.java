package com.wstester.importer.rest;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class RestResource {

    protected List<RestAuthorization> authorizationList;
    protected List<RestResourceApi> resourceApiList;
    
    public void setAuthorization(
			List<RestAuthorization> authorization) {
		this.authorizationList = authorization;
	}

	public List<RestAuthorization> getAuthorization() {
        if (authorizationList == null) {
            authorizationList = new ArrayList<RestAuthorization>();
        }
        return this.authorizationList;
    }

	public void setResourceApi(
			List<RestResourceApi> resourceApi) {
		this.resourceApiList = resourceApi;
	}

	public List<RestResourceApi> getResourceApi() {
        if (resourceApiList == null) {
        	resourceApiList = new ArrayList<RestResourceApi>();
        }
        return this.resourceApiList;
    }
}
