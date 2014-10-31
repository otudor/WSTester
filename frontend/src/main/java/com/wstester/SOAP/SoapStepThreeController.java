package com.wstester.SOAP;


import javax.annotation.PostConstruct;

import org.datafx.controller.FXMLController;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

@FXMLController(value="/fxml/SOAP/stepThree.fxml", title = "Wizard: Step 3 final")
public class SoapStepThreeController extends SoapAbstractWizardController {
	@FXML
    private Button nextButton;

    @PostConstruct
    public void init() {
        nextButton.setDisable(true);
        getFinishButton().setDisable(true);
    }
	
}
