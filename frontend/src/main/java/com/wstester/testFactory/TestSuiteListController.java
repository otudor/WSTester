package com.wstester.testFactory;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
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

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.wstester.model.Environment;
import com.wstester.model.MongoStep;
import com.wstester.model.MySQLStep;
import com.wstester.model.Step;
import com.wstester.model.Execution;
import com.wstester.model.StepStatusType;
import com.wstester.model.TestCase;
import com.wstester.model.TestSuite;

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
        //load the tree also
        //loadTreeItems();
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
	    					TreeItem<Object> stepNode = new TreeItem<>(step, icon);
	    					tcNode.getChildren().add( stepNode);
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
    	    		//show details in right pane
    	    		//selectServer( server.getID());
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
    	MenuItem removeItem = new MenuItem("Remove" /*+ ftp.getID()*/);
    	contextMenu.getItems().addAll( addMysqlItem);
    	contextMenu.getItems().addAll( addMongoItem);
    	contextMenu.getItems().addAll( removeItem);
    	
    	removeItem.setOnAction(new EventHandler<ActionEvent>() 
    	{
    	    @Override
    	    public void handle( ActionEvent event) 
    	    {
    	    	TreeItem<Object> c = (TreeItem<Object>)treeView.getSelectionModel().getSelectedItem();
    	    	int idx = treeView.getSelectionModel().getSelectedIndex();
    	    	if( c == null ) return;    	    	
    	    	
    	    	tsService.removeTestCase( ts.getID(), tc.getID());
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

    	    	    	//TestCase tcItem = (TestCase)(item.getValue());
    	    	    	MySQLStep mysqlStep = tsService.addMySQLStepforTestCase( tc.getID());
    	    	    	
    	    	    	if (mysqlStep != null)
    	    	    	{
    	    	    		Node icon =  new ImageView(new Image(getClass().getResourceAsStream("/images/treeIcon_TestStep.png")));
    	    	    		TreeItem<Object> stepNode = new TreeItem<>(mysqlStep, icon);
    	    	    		item.getChildren().add( stepNode);
    	    	    		treeView.getSelectionModel().select( stepNode);
    	    	    		//show details in right pane
    	    	    		//selectMySQLService( s.getID(),service.getID());
    	    	    	}
    	                //treeView.getSelectionModel().select( idx > 0 ? idx-1 : 0);
    	    	    }
    	    	});
    	
    	addMongoItem.setOnAction(new EventHandler<ActionEvent>() 
    	    	{
    	    	    @Override
    	    	    public void handle(ActionEvent event) 
    	    	    {
    	    	    	TreeItem<Object> item = (TreeItem<Object>)treeView.getSelectionModel().getSelectedItem();
    	    	    	if( item == null ) return;

    	    	    	//TestCase tcItem = (TestCase)(item.getValue());
    	    	    	MongoStep mongoStep = tsService.addMongoStepforTestCase( tc.getID());
    	    	    	
    	    	    	if (mongoStep != null)
    	    	    	{
    	    	    		Node icon =  new ImageView(new Image(getClass().getResourceAsStream("/images/treeIcon_TestStep.png")));
    	    	    		TreeItem<Object> stepNode = new TreeItem<>(mongoStep, icon);
    	    	    		item.getChildren().add( stepNode);
    	    	    		treeView.getSelectionModel().select( stepNode);
    	    	    		//show details in right pane
    	    	    		//selectMongoService( s.getID(),service.getID());
    	    	    	}
    	                //treeView.getSelectionModel().select( idx > 0 ? idx-1 : 0);
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
                else
                {
                	setText(getItem() == null ? "" : getItem().toString());
                	setGraphic(getTreeItem().getGraphic());
                }
                
                if ( getItem().getClass() == MongoStep.class )
                {
                	setGraphic( createMongoStepGraphic(item));
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
    	    
    	    Execution execution = ((MySQLStep) getItem()).getLastExecution();
    	    if ( execution != null)
    	    {
	    	    ImageView pic = null;
	    	    if ( execution.getStatus() == StepStatusType.PASSED)
	    	    	pic = new ImageView(new Image(getClass().getResourceAsStream("/images/treeIcon_step_passed.png")));

	    	    hbox.getChildren().addAll(pic);
    	    }
    	    
    	    return hbox;
    	}
    	
    	public HBox createMongoStepGraphic( Object item) {
    	    HBox hbox = new HBox();
    	    hbox.setPadding(new Insets(0, 0, 0, 0));
    	    hbox.setSpacing(5);

    	    Label lblNodeName = new Label( getItem() == null ? "" : getItem().toString() ); 
    	    hbox.getChildren().addAll(lblNodeName);
    	    
    	    Execution execution = ((MongoStep) getItem()).getLastExecution();
    	    if ( execution != null)
    	    {
	    	    ImageView pic = null;
	    	    if ( execution.getStatus() == StepStatusType.PASSED)
	    	    	pic = new ImageView(new Image(getClass().getResourceAsStream("/images/treeIcon_step_passed.png")));

	    	    hbox.getChildren().addAll(pic);
    	    }
    	    
    	    return hbox;
    	}
    	
    	@Override
        public void startEdit()
    	{
            super.startEdit();

            if (textField == null)
                createTextField();
                
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }

        @Override
        public void cancelEdit()
        {
            super.cancelEdit();
            
            setText( ((Environment) getItem()).getName());
            if( getTreeItem() != null )
            	setGraphic(getTreeItem().getGraphic());
            
            //textField = null;
            treeView.setEditable( false);
        }

        private void createTextField() 
        {
            textField = new TextField( ((Environment) getItem()).getName());
            textField.setOnKeyReleased(new EventHandler<KeyEvent>() 
            { 
                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) 
                    {
                    	Environment e = (Environment) getItem();
                    	e.setName( textField.getText());
                        commitEdit( e);
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
            });
        }
    }   
}
