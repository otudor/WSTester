/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wstester.plot2d;

import com.wstester.elements.Dialog;
import com.wstester.model.Environment;
import com.wstester.model.MongoService;
import com.wstester.model.Server;
import com.wstester.model.Service;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IProjectFinder;

import eu.mihosoft.vrl.workflow.ConnectionEvent;
import eu.mihosoft.vrl.workflow.Connector;
import eu.mihosoft.vrl.workflow.VNode;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


/**
 * FXML Controller class
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class PlotterUIController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void onAction(ActionEvent event) {
    }
    private VNode node;
    @FXML
    private Pane contentPane;
    private ServerInput functionInput;

    /**
     * @return the node
     */
    public VNode getNode() {
        return node;
    }

    /**
     * @param node the node to set
     */
    public void setNode(VNode node) {
        this.node = node;

        registerListener();
    }

    private void registerListener() {
        if (getNode() == null) {
            return;
        }

        if (getNode().getValueObject().getValue() == null) {
            return;
        }

        Plotter2D plotter = (Plotter2D) getNode().getValueObject().getValue();

         Connector c = getNode().getInputs().get(0);

   /*     ChangeListener<Number> propertyListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                final LineChart<Number, Number> lineChart = MathUtil.createChart();
                lineChart.getData().clear();
//                plotter.plot(functionInput, lineChart);

                contentPane.getChildren().clear();
                contentPane.getChildren().add(createView());
//                contentPane.getChildren().add(lineChart);
                

                AnchorPane.setTopAnchor(lineChart, 0.0);
                AnchorPane.setBottomAnchor(lineChart, 0.0);
                AnchorPane.setLeftAnchor(lineChart, 0.0);
                AnchorPane.setRightAnchor(lineChart, 0.0);
            }
        };*/

        c.addConnectionEventListener(new EventHandler<ConnectionEvent>() {
            @Override
            public void handle(ConnectionEvent t) {

                Connector sender = t.getSenderConnector();

                functionInput =
                        (ServerInput) sender.getNode().
                        getValueObject().getValue();

                if (t.getEventType().equals(ConnectionEvent.REMOVE)) {
                    contentPane.getChildren().clear();
//                    functionInput.aProperty().removeListener(propertyListener);
                } else {
                   // final LineChart<Number, Number> lineChart = MathUtil.createChart();
                     Button b = new Button();
//                    plotter.plot(functionInput, lineChart);
                    
//                    functionInput.aProperty().addListener(propertyListener);
//                    TextField passwordField = new TextField();
//                	passwordField.setText("adasda");
//                	passwordField.setEditable(true);
                    contentPane.getChildren().clear();
//                    contentPane.getChildren().add(b);
//                    contentPane.getChildren().add(passwordField);
                    contentPane.getChildren().add(createView());
                    contentPane.toFront();
                    
                	

                   /* AnchorPane.setTopAnchor(lineChart, 0.0);
                    AnchorPane.setBottomAnchor(lineChart, 0.0);
                    AnchorPane.setLeftAnchor(lineChart, 0.0);
                    AnchorPane.setRightAnchor(lineChart, 0.0);*/
                   // WorkflowIO.saveToXML(p, flow);
                }
            }
        });
        
        

    }
    
    public GridPane createView(){
    	GridPane dataGrid = new GridPane();
    	Label port = new Label();
    	port.setText("Port: ");
    	port.setTextFill(Color.web("#0076a3"));
    	dataGrid.add(port, 0, 0);
    	Label DBName = new Label();
    	DBName.setText("DBName: ");
    	DBName.setTextFill(Color.web("#0076a3"));
    	dataGrid.add(DBName, 0, 1);
    	Label Username = new Label();
    	Username.setText("Username: ");
    	Username.setTextFill(Color.web("#0076a3"));
    	dataGrid.add(Username, 0, 2);
    	Label password = new Label();
    	password.setText("Password: ");
    	password.setTextFill(Color.web("#0076a3"));
    	dataGrid.add(password, 0, 3);
    	
    	TextField portField = new TextField();
    	portField.setVisible(true);
    	portField.setEditable(true);
    	dataGrid.add(portField, 1, 0);
    	TextField DBNameField = new TextField();
    	dataGrid.add(DBNameField, 1, 1);
    	TextField usernameField = new TextField();
    	dataGrid.add(usernameField, 1, 2);
    	TextField passwordField = new TextField();
    	passwordField.setText("adasda");
    	dataGrid.add(passwordField, 1, 3);
    	dataGrid.setDisable(false);
    	
    	IProjectFinder projectFinder = null;
		try {
			projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
		} catch (Exception e) {
			e.printStackTrace();
			Dialog.errorDialog("Could not find the environmentList. Please try again!", null);
		}
    	List<Environment> environmentList = projectFinder.getTestProject().getEnvironmentList();
    	
    	Environment environment = environmentList.get(0);
    	List<Server> serverList = environment.getServers();
    	for (Server server : serverList){
    		List<Service> serviceList = server.getServices();
    			for (Service service : serviceList) {
    				if (service instanceof MongoService) {
    					portField.setText(((MongoService) service).getPort());
    					DBNameField.setText(((MongoService) service).getDbName());
    					usernameField.setText(((MongoService) service).getUser());
    					passwordField.setText(((MongoService) service).getPassword());
    				}
    			}
    	}
    	
		return dataGrid;
    	
    }
}
