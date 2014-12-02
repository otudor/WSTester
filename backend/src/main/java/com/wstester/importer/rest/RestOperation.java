package com.wstester.importer.rest;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class RestOperation {

    
    protected String nickName; 
    protected String method;
    protected List<RestConsume> consumeList;    
    protected String notes;    
    protected List<RestParameter> parameterList;    
    protected List<RestProduce> produceList;    
    protected List<RestResponseMessage> possibleResponseMessageList;    
    protected String summary;
    @XmlAttribute(name = "name")
    protected String name;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String value) {
        this.nickName = value;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String value) {
        this.method = value;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String value) {
        this.notes = value;
    }

    public void setParameter(
			List<RestParameter> parameter) {
		this.parameterList = parameter;
	}

    public List<RestParameter> getParameter() {
        if (parameterList == null) {
            parameterList = new ArrayList<RestParameter>();
        }
        return this.parameterList;
    }
    
    public void setConsume(
			List<RestConsume> consume) {
		this.consumeList = consume;
	}

    public List<RestConsume> getConsume() {
        if (consumeList == null) {
            consumeList = new ArrayList<RestConsume>();
        }
        return this.consumeList;
    }
    
    public List<RestProduce> getProduce() {
        if (produceList == null) {
            produceList = new ArrayList<RestProduce>();
        }
        return this.produceList;
    }
    public void setProduce(
			List<RestProduce> produce) {
		this.produceList = produce;
	}

    public void setPossibleResponseMessage(
			List<RestResponseMessage> possibleResponseMessage) {
		this.possibleResponseMessageList = possibleResponseMessage;
	}
    
    public List<RestResponseMessage> getPossibleResponseMessage() {
        if (possibleResponseMessageList == null) {
        	possibleResponseMessageList = new ArrayList<RestResponseMessage>();
        }
        return this.possibleResponseMessageList;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String value) {
        this.summary = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

}
