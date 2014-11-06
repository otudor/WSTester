package com.wstester.testFactory;

import java.util.HashMap;
import java.util.Map;
import com.wstester.elements.Dialog;
import com.wstester.main.MainLauncher;
import com.wstester.model.RestMethod;
import com.wstester.model.RestStep;
import com.wstester.model.Step;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextArea;

public class RestStepController {	

	@FXML
	private StepController stepController;
	@FXML
	private ResponseController responseController;
    @FXML 
    private Node rootRestStep;
    @FXML 
    private TextField restPath;
    @FXML 
    private ComboBox<RestMethod> restMethod;
	@FXML
	private TextField contentType;
    @FXML
    private TextArea request;

	// query
    @FXML 
    private Button addQueryButton;
	@FXML 
	private TableView<Pair> queryTable;
	@FXML 
	private TableColumn<Pair, String> queryKey;
	@FXML 
	private TableColumn<Pair, String> queryValue;

	// path
    @FXML 
    private Button addPathButton;
	@FXML
	private TableView<Pair> pathTable;
	@FXML
	private TableColumn<Pair, String> pathKey;
	@FXML
	private TableColumn<Pair, String> pathValue;

	// header
    @FXML 
    private Button addHeaderButton;
	@FXML
	private TableView<Pair> headerTable;
	@FXML
	private TableColumn<Pair, String> headerKey;
	@FXML
	private TableColumn<Pair, String> headerValue;

	// cookie
    @FXML 
    private Button addCookieButton;
	@FXML
	private TableView<Pair> cookieTable;
	@FXML 
	private TableColumn<Pair, String> cookieKey;
	@FXML 
	private TableColumn<Pair, String> cookieValue;

    private String stepId;
    
    public void setStep(String stepId){
		this.stepId = stepId;
	}
    
	@FXML
	private void initialize() {
		
		this.pathKey.setCellValueFactory(new PropertyValueFactory<Pair, String>("key"));
		this.pathValue.setCellValueFactory(new PropertyValueFactory<Pair, String>("value"));
		this.addPathMethod();
		
		this.queryKey.setCellValueFactory(new PropertyValueFactory<Pair, String>("key"));
		this.queryValue.setCellValueFactory(new PropertyValueFactory<Pair, String>("value"));
		this.addQueryMethod();
		
		this.headerKey.setCellValueFactory(new PropertyValueFactory<Pair, String>("key"));
		this.headerValue.setCellValueFactory(new PropertyValueFactory<Pair, String>("value"));
		this.addHeaderMethod();
		
		this.cookieKey.setCellValueFactory(new PropertyValueFactory<Pair, String>("key"));
		this.cookieValue.setCellValueFactory(new PropertyValueFactory<Pair, String>("value"));
		this.addCookieMethod();
	}
	
	private void addCookieMethod() {
		addCookieButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				if(event.getClickCount() == 1) {
					
					Pair pair = Dialog.twoTextBoxDialog("Key", "Value", MainLauncher.stage);
					
					if (pair != null){
						ObservableList<Pair> cookieData = FXCollections.observableArrayList();
						cookieData.addAll(cookieTable.getItems());
						cookieData.add(pair);
						cookieTable.setItems(cookieData);
					}
				} 
			}
		});
	}

	private void addHeaderMethod() {
		addHeaderButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount() == 1) {
					
					Pair pair = Dialog.twoTextBoxDialog("Key", "Value", MainLauncher.stage);
					
					if (pair != null){
						ObservableList<Pair> headerData = FXCollections.observableArrayList();
						headerData.addAll(headerTable.getItems());
						headerData.add(pair);
						headerTable.setItems(headerData);
					}
				} 
			}
		});
	}

	private void addPathMethod() {
		addPathButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount() == 1) {
					
					Pair pair = Dialog.twoTextBoxDialog("Key", "Value", MainLauncher.stage);
					if (pair != null){
						ObservableList<Pair> pathData = FXCollections.observableArrayList();
						pathData.addAll(pathTable.getItems());
						pathData.add(pair);
						pathTable.setItems(pathData);
					}
				} 
			}
		});
	}

	private void addQueryMethod() {
		addQueryButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount() == 1) {
					
					Pair pair = Dialog.twoTextBoxDialog("Key", "Value", MainLauncher.stage);
					
					if (pair != null){
						ObservableList<Pair> queryData = FXCollections.observableArrayList();
						queryData.addAll(queryTable.getItems());
						queryData.add(pair);
						queryTable.setItems(queryData);
					}
				} 
			}
		});
	}
	
    public Node getView() {
		
    	clearFields();
		TestProjectService testProjectService = new TestProjectService();
		Step step = testProjectService.getStep(stepId);
		
        stepController.setStep(stepId);
        stepController.setCommonFields();
        responseController.setResponse(step);
        
    	if(step instanceof RestStep){

			restMethod.getItems().addAll(RestMethod.values());
			restMethod.setValue(((RestStep) step).getMethod());
			
			contentType.setText(((RestStep) step).getContentType());
			
			if (((RestStep) step).getPath() != null) {
				restPath.setText(((RestStep) step).getPath());
			}
			
			if (((RestStep) step).getRequest() != null) {
				request.setText(((RestStep) step).getRequest().toString());
			}
			
			ObservableList<Pair> queryData = FXCollections.observableArrayList();
			if (((RestStep) step).getQuery() != null) {
				Map<String, String> queryMap = ((RestStep) step).getQuery();
				for (String key : queryMap.keySet()){
					queryData.add(new Pair(key, queryMap.get(key)));
				}
			}
			queryTable.setItems(queryData);
			
			ObservableList<Pair> headerData = FXCollections.observableArrayList();
			if (((RestStep) step).getHeader() != null) {
				Map<String, String> hederMap = ((RestStep) step).getHeader();
				for (String key : hederMap.keySet()){
					headerData.add(new Pair(key, hederMap.get(key)));
				}
			}
			headerTable.setItems(headerData);
			
			ObservableList<Pair> cookieData = FXCollections.observableArrayList();
			if (((RestStep) step).getCookie() != null) {
				Map<String, String> cookieMap = ((RestStep) step).getCookie();
				for (String key : cookieMap.keySet()){
					cookieData.add(new Pair(key, cookieMap.get(key)));
				}
			}
			cookieTable.setItems(cookieData);
    	}
    	
		return rootRestStep;
	}
    
    private void clearFields() {
		
    	restMethod.getItems().clear();
		restMethod.setValue(null);
		contentType.clear();
		restPath.setText("");
		request.setText("");
		pathTable.getItems().clear();
		queryTable.getItems().clear();
		headerTable.getItems().clear();
		cookieTable.getItems().clear();
	}

	public void saveRest(ActionEvent e) {
    	
    	TestProjectService testProjectService = new TestProjectService();
    	RestStep step = new RestStep();
    	
    	step.setServer(stepController.getServer());
    	step.setService(stepController.getService());
    	step.setName(stepController.getName());
    	step.setMethod(restMethod.getValue());
    	step.setContentType(contentType.getText());
    	
    	StringBuilder pathBuilder = new StringBuilder(restPath.getText());
    	for (Pair pair: pathTable.getItems()) {
    		pathBuilder.append("/" + pair.getKey() + "/" + pair.getValue());
    	}
    	step.setPath(pathBuilder.toString());
    	
    	Map<String, String> queryMap = new HashMap<String, String>();
    	for (Pair pair : queryTable.getItems()) {
    		queryMap.put(pair.getKey(), pair.getValue());
    	}
		step.setQuery(queryMap);
		
		Map<String, String> headerMap = new HashMap<String, String>();
		for (Pair pair : headerTable.getItems()) {
			headerMap.put(pair.getKey(), pair.getValue());
		}
		step.setHeader(headerMap);
		
		Map<String, String> cookieMap = new HashMap<String, String>();
		for (Pair pair : cookieTable.getItems()) {
			cookieMap.put(pair.getKey(), pair.getValue());
		}
		step.setCookie(cookieMap);
		
		step.setRequest(request.getText());
		
    	testProjectService.setStepByUID(step, stepId);
	}    
}