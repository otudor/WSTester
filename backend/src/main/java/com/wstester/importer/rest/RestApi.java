package com.wstester.importer.rest;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class RestApi {

    
    protected String description;   
    protected String path;    
    protected int hashCode;    
    protected List<RestOperation> operationList;
    @XmlAttribute(name = "name")
    protected String name;

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

    public void setOperation(
			List<RestOperation> operation) {
		this.operationList = operation;
	}
	
    public List<RestOperation> getOperation() {
        if (operationList == null) {
            operationList = new ArrayList<RestOperation>();
        }
        return this.operationList;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

}