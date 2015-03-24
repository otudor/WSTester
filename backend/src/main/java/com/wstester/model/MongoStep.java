package com.wstester.model;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;
import com.wstester.model.Action;

@XmlRootElement
public class MongoStep extends Step{

	private static final long serialVersionUID = 1L;

	private String collection;
	private String query;
	private Action action;
	
	public MongoStep() {
		uuid = UUID.randomUUID().toString();
	}

	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
		
	public Action getAction() {
		return action;
	}

	public void setAction(Action method) {
		this.action = method;
	}
		
	@Override
	public void copyFrom(Step source) {
		
		super.copyFrom(source);
		if(source instanceof MongoStep){
			
			setCollection(((MongoStep) source).getCollection());
			setQuery(((MongoStep) source).getQuery());
			setAction(((MongoStep) source).getAction());
		}
	}
	
	@Override
	public String detailedToString() {
		return "MongoStep [getServerId()=" + getServerId() + ", getAssertList()=" + getAssertList() + ", getServiceId()=" + getServiceId() + ", getAssetMap()=" + getAssetMap() + ", getName()="
				+ getName() + ", getVariableList()=" + getVariableList() + ", getDependsOn()=" + getDependsOn() + ", hasDataProvider()=" + hasDataProvider() + ", collection=" + collection
				+ ", query=" + query + ", action=" + action + ", uuid=" + uuid + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result
				+ ((collection == null) ? 0 : collection.hashCode());
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