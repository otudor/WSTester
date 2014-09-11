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
import com.wstester.model.StepStatusType;
import com.wstester.services.impl.TestRunner;

public class ExecutionUpdate {

	TestSuiteListController testSuiteListController;
	
	public ExecutionUpdate(TestSuiteListController tsListController) {
		
		this.testSuiteListController = tsListController;
	}

	public void updateRunStatus()
    {
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
    					for( TreeItem<Object> stepItem : tcItems)
    					{
    						Step step = (Step)stepItem.getValue();
    						if( step instanceof MySQLStep)
    						{
    							TestRunner testRunner = new TestRunner(null);
    							Response rsp = testRunner.getResponse(step.getID(), 25000L);
    							//step.setName( step.getName());
    							
    							Execution execution = new Execution();
    							Date date= new Date();
    							execution.setRunDate( new Date()/*new Timestamp(date.getTime())*/);
    							execution.setStatus( StepStatusType.PASSED);
    							execution.setResponse( rsp);
    							step.addExecution( execution);
    							
    							Step updatedStep = new MySQLStep();
    							updatedStep.setName("");
    							TreeItem<Object> newStepItem = new TreeItem<>(updatedStep);
    							Event.fireEvent( stepItem, new TreeItem.TreeModificationEvent<Object>(TreeItem.valueChangedEvent(), stepItem, newStepItem));
    						}
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
