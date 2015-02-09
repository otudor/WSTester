package com.wstester.testFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.wstester.model.Asset;
import com.wstester.model.Environment;
import com.wstester.model.RestStep;
import com.wstester.model.Server;
import com.wstester.model.Service;
import com.wstester.model.SoapStep;
import com.wstester.model.ExecutionStatus;
import com.wstester.util.TestProjectService;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class SoapStepController
{
    @FXML private Node rootSoapStep;
    @FXML private TextField lblName;
    @FXML private TextField soapRequest;
    @FXML private Label lblStatus;
    @FXML private Label lblResponse;
//    @FXML private TableView<Execut> tblExecutions;
//    @FXML private TableColumn<Execut, String> columnDate;
//    @FXML private TableColumn<Execut, String> columnStatus;
//    @FXML private TableColumn<Execut, String> columnResponse;
    @FXML private ComboBox<Server> serverBox;
    @FXML private ComboBox<Service> serviceBox;
    
    private SoapStep step;    
    private String uid = null;
    
	@FXML
	private void initialize() 
	{
		
	}


    public void setStep(final String stepUID)
    {
    	lblName.setText("");
        soapRequest.setText("request");
        lblStatus.setText("Not run");
        lblResponse.setText("Not run");
        
        TestProjectService tsService = new TestProjectService();
        step = (SoapStep) tsService.getStep( stepUID);
//        Environment environment = tsService.getTestSuiteByStepUID(stepUID).getEnvironment();
//        if(environment != null) {        	
//        	serverBox.getItems().clear();
//        	serverBox.getItems().addAll(environment.getServers());
//        	if(step.getServer() != null) {
//        		serverBox.setValue(step.getServer());
//        		serviceBox.getItems().clear();
//        		serviceBox.getItems().addAll(step.getServer().getServices());
//        	}
//        	serverBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Server>() {
//					public void changed(ObservableValue ov, Server value, Server new_value) {
//						if(new_value !=null) {
//							step.setServer(new_value);
//							step.setServer(new_value);
//							serviceBox.getItems().clear();
//							serviceBox.getItems().addAll(step.getServer().getServices());
//							if(step.getService() != null) {
//								serviceBox.setValue(step.getService());
//							}
//							serviceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Service>() {
//									public void changed(ObservableValue ov, Service value,Service new_value) {
//										step.setService(new_value);
//									}
//								});
//							step.setServer(new_value);
//							tsService.setStepByUID(step, step.getId());
//						}
//					}
//        	});
//        }
//        lblName.setText(step.getName());
//        Execution execution = step.getLastExecution();
//        uid = stepUID;
//        if( execution != null)
//        {
//        	if (execution.getResponse().getStatus() == ExecutionStatus.PASSED)
//        		lblStatus.setText("PASSED");
//        	//else ....
//        		//FAILED
//        	lblResponse.setText(execution.getResponse().getContent());
//        	List<Execution> list = new ArrayList<>();
//        	ObservableList<Execut> lista = FXCollections.observableArrayList();
//        	list = step.getExecutionList();
//        	for(Execution exec : list)
//        	{
//        	Execut exemplu = new Execut(exec.getRunDate().toString(),exec.getResponse().getStatus().toString(),exec.getResponse().getContent());
//            lista.add(exemplu);
//        	}
//            tblExecutions.setItems(lista);
//            columnDate.setCellValueFactory(
//            		new PropertyValueFactory<Execut,String>("Date")
//            		);
//            
//            columnStatus.setCellValueFactory(
//            		new PropertyValueFactory<Execut,String>("Status")
//            		);
//            columnResponse.setCellValueFactory(
//            		new PropertyValueFactory<Execut,String>("Response")
//            		);
           
        }
          
        
        
//    }
//    public static class Execut{
//    	private final SimpleStringProperty date;
//    	private final SimpleStringProperty status;
//    	private final SimpleStringProperty response;
//		
//    	private Execut(String Date, String Status, String Response){
//    		this.date = new SimpleStringProperty(Date);
//    		this.status = new SimpleStringProperty(Status);
//    		this.response = new SimpleStringProperty(Response);
//     	}
//    	
//    	public String getDate(){
//    		return date.get();
//    	}
//    	public String getStatus(){
//    		return status.get();
//    	}
//    	public String getResponse(){
//    		return response.get();
//    	}
//    	public void setDate(String Date){
//    		date.set(Date);
//    	}
//    	public void setStatus(String Status){
//    		status.set(Status);
//    	}
//    	public void setResponse(String Response){
//    		response.set(Response);
//    	}
//    }
    
    public void saveSoap(ActionEvent e) {
    	SoapStep soap = new SoapStep();
		soap.setAssertList(step.getAssertList());
		soap.setAssetMap(step.getAssetMap());
		soap.setDependsOn(step.getDependsOn());
//		soap.setExecutionList(step.getExecutionList());
		soap.setName(step.getName());
		soap.setRequest(step.getRequest());
//		soap.setServer(step.getServerId());
		soap.setService(step.getService());
		soap.setVariableList(step.getVariableList());
		
		TestProjectService tsService = new TestProjectService();
		tsService.setStepByUID(soap, soap.getId());
	} 
    
    
}