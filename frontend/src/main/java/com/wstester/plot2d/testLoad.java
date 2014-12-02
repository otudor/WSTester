package com.wstester.plot2d;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import eu.mihosoft.vrl.workflow.FlowFactory;
import eu.mihosoft.vrl.workflow.VFlow;
import eu.mihosoft.vrl.workflow.VNode;
import eu.mihosoft.vrl.workflow.fx.FXSkinFactory;
import eu.mihosoft.vrl.workflow.fx.VCanvas;
import eu.mihosoft.vrl.workflow.io.WorkflowIO;

public class testLoad extends Application{
	
	public static void main(String[] args) {
        launch(args);
    }
	
	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		VFlow flow = FlowFactory.newFlow();
		
//		VFlow flow = WorkflowIO.loadFromXML(Paths.get("D:\\FileNameToWrite.txt"));
//		VFlow flow = WorkflowIO.loadFromXML("<flow><connections/><nodes><node><x>0.0</x><y>0.0</y><width>200.0</width><height>150.0</height><title>Node</title><valueObject><parentId>1</parentId><vReq class=\"eu.mihosoft.vrl.workflow.VisualizationRequestImpl\"/></valueObject><vReq class=\"eu.mihosoft.vrl.workflow.VisualizationRequestImpl\"/><id>1</id><connectors/><mainInputs/><mainOutputs/></node></nodes><visible>true</visible></flow>");
		// add two nodes to the flow
		VNode n1 = flow.newNode();
//		VNode n2 = flow.newNode();
		
		flow.setVisible(true);

		// create a zoomable canvas
		VCanvas canvas = new VCanvas();
		Pane root = canvas.getContentPane();

		// creating a skin factory and attach it to the flow
		FXSkinFactory skinFactory = new FXSkinFactory(root);
		flow.setSkinFactories(skinFactory);
		
		Scene scene = new Scene(canvas, 1024, 600);

        // add css style
		 Button saveBtn = new Button();
	        saveBtn.setLayoutX(10);
	        saveBtn.setLayoutY(450);
	        saveBtn.setText("SAVE");
	        root.getChildren().add(saveBtn);
	        Button loadBtn = new Button();
	        loadBtn.setLayoutX(10);
	        loadBtn.setLayoutY(500);
	        loadBtn.setText("Load");
	        root.getChildren().add(loadBtn);
	        saveBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
				
	        	@Override
				public void handle(MouseEvent event) {
//				  String xml = WorkflowIO.saveToXML( flow.getModel());
//					try {
////						File a = new File("D:\\FileNameToWrite.txt");
////						FileUtils.writeStringToFile(a, xml);
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					
				
					try {
						WorkflowIO.saveToXML(Paths.get("D:\\FileNameToWrite.txt"), flow.getModel());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	        });
	        
	        loadBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
				
				@Override
				public void handle(MouseEvent event) {
				 
					try {
						WorkflowIO.loadFromXML(Paths.get("D:\\FileNameToWrite.txt"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
	        });
	        
        
        stage.setScene(scene);
        stage.show();
		
	}

}
