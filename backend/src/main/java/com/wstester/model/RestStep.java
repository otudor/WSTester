package com.wstester.model;

import java.util.HashMap;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RestStep extends Step {

	private static final long serialVersionUID = 1L;
	private String path;
	private HashMap<String,String> query;
	private HashMap<String,String> cookie;
	private HashMap<String,String> header;
	private String contentType = "text/plain";
	private String method;
	private Object request;
	
	public RestStep() {
		uuid = UUID.randomUUID().toString();
	}
	
	public String getContentType() {
		return contentType;
	}
	
	public String toString()
	{
		return super.getName();
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public HashMap<String, String> getQuery() {
		return query;
	}

	public void setQuery(HashMap<String, String> query) {
		this.query = query;
	}

	public HashMap<String, String> getCookie() {
		return cookie;
	}

	public void setCookie(HashMap<String, String> cookie) {
		this.cookie = cookie;
	}

	public HashMap<String, String> getHeader() {
		return header;
	}

	public void setHeader(HashMap<String, String> header) {
		this.header = header;
	}

	public Object getRequest() {
		return request;
	}

	public void setRequest(Object request) {
		this.request = request;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	@Override
	public String detailedToString() {
		return "RestStep [path=" + path + ", query=" + query + ", cookie=" + cookie + ", header=" + header + ", contentType=" + contentType + ", method=" + method + ", request=" + request
				+ ", getID()=" + getID() + ", getServer()=" + getServer() + ", getAssertList()=" + getAssertList() + ", getService()=" + getService() + ", getAssetMap()=" + getAssetMap()
				+ ", getName()=" + getName() + ", getVariableList()=" + getVariableList() + ", getExecutionList()=" + getExecutionList() + ", getDependsOn()=" + getDependsOn() + "]";
	}	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((request == null) ? 0 : request.hashCode());
		result = prime * result	+ ((contentType == null) ? 0 : contentType.hashCode());
		result = prime * result + ((cookie == null) ? 0 : cookie.hashCode());
		result = prime * result + ((header == null) ? 0 : header.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((query == null) ? 0 : query.hashCode());
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
		if (request == null) {
			if (other.request != null)
				return false;
		} else if (!request.equals(other.request))
			return false;
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
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
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
		return true;
	}
}
