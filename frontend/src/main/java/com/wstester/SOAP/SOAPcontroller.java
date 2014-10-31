package com.wstester.SOAP;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import org.datafx.controller.flow.Flow;
import org.datafx.controller.flow.FlowException;

import com.wstester.main.MainLauncher;

public class SOAPcontroller implements Initializable {
    
    @FXML
    private Button imp;
    private Stage stageImport = new Stage();
    boolean isDisplayed = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.importDefinitions();
    }



	private void importDefinitions() {
		imp.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				// TODO Auto-generated method stub	
				if(event.getClickCount() == 1 && isDisplayed == false) {
					stageImport = new Stage();
					stageImport.initOwner(MainLauncher.stage);
					 try {
						new Flow(SoapStepOneController.class).startInStage(stageImport);
					} catch (FlowException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					

				}

			

			}
		});
		

	}    
}
