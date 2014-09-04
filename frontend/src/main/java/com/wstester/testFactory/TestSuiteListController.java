package com.wstester.testFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
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
import javafx.util.Callback;
import javafx.collections.ObservableList;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.hamcrest.core.IsInstanceOf;

import com.wstester.model.Environment;
import com.wstester.model.MongoService;
import com.wstester.model.MySQLService;
import com.wstester.model.MySQLStep;
import com.wstester.model.Server;
import com.wstester.model.Service;
import com.wstester.model.ServiceType;
import com.wstester.model.SoapStep;
import com.wstester.model.Step;
import com.wstester.model.TestCase;
import com.wstester.model.TestSuite;

public class TestSuiteListController implements Initializable
{
    @FXML private Node root;
    //@FXML private TextField searchField;
   // @FXML private ListView<Old> resultsList;
    private ObservableList<TestSuite> tsList;
    @FXML static private TreeView<Object> treeView;
    
    private TestSuiteManagerController tsManagerController;
    static private TestSuiteService tsService;
    
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

    public void search()
    {
    	tsList = FXCollections.observableList(tsService.getTestSuites());    
        //load the tree also
        loadTreeItems();
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
    
    public String getFirstEnv()
    {
    	return tsService.getFirstTestSuite().getID();
    }
    
    public void loadTreeItems() 
    {
    	Node icon = null;
    	
    	TreeItem<Object> root = new TreeItem<Object>("");
    	root.setExpanded(true);
    	
    	List<TestSuite> envs = (List<TestSuite>) tsService.getTestSuites();    	
    	for (TestSuite env : envs)
    	{
    		icon =  new ImageView(new Image(getClass().getResourceAsStream("/images/treeIcon_TestSuite.png")));
    		TreeItem<Object> envNode = new TreeItem<>(env, icon);    		
    		List<TestCase> tclist = env.getTestCaseList();
    		
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
    	MenuItem removeItem = new MenuItem("Remove" /*+ ftp.getID()*/);
    	contextMenu.getItems().addAll( addMysqlItem);
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
    
    /*
    public static void updateRunStatus()
    {
    	Node icon = null;
    	
    	TreeItem<Object> root = new TreeItem<Object>("");
    	root.setExpanded(true);
    	
    	List<TestSuite> envs = (List<TestSuite>) tsService.getTestSuites();    	
    	for (TestSuite env : envs)
    	{
    		//icon =  new ImageView(new Image(getClass().getResourceAsStream("/images/treeIcon_TestSuite.png")));
    		TreeItem<Object> envNode = new TreeItem<>(env, icon);    		
    		List<TestCase> tclist = env.getTestCaseList();
    		
    		if ( tclist!= null && !tclist.isEmpty())
    		{
    			for (TestCase tc: tclist)
    			{
    				icon =  new ImageView(new Image(TestSuiteListController.class.getResourceAsStream("/images/treeIcon_TestCase.png")));
    				TreeItem<Object> tcNode = new TreeItem<>(tc, icon);
    				
    				    				
    				List<Step> steps = tc.getStepList();    				
    				if ( steps!= null && !steps.isEmpty())
	    				for (Step step: steps)
	        			{
	    					if ( step instanceof MySQLStep )
	    					{
	    						TreeItem<Object> ts = 
	    					
	    						//response = testRunner.getResponse(stp.getID(), 25000L);
	    						//root.getChildren().get(0).s
	    						treeView.getTreeItem( treeView.getRow());
	    					}
	    					
	        			}
    				
    				envNode.getChildren().add( tcNode);
    			}
    		}
    		
    		root.getChildren().add(envNode);
    	}
    }*/
}