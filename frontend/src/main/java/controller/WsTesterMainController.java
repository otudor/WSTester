package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.wstester.Delta;
import com.wstester.EnvironmentsAppFactory;
import com.wstester.MainPresenter;
import com.wstester.WsTesterMain;

public class WsTesterMainController implements Initializable {
	@FXML
	private AnchorPane pane;
	@FXML
	private AnchorPane topPane;
	@FXML
	private VBox newIco;
	@FXML
	private AnchorPane bar = new AnchorPane();
	@FXML
	private VBox newIco2= new VBox();
	private VBox newIco3= new VBox();
	private VBox newIco4= new VBox();
	private VBox newIco5= new VBox();
	private Stage stage = new Stage();
	private Stage stageSoap = new Stage();
	private Stage stageRest = new Stage();
	private Stage stageRnd = new Stage();
	private Stage stageAssets = new Stage();
	private Stage stageEnv = new Stage();
	boolean isDisplayed =false;
	boolean isDisplayed1 =false;
	boolean isDisplayed2 =false;
	boolean isDisplayed3 =false;
	boolean isDisplayed4 =false;
	boolean isDisplayed5 =false;
	MenuBar menuBar ;
	Menu menu;
	private Menu menuRnd = new Menu("CreateRnd");
	Parent root;
	final Delta dragDelta2 = new Delta();

	Menu menu2 = new Menu("Environments definition");

	//main functionality 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.createIcons();
		this.createAssetsWindow();
		this.createEnvWindow();
		this.createSOAPWindow();
		this.createTaskbar();
		this.createRESTWindow();
		this.createRndWindow();
		stage.initOwner(WsTesterMain.stage);
		//stage.initModality(Modality.
		//stage.initModality(Modality.WINDOW_MODAL);
		/*stageRnd.setOnHidden(new EventHandler<WindowEvent>() {
			@Override public void handle(WindowEvent onClosing) {
				System.out.println("hidden");
				stageRnd.hide();
			}
		});
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				menuBar.getMenus().remove(menu);
				
			}
		});*/

	}
	//method to create+add the icons
	private void createIcons(){		
		newIco = (VBox) CreateIcon("/images/document-open-remote.png","Assets");
		newIco.setLayoutX(10);
		newIco2 = (VBox) CreateIcon("/images/document-open-remote.png","Environments Definition");
		newIco2.setLayoutX(10);
		newIco2.setLayoutY(100);
		newIco3 = (VBox) CreateIcon("/images/document-open-remote.png","Import SOAP Definitions");
		newIco3.setLayoutX(10);
		newIco3.setLayoutY(200);
		newIco4 = (VBox) CreateIcon("/images/document-open-remote.png","Import REST Definitions");
		newIco4.setLayoutX(10);
		newIco4.setLayoutY(300);
		newIco5 = (VBox) CreateIcon("/images/document-open-remote.png","Generate random JSON");
		newIco5.setLayoutX(10);
		newIco5.setLayoutY(400);
		topPane.getChildren().addAll(newIco,newIco2,newIco3,newIco4,newIco5);
	}
	private void createTaskbar(){
		Label menuLabel = new Label("Start");
		menuLabel.setStyle(" -fx-padding: 0px;");
		menuLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent event) {
	            System.out.println("File menu clicked");
	        }
	    });
		Menu startMenu= new Menu();
		menuBar = new MenuBar();
		startMenu.setGraphic(menuLabel);
		menuBar.getMenus().add(startMenu);
		AnchorPane.setTopAnchor(menuBar, 15.0);
		bar.getStylesheets().add(WsTesterMainController.class.getResource("/styles/application.css").toExternalForm());
		bar.getChildren().add(menuBar);
		
	}
	
	public void moveIcons(VBox icon){
		  icon.setOnMousePressed(new EventHandler<MouseEvent>() {
		   @Override
		   public void handle(MouseEvent event) {
		    dragDelta2.x = icon.getLayoutX() - event.getSceneX();
		    dragDelta2.y = icon.getLayoutY() - event.getSceneY();
		    icon.setCursor(Cursor.MOVE); 
		    AnchorPane.setTopAnchor(menuBar, 15.0);
		    System.out.println("aici");
		   }
		  });
		  icon.setOnMouseReleased(new EventHandler<MouseEvent>() {
		   @Override public void handle(MouseEvent mouseEvent) {
		    icon.setCursor(Cursor.HAND);
		   }
		  });
		  icon.setOnMouseDragged(new EventHandler<MouseEvent>() {
		   @Override public void handle(MouseEvent mouseEvent) {
		    icon.setLayoutX(mouseEvent.getSceneX() + dragDelta2.x);
		    icon.setLayoutY(mouseEvent.getSceneY() + dragDelta2.y);
		   }
		  });
		  icon.setOnMouseEntered(new EventHandler<MouseEvent>() {
		   @Override public void handle(MouseEvent mouseEvent) {
		    icon.setCursor(Cursor.HAND);
		   }
		  });
		 }
	private void createSOAPWindow(){
		newIco3.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				
				// TODO Auto-generated method stub	
				if(event.getClickCount() == 2 && isDisplayed3 == false) {
					stageSoap = new Stage();
					isDisplayed3 = true;
					try {
						root = FXMLLoader.load(getClass().getResource("/fxml/SOAPDefinition.fxml"));

						Scene second = new Scene(root);
						second.getStylesheets().add(WsTesterMain.class.getResource("/styles/application.css").toExternalForm());					
						root.getStyleClass().add("mainWind");	
						Menu menu2 = new Menu("CreateSOAP");
						menuBar.getMenus().add(menu2);
						stageSoap.initOwner(pane.getScene().getWindow());
						stageSoap.setScene(second);
						
						stageSoap.setTitle("SOAP Window");
						stageSoap.initOwner(WsTesterMain.stage);
						//stageSoap.initModality(Modality.APPLICATION_MODAL);
						
						stageSoap.show();
						
						// modificare laur: inchidere stage din taskbar
						
						stageSoap.setOnCloseRequest(new EventHandler<WindowEvent>() {
					          public void handle(WindowEvent we) {
					        	  
					              System.out.println("Inchid stage'ul");
					              
					              menuBar.getMenus().remove(menu2);
					              isDisplayed3 = false;
					              stageSoap=null;
					          }
					      }); 
						
						
					        
					   //pana aici 
							
							
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				 if (isDisplayed3 == true && event.getClickCount() == 2)
				{
					stageSoap.toFront();
					
				}
				
			}
		});
		moveIcons(newIco3);
	
	}
		
		
		//pana aici
	
	private void createRESTWindow(){
		newIco4.setOnMouseClicked(new EventHandler<MouseEvent>() {			
			@Override
			public void handle(MouseEvent event) {
				
				
				// TODO Auto-generated method stub	
				if(event.getClickCount() == 2 && isDisplayed4 == false) {
					stageRest = new Stage();
					isDisplayed4 = true;
					try {
						root = FXMLLoader.load(getClass().getResource("/fxml/Rest.fxml"));
						Scene second = new Scene(root);
						second.getStylesheets().add(WsTesterMain.class.getResource("/styles/application.css").toExternalForm());					
						root.getStyleClass().add("mainWind");	
						Menu menu2 = new Menu("CreateREST");
						menuBar.getMenus().add(menu2);
						stageRest.initOwner(pane.getScene().getWindow());
						//stage.initModality(Modality.WINDOW_MODAL);
						stageRest.setScene(second);
						stageRest.setTitle("REST Window");
						//stageRest.initOwner(WsTesterMain.stage);
						stageRest.show();
						
						// modificare laur
						stageRest.setOnCloseRequest(new EventHandler<WindowEvent>() {
					          public void handle(WindowEvent we) {
					        	  
					              System.out.println("Inchid stage'ul rest");
					              menuBar.getMenus().remove(menu2);
					              //stageRest.setScene(null);
					              isDisplayed4 = false;
					              stageRest=null;
					          }
					      });        
					    
					
						
						//pana aici 
						
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
				if (isDisplayed4 == true && event.getClickCount() == 2)
				{
					stageRest.toFront();
					
				}
			}
		});
	
	
	
	
		moveIcons(newIco4);
}
	
	
	
	private void createRndWindow(){
		newIco5.setOnMouseClicked(new EventHandler<MouseEvent>() {			


			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				
				if(event.getClickCount() == 2 && isDisplayed5 == false) {
					stageRnd = new Stage();
					isDisplayed5 = true;
					try {
						root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
						Scene second = new Scene(root);
						second.getStylesheets().add(WsTesterMain.class.getResource("/styles/application.css").toExternalForm());					
						root.getStyleClass().add("mainWind");	

						menuBar.getMenus().add(menuRnd);
						stageRnd.initOwner(pane.getScene().getWindow());
						//stage.initModality(Modality.WINDOW_MODAL);
						stageRnd.setScene(second);
						//stageRnd.initOwner(WsTesterMain.stage);
						stageRnd.show();
						
						//modificare laur
						
						stageRnd.setOnCloseRequest(new EventHandler<WindowEvent>() {
					          public void handle(WindowEvent we) {
					        	  
					              System.out.println("Inchid stage'ul");
					              menuBar.getMenus().remove(menuRnd);
					              isDisplayed5 = false;
					              stageRnd=null;
					          }
					      });        
						
						
						//pana aici
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (isDisplayed5 == true && event.getClickCount() == 2)
				{
					stageRnd.toFront();
					
				}
			}
		});
		
		moveIcons(newIco5);
		}
			
		// pana aici
	
	private void createAssetsWindow(){
		newIco.setOnMouseClicked(new EventHandler<MouseEvent>() {			
			@Override
			public void handle(MouseEvent event) {
				
				if(event.getClickCount() == 2 && isDisplayed == false) {
					stageAssets = new Stage();
					isDisplayed = true;
					
					try {
						root = FXMLLoader.load(getClass().getResource("/fxml/Assets.fxml"));
						Scene second = new Scene(root);
						second.getStylesheets().add(WsTesterMain.class.getResource("/styles/application.css").toExternalForm());					
						root.getStyleClass().add("mainWind");									
						//stage.initModality(Modality.WINDOW_MODAL);
						menu = new Menu("Assets");
						menuBar.getMenus().add(menu);
						stageAssets.initOwner(pane.getScene().getWindow());
						stageAssets.setScene(second);
						//stageAssets.initOwner(WsTesterMain.stage);
						stageAssets.show();
						
						// modificare laur
						stageAssets.setOnCloseRequest(new EventHandler<WindowEvent>() {
					          public void handle(WindowEvent we) {
					        	  
					              System.out.println("Inchid stage'ul");
					              menuBar.getMenus().remove(menu);
					              isDisplayed = false;
					              stageAssets=null;
					             
					          }
					      });        
						
						
						//pana aici
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (isDisplayed == true && event.getClickCount() == 2)
				{
					stageAssets.toFront();
					
				}
			}
		});
		moveIcons(newIco);
		
		
	
		
		
	}
	private void createEnvWindow(){
		newIco2.setOnMouseClicked(new EventHandler<MouseEvent>() {			
			@Override
			public void handle(MouseEvent event) {
				
				// TODO Auto-generated method stub	
				if(event.getClickCount() == 2 && isDisplayed2 == false) {
					stageEnv = new Stage();
					isDisplayed2 = true;
					try {
						root = FXMLLoader.load(getClass().getResource("/fxml/EnvironmentManager.fxml"));
						EnvironmentsAppFactory factory = new EnvironmentsAppFactory();
	                    MainPresenter mainPresenter = factory.getMainPresenter();
                        mainPresenter.loadEnvironments();
                        root = mainPresenter.getView();
						Scene second = new Scene(root, 600, 480);
						stage.setTitle("Environments window");
						second.getStylesheets().add(WsTesterMain.class.getResource("/styles/application.css").toExternalForm());					
						root.getStyleClass().add("mainWind");	

						menuBar.getMenus().add(menu2);
						stageEnv.initOwner(pane.getScene().getWindow());
						stageEnv.setScene(second);
						//stageEnv.initOwner(WsTesterMain.stage);
						stageEnv.show();
						
						// stergere din taskbar upon closure
						stageEnv.setOnCloseRequest(new EventHandler<WindowEvent>() {
					          public void handle(WindowEvent we) {
					        	  
					              System.out.println("Inchid stage'ul");
					              menuBar.getMenus().remove(menu2);
					              isDisplayed2 = false;
					              stageEnv=null;
					          }
					      });        
						
						//stageEnv.setOnHidden(value);
						
						//pana aici
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (isDisplayed2 == true && event.getClickCount() == 2)
				{
					stageEnv.toFront();
					
				}
			}
		});
		moveIcons(newIco2);
	}
	// create elements utils functions
	static Node CreateIcon(String iconPath, String text) {
		VBox vbox2 = new VBox();
		vbox2.setPadding(new Insets(2)); // Set all sides to 10
		vbox2.setSpacing(2);
		ImageView imageComp = new ImageView(new Image(
				WsTesterMainController.class.getResourceAsStream(iconPath)));
		imageComp
		.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
		Text t2 = SetText(text);
		vbox2.getChildren().addAll(imageComp, t2);
		return vbox2;
	}
	static Text SetText(String text) {
		Text t2 = new Text();
		t2.setText(text);
		t2.setWrappingWidth(70);
		t2.setTextAlignment(TextAlignment.CENTER);
		t2.setFill(Color.WHITESMOKE);
		t2.setFont(Font.font(null, FontWeight.BOLD, 10));
		return t2;
	}
	/*
	private HBox downBox() {
		HBox box = new HBox();
		MenuBar menuBar = new MenuBar();
		Menu menu = new Menu("Start");
		menuBar.getMenus().add(menu);
		box.getChildren().add(menuBar);
		return box;

	}
	 */
	//to delete
	//@FXML
	private void handleButtonAction(ActionEvent event) {
		System.out.println("Minimize!");
		Menu menu = new Menu("Start");
		Button btn = new Button("Test");
		HBox container = new HBox();
		container.setSpacing(5);
		container.getChildren().add(btn);
		bar.getChildren().add(btn);
		//menuBar.getMenus().add(menu);
		//menuBar.applyCss();
		//AnchorPane.setTopAnchor(menuBar, 15.0);
		//bar.getChildren().add(menuBar);
	}

	//minimize the assets window
	/*stage.iconifiedProperty().addListener(new ChangeListener<Boolean>() {
		  @Override public void changed(ObservableValue<? extends Boolean> prop, Boolean oldValue, Boolean newValue) {
		    System.out.println("Iconified? " + newValue);
		    if(isDisplayed)
		    	stage.hide();
			System.out.println("Minimize!");
			//bar.getChildren().add(menuBar);
		  }
		});
	 */
	//maximize the assets window
	//menuBar
}
