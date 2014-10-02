package com.wstester.testFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.wstester.model.Asset;
import com.wstester.model.Environment;
import com.wstester.model.MySQLStep;
import com.wstester.model.RestStep;
import com.wstester.model.Execution;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Server;
import com.wstester.model.Service;

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

public class RestStepController
{
    @FXML private Node rootRestStep;
    @FXML private TextField lblName;
    @FXML private TextField restPath;
    @FXML private TextField restQuery;
    @FXML private TextField restCookie;
    @FXML private TextField restHeader;
    @FXML private TextField restContentType;
    @FXML private TextField restMethod;
    @FXML private TextField restRequest;
    @FXML private Label lblStatus;
    @FXML private Label lblResponse;
    @FXML private TableView<Execut> tblExecutions;
    @FXML private TableColumn<Execut, String> columnDate;
    @FXML private TableColumn<Execut, String> columnStatus;
    @FXML private TableColumn<Execut, String> columnResponse;
    @FXML private ComboBox<Server> serverBox;
    @FXML private ComboBox<Service> serviceBox;
    
    private RestStep step;    
    private TestSuiteService tsService;
    private TestSuiteManagerController tsMainController;
    private String query;
    private String cookie;
    private String header;
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
        return rootRestStep;
    }

    public void setRestStep(final String stepUID)
    {
    
    	lblName.setText("");
        restPath.setText("");
        restQuery.setText("");
        restCookie.setText("");
        restHeader.setText("");
        restContentType.setText("");
        restMethod.setText("");
        restRequest.setText("");
        lblStatus.setText("Not run");
        lblResponse.setText("Not run");
        step = (RestStep) tsService.getStep( stepUID);
        lblName.setText(step.getName());
        restPath.setText(step.getPath());
        if (step.getQuery() != null) {
			for (String key : step.getQuery().keySet()) {
				query = query + step.getQuery().get(key);
			}
		}
        restQuery.setText(query);
        if (step.getCookie() != null) {
			for (String key : step.getCookie().keySet()) {
				cookie = cookie + step.getCookie().get(key);
			}
		}
        restCookie.setText(cookie);
        if (step.getHeader() != null) {
			for (String key : step.getHeader().keySet()) {
				header = header + step.getHeader().get(key);
			}
		}
    	step = (RestStep) tsService.getStep(stepUID);
        Environment environment = tsService.getTestSuiteByStepUID(stepUID).getEnvironment();
        if(environment != null) {        	
        	serverBox.setItems(FXCollections.observableArrayList(environment.getServers()));
        	serverBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Server>() {
					public void changed(ObservableValue ov, Server value, Server new_value) {
						if(new_value !=null) {
							step.setServer(new_value);
							serviceBox.setItems(FXCollections.observableArrayList(tsService.getServiceList(step.getServer().getID())));
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
        restHeader.setText(header);
        restContentType.setText(step.getContentType());
        restMethod.setText(step.getMethod());
        restRequest.setText((String) step.getRequest());
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
    
    public void saveRest(ActionEvent e) {

		RestStep rest = new RestStep();
		rest.setAssertList(step.getAssertList());
		rest.setAssetMap((HashMap<Asset, String>)step.getAssetMap());
		rest.setContentType(step.getContentType());
		rest.setCookie(step.getCookie());
		rest.setDependsOn(step.getDependsOn());
		rest.setExecutionList(step.getExecutionList());
		rest.setHeader(step.getHeader());
		rest.setMethod(step.getMethod());
		rest.setName(step.getName());
		rest.setPath(step.getPath());
		rest.setQuery(step.getQuery());
		rest.setRequest(step.getRequest());
		rest.setServer(step.getServer());
		rest.setService(step.getService());
		rest.setVariableList(step.getVariableList());
		tsService.setStepByUID(rest, uid);
		tsService.saveTestSuite();
	} 
    
    
}