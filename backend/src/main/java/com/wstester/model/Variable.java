package com.wstester.model;

import java.io.Serializable;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Variable implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private VariableType type;
	private String content;
	private String selector;
	
	public Variable(){
		id = UUID.randomUUID().toString();
	}
	
	/**
	 * Use this constructor when the content is dynamically constructed
	 * @param name The name of the variable
	 * @param type The type of the variable
	 * @param selector How the content will be populated
	 */
	
	public Variable(String selector, String name) {
		id = UUID.randomUUID().toString();
		this.selector = selector;
		this.name = name;
	}

	/**
	 * Use this constructor for global variables
	 * @param name The name of the variable
	 * @param type The type of the variable
	 * @param content The content of the variable(this content should not change between tests)
	 */
	public Variable(String name, VariableType type, String content){
		id = UUID.randomUUID().toString();
		setName(name);
		setType(type);
		setContent(content);
	}
	
	public Variable(Variable variable) {
		
		this.id = variable.getId();
		this.name = variable.getName();
		this.type = variable.getType();
		this.selector = variable.getSelector();
		this.content = variable.getContent();
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public VariableType getType() {
		return type;
	}

	public void setType(VariableType type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSelector() {
		return selector;
	}

	public void setSelector(String selector) {
		this.selector = selector;
	}

	@Override
	public String toString() {
		return "Variable [uuid=" + id + ", name=" + name + ", type=" + type + ", content=" + content + ", selector=" + selector + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((selector == null) ? 0 : selector.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Variable other = (Variable) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (selector == null) {
			if (other.selector != null)
				return false;
		} else if (!selector.equals(other.selector))
			return false;
		if (type != other.type)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
