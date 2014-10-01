package com.wstester.SOAP;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import org.datafx.controller.FXMLController;
import org.datafx.controller.flow.action.LinkAction;

import javax.annotation.PostConstruct;

@FXMLController(value="/fxml/SOAP/stepOne.fxml", title = "Wizard: Step 1")
public class RestStepOneController extends RestAbstractWizardController {
    
	@FXML
    @LinkAction(RestStepTwoController.class)
    private Button nextButton;

    @PostConstruct
    public void init() {
        getBackButton().setDisable(true);
    }
}