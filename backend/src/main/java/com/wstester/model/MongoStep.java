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
