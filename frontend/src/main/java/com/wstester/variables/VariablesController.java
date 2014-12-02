package com.wstester.variables;

import java.util.ArrayList;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import com.wstester.model.Variable;


/**
 * 
 * @author vdumitrache
 *
 */

public class VariablesController {
	//list of variables
	private ArrayList<Variable> variablesList = new ArrayList<>();
	public static Stage stageAddVariables;
	
	@FXML private TableView<TableVariables> tableViewVars;
	@FXML private TableColumn<TableVariables, String> tableColumnVarName;
	@FXML private TableColumn<TableVariables, String> tableColumnValue;
	@FXML private Button addGlobal;
	
	//public static variables on order to pass references to table view and table columns, and use them in AddWindowController class
	public static TableView<TableVariables> tableViewVarsPassed;
	public static TableColumn<TableVariables, String> tableColumnVarNamePassed = new TableColumn<TableVariables, String>();
	public static TableColumn<TableVariables, String> tableColumnValuePassed = new TableColumn<TableVariables, String>();
	
	public static ObservableList<TableVariables> tableViewVarData = FXCollections
			.observableArrayList();
	
	@FXML
	public void initialize(){
		//add vars to the list -hard coded 
		//waiting for the values, in order to retrieve 
		variablesList.add(new Variable("abc", "def"));
		variablesList.add(new Variable("test", "test2"));
		variablesList.add(new Variable("asb", "wqeqwe"));
		
		for(int i=0; i<variablesList.size(); i++){
			tableViewVarData.add(
					new TableVariables(variablesList.get(i).getName(), variablesList.get(i).getContent()));
		}
		
		//add vars to table view
		tableViewVars.setItems(tableViewVarData);
		tableViewVars = tableViewVarsPassed;
		populateTable();
		
		//passing the values from columns to static variables
		tableColumnVarName = tableColumnVarNamePassed;
		tableColumnValue = tableColumnValuePassed;
		
		//event on click Add button from Global
		addGlobal.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//adding addVariables fxml for Global
				stageAddVariables = new Stage();
				try{
					AnchorPane page = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/var/addVariables.fxml"));
					Scene scene = new Scene(page);
					stageAddVariables.setScene(scene);
					stageAddVariables.setTitle("Add a variable");
					stageAddVariables.show();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
	}
	
	//fill the columns with data
	public void populateTable(){
		tableColumnVarName.setCellValueFactory(new Callback<CellDataFeatures<TableVariables, String>, ObservableValue<String>>() {
		     public ObservableValue<String> call(CellDataFeatures<TableVariables, String> p) {
		         return p.getValue().getName();
		     }
		  });
		
		tableColumnValue.setCellValueFactory(new Callback<CellDataFeatures<TableVariables, String>, ObservableValue<String>>() {
		     public ObservableValue<String> call(CellDataFeatures<TableVariables, String> p) {
		         return p.getValue().getContent();
		     }
		  });
	}
	
	public static void populateTableStatic(){
		tableColumnVarNamePassed.setCellValueFactory(new Callback<CellDataFeatures<TableVariables, String>, ObservableValue<String>>() {
		     public ObservableValue<String> call(CellDataFeatures<TableVariables, String> p) {
		         return p.getValue().getName();
		     }
		  });
		
		tableColumnValuePassed.setCellValueFactory(new Callback<CellDataFeatures<TableVariables, String>, ObservableValue<String>>() {
		     public ObservableValue<String> call(CellDataFeatures<TableVariables, String> p) {
		         return p.getValue().getContent();
		     }
		  });
	}
}
