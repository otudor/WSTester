package com.wstester.variables;

import java.util.ArrayList;

import com.wstester.model.Variable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableVariables {
	private final StringProperty name;
	private final StringProperty content;
	
	public TableVariables(String name, String content){
		this.name = new SimpleStringProperty(name);
		this.content = new SimpleStringProperty(content);
	}
	
	public StringProperty getName() {
		return name;
	}

	public StringProperty getContent() {
		return content;
	}
	
	public StringProperty nameProperty(){
		return this.name;
	}
	
	public StringProperty contentProperty(){
		return this.content;
	}
	
	
}
