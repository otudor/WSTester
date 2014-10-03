package com.wstester.testFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.wstester.main.ControlledScreen;
import com.wstester.model.Asset;
import com.wstester.model.Environment;
import com.wstester.model.MySQLStep;
import com.wstester.model.Server;
import com.wstester.model.Service;
import com.wstester.model.Execution;
import com.wstester.model.ExecutionStatus;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

public class MySQLStepController implements Initializable {

    @FXML private Node rootMySQLStep;
    @FXML private TextField stepName;
    @FXML private TextField sqlField;
    @FXML private Label sqlStatus;
    @FXML private Label sqlResponse;
    @FXML private TableView<Execut> tblExecutions;
    @FXML private TableColumn<Execut, String> columnDate;
    @FXML private TableColumn<Execut, String> columnStatus;
    @FXML private GridPane gridPane;
    @FXML private Button cellButton;
    @FXML private ComboBox<Server> serverBox;
    @FXML private ComboBox<Service> serviceBox;
    @FXML private StackPane treePane;
    
    private MySQLStep step;    
    private TestSuiteService tsService;
    private TestSuiteManagerController tsMainController;
    private String uid = null;
    private XmlParser xmlParser;

    @Override
    public void initialize (URL location, ResourceBundle resources) {
    	this.xmlParser = new XmlParser();
    	this.showTreeView();
    }
	

	private void showTreeView() {

		try {
			
			FileInputStream file = new FileInputStream(new File("C:/Users/gvasile/Documents/GitHub/WSTester/backend/pom.xml"));
			treePane.getChildren().clear();
			treePane.getChildren().add((Node)xmlParser.getTreeViewOfXml(file));
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
        return rootMySQLStep;
    }

    public void setMySQLStep(final String stepUID)
    {
          
        step = (MySQLStep) tsService.getStep(stepUID);
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
        stepName.setText(step.getName());
        sqlField.setText(step.getOperation());
        Execution execution = step.getLastExecution();
        
        if(gridPane.getChildren().contains(cellButton))
        {
        	gridPane.getChildren().remove(cellButton);
        }
        
        uid = stepUID;
        if( execution != null)
        {
        	if (execution.getResponse().getStatus() == ExecutionStatus.PASSED)
        		sqlStatus.setText("PASSED");
        	//else ....
        		//FAILED
        	sqlResponse.setText(execution.getResponse().getContent());
        	List<Execution> list = step.getExecutionList();
        	List<Execut> lista = new ArrayList<>();
        	for(Execution exec : list)
        	{
        		Execut exemplu = new Execut(exec.getRunDate().toString(),exec.getResponse().getStatus().toString(),exec.getResponse().getContent());
        		lista.add(exemplu);
        	}
        	        	
            columnDate.setCellValueFactory(
            		new PropertyValueFactory<Execut,String>("Date")
            		);
            
            columnStatus.setCellValueFactory(
            		new PropertyValueFactory<Execut,String>("Status")
            		);
            TableColumn columnResponse = new TableColumn("Response");
            columnResponse.setSortable(false);
            columnResponse.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Execut, Boolean>, ObservableValue<Boolean>>() {
     
                    @Override
                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Execut, Boolean> p) {
                        return new SimpleBooleanProperty(p.getValue() != null);
                    }
                });
     
            columnResponse.setCellFactory(
                new Callback<TableColumn<Execut, Boolean>, TableCell<Execut, Boolean>>() {
     
                    @Override
                    public TableCell<Execut, Boolean> call(TableColumn<Execut, Boolean> p) {
                        return new ButtonCell(tblExecutions);
                    }
     
                });
            columnResponse.setPrefWidth(80);
            tblExecutions.getColumns().add(columnResponse);
            if(tblExecutions.getColumns().size()>3)
            {
            tblExecutions.getColumns().remove(2);
            }
            tblExecutions.setItems(null);
        	tblExecutions.setItems(FXCollections.observableArrayList(lista));
        	FileInputStream file = null;
        	treePane.getChildren().clear();
//        	showTreeView();

        	 
        }
          
        
        
    }
    public static class Execut{
    	private final SimpleStringProperty date;
    	private final SimpleStringProperty status;
  	
    	private Execut(String Date, String Status, String Response){
    		this.date = new SimpleStringProperty(Date);
    		this.status = new SimpleStringProperty(Status);
       	}
    	
    	public String getDate(){
    		return date.get();
    	}
    	public String getStatus(){
    		return status.get();
    	}

    	public void setDate(String Date){
    		date.set(Date);
    	}
    	public void setStatus(String Status){
    		status.set(Status);
    	}

    }
    
    private class ButtonCell extends TableCell<Execut, Boolean> {
    	 
        final Button cellButton = new Button("Details");
        
        ButtonCell(final TableView tblView) {
        	
        	cellButton.setPrefSize(80, 25);
            cellButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
 
               
            });
        }
 
       
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(cellButton);
            }
        }
    }
    public void saveMySQL(ActionEvent e) {

		MySQLStep sql = new MySQLStep();
		sql.setOperation(sqlField.getText());
		sql.setName(stepName.getText());
		sql.setService(step.getService());
		sql.setServer(step.getServer());
		sql.setExecutionList(step.getExecutionList());
		sql.setAssertList(step.getAssertList());
		sql.setAssetMap((HashMap<Asset, String>) step.getAssetMap());
		sql.setDependsOn(step.getDependsOn());
		sql.setVariableList(step.getVariableList());
		tsService.setStepByUID(sql, uid);
		tsService.saveTestSuite();
	} 
    
    
    
}