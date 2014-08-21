package com.wstester.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
@XmlSeeAlso({MongoService.class, MySQLService.class, RestService.class, SoapService.class})
public abstract class Service implements Serializable {

	private static final long serialVersionUID = 1L;
	protected String uuid;
	protected String name;
	protected ServiceType type;
	
	public String getID() {
		return this.uuid;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public ServiceType getType() {
		return type;
	}

	protected void setType(ServiceType type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Service other = (Service) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
}
