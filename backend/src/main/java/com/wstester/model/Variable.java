package com.wstester.model;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Variable {

	private String uuid;
	private String name;
	private VariableType type;
	private Object content;
	private String selector;
	
	public Variable(){
		uuid = UUID.randomUUID().toString();
	}
	
	/**
	 * Use this constructor when the content is dynamically constructed
	 * @param name The name of the variable
	 * @param type The type of the variable
	 * @param selector How the content will be populated
	 */
	public Variable(String name, VariableType type, String selector) {
		uuid = UUID.randomUUID().toString();
		setName(name);
		setType(type);
		setSelector(selector);
	}

	/**
	 * Use this constructor for global variables
	 * @param name The name of the variable
	 * @param type The type of the variable
	 * @param content The content of the variable(this content should not change between tests)
	 */
	public Variable(String name, VariableType type, Object content){
		uuid = UUID.randomUUID().toString();
		setName(name);
		setType(type);
		setContent(content);
	}
	
	public String getID() {
		return this.uuid;
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

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public String getSelector() {
		return selector;
	}

	public void setSelector(String selector) {
		this.selector = selector;
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
		return true;
	}
}