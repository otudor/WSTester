package com.wstester.main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import com.wstester.RightClickMenu.DemoUtil;
import com.wstester.RightClickMenu.RadialGlobalMenu;
import com.wstester.environment.Delta;
import com.wstester.environment.EnvironmentsAppFactory;
import com.wstester.environment.MainPresenter;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.ICamelContextManager;
import com.wstester.testFactory.TestSuiteFactory;
import com.wstester.testFactory.TestSuiteManagerController;

public class WsTesterMainController implements Initializable, ControlledScreen {


	@FXML
	private AnchorPane pane;
	@FXML
	private AnchorPane topPane;
	@FXML
	private VBox newIco;
	@FXML
	private AnchorPane bar = new AnchorPane();
	@FXML
	private Button SaveProject = new Button();

	@FXML
	private VBox newIco2= new VBox();
	private VBox newIco3= new VBox(); //SOAP
	private VBox newIco4= new VBox();  //REST
	private VBox newIco5= new VBox(); 
	private VBox newIco6 = new VBox();  //v
	private VBox newIcoM1= new VBox();
	private VBox newIcoM2= new VBox(); //SOAP
	private VBox newIcoM3= new VBox(); //REST
	private VBox newIcoM4= new VBox();
	private VBox newIcoM5= new VBox();
	private VBox newIcoM6= new VBox(); //ENV
	private VBox newIcoM7= new VBox(); //v
	private Stage stage = new Stage();
	private Stage stageRightClickMenu = new Stage();
	private Stage stageSoap = new Stage();
	private Stage stageRest = new Stage();
	private Stage stageRnd = new Stage();
	private Stage stageAssets = new Stage();
	private Stage stageEnv = new Stage();
	private Stage stageVar = new Stage(); //v
	boolean isDisplayed =false;
	boolean isDisplayed1 =false;
	boolean isDisplayed2 =false;
	boolean isDisplayed3 =false;
	boolean isDisplayed4 =false;
	boolean isDisplayed5 =false;
	boolean isDisplayed6 =false; //v
	private int poz = 200;
	public Group container;
	public RadialGlobalMenu radialMenu;
	public boolean visible = false;
	public boolean visible2 = false;
	MenuBar menuBar ;
	Menu menu;
	private Menu menuRnd = new Menu("CreateRnd");
	Parent root;
	final Delta dragDelta2 = new Delta();
	List<VBox> lista = new ArrayList<VBox>();
	private int ind=0;
	Menu menu2 = new Menu("Environments definition");
	ScreensController myController;

	public void setScreenParent(ScreensController screenParent){
		myController = screenParent;
	}

	//main functionality 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.createIcons();
		this.createAssetsWindow();
		this.createEnvWindow();
		this.createSOAPWindow();
		//this.createTaskbar();
		this.createRightClickMenu();
		this.createRESTWindow();
		this.createTestFactoryWindow();
		this.createVarWindow(); //v
		this.saveProject();
		this.goToLoad();
		stage.initOwner(WsTesterMain.stage);
		stage.initOwner(MainLauncher.stage);

		//pane.setRightAnchor(bar, 10d);
		//topPane.setLeftAnchor(child, value);
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


	private void saveProject() {
		SaveProject.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
			}
		});
	}




	private void goToLoad() {
		newIcoM1.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (isDisplayed==true) {
					stageAssets.close();
					bar.getChildren().remove(newIcoM5);
					isDisplayed=false;
					poz=poz-100;
				}
				
				if (isDisplayed2==true) {
					isDisplayed2=false;
					stageEnv.close();
					bar.getChildren().remove(newIcoM6);
					poz=poz-100;
				}
				
				if (isDisplayed3==true) {
					stageSoap.close();
					bar.getChildren().remove(newIcoM2);
					isDisplayed3=false;
					poz=poz-100;
				}
				if (isDisplayed4==true) {
					stageRest.close();
					bar.getChildren().remove(newIcoM3);
					isDisplayed4=false;
					poz=poz-100;
				}
				if (isDisplayed5==true) {
					stageRnd.close();
					bar.getChildren().remove(newIcoM4);
					isDisplayed5=false;
					poz=poz-100;
				}
				if (isDisplayed6==true) {
					stageVar.close();
					bar.getChildren().remove(newIcoM7);
					isDisplayed6=false;
					poz=poz-100;
				}
				
	    		ICamelContextManager manager = null;
				try {
					manager = ServiceLocator.getInstance().lookup(ICamelContextManager.class);
				} catch (Exception e) {
					// TODO Make an exception window that informs the user we could not close the project
					// he should retry again the same operation
					e.printStackTrace();
				}
	    		manager.closeCamelContext();
				
				myController.setScreen(MainLauncher.screen1ID);
				
			}

		});
		
		
//		WsTesterMain.stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//			public void handle(WindowEvent we) {
//				
//				ServiceLocator serviceLocator = ServiceLocator.getInstance();
//	    		ICamelContextManager manager = serviceLocator.lookup(ICamelContextManager.class);
//	    		manager.closeCamelContext();
//			}
//		});        

	}
	//method to create+add the icons
	private void createIcons(){		
		newIco = (VBox) CreateIcon("/images/Applications-Folder.png","Assets");
		newIco.setLayoutX(10);
		newIco.setLayoutY(20);
		newIco2 = (VBox) CreateIcon("/images/Globe-Folder.png","   Environments Definition");
		newIco2.setLayoutX(10);
		newIco2.setLayoutY(120);
		newIco3 = (VBox) CreateIcon("/images/DropBox-Folder.png","Import SOAP Definitions");
		newIco3.setLayoutX(10);
		newIco3.setLayoutY(220);
		newIco4 = (VBox) CreateIcon("/images/Downloads-Folder.png","Import REST Definitions");
		newIco4.setLayoutX(10);
		newIco4.setLayoutY(320);
		newIco5 = (VBox) CreateIcon("/images/Smart-Folder.png","Test Factory");
		newIco5.setLayoutX(10);
		newIco5.setLayoutY(420);
		newIco6 = (VBox) CreateIcon("/images/Documents-Folder.png","Variables"); //v
		newIco6.setLayoutX(10); //v
		newIco6.setLayoutY(520); //v

		newIcoM1 = (VBox) CreateIcon("/images/VLC.png","Start");
		newIcoM1.setLayoutX(1);
		newIcoM1.setLayoutY(1);

		//newIcoM2 = (VBox) CreateIcon("/images/task_img_open.png","Soap");	
		//newIcoM2.setLayoutX(poz);
		//newIcoM2.setLayoutY(1);

		//newIcoM3 = (VBox) CreateIcon("/images/task_img_open.png","Rest");	
		//newIcoM3.setLayoutX(poz);
		//newIcoM3.setLayoutY(1);



		System.out.println(stage.getHeight());
		//VBox.setVgrow(newIco6, Priority.ALWAYS);

		topPane.getChildren().addAll(newIco,newIco2,newIco3,newIco4,newIco5, newIco6);
		bar.getChildren().addAll(newIcoM1);
	}










	//meniu vechi
	/*private void createTaskbar(){
		Label menuLabel = new Label("Startaa");
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

	}*/


	public void moveIcons(VBox icon){
		icon.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				dragDelta2.x = icon.getLayoutX() - event.getSceneX();
				dragDelta2.y = icon.getLayoutY() - event.getSceneY();
				icon.setCursor(Cursor.MOVE); 
				//AnchorPane.setTopAnchor(menuBar, 15.0);
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

		// Timeline bouncer=new Timeline();


		icon.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent mouseEvent) {
				//icon.setScaleX(1.2);
				//icon.setScaleY(1.2);
				icon.setCursor(Cursor.HAND);	

				/* bouncer.getKeyFrames().addAll(
		    		  makeKeyFrame(0,0,1.2,1),
		    		  makeKeyFrame(0,0,1,1.2),
		    		  makeKeyFrame(300,-20,1,1),
		    		  makeKeyFrame(0,0,1,1.2),
		    		  makeKeyFrame(6000,0,1.2,1.0)
		    		 );

				//	  new KeyFrame(new Duration(0),new KeyValue(icon.translateYProperty(),0.0)),
					//  new KeyFrame(new Duration(3000),new KeyValue(icon.translateYProperty(),-20.0)),
					//  new KeyFrame(new Duration(6000),new KeyValue(icon.translateYProperty(),0.0))



			  bouncer.setCycleCount(Animation.INDEFINITE);			 		  		  
			  bouncer.play();

		   }		

		   public KeyFrame makeKeyFrame(int d, double y, double sx, double sy){
			   return new KeyFrame(
					   new Duration(d),
					   new KeyValue(icon.translateYProperty(),y),
					   new KeyValue(icon.translateXProperty(), sx),
					   new KeyValue(icon.translateYProperty(), sy)
					   );*/
			}


		});
		icon.setOnMouseExited(new EventHandler<MouseEvent>() {
			   @Override public void handle(MouseEvent mouseEvent) {
				icon.setScaleX(1);
				icon.setScaleY(1);
			    icon.setCursor(Cursor.HAND);	

			   }	  
			  });


	}

//	public void expandIcons(VBox icon){
//		icon.setOnMouseEntered(new EventHandler<MouseEvent>() {
//			@Override public void handle(MouseEvent e) {
//				icon.setScaleX(1.2);
//				icon.setScaleY(1.2);
//			}
//		});
//
//		icon.setOnMouseExited(new EventHandler<MouseEvent>() {
//			@Override public void handle(MouseEvent e) {
//				icon.setScaleX(1);
//				icon.setScaleY(1);
//			}
//		});
//
//	};
	
	private void createRightClickMenu() {
		topPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.SECONDARY && visible2 == false){
				stageRightClickMenu = new Stage();
				visible2=true;	
				container = new Group();
				final Scene scene = new Scene(container, Color.TRANSPARENT);
//				stageRightClickMenu.setOnCloseRequest(new EventHandler<WindowEvent>() {
//				    @Override
//				    public void handle(final WindowEvent event) {
//					System.exit(0);
//				    }
//				});
				stageRightClickMenu.initOwner(pane.getScene().getWindow());
				stageRightClickMenu.initStyle(StageStyle.TRANSPARENT);
				stageRightClickMenu.setResizable(false);
				stageRightClickMenu.setScene(scene);
				stageRightClickMenu.centerOnScreen();
				final Dimension screenSize = Toolkit.getDefaultToolkit()
					.getScreenSize();
				stageRightClickMenu.setWidth(MainLauncher.stage.getWidth());
				stageRightClickMenu.setHeight(MainLauncher.stage.getHeight());
				stageRightClickMenu.setX(MainLauncher.stage.getX());;
				stageRightClickMenu.setY(MainLauncher.stage.getY());
//				stageRightClickMenu.setY(scene.getY());
				stageRightClickMenu.toFront();
				radialMenu = new RadialGlobalMenu();
//				System.out.println(getClass().getClassLoader().);
				radialMenu.addMenuItem("/images/asset2.png", null);
				radialMenu.computeItemsStartAngle();
				radialMenu.addMenuItem("/images/asset2.png", null);
				radialMenu.computeItemsStartAngle();
				radialMenu.addMenuItem("/images/asset2.png", null);
				radialMenu.computeItemsStartAngle();
				radialMenu.addMenuItem("/images/asset2.png", null);
				radialMenu.computeItemsStartAngle();
				radialMenu.addMenuItem("/images/asset2.png", null);
				radialMenu.computeItemsStartAngle();
				radialMenu.addMenuItem("/images/asset2.png", null);
				radialMenu.computeItemsStartAngle();
				
				radialMenu.translateXProperty().bind(scene.widthProperty().divide(2.0));
				radialMenu.translateYProperty()
					.bind(scene.heightProperty().divide(2.0));
				radialMenu.widthProperty().bind(scene.widthProperty());
				radialMenu.heightProperty().bind(scene.heightProperty());
				stageRightClickMenu.isIconified();
				
				radialMenu.transitionVisible(true);
				
				
//				radialMenu.setVisible(visible);
				
				radialMenu.setOnMouseClicked(new EventHandler<MouseEvent>() {
					
					@Override
					public void handle(MouseEvent e) {
						if (e.getButton() == MouseButton.SECONDARY) {
							
							radialMenu.transitionVisible(false);						
							stageRightClickMenu.isIconified();
//							stageRightClickMenu=null;
							visible2=false;
							
//							stageRightClickMenu=null;
							
						}
					}
				});
				
//				stageRightClickMenu.setOnCloseRequest(new EventHandler<WindowEvent>() {
//					public void handle(WindowEvent we) {
//						
//										        	 
//						System.out.println("Inchid stage'ul");
//						
//						stageRightClickMenu=null;
////						visible2=false;
//						
//					}
//				}); 
				
				container.getChildren().addAll(radialMenu); 
			
				DemoUtil demoUtil = new DemoUtil();
				demoUtil.setTranslateX(100);
				demoUtil.setTranslateY(300);
				
				stageRightClickMenu.show();
				}
				
			}
			private void setRadialMenuVisible( boolean b) {
				radialMenu.transitionVisible(visible);
				
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
						root = FXMLLoader.load(getClass().getResource("/fxml/SOAP/SOAPDefinition.fxml"));

						Scene second = new Scene(root);

						second.getStylesheets().add(WsTesterMain.class.getResource("/styles/application.css").toExternalForm());					
						root.getStyleClass().add("mainWind");

						newIcoM2 = (VBox) CreateIcon("/images/DropBox-Folder.png","Soap");	
						newIcoM2.setLayoutX(poz);
						newIcoM2.setLayoutY(1);
						lista.add(newIcoM2);
						bar.getChildren().add(newIcoM2);
						poz=poz+100;										
//						expandIcons(newIcoM2);



						newIcoM2.setOnMouseClicked(new EventHandler<MouseEvent>() {

							@Override
							public void handle(MouseEvent event2) {

								// TODO Auto-generated method stub	
								if(event2.getClickCount() == 1 ) {

									stageSoap.centerOnScreen();
									stageSoap.setIconified(false);
									stageSoap.toFront();
									stageSoap.show();
								}



							}
						});




						//Menu menu2 = new Menu("CreateSOAP");
						//menuBar.getMenus().add(menu2);
						
						stageSoap.initOwner(MainLauncher.stage);
						
						stageSoap.setScene(second);

						stageSoap.setTitle("SOAP Window");
						
						//stageSoap.initModality(Modality.WINDOW_MODAL);


						stageSoap.show();


						// modificare laur: inchidere stage din taskbar

						stageSoap.setOnCloseRequest(new EventHandler<WindowEvent>() {
							public void handle(WindowEvent we) {
								ind=lista.indexOf(newIcoM2);
								arrangeIcons(ind);
								lista.remove(lista.indexOf(newIcoM2));				        	 
								System.out.println("Inchid stage'ul");
								bar.getChildren().remove(newIcoM2);
								isDisplayed3 = false;
								stageSoap=null;
								poz=poz-100;
							}
						}); 
						stageSoap.iconifiedProperty().addListener(new ChangeListener<Boolean>() {

							@Override
							public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
								stageSoap.hide();
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
					stageSoap.centerOnScreen();
					stageSoap.setIconified(false);
					stageSoap.show();
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
						root = FXMLLoader.load(getClass().getResource("/fxml/REST/Rest.fxml"));
						Scene second = new Scene(root);
						second.getStylesheets().add(WsTesterMain.class.getResource("/styles/application.css").toExternalForm());
						//second.setFill(Color.TRANSPARENT);
						//second.setFill(Color.TRANSPARENT);
						//stageRest.initStyle(StageStyle.TRANSPARENT);
						root.getStyleClass().add("mainWind");

						newIcoM3 = (VBox) CreateIcon("/images/Downloads-Folder.png","Rest");	
						newIcoM3.setLayoutX(poz);
						newIcoM3.setLayoutY(1);
						lista.add(newIcoM3);
						//AfiseazaIcons("/images/task_img_open.png","RestWindow");
						bar.getChildren().add(newIcoM3);
						poz=poz+100;
//						expandIcons(newIcoM3);


						newIcoM3.setOnMouseClicked(new EventHandler<MouseEvent>() {

							@Override
							public void handle(MouseEvent event2) {

								// TODO Auto-generated method stub	
								if(event2.getClickCount() == 1 ) {

									stageRest.centerOnScreen();
									stageRest.setIconified(false);
									stageRest.toFront();
									stageRest.show();

								}



							}
						});

						stageRest.iconifiedProperty().addListener(new ChangeListener<Boolean>() {

							@Override
							public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
								stageRest.hide();
							}
						});

						//if(stageRest.setIconified(true))
						//{
						//stageRest.initStyle(StageStyle.TRANSPARENT);
						//stageRest.centerOnScreen();
						//}


						//Menu menu2 = new Menu("CreateREST");
						//menuBar.getMenus().add(menu2);
						stageRest.initOwner(pane.getScene().getWindow());
						//stage.initModality(Modality.WINDOW_MODAL);
						//stageRest.initStyle(StageStyle.UTILITY);
						stageRest.setScene(second);
						stageRest.setTitle("REST Window");
						//stageRest.initOwner(WsTesterMain.stage);
						stageRest.show();


						// modificare laur



						stageRest.setOnCloseRequest(new EventHandler<WindowEvent>() {
							public void handle(WindowEvent we) {
								ind=lista.indexOf(newIcoM3);
								arrangeIcons(ind);
								lista.remove(lista.indexOf(newIcoM3));

								System.out.println("Inchid stage'ul rest");
								bar.getChildren().remove(newIcoM3);;
								//stageRest.setScene(null);
								isDisplayed4 = false;
								stageRest=null;
								poz=poz-100;
							}
						});        
						stageRest.iconifiedProperty().addListener(new ChangeListener<Boolean>() {

							@Override
							public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
								stageRest.hide();
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


					stageRest.centerOnScreen();
					stageRest.setIconified(false);
					stageRest.toFront();
					//stageRest.isShowing();
					stageRest.show();



				}

			}
		});

		moveIcons(newIco4);
	}
	
	//v
	private void createVarWindow(){
		newIco6.setOnMouseClicked(new EventHandler<MouseEvent>() {			
			@Override
			public void handle(MouseEvent event) {


				// TODO Auto-generated method stub	
				if(event.getClickCount() == 2 && isDisplayed6 == false) {
					stageVar = new Stage();
					isDisplayed6 = true;
					try {
						root = FXMLLoader.load(getClass().getResource("/fxml/var/variables.fxml"));
						Scene second = new Scene(root);
						second.getStylesheets().add(WsTesterMain.class.getResource("/styles/application.css").toExternalForm());
						//second.setFill(Color.TRANSPARENT);
						//second.setFill(Color.TRANSPARENT);
						//stageRest.initStyle(StageStyle.TRANSPARENT);
						root.getStyleClass().add("mainWind");

						newIcoM7 = (VBox) CreateIcon("/images/Documents-Folder.png","Variables");	
						newIcoM7.setLayoutX(poz);
						newIcoM7.setLayoutY(1);
						lista.add(newIcoM7);
						//AfiseazaIcons("/images/task_img_open.png","RestWindow");
						bar.getChildren().add(newIcoM7);
						poz=poz+100;
//						expandIcons(newIcoM7);


						newIcoM7.setOnMouseClicked(new EventHandler<MouseEvent>() {

							@Override
							public void handle(MouseEvent event2) {

								// TODO Auto-generated method stub	
								if(event2.getClickCount() == 1 ) {

									stageVar.centerOnScreen();
									stageVar.setIconified(false);
									stageVar.toFront();
									stageVar.show();

								}



							}
						});

						stageVar.iconifiedProperty().addListener(new ChangeListener<Boolean>() {

							@Override
							public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
								stageVar.hide();
							}
						});


						stageVar.initOwner(pane.getScene().getWindow());
						//stage.initModality(Modality.WINDOW_MODAL);
						//stageRest.initStyle(StageStyle.UTILITY);
						stageVar.setScene(second);
						stageVar.setTitle("Variables Window");
						//stageRest.initOwner(WsTesterMain.stage);
						stageVar.show();


						// modificare laur



						stageVar.setOnCloseRequest(new EventHandler<WindowEvent>() {
							public void handle(WindowEvent we) {
								ind=lista.indexOf(newIcoM7);
								arrangeIcons(ind);
								lista.remove(lista.indexOf(newIcoM7));

								System.out.println("Inchid stage'ul var");
								bar.getChildren().remove(newIcoM7);;
								//stageRest.setScene(null);
								isDisplayed6 = false;
								stageVar=null;
								poz=poz-100;
							}
						});        
						stageVar.iconifiedProperty().addListener(new ChangeListener<Boolean>() {

							@Override
							public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
								stageVar.hide();
							}
						});

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (isDisplayed6 == true && event.getClickCount() == 2)
				{
					stageVar.centerOnScreen();
					stageVar.setIconified(false);
					stageVar.toFront();
					//stageRest.isShowing();
					stageVar.show();
				}
			}
		});

		moveIcons(newIco6);
	}


	private void createTestFactoryWindow(){
		newIco5.setOnMouseClicked(new EventHandler<MouseEvent>() {			


			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub

				if(event.getClickCount() == 2 && isDisplayed5 == false) {
					stageRnd = new Stage();
					isDisplayed5 = true;
					try {
						/*Parent root = FXMLLoader.load(getClass().getResource("/fxml/TestFactory/TestFactoryManager.fxml"));
						 Undecorator undecorator = new Undecorator(stageRnd, (Region) root);
						 undecorator.getStylesheets().add("skin/undecorator.css");
						 undecorator.setFadeInTransition();
						 Scene secound = new Scene(undecorator);
						 secound.setFill(Color.TRANSPARENT);
						 stageRnd.initStyle(StageStyle.TRANSPARENT);
						 stageRnd.setMinWidth(500);
						 stageRnd.setMinHeight(400);

						 stageRnd.setHeight(600);
						 stageRnd.setWidth(700);*/
						root = FXMLLoader.load(getClass().getResource("/fxml/TestFactory/TestSuiteManager.fxml"));
						TestSuiteFactory factory = new TestSuiteFactory();
						TestSuiteManagerController mainPresenter = factory.getManagerController();
						mainPresenter.loadTestSuites();
						mainPresenter.showEmptyTabController();
						root = mainPresenter.getView();
						Scene second = new Scene(root,1280, 720);
						root.getStylesheets().addAll("/styles/testFactory.css");
						stage.setTitle("Test Suites window");


						//root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
						//Scene second = new Scene(root);
						//second.getStylesheets().add(WsTesterMain.class.getResource("/styles/application.css").toExternalForm());					
						//root.getStyleClass().add("mainWind");

						newIcoM4 = (VBox) CreateIcon("/images/Smart-Folder.png","Test Factory");	
						newIcoM4.setLayoutX(poz);
						newIcoM4.setLayoutY(1);
						lista.add(newIcoM4);
						bar.getChildren().add(newIcoM4);
						poz=poz+100;
//						expandIcons(newIcoM4);


						newIcoM4.setOnMouseClicked(new EventHandler<MouseEvent>() {

							@Override
							public void handle(MouseEvent event2) {

								// TODO Auto-generated method stub	
								if(event2.getClickCount() == 1 ) {

									stageRnd.toFront();
									stageRnd.centerOnScreen();
									stageRnd.setIconified(false);
									stageRnd.show();
								}




							}
						});

						//menuBar.getMenus().add(menuRnd);
						stageRnd.initOwner(pane.getScene().getWindow());
						//stage.initModality(Modality.WINDOW_MODAL);
						stageRnd.setScene(second);
						//stageRnd.initOwner(WsTesterMain.stage);
						stageRnd.show();

						//modificare laur

						stageRnd.setOnCloseRequest(new EventHandler<WindowEvent>() {
							public void handle(WindowEvent we) {
								ind=lista.indexOf(newIcoM4);
								arrangeIcons(ind);
								lista.remove(lista.indexOf(newIcoM4));

								System.out.println("Inchid stage'ul");
								bar.getChildren().remove(newIcoM4);
								isDisplayed5 = false;
								stageRnd=null;
								poz=poz-100;
							}
						});        
						stageRnd.iconifiedProperty().addListener(new ChangeListener<Boolean>() {

							@Override
							public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
								stageRnd.hide();
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
					stageRnd.centerOnScreen();
					stageRnd.setIconified(false);
					stageRnd.show();
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
						root = FXMLLoader.load(getClass().getResource("/fxml/assets/Assets.fxml"));
						Scene second = new Scene(root);
						String cssPath="/styles/asset.css";     //    the css path for assets
						second.getStylesheets().addAll(cssPath); //	   the css add 			
						root.getStyleClass().add("mainWind");

						newIcoM5 = (VBox) CreateIcon("/images/Applications-Folder.png","Assets");	
						newIcoM5.setLayoutX(poz);
						newIcoM5.setLayoutY(1);
						lista.add(newIcoM5);
						bar.getChildren().add(newIcoM5);
						poz=poz+100;
//						expandIcons(newIcoM5);
						newIcoM5.setOnMouseClicked(new EventHandler<MouseEvent>() {

							@Override
							public void handle(MouseEvent event2) {

								// TODO Auto-generated method stub	
								if(event2.getClickCount() == 1 ) {

									stageAssets.toFront();
									stageAssets.centerOnScreen();
									stageAssets.setIconified(false);
									stageAssets.show();
								}



							}
						});
						stageAssets.iconifiedProperty().addListener(new ChangeListener<Boolean>() {

							@Override
							public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
								stageAssets.hide();
							}
						});
						//stage.initModality(Modality.WINDOW_MODAL);
						//menu = new Menu("Assets");
						//menuBar.getMenus().add(menu);
						stageAssets.initOwner(pane.getScene().getWindow());
						stageAssets.setScene(second);
						//stageAssets.initOwner(WsTesterMain.stage);
						stageAssets.show();

						// modificare laur
						stageAssets.setOnCloseRequest(new EventHandler<WindowEvent>() {
							public void handle(WindowEvent we) {
								ind=lista.indexOf(newIcoM5);
								arrangeIcons(ind);
								lista.remove(lista.indexOf(newIcoM5));

								System.out.println("Inchid stage'ul");
								bar.getChildren().remove(newIcoM5);
								isDisplayed = false;
								stageAssets=null;
								poz=poz-100;
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
					stageAssets.centerOnScreen();
					stageAssets.setIconified(false);
					stageAssets.show();
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
						root = FXMLLoader.load(getClass().getResource("/fxml/environment/EnvironmentManager.fxml"));
						EnvironmentsAppFactory factory = new EnvironmentsAppFactory();
						MainPresenter mainPresenter = factory.getMainPresenter();
						mainPresenter.loadEnvironments();
						//                        mainPresenter.setTestProject(testProject);
						root = mainPresenter.getView();
						Scene second = new Scene(root, 650, 550);
						stage.setTitle("Environments window");
						String cssPath = "/styles/Envwindows.css"; // the css path for enviroment window	
						second.getStylesheets().addAll(cssPath);   // the css add
						root.getStyleClass().add("mainWind");
						newIcoM6 = (VBox) CreateIcon("/images/Globe-Folder.png","Env");	
						newIcoM6.setLayoutX(poz);
						newIcoM6.setLayoutY(1);
						lista.add(newIcoM6);
						bar.getChildren().addAll(newIcoM6);
						poz=poz+100;
//						expandIcons(newIcoM6);

						newIcoM6.setOnMouseClicked(new EventHandler<MouseEvent>() {

							@Override
							public void handle(MouseEvent event2) {

								// TODO Auto-generated method stub	
								if(event2.getClickCount() == 1 ) {

									stageEnv.toFront();
									stageEnv.centerOnScreen();
									stageEnv.setIconified(false);
									stageEnv.show();
								}



							}
						});

						//menuBar.getMenus().add(menu2);
						stageEnv.initOwner(pane.getScene().getWindow());
						stageEnv.setScene(second);
						//stageEnv.initStyle(StageStyle.TRANSPARENT);
						//stageEnv.initOwner(WsTesterMain.stage);
						stageEnv.show();

						// stergere din taskbar upon closure
						stageEnv.setOnCloseRequest(new EventHandler<WindowEvent>() {
							public void handle(WindowEvent we) {
								ind=lista.indexOf(newIcoM6);
								arrangeIcons(ind);
								lista.remove(lista.indexOf(newIcoM6));

								System.out.println("Inchid stage'ul");
								bar.getChildren().remove(newIcoM6);;
								isDisplayed2 = false;
								stageEnv=null;
								poz=poz-100;
							}
						});        

						//stageEnv.setOnHidden(value);
						stageEnv.iconifiedProperty().addListener(new ChangeListener<Boolean>() {

							@Override
							public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
								stageEnv.hide();
							}
						});
						//pana aici
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (isDisplayed2 == true && event.getClickCount() == 2)
				{
					stageEnv.toFront();
					stageEnv.centerOnScreen();
					stageEnv.setIconified(false);
					stageEnv.show();
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


		Reflection reflection = new Reflection();
		reflection.setFraction(0.7);
		imageComp.setEffect(reflection);
		Timeline bouncer=new Timeline();
		//boolean mouseIn = false;
		boolean mouseIn = false;  
//		imageComp.setOnMouseEntered(new EventHandler<MouseEvent>() {
//			@Override public void handle(MouseEvent mouseEvent) {
//				// mouseIn = true;
//
				imageComp.setCursor(Cursor.HAND);	
//
//				bouncer.getKeyFrames().addAll(
//						makeKeyFrame(0,0,1.2,1),
//						makeKeyFrame(0,0,1,1.2),
//						makeKeyFrame(300,-20,1,1),
//						makeKeyFrame(0,0,1,1.2),
//						makeKeyFrame(600,0,1.2,1.0)
//
//
//						//makeKeyFrame(0,100,0.8,0.8)
//
//						);
//
//
//				//	  new KeyFrame(new Duration(0),new KeyValue(icon.translateYProperty(),0.0)),
//				//  new KeyFrame(new Duration(3000),new KeyValue(icon.translateYProperty(),-20.0)),
//				//  new KeyFrame(new Duration(6000),new KeyValue(icon.translateYProperty(),0.0))
//
//
//
//				bouncer.setCycleCount(1);			 		  		  
//				// bouncer.play();
//				bouncer.setOnFinished(new EventHandler<ActionEvent>(){
//
//					@Override
//					public void handle (ActionEvent arg0){
//
//						//new KeyValue(image.c)
//						// imageComp.setX(1);
//						// imageComp.setY(1);
//
//					}
//
//				});
//
//				bouncer.play();  
//
//			}		
//
//			private KeyFrame makeKeyFrame(int d, double y, double sx, double sy){
//				return new KeyFrame(
//						new Duration(d),
//						new KeyValue(imageComp.translateYProperty(),y),
//						new KeyValue(imageComp.scaleXProperty(), sx),
//						new KeyValue(imageComp.scaleYProperty(), sy)
//
//						);
//			}
//
//
//		});
//
//
		imageComp.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent mouseEvent) {
				bouncer.play();
				imageComp.setCursor(Cursor.HAND);	

			}	  
		});





		Text t2 = SetText(text);
		vbox2.getChildren().addAll(imageComp, t2);
		return vbox2;
	}
	static Text SetText(String text) {
		Text t2 = new Text();
		t2.setText(text);
		t2.setWrappingWidth(70);
		t2.setTextAlignment(TextAlignment.CENTER);
		//t2.setFill(Color.web("0x3b596d"));
		t2.setFont(Font.font ("Verdana", 10));
		//t2.setFont(Font.font(null, FontWeight.BOLD, 10));
		t2.setFill(Color.WHITESMOKE);
		//t2.setFont(Font.font(null, FontWeight.BOLD, 10));
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

	private void arrangeIcons(int i){


		for(i=ind;i<lista.size();i++)
		{
			lista.get(i).setLayoutX(lista.get(i).getLayoutX()-100);
		}
	}

	static Node AfiseazaIcons(String iconPath, String text){
		VBox vbox3 = new VBox();
		vbox3.setPadding(new Insets(2)); // Set all sides to 10
		vbox3.setSpacing(2);
		ImageView imageComp = new ImageView(new Image(
				WsTesterMainController.class.getResourceAsStream(iconPath)));
		imageComp
		.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");


		Reflection reflection = new Reflection();
		reflection.setFraction(0.7);
		imageComp.setEffect(reflection);
		Timeline bouncer=new Timeline();
		//boolean mouseIn = false;
		boolean mouseIn = false;  
//		imageComp.setOnMouseEntered(new EventHandler<MouseEvent>() {
//			@Override public void handle(MouseEvent mouseEvent) {
//				// mouseIn = true;
//
				imageComp.setCursor(Cursor.HAND);	
//
//				bouncer.getKeyFrames().addAll(
//						makeKeyFrame(0,0,1.2,1),
//						makeKeyFrame(0,0,1,1.2),
//						makeKeyFrame(300,-20,1,1),
//						makeKeyFrame(0,0,1,1.2),
//						makeKeyFrame(600,0,1.2,1.0),
//						makeKeyFrame(0,100,0.8,0.8)
//
//						);
//
//
//				//	  new KeyFrame(new Duration(0),new KeyValue(icon.translateYProperty(),0.0)),
//				//  new KeyFrame(new Duration(3000),new KeyValue(icon.translateYProperty(),-20.0)),
//				//  new KeyFrame(new Duration(6000),new KeyValue(icon.translateYProperty(),0.0))
//
//
//
//				bouncer.setCycleCount(1);			 		  		  
//				// bouncer.play();
//				bouncer.setOnFinished(new EventHandler<ActionEvent>(){
//
//					@Override
//					public void handle (ActionEvent arg0){
//
//						//new KeyValue(image.c)
//						// imageComp.setX(1);
//						// imageComp.setY(1);
//
//					}
//
//				});
//
//				bouncer.play();  
//
//			}		
//
//			private KeyFrame makeKeyFrame(int d, double y, double sx, double sy){
//				return new KeyFrame(
//						new Duration(d),
//						new KeyValue(imageComp.translateYProperty(),y),
//						new KeyValue(imageComp.scaleXProperty(), sx),
//						new KeyValue(imageComp.scaleYProperty(), sy)
//
//						);
//			}
//
//
//		});
//
//
		imageComp.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent mouseEvent) {
				bouncer.play();
				
				imageComp.setCursor(Cursor.HAND);	

			}	  
		});





		Text t2 = SetText(text);
		vbox3.getChildren().addAll(imageComp, t2);
		return vbox3;

	}

}
