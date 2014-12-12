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

public class RestFlowNodeSkin extends CustomFlowNodeSkinNew {

    public RestFlowNodeSkin(FXSkinFactory skinFactory,
            VNode model, VFlow controller) {
        super(skinFactory, model, controller);

    }

    @Override
    protected Node createView() {

        contentPane.setMaxScaleX(1.0);
        contentPane.setMaxScaleY(1.0);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RestUI2.fxml"));
 
        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(eu.mihosoft.vrl.workflow.demo.Main.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

        RestUI2Controller controller = fxmlLoader.getController();
        controller.setNode(getModel());
        
        Pane root = (Pane) fxmlLoader.getRoot();

        return root;
    }
    
    
}