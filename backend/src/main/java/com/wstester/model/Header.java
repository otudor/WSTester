package com.wstester.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "responseHeader")
public class Header implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    private long id;  //NOPMD
	
	@Column(name = "keyField")
	private String keyField;
	
	@Column(name = "valueField")
	private String valueField;
	
	@ManyToOne
    @JoinColumn(name = "responseId")
    private Response response;

	public Header() {
		super();
	}
	
	public Header(String keyField, String valueField) {
		this.keyField = keyField;
		this.valueField = valueField;
	}
	
	public String getKeyField() {
		return keyField;
	}

	public void setKeyField(String keyField) {
		this.keyField = keyField;
	}

	public String getValueField() {
		return valueField;
	}

	public void setValueField(String valueField) {
		this.valueField = valueField;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "Header [keyField=" + keyField + ", valueField=" + valueField + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((keyField == null) ? 0 : keyField.hashCode());
		result = prime * result + ((response == null) ? 0 : response.hashCode());
		result = prime * result + ((valueField == null) ? 0 : valueField.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Header other = (Header) obj;
		if (keyField == null) {
			if (other.keyField != null)
				return false;
		} else if (!keyField.equals(other.keyField))
			return false;
		if (valueField == null) {
			if (other.valueField != null)
				return false;
		} else if (!valueField.equals(other.valueField))
			return false;
		return true;
	}
}
