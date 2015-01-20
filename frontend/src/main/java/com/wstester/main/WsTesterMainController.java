package com.wstester.main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.stage.FileChooser;
import javafx.scene.input.MouseButton;
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
import jfxtras.scene.menu.CornerMenu;
import javafx.scene.control.MenuItem;

import com.wstester.RightClickMenu.DemoUtil;
import com.wstester.RightClickMenu.RadialGlobalMenu;
import com.wstester.elements.Dialog;
import com.wstester.environment.Delta;
import com.wstester.environment.EnvironmentsAppFactory;
import com.wstester.environment.MainPresenter;
import com.wstester.model.TestProject;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.ICamelContextManager;
import com.wstester.services.definition.ITestProjectActions;
import com.wstester.util.MainConstants;
import com.wstester.util.TestProjectService;

public class WsTesterMainController implements Initializable, ControlledScreen {


	@FXML
	private AnchorPane pane;
	@FXML
	private AnchorPane topPane;
	@FXML
	private VBox assetsIcon;
	@FXML
	private AnchorPane bar = new AnchorPane();
	@FXML
	private Button SaveProject = new Button();
	
	@FXML
	private CornerMenu cornerMenu;

	@FXML
	private VBox envIcon= new VBox();
	private VBox soapWindowIcon= new VBox(); //SOAP
	private VBox restIcon= new VBox();  //REST
	private VBox testFactoryIcon= new VBox(); 
	private VBox variablesIcon = new VBox();  //v
	private VBox startButton= new VBox();
	private VBox soapIcon= new VBox(); //SOAP
	private VBox restWindowIcon= new VBox(); //REST
	private VBox environmentIcon= new VBox(); //ENV
	private VBox varWindowIcon= new VBox(); //v
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
	boolean isTestFactoryDisplayed =false;
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
	public static Stage stageEnvironment;

	public void setScreenParent(ScreensController screenParent){
		myController = screenParent;
	}

	//main functionality 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		initializeCornerMenu();
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


	private void initializeCornerMenu() {
		
		startButton = (VBox) CreateIcon(MainConstants.START_ICON.toString(),"Start");
		startButton.setLayoutX(1);
		startButton.setLayoutY(1);
		startButton.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {
				
				cornerMenu.show();
			}
		});
		
		createCornerMenu();
	}

	private void saveProject() {
		SaveProject.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
			}
		});
	}

	//method to create+add the icons
	private void createIcons(){		
		assetsIcon = (VBox) CreateIcon(MainConstants.ASSETS_ICON.toString(), "Assets");
		assetsIcon.setLayoutX(10);
		assetsIcon.setLayoutY(20);
		envIcon = (VBox) CreateIcon(MainConstants.ENVIRONMENTS_ICON.toString(), "Environments Definition");
		envIcon.setLayoutX(10);
		envIcon.setLayoutY(120);
		soapWindowIcon = (VBox) CreateIcon(MainConstants.SOAP_WINDOW_ICON.toString(), "Import SOAP Definitions");
		soapWindowIcon.setLayoutX(10);
		soapWindowIcon.setLayoutY(220);
		restIcon = (VBox) CreateIcon(MainConstants.REST_ICON.toString(), "Import REST Definitions");
		restIcon.setLayoutX(10);
		restIcon.setLayoutY(320);
		testFactoryIcon = (VBox) CreateIcon(MainConstants.TEST_FACTORY_ICON.toString(), "Test Factory");
		testFactoryIcon.setLayoutX(10);
		testFactoryIcon.setLayoutY(420);
		variablesIcon = (VBox) CreateIcon(MainConstants.VARIABLES_ICON.toString(), "Variables"); //v
		variablesIcon.setLayoutX(10); //v
		variablesIcon.setLayoutY(520); //v

		topPane.getChildren().addAll(assetsIcon,envIcon,soapWindowIcon,restIcon,testFactoryIcon, variablesIcon);
		bar.getChildren().addAll(startButton);
	}

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
				radialMenu.addMenuItem(MainConstants.ASSET_MENU_ICON.toString(), null);
				radialMenu.computeItemsStartAngle();
				radialMenu.addMenuItem(MainConstants.ASSET_MENU_ICON.toString(), null);
				radialMenu.computeItemsStartAngle();
				radialMenu.addMenuItem(MainConstants.ASSET_MENU_ICON.toString(), null);
				radialMenu.computeItemsStartAngle();
				radialMenu.addMenuItem(MainConstants.ASSET_MENU_ICON.toString(), null);
				radialMenu.computeItemsStartAngle();
				radialMenu.addMenuItem(MainConstants.ASSET_MENU_ICON.toString(), null);
				radialMenu.computeItemsStartAngle();
				radialMenu.addMenuItem(MainConstants.ASSET_MENU_ICON.toString(), null);
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
		soapWindowIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				// TODO Auto-generated method stub	
				if(event.getClickCount() == 2 && isDisplayed3 == false) {
					stageSoap = new Stage();

					isDisplayed3 = true;
					try {
						root = FXMLLoader.load(getClass().getResource(MainConstants.SOAP_DEFINITION.toString()));

						Scene second = new Scene(root);

						second.getStylesheets().add(WsTesterMain.class.getResource(MainConstants.APPLICATION_STYLE_CSS.toString()).toExternalForm());					
						root.getStyleClass().add("mainWind");

						soapWindowIcon = (VBox)CreateIcon(MainConstants.SOAP_WINDOW_ICON.toString(),"Soap");	
						soapWindowIcon.setLayoutX(poz);
						soapWindowIcon.setLayoutY(1);
						lista.add(soapWindowIcon);
						bar.getChildren().add(soapWindowIcon);
						poz=poz+100;										
//						expandIcons(newIcoM2);



						soapWindowIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {

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
								ind=lista.indexOf(soapWindowIcon);
								arrangeIcons(ind);
								lista.remove(lista.indexOf(soapWindowIcon));				        	 
								System.out.println("Inchid stage'ul");
								bar.getChildren().remove(soapWindowIcon);
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
		moveIcons(soapWindowIcon);

	}


	//pana aici

	private void createRESTWindow(){
		restIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {			
			@Override
			public void handle(MouseEvent event) {


				// TODO Auto-generated method stub	
				if(event.getClickCount() == 2 && isDisplayed4 == false) {
					stageRest = new Stage();
					isDisplayed4 = true;
					try {
						//TODOL:  Make this fxml shorter (doesnt load from : /fxml/....  now)
						
						root = FXMLLoader.load(getClass().getResource(MainConstants.REST_WINDOW.toString()));
						Scene second = new Scene(root);
//						second.getStylesheets().add(WsTesterMain.class.getResource("/styles/application.css").toExternalForm());
						 second.getStylesheets().setAll(
					                "/plot2d/resources/default.css");
						//second.setFill(Color.TRANSPARENT);
						//second.setFill(Color.TRANSPARENT);
						//stageRest.initStyle(StageStyle.TRANSPARENT);
						root.getStyleClass().add("mainWind");

						restWindowIcon = (VBox) CreateIcon(MainConstants.REST_WINDOW_ICON.toString(),"Rest");	
						restWindowIcon.setLayoutX(poz);
						restWindowIcon.setLayoutY(1);
						lista.add(restWindowIcon);
						//AfiseazaIcons("/images/task_img_open.png","RestWindow");
						bar.getChildren().add(restWindowIcon);
						poz=poz+100;
//						expandIcons(newIcoM3);


						restWindowIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {

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
								ind=lista.indexOf(restWindowIcon);
								arrangeIcons(ind);
								lista.remove(lista.indexOf(restWindowIcon));

								System.out.println("Inchid stage'ul rest");
								bar.getChildren().remove(restWindowIcon);;
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

		moveIcons(restIcon);
	}
	
	//v
	private void createVarWindow(){
		variablesIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {			
			@Override
			public void handle(MouseEvent event) {


				// TODO Auto-generated method stub	
				if(event.getClickCount() == 2 && isDisplayed6 == false) {
					stageVar = new Stage();
					isDisplayed6 = true;
					try {
						root = FXMLLoader.load(getClass().getResource(MainConstants.VARIABLES_FXML.toString()));
						Scene second = new Scene(root);
//						second.getStylesheets().add(WsTesterMain.class.getResource(MainConstants.APPLICATION_STYLE_CSS.toString()).toExternalForm());
						//second.setFill(Color.TRANSPARENT);
						//second.setFill(Color.TRANSPARENT);
						//stageRest.initStyle(StageStyle.TRANSPARENT);
						root.getStyleClass().add("mainWind");

						varWindowIcon = (VBox) CreateIcon(MainConstants.VARIABLES_ICON.toString(),"Variables");	
						varWindowIcon.setLayoutX(poz);
						varWindowIcon.setLayoutY(1);
						lista.add(varWindowIcon);
						//AfiseazaIcons("/images/task_img_open.png","RestWindow");
						bar.getChildren().add(varWindowIcon);
						poz=poz+100;
//						expandIcons(newIcoM7);


						varWindowIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {

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
								ind=lista.indexOf(varWindowIcon);
								arrangeIcons(ind);
								lista.remove(lista.indexOf(varWindowIcon));

								System.out.println("Inchid stage'ul var");
								bar.getChildren().remove(varWindowIcon);;
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

		moveIcons(variablesIcon);
	}


	private void createTestFactoryWindow(){
		testFactoryIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {		
			
			@Override
			public void handle(MouseEvent event) {

				if (event.getClickCount() == 2 && isTestFactoryDisplayed == false) {
					stageRnd = new Stage();
					isTestFactoryDisplayed = true;
					try {
						FXMLLoader loader = new FXMLLoader(getClass().getResource(MainConstants.TEST_MACHINE.toString()));
						Scene second = new Scene(loader.load(), 1280, 720);
						((Node)loader.getRoot()).getStyleClass().addAll(MainConstants.TEST_FACTORY_STYLE.toString());
						stage.setTitle("Test Suites window");


						testFactoryIcon = (VBox) CreateIcon(MainConstants.TEST_FACTORY_ICON.toString(), "Test Factory");
						testFactoryIcon.setLayoutX(poz);
						testFactoryIcon.setLayoutY(1);
						lista.add(testFactoryIcon);
						bar.getChildren().add(testFactoryIcon);
						poz = poz + 100;
						// expandIcons(newIcoM4);

						testFactoryIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {

							@Override
							public void handle(MouseEvent event2) {

								// TODO Auto-generated method stub
								if (event2.getClickCount() == 1) {

									stageRnd.toFront();
									stageRnd.centerOnScreen();
									stageRnd.setIconified(false);
									stageRnd.show();
								}

							}
						});

						// menuBar.getMenus().add(menuRnd);
						stageRnd.initOwner(pane.getScene().getWindow());
						// stage.initModality(Modality.WINDOW_MODAL);
						stageRnd.setScene(second);
						// stageRnd.initOwner(WsTesterMain.stage);
						stageRnd.show();

						// modificare laur

						stageRnd.setOnCloseRequest(new EventHandler<WindowEvent>() {
							public void handle(WindowEvent we) {
								ind = lista.indexOf(testFactoryIcon);
								arrangeIcons(ind);
								lista.remove(lista.indexOf(testFactoryIcon));

								System.out.println("Inchid stage'ul");
								bar.getChildren().remove(testFactoryIcon);
								isTestFactoryDisplayed = false;
								stageRnd = null;
								poz = poz - 100;
							}
						});
						stageRnd.iconifiedProperty().addListener(new ChangeListener<Boolean>() {

							@Override
							public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
								stageRnd.hide();
							}
						});

						// pana aici
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (isTestFactoryDisplayed == true && event.getClickCount() == 2) {
					stageRnd.toFront();
					stageRnd.centerOnScreen();
					stageRnd.setIconified(false);
					stageRnd.show();
				}
			}
		});

		moveIcons(testFactoryIcon);
	}

	// pana aici

	private void createAssetsWindow(){
		assetsIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {			
			@Override
			public void handle(MouseEvent event) {

				if(event.getClickCount() == 2 && isDisplayed == false) {
					stageAssets = new Stage();
					isDisplayed = true;

					try {
						root = FXMLLoader.load(getClass().getResource(MainConstants.ASSETS_FXML.toString()));
						Scene second = new Scene(root);
						String cssPath="/styles/asset.css";     //    the css path for assets
						second.getStylesheets().addAll(cssPath); //	   the css add 			
						root.getStyleClass().add("mainWind");

						assetsIcon = (VBox) CreateIcon(MainConstants.ASSETS_ICON.toString(),"Assets");	
						assetsIcon.setLayoutX(poz);
						assetsIcon.setLayoutY(1);
						lista.add(assetsIcon);
						bar.getChildren().add(assetsIcon);
						poz=poz+100;
//						expandIcons(newIcoM5);
						assetsIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {

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
								ind=lista.indexOf(assetsIcon);
								arrangeIcons(ind);
								lista.remove(lista.indexOf(assetsIcon));

								System.out.println("Inchid stage'ul");
								bar.getChildren().remove(assetsIcon);
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
		moveIcons(assetsIcon);





	}
	private void createEnvWindow(){
		envIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {			
			@Override
			public void handle(MouseEvent event) {

				// TODO Auto-generated method stub	
				if(event.getClickCount() == 2 && isDisplayed2 == false) {
					stageEnv = new Stage();
					isDisplayed2 = true;
					try {
						 
						WsTesterMainController.stageEnvironment=stageEnv;
						root = FXMLLoader.load(getClass().getResource(MainConstants.ENVIRONMENT_MANAGER_FXML.toString()));
						EnvironmentsAppFactory factory = new EnvironmentsAppFactory();
						MainPresenter mainPresenter = factory.getMainPresenter();
						mainPresenter.loadEnvironments();
						//                        mainPresenter.setTestProject(testProject);
						root = mainPresenter.getView();
						Scene second = new Scene(root, 950, 550);
						stage.setTitle("Environments window");
						String cssPath = "/styles/Envwindows.css"; // the css path for enviroment window	
						second.getStylesheets().addAll(cssPath);   // the css add
						root.getStyleClass().add("mainWind");
						environmentIcon = (VBox) CreateIcon(MainConstants.ENVIRONMENTS_ICON.toString(),"Env");	
						environmentIcon.setLayoutX(poz);
						environmentIcon.setLayoutY(1);
						lista.add(environmentIcon);
						bar.getChildren().addAll(environmentIcon);
						poz=poz+100;
//						expandIcons(newIcoM6);

						environmentIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {

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
								ind=lista.indexOf(environmentIcon);
								arrangeIcons(ind);
								lista.remove(lista.indexOf(environmentIcon));

								System.out.println("Inchid stage'ul");
								bar.getChildren().remove(environmentIcon);;
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
		moveIcons(envIcon);
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
	
	 private void createCornerMenu() {
	        
	        // create a new corner menu
	        cornerMenu = new CornerMenu(CornerMenu.Location.BOTTOM_LEFT,pane,false);
	    	MenuItem saveMenuItem = new MenuItem("Save", new ImageView(new Image(this.getClass().getResourceAsStream(MainConstants.SAVE_ICON.toString()))));
	    	MenuItem toHomePageMenuItem = new MenuItem("Go to Home Page", new ImageView(new Image(this.getClass().getResourceAsStream(MainConstants.HOMEPAGE_ICON.toString()))));
	   
	        cornerMenu.getItems().addAll(saveMenuItem, toHomePageMenuItem);
	        
	        saveMenuItem.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent arg0) {
					
					TestProjectService service = new TestProjectService();
					TestProject testProject = service.getTestProject();
					
		            FileChooser fileChooser = new FileChooser();
		            fileChooser.setTitle("Save Test Project");
		    		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("STEP project files (*.step)", "*.step");
		    		fileChooser.getExtensionFilters().add(extFilter);
		            File file = fileChooser.showSaveDialog(stage);
		            if(file != null){
						try {
							ITestProjectActions testProjectActions = ServiceLocator.getInstance().lookup(ITestProjectActions.class);
							testProjectActions.save(file.getPath(), testProject);
						} catch (Exception e) {
							Dialog.errorDialog("The project could not be saved. Please try again!", stage);
						}
		            }
				}
			});
	        
	        toHomePageMenuItem.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					if (isDisplayed==true) {
						stageAssets.close();
						bar.getChildren().remove(assetsIcon);
						isDisplayed=false;
						poz=poz-100;
					}
					
					if (isDisplayed2==true) {
						isDisplayed2=false;
						stageEnv.close();
						bar.getChildren().remove(environmentIcon);
						poz=poz-100;
					}
					
					if (isDisplayed3==true) {
						stageSoap.close();
						bar.getChildren().remove(soapWindowIcon);
						isDisplayed3=false;
						poz=poz-100;
					}
					if (isDisplayed4==true) {
						stageRest.close();
						bar.getChildren().remove(restWindowIcon);
						isDisplayed4=false;
						poz=poz-100;
					}
					if (isTestFactoryDisplayed==true) {
						stageRnd.close();
						bar.getChildren().remove(testFactoryIcon);
						isTestFactoryDisplayed=false;
						poz=poz-100;
					}
					if (isDisplayed6==true) {
						stageVar.close();
						bar.getChildren().remove(varWindowIcon);
						isDisplayed6=false;
						poz=poz-100;
					}
					
					try {
						ICamelContextManager manager = ServiceLocator.getInstance().lookup(ICamelContextManager.class);
						manager.closeCamelContext();
					} catch (Exception e) {
						Dialog.errorDialog("Could't go back to the home page. Please try again!", stage);
					}
					
					myController.setScreen(MainLauncher.screen1ID);
				}
	    	 
	     });
	 }
}
