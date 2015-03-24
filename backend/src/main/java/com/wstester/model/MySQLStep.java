package com.wstester.model;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MySQLStep extends Step {

	private static final long serialVersionUID = 1L;
    private String operation;

	public MySQLStep() {
		uuid = UUID.randomUUID().toString();
	}
	
	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	@Override
	public void copyFrom(Step source) {
		
		super.copyFrom(source);
		if(source instanceof MySQLStep){
			setOperation(((MySQLStep) source).getOperation());
		}
	}
	
	@Override
	public String detailedToString() {
		return "MySQLStep [operation=" + operation + ", ID=" + getId() + ", Server=" + getServerId() + ", AssertList=" + getAssertList() 
				+ ", Service=" + getServiceId() + ", getAssetMap=" + getAssetMap() + ", Name=" + getName() + ", VariableList=" + getVariableList() 
				+ ", DependsOn=" + getDependsOn() + ", hasDataProvider()=" + hasDataProvider() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((operation == null) ? 0 : operation.hashCode());
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
		MySQLStep other = (MySQLStep) obj;
		if (operation == null) {
			if (other.operation != null)
				return false;
		} else if (!operation.equals(other.operation))
			return false;
		return true;
	}
}
