package com.wstester.importer.rest;

import java.util.ArrayList;
import java.util.List;

public class RestResourceApi {

    protected String description;
    protected String path;
    protected int hashCode;
    protected List<RestApi> apiList;

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String value) {
        this.path = value;
    }

    public int getHashCode() {
        return hashCode;
    }

    public void setHashCode(int value) {
        this.hashCode = value;
    }
    
    public List<RestApi> getApi() {
        if (apiList == null) {
            apiList = new ArrayList<RestApi>();
        }
        return this.apiList;
    }


    public void setApi(List<RestApi> api) {
		this.apiList = api;
	}

}