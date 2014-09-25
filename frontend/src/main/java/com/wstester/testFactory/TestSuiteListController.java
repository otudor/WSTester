package com.wstester.testFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.collections.ObservableList;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.camel.util.CaseInsensitiveMap;

import com.wstester.model.Environment;
import com.wstester.model.MongoService;
import com.wstester.model.MongoStep;
import com.wstester.model.MySQLService;
import com.wstester.model.MySQLStep;
import com.wstester.model.Response;
import com.wstester.model.RestStep;
import com.wstester.model.Server;
import com.wstester.model.Service;
import com.wstester.model.SoapStep;
import com.wstester.model.Step;
import com.wstester.model.Execution;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.TestCase;
import com.wstester.model.TestSuite;
import com.wstester.services.impl.TestRunner;

public class TestSuiteListController implements Initializable
{
    @FXML private Node root;
    //@FXML private TextField searchField;
   // @FXML private ListView<Old> resultsList;
    private List<TestSuite> tsList;
    @FXML private TreeView<Object> treeView;
    
    private TestSuiteManagerController tsManagerController;
    private TestSuiteService tsService;
    
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
    }

    public Node getView()
    {
        return root;
    }

    public void setTestSuiteManagerController( TestSuiteManagerController tsManagerController)
    {
        this.tsManagerController = tsManagerController;
    }
    
    public void setTestSuiteService(TestSuiteService tsService)
    {
        this.tsService = tsService;
    }

    public void loadSuites()
    {
    	tsList = tsService.getTestSuites();    
    }
    
    public void selectTestSuite( String tsUID)
    {
    	tsManagerController.showTestSuite( tsUID);
    }
    
    public void selectTestCase( String tsUID)
    {
    	tsManagerController.showTestCase( tsUID);
    }

    public void selectMySQLStep( String sUID)
    {
    	tsManagerController.showMySQLStep( sUID);
    }
    
    public void selectMongoStep( String sUID)
    {
    	tsManagerController.showMongoStep( sUID);
    }
    
    public void selectSoapStep( String sUID)
    {
    	tsManagerController.showSoapStep( sUID);
    }
    
    public void selectRestStep( String sUID)
    {
    	tsManagerController.showRestStep( sUID);
    }
    
    public String getFirstEnv()
    {
    	return tsService.getFirstTestSuite().getID();
    }
    
    public List<TestSuite> getTestSuiteList()
    {
    	return this.tsList;
    }
    
    public TreeView<Object> getTreeView(){
    	return this.treeView;
    }
    
    public void loadTreeItems() 
    {
    	Node icon = null;
    	
    	TreeItem<Object> root = new TreeItem<Object>("treeRoot");
    	root.setExpanded(true);
        	
    	List<TestSuite> suiteList = this.tsList; /*tsService.getTestSuites();*/    	
    	for (TestSuite ts : suiteList)
    	{
    		icon =  new ImageView(new Image(getClass().getResourceAsStream("/images/treeIcon_TestSuite.png")));
    		TreeItem<Object> envNode = new TreeItem<>(ts, icon);    		
    		List<TestCase> tclist = ts.getTestCaseList();
    		
    		if ( tclist!= null && !tclist.isEmpty())
    		{
    			for (TestCase tc: tclist)
    			{
    				icon =  new ImageView(new Image(getClass().getResourceAsStream("/images/treeIcon_TestCase.png")));
    				TreeItem<Object> tcNode = new TreeItem<>(tc, icon);
    				
    				List<Step> steps = tc.getStepList();    				
    				if ( steps!= null && !steps.isEmpty())
	    				
    					for (Step step: steps)
	        			{
	    					if ( step instanceof MySQLStep )
	    						icon =  new ImageView(new Image(getClass().getResourceAsStream("/images/treeIcon_TestStep.png")));
	    					else
	    						if ( step instanceof MongoStep )
	    							icon =  new ImageView(new Image(getClass().getResourceAsStream("/images/treeIcon_TestStep.png")));
	    						else if ( step instanceof SoapStep )
    							icon =  new ImageView(new Image(getClass().getResourceAsStream("/images/treeIcon_TestStep.png")));
	    						else if ( step instanceof RestStep )
	    							icon =  new ImageView(new Image(getClass().getResourceAsStream("/images/treeIcon_TestStep.png")));
	    					TreeItem<Object> stepNode = new TreeItem<>(step, icon);
	    					tcNode.getChildren().add(stepNode);
	        			}    				    				  				
    				envNode.getChildren().add( tcNode);
    			}
    		}
    		
    		root.getChildren().add(envNode);
    	}
    	
    	treeView.setShowRoot(false);
    	treeView.setRoot(root);
    	treeView.setEditable(false);
    	treeView.getSelectionModel().setSelectionMode( SelectionMode.SINGLE);
    	treeView.setCellFactory(new Callback<TreeView<Object>,TreeCell<Object>>(){
            @Override
            public TreeCell<Object> call(TreeView<Object> p) {
                return new TestSuiteTreeCellImplementation();
            }
        });
    }
    
    public ContextMenu createTestSuiteContextMenu( TestSuite ts)
    {
    	final ContextMenu contextMenu = new ContextMenu();
    	MenuItem addTCMenu = new MenuItem("Add Test Case");
    	contextMenu.getItems().addAll(addTCMenu);
    	
    	addTCMenu.setOnAction(new EventHandler<ActionEvent>() 
    	{
    	    @Override
    	    public void handle(ActionEvent event) 
    	    {
    	    	TreeItem<Object> item = (TreeItem<Object>)treeView.getSelectionModel().getSelectedItem();
    	    	if( item == null ) return;

    	    	//Environment e = (Environment)(item.getValue());
    	    	TestCase newTC = tsService.addTestCaseForTestSuite(ts.getID());
    	    	if (newTC != null)
    	    	{
    	    		Node icon =  new ImageView(new Image(getClass().getResourceAsStream("/images/treeIcon_TestCase.png")));
    	    		TreeItem<Object> tcNode = new TreeItem<>(newTC, icon);
    	    		item.getChildren().add( tcNode);
    	    		treeView.getSelectionModel().select( tcNode);
    	    		selectTestCase(newTC.getID());
       	    	}
    	    }
    	});
    	
    	// rename
    	/*rename.setOnAction(new EventHandler<ActionEvent>() 
    	{
    	    @Override
    	    public void handle(ActionEvent event) 
    	    {
    	    	treeView.setEditable( true);
    	    	treeView.edit( (TreeItem<Object>)treeView.getSelectionModel().getSelectedItem());
    	    }
    	});*/
    	
    	return contextMenu;
    }
    
    public ContextMenu createTestCaseContextMenu( TestSuite ts, TestCase tc)
    {
    	final ContextMenu contextMenu = new ContextMenu();
    	MenuItem addMysqlItem = new MenuItem("Add MySQL step" /*+ ftp.getID()*/);
    	MenuItem addMongoItem = new MenuItem("Add Mongo step" /*+ ftp.getID()*/);
    	MenuItem addSoapItem = new MenuItem("Add Soap step" /*+ ftp.getID()*/);
    	MenuItem addRestItem = new MenuItem("Add Rest step" /*+ ftp.getID()*/);
    	MenuItem removeItem = new MenuItem("Remove" /*+ ftp.getID()*/);
    	contextMenu.getItems().addAll( addMysqlItem,addMongoItem,addSoapItem,addRestItem,removeItem);
    	
    	
    	removeItem.setOnAction(new EventHandler<ActionEvent>() 
    	{
    	    @Override
    	    public void handle( ActionEvent event) 
    	    {
    	    	TreeItem<Object> c = (TreeItem<Object>)treeView.getSelectionModel().getSelectedItem();
    	    	int idx = treeView.getSelectionModel().getSelectedIndex();
    	    	if( c == null ) return;    	    	
    	    	
    	    	tsService.removeTestCase( tc.getID());
                c.getParent().getChildren().remove(c);
                               
                treeView.getSelectionModel().select( idx > 0 ? idx-1 : 0);
    	    }
    	});
    	
    	addMysqlItem.setOnAction(new EventHandler<ActionEvent>() 
    	    	{
    	    	    @Override
    	    	    public void handle(ActionEvent event) 
    	    	    {
    	    	    	TreeItem<Object> item = (TreeItem<Object>)treeView.getSelectionModel().getSelectedItem();
    	    	    	if( item == null ) return;
    	    	    	
    	    	    	MySQLStep mySQLStep = new MySQLStep();
    	    	    	mySQLStep.setName("New MySQL Step");
    	    	    	tsService.addStepForTestCase( mySQLStep, tc.getID());
    	    	    	
    	    	    	Node icon =  new ImageView(new Image(getClass().getResourceAsStream("/images/treeIcon_TestStep.png")));
    	    	    	TreeItem<Object> stepNode = new TreeItem<>(mySQLStep, icon);
    	    	    	item.getChildren().add( stepNode);
    	    	    	treeView.getSelectionModel().select( stepNode);
    	    	    	selectMySQLStep(mySQLStep.getID());
    	    	    	
    	    	    }
    	    	});
    	
    	addMongoItem.setOnAction(new EventHandler<ActionEvent>() 
    	    	{
    	    	    @Override
    	    	    public void handle(ActionEvent event) 
    	    	    {
    	    	    	TreeItem<Object> item = (TreeItem<Object>)treeView.getSelectionModel().getSelectedItem();
    	    	    	if( item == null ) return;

    	    	    	MongoStep mongoStep = new MongoStep();
    	    	    	mongoStep.setName("New Mongo Step");
    	    	    	tsService.addStepForTestCase( mongoStep, tc.getID());
    	    	   
    	    	    	Node icon =  new ImageView(new Image(getClass().getResourceAsStream("/images/treeIcon_TestStep.png")));
    	    	    	TreeItem<Object> stepNode = new TreeItem<>(mongoStep, icon);
    	    	    	item.getChildren().add( stepNode);
    	    	    	treeView.getSelectionModel().select( stepNode);
    	    	    	selectMongoStep(mongoStep.getID());
    	       	    	
    	            }
    	    	});
    	addSoapItem.setOnAction(new EventHandler<ActionEvent>() 
    	    	{
    	    	    @Override
    	    	    public void handle(ActionEvent event) 
    	    	    {
    	    	    	TreeItem<Object> item = (TreeItem<Object>)treeView.getSelectionModel().getSelectedItem();
    	    	    	if( item == null ) return;
    	    	    	  	    	    	
    	    	    	SoapStep soapStep = new SoapStep();
    	    	    	soapStep.setName("New Soap Step");
    	    	    	tsService.addStepForTestCase( soapStep, tc.getID());
    	    	    	
    	    	    	Node icon =  new ImageView(new Image(getClass().getResourceAsStream("/images/treeIcon_TestStep.png")));
    	    	    	TreeItem<Object> stepNode = new TreeItem<>(soapStep, icon);
    	    	    	item.getChildren().add( stepNode);
    	    	    	treeView.getSelectionModel().select( stepNode);
    	    	    	selectSoapStep(soapStep.getID());
    	    	    	
    	              
    	    	    }
    	    	});
    	addRestItem.setOnAction(new EventHandler<ActionEvent>() 
    	    	{
    	    	    @Override
    	    	    public void handle(ActionEvent event) {
    	    	    	TreeItem<Object> item = (TreeItem<Object>)treeView.getSelectionModel().getSelectedItem();
    	    	    	if( item == null ) return;
    	    	    	RestStep restStep = new RestStep();
    	    	    	restStep.setName("New Rest Step");
    	    	    	tsService.addStepForTestCase( restStep, tc.getID());
						
    	    	    	Node icon = new ImageView(new Image(getClass().getResourceAsStream("/images/treeIcon_TestStep.png")));
						TreeItem<Object> stepNode = new TreeItem<>(restStep, icon);
						item.getChildren().add(stepNode);
						treeView.getSelectionModel().select(stepNode);
						selectRestStep(restStep.getID());
				  }
    	    	});
    	
    	return contextMenu;
    }
    
    
    // TREE CELL CLASS
    public class TestSuiteTreeCellImplementation extends TreeCell<Object> 
    {    	
    	private TextField textField;
    	
    	public TestSuiteTreeCellImplementation()
    	{
    		this.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                public void handle(MouseEvent event)
                {
                	TreeItem<Object> t = getTreeItem();
                	if( t == null) return;
                	
                	if( (event.getClickCount() == 1) && (event.getButton() == MouseButton.PRIMARY))
                	{
                    	if ( getItem() != null)
                    	{
    	                	if ( getItem().getClass() == TestSuite.class)
    	                    	selectTestSuite( ((TestSuite) getItem()).getID());
    	                    else if ( getItem().getClass() == TestCase.class)
    	                    	selectTestCase(  ((TestCase) getItem()).getID());
    	                    else if ( getItem().getClass() == MySQLStep.class)
    	                    	selectMySQLStep(  ((MySQLStep) getItem()).getID());
    	                    else if ( getItem().getClass() == MongoStep.class)
    	                    	selectMongoStep(  ((MongoStep) getItem()).getID());
    	                    else if ( getItem().getClass() == SoapStep.class)
    	                    	selectSoapStep(  ((SoapStep) getItem()).getID());
    	                    else if ( getItem().getClass() == RestStep.class)
    	                    	selectRestStep(  ((RestStep) getItem()).getID());
                    	}
                	}
                }
            });
    	}
    	
    	@Override
        public void updateItem(Object item, boolean empty) 
        {
            super.updateItem(item, empty);
            
            if (empty)
            {
                setText(null);
                setGraphic(null);
                setContextMenu(null);
            }
            else 
            {
            	/*if( isEditing())
            	{
            		if (textField != null) 
                        textField.setText( getItem().toString());
            		setText(null);
            		setGraphic(textField);
            		System.out.println("updateItem -" + getItem().toString() + "- e editing ");
            	}
            	else */
            	{
            		//System.out.println("updateItem (" + getItem().toString() + ") nu e editing ");
            		//setText(getItem() == null ? "" : getItem().toString());
                    //setGraphic(getTreeItem().getGraphic());
            		//setGraphic( addHBox());
            	}
            	
            	//System.out.println("am un nod: " + getItem().getClass());
            
                if ( getItem().getClass() == MySQLStep.class )
                {
                	setGraphic( createStepGraphic(item));
                }
                else if ( getItem().getClass() == MongoStep.class )
                {
                    	setGraphic( createStepGraphic(item));
                }
                else if ( getItem().getClass() == SoapStep.class )
                {
                    	setGraphic( createStepGraphic(item));
                }
                else if ( getItem().getClass() == RestStep.class )
                {
                    	setGraphic( createStepGraphic(item));
                }
                else
                {
                	setText(getItem() == null ? "" : getItem().toString());
                	setGraphic(getTreeItem().getGraphic());
                }                       
                if( getItem() != null)
                    if ( getItem().getClass() == TestSuite.class)
                    {
                    	TestSuite ts = (TestSuite) getItem();
                    	this.setContextMenu( createTestSuiteContextMenu(ts));
                    }
                    else if ( getItem().getClass() == TestCase.class)
	                {
	                	TestSuite ts = (TestSuite)getTreeItem().getParent().getValue();
	                	TestCase tc = (TestCase) getItem();
	                	this.setContextMenu( createTestCaseContextMenu(ts, tc));
	                }
                

            }
        }
    	
    	public HBox createStepGraphic( Object item) {
    	    HBox hbox = new HBox();
    	    hbox.setPadding(new Insets(0, 0, 0, 0));
    	    hbox.setSpacing(5);

    	    Label lblNodeName = new Label( getItem() == null ? "" : getItem().toString() ); 
    	    hbox.getChildren().addAll(lblNodeName);
    	    
    	    Execution execution = ((Step) getItem()).getLastExecution();
    	    if ( execution != null)
    	    {
	    	    ImageView pic = null;
	    	    if ( execution.getResponse().getStatus() == ExecutionStatus.PASSED)
	    	    	pic = new ImageView(new Image(getClass().getResourceAsStream("/images/treeIcon_step_passed.png")));

	    	    hbox.getChildren().addAll(pic);
    	    }
    	    
    	    return hbox;
    	}
    }
    
    public void createTestSuite( ActionEvent event)
    {
    	TestSuite ts = tsService.createTestSuite("New TestSuite");
    	Node icon =  new ImageView(new Image(getClass().getResourceAsStream("/images/treeIcon_environment.png")));
    	TreeItem<Object> node = new TreeItem<>( ts, icon);
        treeView.getRoot().getChildren().add(node);
        //root.getChildren().add(envNode);
        treeView.setEditable(true);
        treeView.getSelectionModel().select( node);
        treeView.getFocusModel().focusNext();
        treeView.edit( node);
        selectTestSuite( ts.getID());
    }
}
