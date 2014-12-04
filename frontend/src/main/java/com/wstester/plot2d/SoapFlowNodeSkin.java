package com.wstester.plot2d;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import eu.mihosoft.vrl.workflow.VFlow;
import eu.mihosoft.vrl.workflow.VNode;
import eu.mihosoft.vrl.workflow.fx.FXSkinFactory;

public class SoapFlowNodeSkin extends CustomFlowNodeSkinNew {

    public SoapFlowNodeSkin(FXSkinFactory skinFactory,
            VNode model, VFlow controller) {
        super(skinFactory, model, controller);

    }

    @Override
    protected Node createView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SoapUI.fxml"));

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(eu.mihosoft.vrl.workflow.demo.Main.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

//        DbController controller = fxmlLoader.getController();
//        controller.setInput((FunctionInput) getModel().getValueObject().getValue());

        Pane root = (Pane) fxmlLoader.getRoot();
        
        
        return root;
    }
    
    
}