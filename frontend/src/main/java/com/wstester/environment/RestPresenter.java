package com.wstester.environment;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;

import com.wstester.main.WsTesterMainController;
import com.wstester.model.RestService;
import com.wstester.model.Server;
import com.wstester.model.Service;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.PopupBuilder;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class RestPresenter  implements Initializable  {
	@FXML
	private Node restPanel;
	@FXML
	private TextField restField;
	@FXML
	private TextField restName;
	@FXML
	private Button save;
	@FXML
	private Button edit;
	@FXML
	private Button pathButton;
	@FXML
	private Button methodButton;
	@FXML
	private Button textButton;
	@FXML
	private Button textOptButton;
	@FXML
	private Button assetOptButton;
	@FXML
	private Button assetButton;
	@FXML
	private Pane progressPane;
	@FXML
	private Pane pathLabel;
	@FXML
	private Pane methodName;
	@FXML
	private Pane textInputLabel;
	@FXML
	private Pane textOutputLabel;
	@FXML
	private Pane assetInputLabel;
	@FXML
	private Pane assetOutputLabel;
	@FXML
	private Label pathName;
	@FXML Button cancel;
	@FXML Label labelport;
	@FXML Label labelname;
	@FXML HBox hbox1;
	@FXML GridPane gridpane;
	@FXML
	private Button inputType;
	@FXML
	private Pane mockStepPane;
	@FXML
	private Pane inputTypePane;
	@FXML
	private Button inputButton;
	@FXML
	private AnchorPane mockWindow;
	@FXML
	private Pane paneFirst;
	@FXML 
	private Pane secoundStepPane;
	@FXML
	private Pane thirdStepPane;
	@FXML
	private Pane fourthStepPane;
	@FXML
	private Pane inputPane;
	@FXML
	private Pane outputPane;
	@FXML
	private Pane assetInputWindow;
	@FXML
	private TextField demoText;
	@FXML
	private Pane rect;
	
	
	private String uid = null;
	private EnvironmentService envService;
	private MainPresenter mainPresenter;
	private boolean imputShown=false;
	private boolean firstStep = false;
	private boolean secoundStep= false;
	private boolean thirdStep = false;
	private Stage stageImpAsset;
	Parent root;
	final Delta dragDelta2 = new Delta();
	final Tooltip tooltip = new Tooltip();
	
	
public void setRest( final String serverUID, final String serviceUID) 
	{
		imputShown = false;
		hbox1.getChildren().remove(save);
		hbox1.getChildren().remove(cancel);
		hbox1.getChildren().remove(edit);
		gridpane.getChildren().remove(restField);
		gridpane.getChildren().remove(labelport);
		gridpane.getChildren().remove(restName);
		gridpane.getChildren().remove(labelname);
		mockStepPane.getChildren().remove(inputTypePane);
		
		
		hbox1.getChildren().add(edit);
		gridpane.getChildren().add(labelport);
		gridpane.getChildren().add(labelname);
		
//		inputType.getItems().clear();
//		inputType.getItems().addAll(InputType.values());
		Server server = envService.getServerByUID( serverUID);
		Service service = envService.getServiceByUID( server.getID(), serviceUID );
		RestService srv = (RestService) service;
		uid = serviceUID;
		restField.setText(srv.getPort());
		labelport.setText(srv.getPort());
		restName.setText(srv.getName());
		labelname.setText(srv.getName());
		
	}

	public void saveRest(ActionEvent e) {
		
		RestService rst = new RestService();
		rst.setPort(restField.getText());
		rst.setName(restName.getText());
		
		envService.setRestServiceByUID(rst,uid);
    	hbox1.getChildren().add(edit);
    	hbox1.getChildren().remove(save);
    	hbox1.getChildren().remove(cancel);
    	gridpane.getChildren().remove(restField);
    	gridpane.getChildren().add(labelport);
    	gridpane.getChildren().remove(restName);
    	gridpane.getChildren().add(labelname);
    	labelport.setText(restField.getText());
    	labelname.setText(restName.getText());
    	envService.saveEnv();
	}
	
	public void editRest(ActionEvent e) {
		
		gridpane.getChildren().add(restField);
		gridpane.getChildren().remove(labelport);
		gridpane.getChildren().add(restName);
		gridpane.getChildren().remove(labelname);
		hbox1.getChildren().remove(edit);
    	
    	hbox1.getChildren().add(save);
    	hbox1.getChildren().add(cancel);
	}
	
	public void cancelEdit(ActionEvent e)
	{
		restField.setText(labelport.getText());
		restName.setText(labelname.getText());
		hbox1.getChildren().remove(cancel);
    	hbox1.getChildren().remove(save);
    	hbox1.getChildren().add(edit);
    	gridpane.getChildren().remove(restField);
    	gridpane.getChildren().add(labelport);
    	gridpane.getChildren().remove(restName);
    	gridpane.getChildren().add(labelname);
	}

	public void setEnvironmentService(EnvironmentService envService) {
		this.envService = envService;
	}

	public void setMainPresenter(MainPresenter mainPresenter) {
		this.mainPresenter = mainPresenter;
	}

	public Node getView() {
		return restPanel;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.createInputTypeWindow();
		this.selectPath();
		this.selectMethod();
		this.selectTextImp();
		this.selectAssetImp();
		this.selectTextOpt();
		this.selectAssetOpt();
//		tooltip.setText(
//			    "\nYour password must be\n" +
//			    "at least 8 characters in length\n"  
//			);
//			assetButton.setTooltip(tooltip);
		
		
		
	}

	private void createInputTypeWindow(){
		inputType.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				if(event.getClickCount() == 1 && imputShown == false) {
					mockStepPane.getChildren().add(inputTypePane);
					imputShown = true;
				}
				
			}
		});
		}
	
	
	private void selectPath(){
		pathButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				if(event.getClickCount() == 1 && firstStep == false) {
					
					
					paneFirst.getChildren().add(secoundStepPane);
					secoundStepPane.setLayoutY(27);
					mockStepPane.getChildren().remove(inputTypePane);
					mockStepPane.getChildren().add(inputPane);
					progressPane.getChildren().add(pathLabel);
					firstStep = true;
					imputShown = false;
					
				}
				
			}
		});
		}
	
	private void selectMethod(){
		methodButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				if(event.getClickCount() == 1 && firstStep == false) {
					
					
					paneFirst.getChildren().add(secoundStepPane);
					secoundStepPane.setLayoutY(27);
					mockStepPane.getChildren().remove(inputTypePane);
					mockStepPane.getChildren().add(inputPane);
					progressPane.getChildren().add(methodName);
					firstStep = true;
					imputShown = false;
					
				}
				
			}
		});
	}
		private void selectTextImp(){
			textButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					
					if(event.getClickCount() == 1 && secoundStep == false) {
						
						
				      
						paneFirst.getChildren().add(thirdStepPane);
						thirdStepPane.setLayoutY(54);
						mockStepPane.getChildren().remove(inputPane);
						mockStepPane.getChildren().add(outputPane);
						progressPane.getChildren().add(textInputLabel);
						textInputLabel.setLayoutY(40);
						secoundStep = true;
						imputShown = false;
						
					}
					
				}
			});
		}
		
		private void selectAssetImp(){
			assetButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					
					if(event.getClickCount() == 1 && secoundStep == false) {
						
						PopOver pop = new PopOver(rect);
						pop.setArrowLocation(PopOver.ArrowLocation.LEFT_BOTTOM);
				        pop.setHideOnEscape(true);
				        pop.setId("Asset Input");
				        pop.setAutoFix(true);
				        
				        pop.show(WsTesterMainController.stageEnvironment);
//				        if (WsTesterMainController.stageEnvironment == null && pop.isShowing()) {
//				        	pop.hide();
//				        }
				        pop.getOwnerWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
							public void handle(WindowEvent we) {
							System.out.println("sdadadadad");
							}
				        });
//						StackPane pane = new StackPane();
//				        //pane.getStyleClass().add("pane");
//				        Rectangle rectangle = new Rectangle(50, 50);
//				        
//				       // rectangle.getStyleClass().add("rect");
//				        Label text = new Label("popup test");
//				       // text.getStyleClass().add("text");
//				        pane.getChildren().addAll( rectangle, text);
				        
				        
						
//						PopOver pop = new PopOver(rect);
//						pop.setArrowLocation(PopOver.ArrowLocation.LEFT_TOP);
//				        pop.setHideOnEscape(true);
//				        pop.setId("Asset Input");
//				        pop.setAutoFix(true);
//				        pop.show(assetButton);
				        
				        
				        
				        // how to display to pane when the popup is shown?
				        
//						showTooltip(WsTesterMainController.stageEnvironment, assetButton, "test tool tip radalilalalalalalalaa", demoText);
					
//						stageImpAsset = new Stage();
//						
//						try {
//							root = FXMLLoader.load(getClass().getResource("/fxml/environment/InputAsset.fxml"));
//							Scene inputAsset = new Scene (root);
//							stageImpAsset.setScene(inputAsset);
//							stageImpAsset.initStyle(StageStyle.UNDECORATED);
//							stageImpAsset.initOwner(WsTesterMainController.stageEnvironment);
//							stageImpAsset.setX(event.getScreenX());;
//							stageImpAsset.setY(event.getScreenY());
//							stageImpAsset.show();
//							
//							
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
						
						paneFirst.getChildren().add(thirdStepPane);
						thirdStepPane.setLayoutY(54);
//						mockStepPane.getChildren().remove(inputPane);
//						mockStepPane.getChildren().add(outputPane);
						progressPane.getChildren().add(assetInputLabel);
						assetInputLabel.setLayoutY(40);
						secoundStep = true;
						imputShown = false;
						
					}
					
				}
			});
		}
		
		public static void showTooltip(Stage owner, Control control, String tooltipText,
			    TextField tooltipGraphic)
			{
			    javafx.geometry.Point2D p = control.localToScene(0.0, 0.0);

			    final Tooltip customTooltip = new Tooltip();
			    customTooltip.setText(tooltipText);
			    customTooltip.setGraphic(tooltipGraphic);

			    control.setTooltip(customTooltip);
			    customTooltip.setAutoHide(true);
			    customTooltip.setHeight(200);
			    customTooltip.setWidth(300);
			    customTooltip.show(owner, p.getX()
			        + control.getScene().getX() + control.getScene().getWindow().getX(), p.getY()
			        + control.getScene().getY() + control.getScene().getWindow().getY());

			}
		
		private void selectTextOpt(){
			textOptButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					
					if(event.getClickCount() == 1 && thirdStep == false) {
						
						
						paneFirst.getChildren().add(fourthStepPane);
						fourthStepPane.setLayoutY(81);
						mockStepPane.getChildren().remove(outputPane);
//						mockStepPane.getChildren().add(outputPane);
						progressPane.getChildren().add(textOutputLabel);
						textOutputLabel.setLayoutY(80);
						thirdStep = true;
						imputShown = false;
						
					}
					
				}
			});
		}
		
		private void selectAssetOpt(){
			assetOptButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					
					if(event.getClickCount() == 1 && thirdStep == false) {
						
						
						paneFirst.getChildren().add(fourthStepPane);
						fourthStepPane.setLayoutY(54);
						mockStepPane.getChildren().remove(outputPane);
//						mockStepPane.getChildren().add(outputPane);
						progressPane.getChildren().add(assetOutputLabel);
						assetOutputLabel.setLayoutY(80);
						thirdStep = true;
						imputShown = false;
						
					}
					
				}
			});
		}
	
	
	

}
