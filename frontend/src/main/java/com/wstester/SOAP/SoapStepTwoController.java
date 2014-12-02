package com.wstester.SOAP;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import org.datafx.controller.FXMLController;
import org.datafx.controller.flow.action.LinkAction;


@FXMLController(value="/fxml/SOAP/stepTwo.fxml", title = "Wizard: Step 2")
public class SoapStepTwoController extends SoapAbstractWizardController {
	@FXML
	@LinkAction(SoapStepOneController.class)
    private Button backButton;
	@FXML
    @LinkAction(SoapStepThreeController.class)
    private Button nextButton;
	@FXML
    @LinkAction(SoapStepThreeController.class)
    private Button finishButton;
    
}
