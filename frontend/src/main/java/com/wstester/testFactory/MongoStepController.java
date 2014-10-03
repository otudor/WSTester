package com.wstester.testFactory;

import java.util.HashMap;
import java.util.Map;

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

import com.wstester.model.Asset;
import com.wstester.model.Environment;
import com.wstester.model.Execution;
import com.wstester.model.MongoStep;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Server;
import com.wstester.model.Service;

public class MongoStepController
{
    @FXML private Node rootMongoStep;
    @FXML private TextField lblName;
    @FXML private TextField mongoCollection;
    @FXML private TextField mongoAction;
    @FXML private TextField mongoQuery;
    @FXML private Label lblStatus;
    @FXML private Label lblResponse;
    @FXML private TableView<Execut> tblExecutions;
    @FXML private TableColumn<Execut, String> columnDate;
    @FXML private TableColumn<Execut, String> columnStatus;
    @FXML private TableColumn<Execut, String> columnResponse;
    @FXML private ComboBox<Server> serverBox;
    @FXML private ComboBox<Service> serviceBox;
    
    private MongoStep step;    
    private TestSuiteService tsService;
    private TestSuiteManagerController tsMainController;
    final ObservableList<Execut> lista = FXCollections.observableArrayList();
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
        return rootMongoStep;
    }

    public void setMongoStep(final String stepUID)
    {        
        step = (MongoStep) tsService.getStep( stepUID);
        lblName.setText(step.getName());
    	mongoQuery.setText("");
        mongoAction.setText("");
        mongoCollection.setText("");
        lblStatus.setText("Not run");
        lblResponse.setText("Not run");
        Environment environment = tsService.getTestSuiteByStepUID(stepUID).getEnvironment();
        if(environment != null) {        	
        	serverBox.getItems().clear();
        	serverBox.getItems().addAll(environment.getServers());
        	if(step.getServer() != null) {
        		serverBox.setValue(step.getServer());
        		serviceBox.getItems().clear();
        		serviceBox.getItems().addAll(step.getServer().getServices());
        	}
        	serverBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Server>() {
					public void changed(ObservableValue ov, Server value, Server new_value) {
						if(new_value !=null) {
							step.setServer(new_value);
							serviceBox.getItems().clear();
							serviceBox.getItems().addAll(step.getServer().getServices());
							if(step.getService() != null) {
								serviceBox.setValue(step.getService());
							}
							serviceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Service>() {
									public void changed(ObservableValue ov, Service value,Service new_value) {
										step.setService(new_value);
									}
								});
							step.setServer(new_value);
							tsService.setStepByUID(step, uid);
							tsService.saveTestSuite();
						}
					}
        	});
        }
        lblName.setText(step.getName());
        if(step.getAction()!=null){
        	mongoAction.setText(step.getAction().toString());
        }
        //mongoQuery.setText(step.getQuery().toString());
        mongoCollection.setText(step.getCollection());
        Execution execution = step.getLastExecution();
        uid = stepUID;
        if( execution != null)
        {
        	if (execution.getResponse().getStatus() == ExecutionStatus.PASSED)
        		lblStatus.setText("PASSED");
        	//else ....
        		//FAILED
        	lblResponse.setText(execution.getResponse().getContent());
        	Execut exemplu = new Execut(execution.getRunDate().toString(),execution.getResponse().getStatus().toString(),execution.getResponse().getContent());
            lista.add(exemplu);
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
    
    public void saveMongo(ActionEvent e) {

		MongoStep mongo = new MongoStep();

		mongo.setAction(step.getAction());
		mongo.setAssertList(step.getAssertList());
		mongo.setAssetMap((HashMap<Asset, String>)step.getAssetMap());
		mongo.setCollection(step.getCollection());
		mongo.setDependsOn(step.getDependsOn());
		mongo.setExecutionList(step.getExecutionList());
		mongo.setName(step.getName());
		mongo.setQuery(step.getQuery());
		mongo.setServer(step.getServer());
		mongo.setService(step.getService());
		mongo.setVariableList(step.getVariableList());
		tsService.setStepByUID(mongo, uid);
		tsService.saveTestSuite();
	} 
}
	