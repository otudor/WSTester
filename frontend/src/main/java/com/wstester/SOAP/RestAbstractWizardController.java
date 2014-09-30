package com.wstester.SOAP;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.datafx.controller.flow.action.BackAction;
import org.datafx.controller.flow.action.LinkAction;

public class RestAbstractWizardController {

    @FXML
    @BackAction
    private Button backButton;

    @FXML
    @LinkAction(RestStepThreeController.class)
    private Button finishButton;

    public Button getBackButton() {
        return backButton;
    }

    public Button getFinishButton() {
        return finishButton;
    }
}