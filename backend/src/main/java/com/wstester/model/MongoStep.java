package com.wstester.model;

import java.util.HashMap;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MongoStep extends Step{

	private String collection;
	private HashMap<String, String> query;
	
	public MongoStep() {
		uuid = UUID.randomUUID().toString();
	}
	
	public String getCollection() {
		return collection;
	}

	public HashMap<String, String> getQuery() {
		return query;
	}

	public void select(String collection, HashMap<String, String> query){
		this.collection = collection;
		this.query = query;
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
