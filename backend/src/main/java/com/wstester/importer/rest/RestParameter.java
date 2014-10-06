package com.wstester.importer.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class RestParameter {

    protected String type;
    protected String description;
    protected int hashCode;
    protected String paramType;
    protected boolean allowMultiple;
    protected boolean required;
    @XmlAttribute(name = "name")
    protected String name;

    public String getType() {
        return type;
    }

    public void setType(String value) {
        this.type = value;
    }
    
    public String getDescription() {
        return description;
    }
    public void setDescription(String value) {
        this.description = value;
    }

    public int getHashCode() {
        return hashCode;
    }

    public void setHashCode(int value) {
        this.hashCode = value;
    }

    public String getParamType() {
        return paramType;
    }

    /**
     * Sets the value of the paramType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParamType(String value) {
        this.paramType = value;
    }

    public boolean getAllowMultiple() {
        return allowMultiple;
    }

    public void setAllowMultiple(boolean b) {
        this.allowMultiple = b;
    }

    public boolean getRequired() {
        return required;
    }

    public void setRequired(boolean b) {
        this.required = b;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }


}