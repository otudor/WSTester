package com.wstester.testFactory;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.cxf.jaxws.javaee.EnvEntryType;

import com.sun.javafx.collections.MappingChange.Map;
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
import com.wstester.model.Step;
import com.wstester.model.TestProject;
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
import junit.framework.Test;

public class RestStepController
{	
	 private TestSuiteManagerController tsMainController;
	private TestProjectService testProjectService;
	private String stepId;
	@FXML
	private StepController stepController;
    @FXML 
    private Node rootRestStep;
    @FXML private TextField lblName;
    @FXML private TextField restPath;
    @FXML private Label restResponse;
   
    @FXML private ComboBox<RestMethod> restMethod;
    public static String aa= "dasad";
   
    
//    @FXML private TableView<Execut> tblExecutions;
//    @FXML private TableColumn<Execut, String> columnDate;
//    @FXML private TableColumn<Execut, String> columnStatus;
//    @FXML private TableColumn<Execut, String> columnResponse;
    
    
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
    public static Stage stageQuerry;
	public static Stage stagePath;
	public static Stage stageHeader;
	public static Stage stageCookie;
	
	
    // addul de variabile in tabel
    
	//querry
    private ArrayList<Variable> variablesList = new ArrayList<>();
	
	
	@FXML private TableView<TableQuerry> tableQuerryVars;
	@FXML private TableColumn<TableQuerry, String> columnQuerryName;
	@FXML private TableColumn<TableQuerry, String> columnQuerryValue;

	
	
	//public static variables on order to pass references to table view and table columns, and use them in AddWindowController class
	public static TableView<TableQuerry> tableQuerryVarsPassed;
	public static TableColumn<TableQuerry, String> tableColumnVarNamePassed = new TableColumn<TableQuerry, String>();
	public static TableColumn<TableQuerry, String> tableColumnValuePassed = new TableColumn<TableQuerry, String>();
	
	public static ObservableList<TableQuerry> tableQuerryVarData = FXCollections
			.observableArrayList();
    
    //path
	 private ArrayList<Variable> variablesPathList = new ArrayList<>();
		
		
		@FXML private TableView<TablePath> tablePathVars;
		@FXML private TableColumn<TablePath, String> columnPathName;
		@FXML private TableColumn<TablePath, String> columnPathValue;

		//public static variables on order to pass references to table view and table columns, and use them in AddWindowController class
		public static TableView<TablePath> tablePathVarsPassed;
		public static TableColumn<TablePath, String> tableColumnPathNamePassed = new TableColumn<TablePath, String>();
		public static TableColumn<TablePath, String> tableColumnPathPassed = new TableColumn<TablePath, String>();
	
		public static ObservableList<TablePath> tablePathVarData = FXCollections
				.observableArrayList();
        
    
    //header
		
		 private ArrayList<Variable> variablesHeaderList = new ArrayList<>();
			
			
			@FXML private TableView<TableHeader> tableHeaderVars;
			@FXML private TableColumn<TableHeader, String> columnHeaderName;
			@FXML private TableColumn<TableHeader, String> columnHeaderValue;

			//public static variables on order to pass references to table view and table columns, and use them in AddWindowController class
			public static TableView<TableHeader> tableHeaderVarsPassed;
			public static TableColumn<TableHeader, String> tableColumnHeaderNamePassed = new TableColumn<TableHeader, String>();
			public static TableColumn<TableHeader, String> tableColumnHeaderPassed = new TableColumn<TableHeader, String>();
			
			public static ObservableList<TableHeader> tableHeaderVarData = FXCollections
					.observableArrayList();
			
	//cookie
			
			private ArrayList<Variable> variablesCookieList = new ArrayList<>();
			
			
			@FXML private TableView<TableCookie> tableCookieVars;
			@FXML private TableColumn<TableCookie, String> columnCookieName;
			@FXML private TableColumn<TableCookie, String> columnCookieValue;

			//public static variables on order to pass references to table view and table columns, and use them in AddWindowController class
			public static TableView<TableCookie> tableCookieVarsPassed;
			public static TableColumn<TableCookie, String> tableColumnCookieNamePassed = new TableColumn<TableCookie, String>();
			public static TableColumn<TableCookie, String> tableColumnCookiePassed = new TableColumn<TableCookie, String>();
			
			public static ObservableList<TableCookie> tableCookieVarData = FXCollections
					.observableArrayList();
			
		
		
			private RestStep step;    
			private TestProjectService tsService;
			private String uid = null;
			public boolean querryVisible = false;
			Parent root;

	
   
    
	
    
//    @Override
//    public void initialize (URL location, ResourceBundle resources) {
//    	this.xmlParser = new XmlParser();
//    	this.showTreeView();
//    	this.createSlide();
//    }
    
    public void setStep(String stepId){
		this.stepId = stepId;
	}
	@FXML
	private void initialize() 
	{
		this.addQuerryMethod();
		
		for(int i=0; i<variablesList.size(); i++) {
			tableQuerryVarData.add(
					new TableQuerry(variablesList.get(i).getName(), variablesList.get(i).getContent()));
		}
		tableQuerryVars.setItems(tableQuerryVarData);
		tableQuerryVars = tableQuerryVarsPassed;
		populateQuerryTable();
		//path
		this.addPathMethod();
		for(int i=0; i<variablesPathList.size(); i++) {
			tablePathVarData.add(
					new TablePath(variablesPathList.get(i).getName(), variablesPathList.get(i).getContent()));
		}
		tablePathVars.setItems(tablePathVarData);
		tablePathVars = tablePathVarsPassed;
		populatePathTable();
		
		//header
		this.addHeaderMethod();
		for(int i=0; i<variablesHeaderList.size(); i++) {
			tableHeaderVarData.add(
					new TableHeader(variablesHeaderList.get(i).getName(), variablesHeaderList.get(i).getContent()));
		}
		tableHeaderVars.setItems(tableHeaderVarData);
		tableHeaderVars = tableHeaderVarsPassed;
		populateHeaderTable();
		
		this.addCookieMethod();
		for(int i=0; i<variablesCookieList.size(); i++) {
			tableCookieVarData.add(
					new TableCookie(variablesCookieList.get(i).getName(), variablesCookieList.get(i).getContent()));
			
		}
		
		tableCookieVars.setItems(tableCookieVarData);
		tableCookieVars = tableCookieVarsPassed;
		populateCookieTable();

	}
	
   

	
	   
	private void addCookieMethod() {
		
		addCookieBUtton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				stageCookie = new Stage();
				// TODO Auto-generated method stub	
				if(event.getClickCount() == 1 && querryVisible == false ) {
					stageHeader = new Stage();
//					querryVisible = true;
					try {
						root = FXMLLoader.load(getClass().getResource("/fxml/testFactory/AddCookieNameValue.fxml"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					Scene second = new Scene(root);

					stageCookie.initOwner(MainLauncher.stage);
					stageCookie.setScene(second);
					stageCookie.setTitle("Add Path");
					stageCookie.show();
				} 
			}
			});
	
	}





	private void addHeaderMethod() {
		addHeaderButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				stageHeader = new Stage();
				// TODO Auto-generated method stub	
				if(event.getClickCount() == 1 && querryVisible == false ) {
					stageHeader = new Stage();
//					querryVisible = true;
					try {
						root = FXMLLoader.load(getClass().getResource("/fxml/testFactory/AddHeaderNameValue.fxml"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					Scene second = new Scene(root);

					stageHeader.initOwner(MainLauncher.stage);
					stageHeader.setScene(second);
					stageHeader.setTitle("Add Path");
					stageHeader.show();
				} 
			}
			});
	
	}
		
	





	private void addPathMethod() {
		addPathButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				stagePath = new Stage();
				// TODO Auto-generated method stub	
				if(event.getClickCount() == 1 && querryVisible == false ) {
					stagePath = new Stage();
//					querryVisible = true;
					try {
						root = FXMLLoader.load(getClass().getResource("/fxml/testFactory/AddPathNameValue.fxml"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					Scene second = new Scene(root);

					stagePath.initOwner(MainLauncher.stage);
					stagePath.setScene(second);
					stagePath.setTitle("Add Path");
					stagePath.show();
				} 
			}
			});
	
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

					stageQuerry.setTitle("Add Querry");
					
					//stageSoap.initModality(Modality.WINDOW_MODAL);

					
					stageQuerry.show();
				} 
			}
			});
	
	}
	
	public void populateQuerryTable(){
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
	
	public static void populateQuerryTableStatic(){
		tableColumnVarNamePassed.setCellValueFactory(new Callback<CellDataFeatures<TableQuerry, String>, ObservableValue<String>>() {
		     public ObservableValue<String> call(CellDataFeatures<TableQuerry, String> p) {
		         return p.getValue().getName();
		     }
		  });
		
		
	}
	public void populatePathTable(){
		columnPathName.setCellValueFactory(new Callback<CellDataFeatures<TablePath, String>, ObservableValue<String>>() {
		     public ObservableValue<String> call(CellDataFeatures<TablePath, String> p) {
		         return p.getValue().getName();
		     }
		  });
		
		columnPathValue.setCellValueFactory(new Callback<CellDataFeatures<TablePath, String>, ObservableValue<String>>() {
		     public ObservableValue<String> call(CellDataFeatures<TablePath, String> p) {
		         return p.getValue().getContent();
		     }
		  });
	}
	
	public static void populatePathTableStatic(){
		tableColumnPathNamePassed.setCellValueFactory(new Callback<CellDataFeatures<TablePath, String>, ObservableValue<String>>() {
		     public ObservableValue<String> call(CellDataFeatures<TablePath, String> p) {
		         return p.getValue().getName();
		     }
		  });
	
	tableColumnPathPassed.setCellValueFactory(new Callback<CellDataFeatures<TablePath, String>, ObservableValue<String>>() {
	     public ObservableValue<String> call(CellDataFeatures<TablePath, String> p) {
	         return p.getValue().getContent();
	     }
	  });
	}
	
	
	public void populateHeaderTable(){
		columnHeaderName.setCellValueFactory(new Callback<CellDataFeatures<TableHeader, String>, ObservableValue<String>>() {
		     public ObservableValue<String> call(CellDataFeatures<TableHeader, String> p) {
		         return p.getValue().getName();
		     }
		  });
		
		columnHeaderValue.setCellValueFactory(new Callback<CellDataFeatures<TableHeader, String>, ObservableValue<String>>() {
		     public ObservableValue<String> call(CellDataFeatures<TableHeader, String> p) {
		         return p.getValue().getContent();
		     }
		  });
	}
	
	public static void populateHeaderTableStatic(){
		tableColumnHeaderNamePassed.setCellValueFactory(new Callback<CellDataFeatures<TableHeader, String>, ObservableValue<String>>() {
		     public ObservableValue<String> call(CellDataFeatures<TableHeader, String> p) {
		         return p.getValue().getName();
		     }
		  });
	
	tableColumnHeaderPassed.setCellValueFactory(new Callback<CellDataFeatures<TableHeader, String>, ObservableValue<String>>() {
	     public ObservableValue<String> call(CellDataFeatures<TableHeader, String> p) {
	         return p.getValue().getContent();
	     }
	  });
	}
	
	public void populateCookieTable(){
		columnCookieName.setCellValueFactory(new Callback<CellDataFeatures<TableCookie, String>, ObservableValue<String>>() {
		     public ObservableValue<String> call(CellDataFeatures<TableCookie, String> p) {
		         return p.getValue().getName();
		     }
		  });
		
		columnCookieValue.setCellValueFactory(new Callback<CellDataFeatures<TableCookie, String>, ObservableValue<String>>() {
		     public ObservableValue<String> call(CellDataFeatures<TableCookie, String> p) {
		         return p.getValue().getContent();
		     }
		  });
	}
	
	public static void populateCookieTableStatic(){
		tableColumnCookieNamePassed.setCellValueFactory(new Callback<CellDataFeatures<TableCookie, String>, ObservableValue<String>>() {
		     public ObservableValue<String> call(CellDataFeatures<TableCookie, String> p) {
		         return p.getValue().getName();
		     }
		  });
	
	tableColumnCookiePassed.setCellValueFactory(new Callback<CellDataFeatures<TableCookie, String>, ObservableValue<String>>() {
	     public ObservableValue<String> call(CellDataFeatures<TableCookie, String> p) {
	         return p.getValue().getContent();
	     }
	  });
	}

	
	public void setTestSuiteService( TestProjectService tsService)
    {
        this.tsService = tsService;
    }

    public void setTestSuiteManagerController(TestSuiteManagerController tsMainController)
    {
        this.tsMainController = tsMainController;
    }

    public Node getView() {
		
		testProjectService = new TestProjectService();
        
        stepController.setStep(stepId);
        stepController.setCommonFields();
        
        
    	Step step = testProjectService.getStep(stepId);
    	
    	if(step instanceof RestStep){
    		
    		
    			
//    			if(!restMethod.getItems().contains(RestMethod.POST)) {
    			//	restMethod.getPromptText();
    				restMethod.getItems().clear();
    	        	restMethod.getItems().addAll(RestMethod.values());
    	        	
//    	        }
    	        restMethod.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<RestMethod>() {
    				public void changed(ObservableValue ov, RestMethod value,RestMethod new_value) {
    					
    					((RestStep) step).setMethod(new_value);
    					// TO do : find a way to refresh 
    					restMethod.setValue(new_value);
    					
    				}
    			});  
    		
    	}
    	
    	
    	        

    	
		return rootRestStep;
	}
    
    

    public void setRestStep(final String stepUID)
    {
         
        
    }
    
    public void saveRest(ActionEvent e) {
    	
		RestStep rest = new RestStep();
		step = (RestStep) testProjectService.getStep(stepId);
		rest.setAssertList(step.getAssertList());
		rest.setAssetMap(step.getAssetMap());
		rest.setContentType(step.getContentType());
		rest.setDependsOn(step.getDependsOn());
		rest.setExecutionList(step.getExecutionList());
		rest.setMethod(step.getMethod());
		//rest.setName(lblName.getText());
		rest.setRequest(step.getRequest());
		//rest.setServer(serverBox.getValue());
		//rest.setService(serviceBox.getValue());
		rest.setVariableList(step.getVariableList());
		
		HashMap<String, String> cookieMap = new HashMap<String, String>();
		for (int i= 0;i<tableCookieVarData.size();i++){
			cookieMap.put(columnCookieName.getCellData(i), columnCookieValue.getCellData(i));
		}
		rest.setCookie(cookieMap);
		
		HashMap<String, String> headerMap = new HashMap<String, String>();
		for (int i= 0;i<tableHeaderVarData.size();i++){
			headerMap.put(columnHeaderName.getCellData(i), columnHeaderValue.getCellData(i));
		}
		rest.setHeader(headerMap);
		
		HashMap<String, String> queryMap = new HashMap<String, String>();
		for (int i= 0;i<tableQuerryVarData.size();i++){
			queryMap.put(columnQuerryName.getCellData(i), columnQuerryValue.getCellData(i));
		}
		rest.setQuery(queryMap);
		
//		StringBuilder path = new StringBuilder(restPath.getText());
//		for (int i= 0;i<tablePathVarData.size();i++){
//			path.append("/" + columnPathName.getCellData(i) + "/" + columnPathValue.getCellData(i));
//		}
//		rest.setPath(path.toString());
		rest.setPath(restPath.getText());
		System.out.println(restPath.getText());
		
		tsService.setStepByUID(rest, stepId);
		tsService.saveTestSuite();
		
		
		
	} 
    	
    
   
    
}
