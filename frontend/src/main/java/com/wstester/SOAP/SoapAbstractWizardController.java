package com.wstester.SOAP;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.datafx.controller.flow.action.BackAction;
import org.datafx.controller.flow.action.LinkAction;

public class SoapAbstractWizardController {

    @FXML
    @BackAction
    private Button backButton;

    @FXML
    @LinkAction(SoapStepThreeController.class)
    private Button finishButton;

    public Button getBackButton() {
        return backButton;
    }

    public Button getFinishButton() {
        return finishButton;
    }
}