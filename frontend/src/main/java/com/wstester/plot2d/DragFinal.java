/*
 * Copyright 2012 Michael Hoffer <info@michaelhoffer.de>. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY Michael Hoffer <info@michaelhoffer.de> "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL Michael Hoffer <info@michaelhoffer.de> OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of Michael Hoffer <info@michaelhoffer.de>.
 */
package com.wstester.plot2d;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

import com.wstester.elements.Dialog;
import com.wstester.main.ControlledScreen;
import com.wstester.main.ScreensController;
import com.wstester.model.Environment;
import com.wstester.model.Service;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IProjectFinder;

import eu.mihosoft.vrl.workflow.Connector;
import eu.mihosoft.vrl.workflow.FlowFactory;
import eu.mihosoft.vrl.workflow.VFlow;
import eu.mihosoft.vrl.workflow.VFlowModel;
import eu.mihosoft.vrl.workflow.VNode;
import eu.mihosoft.vrl.workflow.fx.FXFlowNodeSkin;
import eu.mihosoft.vrl.workflow.fx.FXFlowNodeSkinBase;
import eu.mihosoft.vrl.workflow.fx.FXSkinFactory;
import eu.mihosoft.vrl.workflow.fx.FXValueSkinFactory;
import eu.mihosoft.vrl.workflow.fx.VCanvas;
import eu.mihosoft.vrl.workflow.io.WorkflowIO;
import eu.mihosoft.vrl.workflow.skin.Skin;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class DragFinal implements Initializable{
	
	@FXML
	private Pane toolBar;
	@FXML
	private AnchorPane workPlace;
	@FXML
	private ComboBox<Environment> envirormentBox;
	@FXML
	private Button addEnvButton;
	String Type;
	private VBox newIco = new VBox();
	private VBox newIco2 = new VBox();
	private VBox newIco3 = new VBox();
	private VBox newIco4 = new VBox();
	private VBox newIco5 = new VBox();
	private VCanvas canvas = new VCanvas();
	private VFlow flow = FlowFactory.newFlow();
	private String envId;
    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	 	 toolBar.setStyle("-fx-background-color: red;");
         toolBar.setPrefSize(200,600);
         
         flow.setVisible(true);
         
         canvas.setTranslateToMinNodePos(false);
         canvas.setMinScaleX(0.5);
         canvas.setMinScaleY(0.5);
         canvas.setMaxScaleX(1.0);
         canvas.setMaxScaleY(1.0);
         
         
//         Pane toolBar = new Pane();
//        
//        toolBar.setStyle("-fx-background-color: red;");
//        toolBar.setPrefSize(200,600);
        canvas.setPrefSize(1024, 600);
        canvas.setLayoutX(200);
         
         this.createWorkspace();
         this.createTools();
         this.showEnvirorments();
//         this.addEnvirorments();
}
	
//	private void addEnvirorments() {
//		addEnvButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
//
//			@Override
//			public void handle(MouseEvent event) {
//				
//				envirormentBox.getItems().add("adad");
//				
//			}
//		});
//	}
//	
	
	
	private void showEnvirorments() {
		
    	IProjectFinder projectFinder = null;
		try {
			projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
		} catch (Exception e) {
			e.printStackTrace();
			Dialog.errorDialog("Could not find the environmentList. Please try again!", null);
		}
    	List<Environment> environmentList = projectFinder.getTestProject().getEnvironmentList();
		envirormentBox.getItems().addAll(environmentList);
	}
	
	
	
	
	private void createWorkspace() {
		   canvas.setStyle("-fx-background-color: linear-gradient(to top, rgb(50,50,50), rgb(110,110,120))");
	         canvas.getStyleClass().setAll("vflow-background");
	         canvas.setLayoutX(0);
	         canvas.setLayoutY(0);
	         workPlace.getChildren().add(canvas);
		
	}



	private void createTools() {
		     newIco = (VBox) CreateIcon("Downloads-2-icon.png");
	         newIco.setLayoutX(10);
	         newIco.setLayoutY(10);
	        
	         newIco2 = (VBox) CreateIcon("Group-icon.png");
	         newIco2.setLayoutX(10);
	         newIco2.setLayoutY(120);
	         
	         newIco3 = (VBox) CreateIcon("Database-icon.png");
	         newIco3.setLayoutX(10);
	         newIco3.setLayoutY(230);
	         
	         newIco4 = (VBox) CreateIcon("man-icon.png");
	         newIco4.setLayoutX(10);
	         newIco4.setLayoutY(340);
	         
	         newIco5 = (VBox) CreateIcon("Download-icon.png");
	         newIco5.setLayoutX(10);
	         newIco5.setLayoutY(450);
	         toolBar.getChildren().addAll(newIco, newIco2, newIco3, newIco4, newIco5);
	         
			
		
		
		
		  newIco.setOnDragDetected(new EventHandler <MouseEvent>() {
	          public void handle(MouseEvent event) {
	              /* drag was detected, start drag-and-drop gesture*/
	              System.out.println("onDragDetected");
	              
	              /* allow any transfer mode */
	              Dragboard db = newIco.startDragAndDrop(TransferMode.ANY);
//	              
//	              /* put a string on dragboard */
	              ClipboardContent content = new ClipboardContent();
	              content.putString("lalala");
	              db.setContent(content);
	              Type = "First";
	              event.consume();
	          }
	      });
	      
	      
	      
	      newIco2.setOnDragDetected(new EventHandler <MouseEvent>() {
	          public void handle(MouseEvent event) {
	              /* drag was detected, start drag-and-drop gesture*/
	              System.out.println("onDragDetected");
	              
	              /* allow any transfer mode */
	              Dragboard db = newIco.startDragAndDrop(TransferMode.ANY);
//	              
//	              /* put a string on dragboard */
	              ClipboardContent content = new ClipboardContent();
	              content.putString("lalala");
	              db.setContent(content);
	              Type = "Secound";
	              event.consume();
	          }
	      });
	      
	      
	      
	      newIco3.setOnDragDetected(new EventHandler <MouseEvent>() {
	          public void handle(MouseEvent event) {
	              /* drag was detected, start drag-and-drop gesture*/
	              System.out.println("3onDragDetected");
	              
	              /* allow any transfer mode */
	              Dragboard db = newIco.startDragAndDrop(TransferMode.ANY);
//	              
//	              /* put a string on dragboard */
	              ClipboardContent content = new ClipboardContent();
	              content.putString("lalala");
	              db.setContent(content);
	              Type = "Third";
	              event.consume();
	          }
	      });
	      
	      newIco4.setOnDragDetected(new EventHandler <MouseEvent>() {
	          public void handle(MouseEvent event) {
	              /* drag was detected, start drag-and-drop gesture*/
	              System.out.println("3onDragDetected");
	              
	              /* allow any transfer mode */
	              Dragboard db = newIco.startDragAndDrop(TransferMode.ANY);
//	              
//	              /* put a string on dragboard */
	              ClipboardContent content = new ClipboardContent();
	              content.putString("lalala");
	              db.setContent(content);
	              Type = "Fourth";
	              event.consume();
	          }
	      });
	      newIco5.setOnDragDetected(new EventHandler <MouseEvent>() {
	          public void handle(MouseEvent event) {
	              /* drag was detected, start drag-and-drop gesture*/
	              System.out.println("3onDragDetected");
	              
	              /* allow any transfer mode */
	              Dragboard db = newIco.startDragAndDrop(TransferMode.ANY);
//	              
//	              /* put a string on dragboard */
	              ClipboardContent content = new ClipboardContent();
	              content.putString("lalala");
	              db.setContent(content);
	              Type = "Fifth";
	              event.consume();
	          }
	      });
	     
	      
	      moveIcons(canvas, flow);
	      
	      FXValueSkinFactory fXSkinFactory = new FXValueSkinFactory(canvas.getContentPane());
//        fXSkinFactory.addSkinClassForValueType(FunctionInput.class, InputFunctionFlowNodeSkin.class);
        fXSkinFactory.addSkinClassForValueType(Plotter2D.class, Plotter2DFlowNodeSkin.class);
//        fXSkinFactory.addSkinClassForValueType(FunctionInput.class, DataBaseFlowNodeSkin.class);
	      
//        fXSkinFactory.addSkinClassForValueType(FunctionInput.class, ServerFlowNodeSkin.class);
        fXSkinFactory.addSkinClassForValueType(MySql.class, DataBaseFlowNodeSkin.class);
        fXSkinFactory.addSkinClassForValueType(ServerInput.class, ServerFlowNodeSkin.class);
        fXSkinFactory.addSkinClassForValueType(Rest.class, RestFlowNodeSkin.class);
        fXSkinFactory.addSkinClassForValueType(Soap.class, SoapFlowNodeSkin.class);
        fXSkinFactory.addSkinClassForValueType(MongoInput.class, MongoFlowNodeSkin.class);
                
        canvas.setOnDragDropped(new EventHandler <DragEvent>() {
      	  
            public void handle(DragEvent event) {
                /* data dropped */
           	 
            	if (Type.equals("Fourth")) {
            
                		
                System.out.println("asdadsasdada");
                
//                VNode n1 = createMongoNode(flow, "Rest",
//                        new MongoInput("x*x+a*sin(x*3)", 80, 1));
//                n1.setId(Math.random()+"");
                //stilul cu ploter
              VNode n1 = createPlotterNode(flow, "Mongo");
              n1.setId(Math.random()+"");
    
              int numNodesPerRow = 2;

              // defines the gap between the nodes
              double gap = 30;

//              // defines the node dimensions
              n1.setX(gap + (2 % numNodesPerRow) * (n1.getWidth() + gap));
              n1.setY(gap + (2 / numNodesPerRow) * (n1.getHeight() + gap));
              
              
        
              flow.removeSkinFactories(fXSkinFactory);
              flow.addSkinFactories(fXSkinFactory);
//              WorkflowIO.saveToXML("D:\test", flow);
//              WorkflowIO.loadFromXML("D:\test");
            	}
            	
            	if (Type.equals("Secound")) {
            		 System.out.println("adada");
                   
                   VNode n1 = createRestNode(flow, "Rest");
                   n1.setId(Math.random()+"");
  
                   
//                 n1.addInput("data");	
//                 n1.addOutput("data");
                 // make it visible
//                 flow.setVisible(true);
                 int numNodesPerRow = 2;
  
                 // defines the gap between the nodes
                 double gap = 30; 
   //
//                 // defines the node dimensions
                 n1.setX(gap + (2 % numNodesPerRow) * (n1.getWidth() + gap));
                 n1.setY(gap + (2 / numNodesPerRow) * (n1.getHeight() + gap));
                 
                 
                 
                 // register visualizations for Integer, String and Image
                
        //        fXSkinFactory.addSkinClassForValueType(Plotter2D.class, Plotter2DFlowNodeSkin.class);
                  
                 
                 
                 // generate the ui for the flow
                 flow.removeSkinFactories(fXSkinFactory);
                 flow.addSkinFactories(fXSkinFactory);
             
            	}
            	if (Type.equals("Third")) {
//            		VNode n1 = createDbNode(flow, "MySQL",
//                            new FunctionInput("x*x+a*sin(x*3)", 80, 1));
            		 VNode n1 = createMySqlNode(flow,"MySQL");
                    n1.setId(Math.random()+"");
                    
                    int numNodesPerRow = 2;
                    
                    // defines the gap between the nodes
                    double gap = 30; 
      //
//                    // defines the node dimensions
                    n1.setX(gap + (2 % numNodesPerRow) * (n1.getWidth() + gap));
                    n1.setY(gap + (2 / numNodesPerRow) * (n1.getHeight() + gap));
                    

                    flow.removeSkinFactories(fXSkinFactory);
                    flow.addSkinFactories(fXSkinFactory);
            	
            	}
            	
            	if (Type.equals("First")) {
            		VNode n1 = createServerNode(flow, "Server",
                            new ServerInput("x*x+a*sin(x*3)", 80, 1));
                    n1.setId(Math.random()+"");
                    
                    int numNodesPerRow = 2;
                    
                    // defines the gap between the nodes
                    double gap = 30; 
      //
//                    // defines the node dimensions
                    n1.setX(gap + (2 % numNodesPerRow) * (n1.getWidth() + gap));
                    n1.setY(gap + (2 / numNodesPerRow) * (n1.getHeight() + gap));
                    
                    flow.removeSkinFactories(fXSkinFactory);
                    flow.addSkinFactories(fXSkinFactory);
            	
            	}
            	
            	if (Type.equals("Fifth")) {
            		VNode n1 = createSoapNode(flow, "Soap");
                    n1.setId(Math.random()+"");
                    
                    int numNodesPerRow = 2;
                    
                    // defines the gap between the nodes
                    double gap = 30; 
      //
//                    // defines the node dimensions
                    n1.setX(gap + (2 % numNodesPerRow) * (n1.getWidth() + gap));
                    n1.setY(gap + (2 / numNodesPerRow) * (n1.getHeight() + gap));
                    
                    flow.removeSkinFactories(fXSkinFactory);
                    flow.addSkinFactories(fXSkinFactory);
            	
            	}
            	 
              event.consume();
            }
               
        
        });
	      
	 
	}
	static Node CreateIcon(String iconPath) {
			VBox vbox2 = new VBox();
			vbox2.setPadding(new Insets(2)); // Set all sides to 10
			vbox2.setSpacing(2);
			ImageView imageComp = new ImageView(new Image(
					DragFinal.class.getResourceAsStream(iconPath)));
			imageComp
			.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");


			Reflection reflection = new Reflection();
			reflection.setFraction(0.7);
			imageComp.setEffect(reflection);

			
			vbox2.getChildren().addAll(imageComp);
			return vbox2;
		}
	 
	 public void moveIcons( VCanvas canvas, VFlow flow) {
      	
         
     	 canvas.setOnDragOver(new EventHandler <DragEvent>() {
              public void handle(DragEvent event) {
                  /* data is dragged over the target */
                  System.out.println("onDragOver");
                  
                  /* accept it only if it is  not dragged from the same node 
                   * and if it has a string data */
//                  if (event.getGestureSource() != canvas &&
//                          event.getDragboard().hasString()) {
                      /* allow for both copying and moving, whatever user chooses */
                      event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                     
//                  }
                  
                  event.consume();
              }
          });
          
          
          canvas.setOnDragEntered(new EventHandler <DragEvent>() {
              public void handle(DragEvent event) {
                  /* the drag-and-drop gesture entered the target */
                  System.out.println("onDragEntered");
                  /* show to the user that it is an actual gesture target */
//                  if (event.getGestureSource() != target &&
//                          event.getDragboard().hasString()) {
////                      canvas.setFill(Color.GREEN);
//                  }
                  
                  event.consume();
              }
          });

          canvas.setOnDragExited(new EventHandler <DragEvent>() {
              public void handle(DragEvent event) {
                  /* mouse moved away, remove the graphical cues */
//                  canvas.setFill(Color.BLACK);
                  
                  event.consume();
              }
          });
	 }
	 
	  private static VNode createInputNode(VFlow flow, String title, ServerInput input) {
	        VNode functionNode = flow.newNode();
	        functionNode.setWidth(300);
	        functionNode.setHeight(380);
	        functionNode.setTitle(title);
	       functionNode.getValueObject().setValue(input);

	        Connector output = functionNode.addOutput("data");

	        return functionNode;
	    }
	    
	    private static VNode createDbNode (VFlow flow, String title, MySql input) {
	    	 VNode functionNode = flow.newNode();
	         functionNode.setWidth(300);
	         functionNode.setHeight(380);
	         functionNode.setTitle(title);
	        functionNode.getValueObject().setValue(input);

	        Connector inputMySql = functionNode.addInput("MySql");

	         return functionNode;
	    }
	    
	    private static VNode createServerNode (VFlow flow, String title, ServerInput input) {
	   	 VNode functionNode = flow.newNode();
	        functionNode.setWidth(300);
	        functionNode.setHeight(380);
	        functionNode.setTitle(title);
	       functionNode.getValueObject().setValue(input);

//	        Connector outputMySql = functionNode.addOutput("MySql");
	        Connector outputRest = functionNode.addOutput("Rest");
	        Connector outputSoap = functionNode.addOutput("Soap");
	        Connector outputMongo = functionNode.addOutput("data");
	        Connector outputMySql = functionNode.addOutput("MySql");
	        
	        
	        return functionNode;
	   }
	    
//	    private static VNode createRestNode (VFlow flow, String title, RestInput input) {
//		   	 VNode functionNode = flow.newNode();
//		        functionNode.setWidth(300);
//		        functionNode.setHeight(380);
//		        functionNode.setTitle(title);
//		       functionNode.getValueObject().setValue(input);
//
//		        Connector output = functionNode.addInput("data");
//
//		        return functionNode;
//		   }
	    
//	    private static VNode createSoapNode (VFlow flow, String title, SoapInput input) {
//		   	 VNode functionNode = flow.newNode();
//		        functionNode.setWidth(300);
//		        functionNode.setHeight(380);
//		        functionNode.setTitle(title);
//		       functionNode.getValueObject().setValue(input);
//
//		        Connector output = functionNode.addInput("data");
//
//		        return functionNode;
//		   }
	    
	    private static VNode createMongoNode (VFlow flow, String title, MongoInput input) {
		   	 VNode functionNode = flow.newNode();
		        functionNode.setWidth(300);
		        functionNode.setHeight(380);
		        functionNode.setTitle(title);
		       functionNode.getValueObject().setValue(input);

		        Connector output = functionNode.addInput("data");

		        return functionNode;
		   }
	   
	    
	    //plotterul cu imput
	    
	    private static VNode createPlotterNode(VFlow flow, String title) {
	        VNode plotterNode = flow.newNode();
	        plotterNode.setWidth(600);
	        plotterNode.setHeight(400);
	        plotterNode.setTitle(title);
	        plotterNode.getValueObject().setValue(new Plotter2D());

	    
	        Connector input = plotterNode.addInput("data");
	        


	        return plotterNode;
	    }
	    
	    private static VNode createMySqlNode(VFlow flow, String title) {
	        VNode MySqlNode = flow.newNode();
	        MySqlNode.setWidth(600);
	        MySqlNode.setHeight(400);
	        MySqlNode.setTitle(title);
	        MySqlNode.getValueObject().setValue(new MySql());

	    
	        Connector inputMySql = MySqlNode.addInput("MySql");
	        


	        return MySqlNode;
	    }
	    
	    private static VNode createRestNode(VFlow flow, String title) {
	        VNode MySqlNode = flow.newNode();
	        MySqlNode.setWidth(600);
	        MySqlNode.setHeight(400);
	        MySqlNode.setTitle(title);
	        MySqlNode.getValueObject().setValue(new Rest());

	    
	        Connector inputMySql = MySqlNode.addInput("Rest");
	        


	        return MySqlNode;
	    }
	    
	    private static VNode createSoapNode(VFlow flow, String title) {
	        VNode MySqlNode = flow.newNode();
	        MySqlNode.setWidth(600);
	        MySqlNode.setHeight(400);
	        MySqlNode.setTitle(title);
	        MySqlNode.getValueObject().setValue(new Soap());

	    
	        Connector inputMySql = MySqlNode.addInput("Soap");
	        


	        return MySqlNode;
	    }
		
}
