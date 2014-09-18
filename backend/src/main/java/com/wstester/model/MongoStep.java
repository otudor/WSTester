package com.wstester.model;

import java.util.HashMap;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import com.wstester.model.Action;

@XmlRootElement
public class MongoStep extends Step{

	private static final long serialVersionUID = 1L;
	private Action action;
	private String collection;
	private HashMap<String, String> query;
	
	public MongoStep() {
		uuid = UUID.randomUUID().toString();
	}

	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public HashMap<String, String> getQuery() {
		return query;
	}

	public void setQuery(HashMap<String, String> query) {
		this.query = query;
	}

	public Action getAction() {
		return action;
	}
	
	public void setAction(Action action) {
		this.action = action;
	}
	
	@Override
	public String detailedToString() {
		return "MongoStep [action=" + action + ", collection=" + collection + ", query=" + query + ", getID()=" + getID() + ", getServer()=" + getServer() + ", getAssertList()=" + getAssertList()
				+ ", getService()=" + getService() + ", getAssetList()=" + getAssetList() + ", getName()=" + getName() + ", getVariableList()=" + getVariableList() + ", getExecutionList()="
				+ getExecutionList() + ", getDependsOn()=" + getDependsOn() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((collection == null) ? 0 : collection.hashCode());
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
		MongoStep other = (MongoStep) obj;
		if (action != other.action)
			return false;
		if (collection == null) {
			if (other.collection != null)
				return false;
		} else if (!collection.equals(other.collection))
			return false;
		if (query == null) {
			if (other.query != null)
				return false;
		} else if (!query.equals(other.query))
			return false;
		return true;
	}
}
