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
import com.wstester.model.StepStatusType;
import com.wstester.services.impl.TestRunner;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MySQLStepController
{
    @FXML private Node rootMySQLStep;
    @FXML private Label lblName;
    @FXML private Label lblSQL;
    @FXML private Label lblStatus;
    @FXML private Label lblResponse;
    @FXML private TableView<Execut> tblExecutions;
    @FXML private TableColumn<Execut, String> columnDate;
    @FXML private TableColumn<Execut, String> columnStatus;
    @FXML private TableColumn<Execut, String> columnResponse;
    
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
    	lblName.setText("");
        lblSQL.setText("SQL");
        lblStatus.setText("Not run");
        lblResponse.setText("Not run");
        
        step = (MySQLStep) tsService.getStep( stepUID);
        lblName.setText(step.getName());
        lblSQL.setText(step.getOperation());
        Execution execution = step.getLastExecution();
        
        if( execution != null)
        {
        	if (execution.getStatus() == StepStatusType.PASSED)
        		lblStatus.setText("PASSED");
        	//else ....
        		//FAILED
        	lblResponse.setText(execution.getResponse().getContent());
        	//List<Execution> list = new ArrayList<>();
        	List<Execution> list = step.getExecutionList();
        	List<Execut> lista = new ArrayList<>();// = FXCollections.observableArrayList(step.getExecutionList());
        	//list = step.getExecutionList();
        	for(Execution exec : list)
        	{
        		Execut exemplu = new Execut(exec.getRunDate().toString(),exec.getStatus().toString(),exec.getResponse().getContent());
        		lista.add(exemplu);
        	}
        	tblExecutions.setItems(null);
        	tblExecutions.setItems(FXCollections.observableArrayList(lista));
            //tblExecutions.setItems(lista);
            columnDate.setCellValueFactory(
            		new PropertyValueFactory<Execut,String>("Date")
            		);
            
            columnStatus.setCellValueFactory(
            		new PropertyValueFactory<Execut,String>("Status")
            		);
            columnResponse.setCellValueFactory(
            		new PropertyValueFactory<Execut,String>("Response")
            		);
           
        }
          
        
        
    }
    public static class Execut{
    	private final SimpleStringProperty date;
    	private final SimpleStringProperty status;
    	private final SimpleStringProperty response;
		
    	private Execut(String Date, String Status, String Response){
    		this.date = new SimpleStringProperty(Date);
    		this.status = new SimpleStringProperty(Status);
    		this.response = new SimpleStringProperty(Response);
     	}
    	
    	public String getDate(){
    		return date.get();
    	}
    	public String getStatus(){
    		return status.get();
    	}
    	public String getResponse(){
    		return response.get();
    	}
    	public void setDate(String Date){
    		date.set(Date);
    	}
    	public void setStatus(String Status){
    		status.set(Status);
    	}
    	public void setResponse(String Response){
    		response.set(Response);
    	}
    }
    
    
    
    
}