package com.wstester.testFactory;
import java.util.ArrayList;
import java.util.List;

import com.sun.prism.paint.Color;
import com.wstester.model.MongoService;
import com.wstester.model.MySQLStep;
import com.wstester.model.Response;
import com.wstester.model.Server;
import com.wstester.model.Service;
import com.wstester.model.Step;
import com.wstester.model.Execution;
import com.wstester.model.ExecutionStatus;
import com.wstester.services.impl.TestRunner;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Random;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MySQLStepController
{
    @FXML private Node rootMySQLStep;
    @FXML private TextField stepName;
    @FXML private TextField sqlField;
    @FXML private Label sqlStatus;
    @FXML private Label sqlResponse;
    @FXML private TableView<Execut> tblExecutions;
    @FXML private TableColumn<Execut, String> columnDate;
    @FXML private TableColumn<Execut, String> columnStatus;
   // @FXML private TableColumn<Execut, String> columnResponse;
    
    private MySQLStep step;    
    private TestSuiteService tsService;
    private TestSuiteManagerController tsMainController;
    
    
	@FXML
	private void initialize() 
	{
		
	}
	
    public void setTestSuiteService( TestSuiteService tsService)
    {
        this.tsService = tsService;
    }

    public void setTestSuiteManagerController(TestSuiteManagerController tsMainController)
    {
        this.tsMainController = tsMainController;
    }

    public Node getView()
    {
        return rootMySQLStep;
    }

    public void setMySQLStep(final String stepUID)
    {
          
        step = (MySQLStep) tsService.getStep( stepUID);
        stepName.setText(step.getName());
        sqlField.setText(step.getOperation());
        Execution execution = step.getLastExecution();
        
        if( execution != null)
        {
        	if (execution.getResponse().getStatus() == ExecutionStatus.PASSED)
        		sqlStatus.setText("PASSED");
        	//else ....
        		//FAILED
        	sqlResponse.setText(execution.getResponse().getContent());
        	List<Execution> list = step.getExecutionList();
        	List<Execut> lista = new ArrayList<>();
        	for(Execution exec : list)
        	{
        		Execut exemplu = new Execut(exec.getRunDate().toString(),exec.getResponse().getStatus().toString(),exec.getResponse().getContent());
        		lista.add(exemplu);
        	}
        	        	
            columnDate.setCellValueFactory(
            		new PropertyValueFactory<Execut,String>("Date")
            		);
            
            columnStatus.setCellValueFactory(
            		new PropertyValueFactory<Execut,String>("Status")
            		);
            TableColumn columnResponse = new TableColumn("Action");
            columnResponse.setSortable(false);
            columnResponse.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Execut, Boolean>, ObservableValue<Boolean>>() {
     
                    @Override
                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Execut, Boolean> p) {
                        return new SimpleBooleanProperty(p.getValue() != null);
                    }
                });
     
            columnResponse.setCellFactory(
                new Callback<TableColumn<Execut, Boolean>, TableCell<Execut, Boolean>>() {
     
                    @Override
                    public TableCell<Execut, Boolean> call(TableColumn<Execut, Boolean> p) {
                        return new ButtonCell(tblExecutions);
                    }
     
                });
            tblExecutions.getColumns().add(columnResponse);
            if(tblExecutions.getColumns().size()>3)
            {
            tblExecutions.getColumns().remove(2);
            }
            tblExecutions.setItems(null);
        	tblExecutions.setItems(FXCollections.observableArrayList(lista));
                      
        }
          
        
        
    }
    public static class Execut{
    	private final SimpleStringProperty date;
    	private final SimpleStringProperty status;
    //	private final SimpleStringProperty response;
		
    	private Execut(String Date, String Status, String Response){
    		this.date = new SimpleStringProperty(Date);
    		this.status = new SimpleStringProperty(Status);
    	//	this.response = new SimpleStringProperty(Response);
     	}
    	
    	public String getDate(){
    		return date.get();
    	}
    	public String getStatus(){
    		return status.get();
    	}

    	public void setDate(String Date){
    		date.set(Date);
    	}
    	public void setStatus(String Status){
    		status.set(Status);
    	}

    }
    
    private class ButtonCell extends TableCell<Execut, Boolean> {
    	 
        final Button cellButton = new Button("Details");
 
        ButtonCell(final TableView tblView) {
 
            cellButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
 
               
            });
        }
 
       
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(cellButton);
            }
        }
    }
    
    
    
}