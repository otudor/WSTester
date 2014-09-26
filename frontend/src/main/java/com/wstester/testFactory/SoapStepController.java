package com.wstester.testFactory;
import java.util.ArrayList;
import java.util.List;

import com.sun.prism.paint.Color;
import com.wstester.model.MongoService;
import com.wstester.model.MySQLStep;
import com.wstester.model.Response;
import com.wstester.model.Server;
import com.wstester.model.Service;
import com.wstester.model.SoapStep;
import com.wstester.model.Step;
import com.wstester.model.Execution;
import com.wstester.model.ExecutionStatus;
import com.wstester.services.impl.TestRunner;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

public class SoapStepController
{
    @FXML private Node rootSoapStep;
    @FXML private TextField lblName;
    @FXML private TextField soapRequest;
    @FXML private Label lblStatus;
    @FXML private Label lblResponse;
    @FXML private TableView<Execut> tblExecutions;
    @FXML private TableColumn<Execut, String> columnDate;
    @FXML private TableColumn<Execut, String> columnStatus;
    @FXML private TableColumn<Execut, String> columnResponse;
    
    private SoapStep step;    
    private TestSuiteService tsService;
    private TestSuiteManagerController tsMainController;
    private String uid = null;
    
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
        return rootSoapStep;
    }

    public void setSoapStep(final String stepUID)
    {
    	lblName.setText("");
        soapRequest.setText("request");
        lblStatus.setText("Not run");
        lblResponse.setText("Not run");
        
        step = (SoapStep) tsService.getStep( stepUID);
        lblName.setText(step.getName());
        Execution execution = step.getLastExecution();
        uid = stepUID;
        if( execution != null)
        {
        	if (execution.getResponse().getStatus() == ExecutionStatus.PASSED)
        		lblStatus.setText("PASSED");
        	//else ....
        		//FAILED
        	lblResponse.setText(execution.getResponse().getContent());
        	List<Execution> list = new ArrayList<>();
        	ObservableList<Execut> lista = FXCollections.observableArrayList();
        	list = step.getExecutionList();
        	for(Execution exec : list)
        	{
        	Execut exemplu = new Execut(exec.getRunDate().toString(),exec.getResponse().getStatus().toString(),exec.getResponse().getContent());
            lista.add(exemplu);
        	}
            tblExecutions.setItems(lista);
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
    
    public void saveSoap(ActionEvent e) {
    	SoapStep soap = new SoapStep();
		soap.setAssertList(step.getAssertList());
		soap.setAssetList(step.getAssetList());
		soap.setDependsOn(step.getDependsOn());
		soap.setExecutionList(step.getExecutionList());
		soap.setName(step.getName());
		soap.setRequest(step.getRequest());
		soap.setServer(step.getServer());
		soap.setService(step.getService());
		soap.setVariableList(step.getVariableList());
		
		tsService.setStepByUID(soap, uid);
		tsService.saveTestSuite();
	} 
    
    
}