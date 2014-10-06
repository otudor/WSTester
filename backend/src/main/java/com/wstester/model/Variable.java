package com.wstester.model;

import java.io.Serializable;
import java.util.UUID;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Variable implements Serializable {

	private static final long serialVersionUID = 1L;
	private String uuid;
	private String name;
	private VariableType type;
	private String content;
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
	
	public Variable(String content, String name) {
		this.content = content;
		this.name = name;
	}

	/**
	 * Use this constructor for global variables
	 * @param name The name of the variable
	 * @param type The type of the variable
	 * @param content The content of the variable(this content should not change between tests)
	 */
	public Variable(String name, VariableType type, String content){
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
		return "Variable [uuid=" + uuid + ", name=" + name + ", type=" + type + ", content=" + content + ", selector=" + selector + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((selector == null) ? 0 : selector.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		return true;
	}
}
