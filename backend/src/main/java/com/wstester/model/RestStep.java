package com.wstester.model;

import java.util.Map;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RestStep extends Step {

	private static final long serialVersionUID = 1L;
	private String path;
	private Map<String,String> query;
	private Map<String,String> cookie;
	private Map<String,String> header;
	private String contentType = "text/plain";
	private RestMethod method;
	private Object request;
	
	public RestStep() {
		uuid = UUID.randomUUID().toString();
	}
	
	public String getContentType() {
		return contentType;
	}
	
	@Override
	public String toString() {
		return super.getName();
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public Map<String, String> getQuery() {
		return query;
	}

	public void setQuery(Map<String, String> query) {
		this.query = query;
	}

	public Map<String, String> getCookie() {
		return cookie;
	}

	public void setCookie(Map<String, String> cookie) {
		this.cookie = cookie;
	}

	public Map<String, String> getHeader() {
		return header;
	}

	public void setHeader(Map<String, String> header) {
		this.header = header;
	}

	public Object getRequest() {
		return request;
	}

	public void setRequest(Object request) {
		this.request = request;
	}

	public RestMethod getMethod() {
		return method;
	}

	public void setMethod(RestMethod method) {
		this.method = method;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	@Override
	public void copyFrom(Step source) {
		
		super.copyFrom(source);
		if(source instanceof RestStep){
			setPath(((RestStep) source).getPath());
			setQuery(((RestStep) source).getQuery());
			setCookie(((RestStep) source).getCookie());
			setHeader(((RestStep) source).getHeader());
			setContentType(((RestStep) source).getContentType());
			setMethod(((RestStep) source).getMethod());
			setRequest(((RestStep) source).getRequest());
		}
	}
	
	@Override
	public String detailedToString() {
		return "RestStep [path=" + path + ", query=" + query + ", cookie=" + cookie + ", header=" + header + ", contentType=" + contentType + ", method=" + method + ", request=" + request
				+ ", getID()=" + getId() + ", getServer()=" + getServerId() + ", getAssertList()=" + getAssertList() + ", getService()=" 
			    + getServiceId() + ", getAssetMap()=" + getAssetMap() + ", getName()=" + getName() + ", getVariableList()=" + getVariableList() + ", getDependsOn()=" + getDependsOn() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((contentType == null) ? 0 : contentType.hashCode());
		result = prime * result + ((cookie == null) ? 0 : cookie.hashCode());
		result = prime * result + ((header == null) ? 0 : header.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((query == null) ? 0 : query.hashCode());
		result = prime * result + ((request == null) ? 0 : request.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		RestStep other = (RestStep) obj;
		if (contentType == null) {
			if (other.contentType != null)
				return false;
		} else if (!contentType.equals(other.contentType))
			return false;
		if (cookie == null) {
			if (other.cookie != null)
				return false;
		} else if (!cookie.equals(other.cookie))
			return false;
		if (header == null) {
			if (other.header != null)
				return false;
		} else if (!header.equals(other.header))
			return false;
		if (method != other.method)
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (query == null) {
			if (other.query != null)
				return false;
		} else if (!query.equals(other.query))
			return false;
		if (request == null) {
			if (other.request != null)
				return false;
		} else if (!request.equals(other.request))
			return false;
		return true;
	}	
}
