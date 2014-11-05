package com.wstester.testFactory;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.event.Event;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import com.wstester.model.MySQLStep;
import com.wstester.model.Response;
import com.wstester.model.Step;
import com.wstester.model.Execution;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.ITestRunner;

public class ExecutionUpdate {

	TestSuiteListController testSuiteListController;
	
	public ExecutionUpdate(TestSuiteListController tsListController) {
		
		this.testSuiteListController = tsListController;
	}

	public void updateRunStatus() {
		
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(new UpdateStatusThread(testSuiteListController.getTreeView()));
		executor.shutdown();
    }
}

class UpdateStatusThread implements Runnable{

	TreeView<Object> innerTreeView;
	
	public UpdateStatusThread(TreeView<Object> treeView) {
		this.innerTreeView = treeView;
	}

	@Override
	public void run() {
		
    	List<TreeItem<Object>> lstSuites = innerTreeView.getRoot().getChildren();
    	for (TreeItem<Object> testSuite : lstSuites)
    	{
    		List<TreeItem<Object>> lstTestCases = testSuite.getChildren();
    		
    		if ( lstTestCases!= null && !lstTestCases.isEmpty())
    		{
    			for( TreeItem<Object> caseItem : lstTestCases)
    			{
    				List<TreeItem<Object>> tcItems = caseItem.getChildren();
    				if ( tcItems!= null && !tcItems.isEmpty())
    	    		{
    					for( TreeItem<Object> stepItem : tcItems) {
    						Step step = (Step)stepItem.getValue();

							ITestRunner testRunner = null;
							try {
								testRunner = ServiceLocator.getInstance().lookup(ITestRunner.class);
							} catch (Exception e) {
								// TODO Make a pop up window to inform the user
								// that we can't get the step responses
								e.printStackTrace();
							}
							
							Response rsp = testRunner.getResponse(step.getID(),	25000L);
							TestProjectService testProjectService = new TestProjectService();

							Execution execution = new Execution();
							Date date = new Date();
							execution.setRunDate(date);
							execution.setResponse(rsp);
							step.addExecution(execution);

							testProjectService.setStepByUID(step, step.getID());
						}
    	    		}
    			}
    		}
    	}
    	
    	//re enable the button
    	//TestSuiteManagerController.btnRun.setDisable(false);
    	//Event.fireEvent( TestSuiteManagerController.btnRun, new MyEvent());
    	//TestSuiteManagerController.btnRun.fireEvent( new );
	}
}
