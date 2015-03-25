package com.wstester.testFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.codehaus.jettison.json.JSONException;
import org.xml.sax.SAXException;

import com.wstester.elements.Dialog;
import com.wstester.elements.Pair;
import com.wstester.main.MainLauncher;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Header;
import com.wstester.model.Response;
import com.wstester.util.Parser;

public class ResponseController implements Initializable {

	@FXML
	private Pane rootPane;
	@FXML
	private Button goToAssertButton;
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
		initializeGoToAssertButton(null);
	}

	public void setRespose(Response response) {

		clearFields();

		if (response.getStatus() == ExecutionStatus.PASSED) {
			status.setText(response.getStatus().toString());
			status.setStyle("-fx-text-fill: green");
			setResponseContent(response.getContent());
			setHeaders(response.getHeaderList());
		} else if (response.getStatus() == ExecutionStatus.ERROR) {
			status.setText(response.getStatus().toString());
			status.setStyle("-fx-text-fill: firebrick");
			setResponseContent(response.getErrorMessage());
		} else if (response.getStatus() == ExecutionStatus.FAILED) {
			status.setText(response.getStatus().toString());
			status.setStyle("-fx-text-fill: blue");
			setResponseContent(response.getContent());
			setHeaders(response.getHeaderList());
		}	
	}

	public void initializeGoToAssertButton(Response response) {

		goToAssertButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent action) {
				TabPane tabPane = (TabPane) rootPane.getParent().getParent().getParent().getParent().getParent();
				tabPane.getTabs().get(2).setUserData(response);
				tabPane.getSelectionModel().select(2);
			}
		});
	}

	private void setResponseContent(String response) {

		Parser parser = new Parser();

		try {
			responsePane.getChildren().clear();
			responsePane.getChildren().add(parser.parseXML(response));
		} catch (SAXException xmlException) {
			try {
				responsePane.getChildren().add(parser.parseJSON(response));
			} catch (JSONException jsonException) {
				Label label = new Label(response);
				responsePane.getChildren().add(label);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Dialog.errorDialog("Response couldn't be displayed. Please try again!", MainLauncher.stage);
		}
	}

	private void setHeaders(List<Header> headerList) {
		if (headerList != null && !headerList.isEmpty()) {
			headerLabel.setVisible(true);
			headerTable.setVisible(true);

			ObservableList<Pair> headerData = FXCollections.observableArrayList();
			for (Header header : headerList) {
				headerData.add(new Pair(header.getKeyField(), header.getValueField()));
			}
			headerTable.setItems(headerData);
		}
	}

	private void clearFields() {
		status.setText("Not Runned");
		status.setStyle("-fx-text-fill: black");
		if (responsePane.getChildren() != null) {
			responsePane.getChildren().clear();
		}
		headerLabel.setVisible(false);
		headerTable.setVisible(false);
		headerTable.getItems().clear();
	}
}