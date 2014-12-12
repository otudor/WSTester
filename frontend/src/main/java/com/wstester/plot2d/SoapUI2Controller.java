/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wstester.plot2d;

import com.wstester.math.MathUtil;
import com.wstester.model.Environment;
import com.wstester.model.MySQLService;
import com.wstester.model.Server;
import com.wstester.model.Service;
import com.wstester.model.SoapService;
import com.wstester.util.TestProjectService;

import eu.mihosoft.vrl.workflow.ConnectionEvent;
import eu.mihosoft.vrl.workflow.Connector;
import eu.mihosoft.vrl.workflow.VNode;
import eu.mihosoft.vrl.workflow.io.WorkflowIO;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


/**
 * FXML Controller class
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class SoapUI2Controller implements Initializable {

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

        Soap plotter = (Soap) getNode().getValueObject().getValue();

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
    	Label wsdl = new Label();
    	wsdl.setText("WSDL URL: ");
    	wsdl.setTextFill(Color.web("#FFAB00"));
    	dataGrid.add(wsdl, 0, 0);
    	Label port = new Label();
    	port.setText("Port: ");
    	port.setTextFill(Color.web("#FFAB00"));
    	dataGrid.add(port, 0, 1);
    	Label path = new Label();
    	path.setText("Path: ");
    	path.setTextFill(Color.web("#FFAB00"));
    	dataGrid.add(path, 0, 2);
 
    	TextField wsdlField = new TextField();
    	wsdlField.setVisible(true);
    	wsdlField.setEditable(true);
    	dataGrid.add(wsdlField, 1, 0);
    	TextField portField = new TextField();
    	dataGrid.add(portField, 1, 1);
    	TextField pathField = new TextField();
    	dataGrid.add(pathField, 1, 2);
    	
    	TestProjectService testProjectService = new TestProjectService();
    	List<Environment> environmentList = testProjectService.getEnvironmentList();
    	
    	Environment environment = environmentList.get(1);
    	List<Server> serverList = environment.getServers();
    	for (Server server : serverList){
    		List<Service> serviceList = server.getServices();
    			for (Service service : serviceList) {
    				if (service instanceof SoapService) {
    					wsdlField.setText(((SoapService) service).getWsdlURL());
    					portField.setText(((SoapService) service).getPort());
    					pathField.setText(((SoapService) service).getPath());
    					
    				}
    			}
    	}
    	
    	
		return dataGrid;
    	
    }
}
