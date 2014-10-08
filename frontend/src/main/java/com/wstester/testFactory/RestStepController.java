package com.wstester.testFactory;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.cxf.jaxws.javaee.EnvEntryType;

import com.wstester.assets.Table;
import com.wstester.main.MainLauncher;
import com.wstester.main.WsTesterMain;
import com.wstester.model.Asset;
import com.wstester.model.Environment;
import com.wstester.model.MySQLStep;
import com.wstester.model.RestMethod;
import com.wstester.model.RestStep;
import com.wstester.model.Execution;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Server;
import com.wstester.model.Service;
import com.wstester.model.Variable;
import com.wstester.testFactory.TableQuerry;
import com.wstester.variables.TableVariables;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

public class RestStepController
{
    @FXML private Node rootRestStep;
    @FXML private TextField lblName;
    @FXML private TextField restPath;
   
   
    @FXML private ComboBox<RestMethod> restMethod;
   
   
    
    @FXML private TableView<Execut> tblExecutions;
    @FXML private TableColumn<Execut, String> columnDate;
    @FXML private TableColumn<Execut, String> columnStatus;
    @FXML private TableColumn<Execut, String> columnResponse;
    @FXML private ComboBox<Server> serverBox;
    @FXML private ComboBox<Service> serviceBox;
    @FXML private Button addQuerryButton;
    @FXML private Button addPathButton;
    @FXML private Button addHeaderButton;
    @FXML private Button addCookieBUtton;
    @FXML
	private TableView<Table> tableView = new TableView<>();
    @FXML
	private TableColumn<Table, String> nameQuerry;
    @FXML private ComboBox<String> editableBox;
    
    // addul de variabile in tabel
    
    private ArrayList<Variable> variablesList = new ArrayList<>();
	public static Stage stageQuerry;
	
	@FXML private TableView<TableQuerry> tableQuerryVars;
	@FXML private TableColumn<TableQuerry, String> columnQuerryName;
	@FXML private TableColumn<TableQuerry, String> columnQuerryValue;
//	@FXML private Button addGlobal;
	
	//public static variables on order to pass references to table view and table columns, and use them in AddWindowController class
	public static TableView<TableQuerry> tableViewVarsPassed;
	public static TableColumn<TableQuerry, String> tableColumnVarNamePassed = new TableColumn<TableQuerry, String>();
	public static TableColumn<TableQuerry, String> tableColumnValuePassed = new TableColumn<TableQuerry, String>();
	
	public static ObservableList<TableQuerry> tableViewVarData = FXCollections
			.observableArrayList();
    
    
    
    
    
    
    
    
    
    
    //
    private RestStep step;    
    private TestSuiteService tsService;
    private TestSuiteManagerController tsMainController;
    private String query;
    private String cookie;
    private String header;
    private String uid = null;
    public boolean querryVisible = false;
    Parent root;
    private String querry;
	
   
    
	
    
//    @Override
//    public void initialize (URL location, ResourceBundle resources) {
//    	this.xmlParser = new XmlParser();
//    	this.showTreeView();
//    	this.createSlide();
//    }
	@FXML
	private void initialize() 
	{
		this.addQuerryMethod();
		for(int i=0; i<variablesList.size(); i++) {
			tableViewVarData.add(
					new TableQuerry(variablesList.get(i).getName(), variablesList.get(i).getContent()));
		}
		tableQuerryVars.setItems(tableViewVarData);
		tableQuerryVars = tableViewVarsPassed;
		populateTable();
//		this.populateTable();
//		this.populateTableStatic();
//		this.addToComboBox();
		//this.editableBox.setItems((ObservableList<String>)new ArrayList<String>());
	}
	
   

	
	   
	private void addQuerryMethod() {
		addQuerryButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				stageQuerry = new Stage();
				// TODO Auto-generated method stub	
				if(event.getClickCount() == 1 && querryVisible == false ) {
					stageQuerry = new Stage();
//					querryVisible = true;
					try {
						root = FXMLLoader.load(getClass().getResource("/fxml/testFactory/AddNameValue.fxml"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					Scene second = new Scene(root);

	
					stageQuerry.initOwner(MainLauncher.stage);
					
					stageQuerry.setScene(second);

					stageQuerry.setTitle("SOAP Window");
					
					//stageSoap.initModality(Modality.WINDOW_MODAL);

					
					stageQuerry.show();
				} 
			}
			});
	
	}
	
	public void populateTable(){
		columnQuerryName.setCellValueFactory(new Callback<CellDataFeatures<TableQuerry, String>, ObservableValue<String>>() {
		     public ObservableValue<String> call(CellDataFeatures<TableQuerry, String> p) {
		         return p.getValue().getName();
		     }
		  });
		
		columnQuerryValue.setCellValueFactory(new Callback<CellDataFeatures<TableQuerry, String>, ObservableValue<String>>() {
		     public ObservableValue<String> call(CellDataFeatures<TableQuerry, String> p) {
		         return p.getValue().getContent();
		     }
		  });
	}
	
	public static void populateTableStatic(){
		tableColumnVarNamePassed.setCellValueFactory(new Callback<CellDataFeatures<TableQuerry, String>, ObservableValue<String>>() {
		     public ObservableValue<String> call(CellDataFeatures<TableQuerry, String> p) {
		         return p.getValue().getName();
		     }
		  });
		
		tableColumnValuePassed.setCellValueFactory(new Callback<CellDataFeatures<TableQuerry, String>, ObservableValue<String>>() {
		     public ObservableValue<String> call(CellDataFeatures<TableQuerry, String> p) {
		         return p.getValue().getContent();
		     }
		  });
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
        
        
//        restMethod.setText("");
        
        step = (RestStep) tsService.getStep( stepUID);
        lblName.setText(step.getName());
        restPath.setText(step.getPath());
        if (step.getQuery() != null) {
			for (String key : step.getQuery().keySet()) {
				query = query + step.getQuery().get(key);
			}
		}
//        restQuery.setText(query);
//        if (step.getCookie() != null) {
//			for (String key : step.getCookie().keySet()) {
//				cookie = cookie + step.getCookie().get(key);
//			}
//		}
//        restCookie.setText(cookie);
//        if (step.getHeader() != null) {
//			for (String key : step.getHeader().keySet()) {
//				header = header + step.getHeader().get(key);
//			}
//		}
    	step = (RestStep) tsService.getStep(stepUID);
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
//        restHeader.setText(header);
//        restContentType.setText(step.getContentType());
//        restMethod.setText(step.getMethod());
//        restRequest.setText((String) step.getRequest());
        Execution execution = step.getLastExecution();
        uid = stepUID;
        if( execution != null)
        {
        	if (execution.getResponse().getStatus() == ExecutionStatus.PASSED){
//        		lblStatus.setText("PASSED");
        	}

        	//else ....
        		//FAILED
//        	lblResponse.setText(execution.getResponse().getContent());
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
        
        if(!restMethod.getItems().contains(RestMethod.POST)) {
        	restMethod.getItems().addAll(RestMethod.values());
        }
        restMethod.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<RestMethod>() {
			public void changed(ObservableValue ov, RestMethod value,RestMethod new_value) {
				step.setMethod(new_value);
			}
		});          
        
        
        
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
		rest.setAssetMap(step.getAssetMap());
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
