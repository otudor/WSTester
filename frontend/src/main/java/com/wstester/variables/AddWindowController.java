package com.wstester.variables;

import org.controlsfx.dialog.Dialogs;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * 
 * @author vdumitrache
 *
 */

public class AddWindowController{
	
	//Ok button
	@FXML public Button btnAddVariablesOk;
	
	//textfields for Name and Value
	@FXML private TextField txtName;
	@FXML private TextField txtValue;
	
	//string values from textfields
	public String valueName, valueContent;
	
	@FXML
	public void initialize(){
		//listener on textfield with name
		txtName.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable,
		            String oldValue, String txtName) {
		    	valueName = txtName;
		    }
		});
		
		//listener on textfield with value
		txtValue.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable,
		            String oldValue, String txtValue) {
		    	valueContent = txtValue;
		    }
		});
		
		//event on Ok button from addVariables fxml
		btnAddVariablesOk.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
					if((valueContent != null && valueName != null) && (!valueContent.trim().isEmpty() && !valueName.trim().isEmpty())){
						VariablesController.stageAddVariables.close();
						VariablesController.tableViewVarData.add(
								new TableVariables(valueName, valueContent));
						VariablesController.populateTableStatic();
					}else{
						try {
							Dialogs.create()
							.lightweight()
							.owner(null)
							.title("Error")
							.message(
									"Please insert data in order to be added in the table!")
									.showError();
						}catch (Exception e) {
							e.printStackTrace();
						}
					}
			}
		});
	}
}
