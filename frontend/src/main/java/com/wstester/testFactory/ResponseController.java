package com.wstester.testFactory;

import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import org.codehaus.jettison.json.JSONException;
import org.xml.sax.SAXException;

import com.wstester.elements.Dialog;
import com.wstester.elements.Pair;
import com.wstester.main.MainLauncher;
import com.wstester.model.Execution;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Response;
import com.wstester.model.Step;
import com.wstester.util.Parser;

public class ResponseController implements Initializable{
	
	@FXML
	private Label status;
	@FXML 
	private Pane responsePane;
	@FXML
	private Label headerLabel;
	@FXML
	private TableView<Pair> headerTable;
	@FXML
	private TableColumn<Pair, String> key;
	@FXML
	private TableColumn<Pair, String> value;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		this.key.setCellValueFactory(new PropertyValueFactory<Pair, String>("key"));
		this.value.setCellValueFactory(new PropertyValueFactory<Pair, String>("value"));
	}
	
	//TODO: please update/'call setResponse' when test finishes running 
	public void setResponse(Step step) {

		clearFields();
		
		if (step.getLastExecution() != null) {

			Execution execution = step.getLastExecution();
			Response response = execution.getResponse();

			if (response.getStatus() == ExecutionStatus.PASSED) {
				status.setText(response.getStatus().toString());
				status.setStyle("-fx-text-fill: green");
				setResponseContent(response.getContent());
				setHeaders(response.getHeaderMap());
			}
			else if (response.getStatus() == ExecutionStatus.ERROR) {
				status.setText(response.getStatus().toString());
				status.setStyle("-fx-text-fill: firebrick");
				setResponseContent(response.getErrorMessage());
			}
			else if (response.getStatus() == ExecutionStatus.FAILED) {
				status.setText(response.getStatus().toString());
				status.setStyle("-fx-text-fill: blue");
				setResponseContent(response.getAssertResponseList().toString());
			}
		}
	}

	private void clearFields() {
		status.setText("Not Runned");
		status.setStyle("-fx-text-fill: black");
		responsePane.getChildren().clear();
		headerLabel.setVisible(false);
		headerTable.setVisible(false);
		headerTable.getItems().clear();
	}

	public void setResponseContent(String response) {

		Parser parser = new Parser();
		
		try {
			responsePane.getChildren().clear();
			responsePane.getChildren().add(parser.parseXML(response));
		} catch (SAXException xmlException) {
			try{
				responsePane.getChildren().add(parser.parseJSON(response));
			} catch (JSONException jsonException) {
				Label label = new Label(response);
				responsePane.getChildren().add(label);
			}
		} catch (Exception e){
			e.printStackTrace();
			Dialog.errorDialog("Response couldn't be displayed. Please try again!", MainLauncher.stage);
		}
	}
	
	private void setHeaders(Map<String, String> headerMap) {
		if (headerMap != null) {
			headerLabel.setVisible(true);
			headerTable.setVisible(true);
			
			ObservableList<Pair> headerData = FXCollections.observableArrayList();
			for (Entry<String, String> entry : headerMap.entrySet()) {
				headerData.add(new Pair(entry.getKey(), entry.getValue()));
			}
			headerTable.setItems(headerData);
		}
	}
}
