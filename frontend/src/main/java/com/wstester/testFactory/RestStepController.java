package com.wstester.testFactory;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import com.wstester.elements.Dialog;
import com.wstester.elements.Pair;
import com.wstester.main.MainLauncher;
import com.wstester.model.RestMethod;
import com.wstester.model.RestStep;
import com.wstester.model.Step;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IProjectFinder;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextArea;
import javafx.fxml.Initializable;

public class RestStepController implements Initializable {

	@FXML
	private StepController stepController;
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
    
	@FXML
	public void initialize(URL url, ResourceBundle resource) {
				
		this.queryKey.setCellValueFactory(new PropertyValueFactory<Pair, String>("key"));
		this.queryKey.setCellFactory(TextFieldTableCell.forTableColumn());
		this.queryValue.setCellValueFactory(new PropertyValueFactory<Pair, String>("value"));
		this.queryValue.setCellFactory(TextFieldTableCell.forTableColumn());
		this.addQueryMethod();
		
		this.headerKey.setCellValueFactory(new PropertyValueFactory<Pair, String>("key"));
		this.headerKey.setCellFactory(TextFieldTableCell.forTableColumn());
		this.headerValue.setCellValueFactory(new PropertyValueFactory<Pair, String>("value"));
		this.headerValue.setCellFactory(TextFieldTableCell.forTableColumn());
		this.addHeaderMethod();
		
		this.cookieKey.setCellValueFactory(new PropertyValueFactory<Pair, String>("key"));
		this.cookieKey.setCellFactory(TextFieldTableCell.forTableColumn());
		this.cookieValue.setCellValueFactory(new PropertyValueFactory<Pair, String>("value"));
		this.cookieValue.setCellFactory(TextFieldTableCell.forTableColumn());
		this.addCookieMethod();
	}
	
	public void setStep(String stepId){
		this.stepId = stepId;
		
    	clearFields();
    	Step step = null;
		try {
			IProjectFinder projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
			step = projectFinder.getStepById(stepId);
		} catch (Exception e) {
			e.printStackTrace();
			Dialog.errorDialog("Could not find the environmentList. Please try again!", null);
		}
		
        stepController.setStep(stepId);
        stepController.setCommonFields();
        
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
				for (Entry<String, String> entry : queryMap.entrySet()){
					queryData.add(new Pair(entry.getKey(), entry.getValue()));
				}
			}
			queryTable.setItems(queryData);
			
			ObservableList<Pair> headerData = FXCollections.observableArrayList();
			if (((RestStep) step).getHeader() != null) {
				Map<String, String> hederMap = ((RestStep) step).getHeader();
				for (Entry<String, String> entry : hederMap.entrySet()){
					headerData.add(new Pair(entry.getKey(), entry.getValue()));
				}
			}
			headerTable.setItems(headerData);
			
			ObservableList<Pair> cookieData = FXCollections.observableArrayList();
			if (((RestStep) step).getCookie() != null) {
				Map<String, String> cookieMap = ((RestStep) step).getCookie();
				for (Entry<String, String> entry : cookieMap.entrySet()){
					cookieData.add(new Pair(entry.getKey(), entry.getValue()));
				}
			}
			cookieTable.setItems(cookieData);
    	}
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
    
    private void clearFields() {
		
    	restMethod.getItems().clear();
		restMethod.setValue(null);
		contentType.clear();
		restPath.setText("");
		request.setText("");
		queryTable.getItems().clear();
		headerTable.getItems().clear();
		cookieTable.getItems().clear();
	}

    // called when the save button is clicked
	public void saveRest(ActionEvent actionEvent) {
    	
		// get the step before the save
		IProjectFinder projectFinder = null;
		try {
			projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
		} catch (Exception e) {
			e.printStackTrace();
			Dialog.errorDialog("Could not find the environmentList. Please try again!", null);
		}
		
		// construct the step after the save
    	RestStep newStep = new RestStep();
    	
    	newStep.setServerId(stepController.getServer() == null ? null : stepController.getServer().getId());
    	newStep.setServiceId(stepController.getService() == null ? null : stepController.getService().getId());
    	newStep.setName(stepController.getName());
    	newStep.setMethod(restMethod.getValue());
    	newStep.setContentType(contentType.getText());
    	
    	newStep.setPath(restPath.getText());
    	
    	Map<String, String> queryMap = new HashMap<String, String>();
    	for (Pair pair : queryTable.getItems()) {
    		queryMap.put(pair.getKey(), pair.getValue());
    	}
		newStep.setQuery(queryMap);
		
		Map<String, String> headerMap = new HashMap<String, String>();
		for (Pair pair : headerTable.getItems()) {
			headerMap.put(pair.getKey(), pair.getValue());
		}
		newStep.setHeader(headerMap);
		
		Map<String, String> cookieMap = new HashMap<String, String>();
		for (Pair pair : cookieTable.getItems()) {
			cookieMap.put(pair.getKey(), pair.getValue());
		}
		newStep.setCookie(cookieMap);
		
		newStep.setRequest(request.getText());
		
		//TODO: add in projectFinder an operation setStepById 
    	projectFinder.getStepById(stepId).copyFrom(newStep);
	}
}