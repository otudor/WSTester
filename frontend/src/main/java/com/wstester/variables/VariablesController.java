package com.wstester.variables;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import com.wstester.assets.Table;
import com.wstester.model.Variable;

public class VariablesController {
	private Variable variable;
	
	@FXML
	private TableView<TableVariables> tableViewVar = new TableView<>();
	
	@FXML
	private TableColumn<TableVariables, String> tableNameVar;
	
	private ObservableList<Table> tableViewVarData = FXCollections
			.observableArrayList();
	
	@FXML
	public void initialize(){
		//to add..
	}
}
