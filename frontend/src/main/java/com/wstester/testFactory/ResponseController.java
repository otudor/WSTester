package com.wstester.testFactory;

import org.codehaus.jettison.json.JSONException;
import org.xml.sax.SAXException;

import com.wstester.model.Execution;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Response;
import com.wstester.model.Step;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

public class ResponseController {
	
	@FXML 
	private AnchorPane rootPane;
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
	
    public AnchorPane getView() { 	
        return rootPane;
    }
	
	public void setResponse(Step step) {

		clearFields();
		
		if (step.getLastExecution() != null) {

			Execution execution = step.getLastExecution();
			Response response = execution.getResponse();

			if (response.getStatus() == ExecutionStatus.PASSED) {
				status.setText(response.getStatus().toString());
				status.setStyle("-fx-text-fill: green");
				setResponseContent(response.getContent());
			}
		}
	}

	private void clearFields() {
		status.setText("Not Runned");
		status.setStyle("-fx-text-fill: black");
		responsePane.getChildren().clear();
		headerLabel.setVisible(false);
		headerTable.setVisible(false);
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
			// TODO: auto-generated catch 
			e.printStackTrace();
		}
	}
}
