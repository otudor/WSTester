package com.wstester.variables;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableVariables {
	private final StringProperty name;
	
	public TableVariables(String name){
		this.name = new SimpleStringProperty(name);
	}
	
	public StringProperty getName() {
		return name;
	}

	public StringProperty nameProperty(){
		return this.name;
	}
}
