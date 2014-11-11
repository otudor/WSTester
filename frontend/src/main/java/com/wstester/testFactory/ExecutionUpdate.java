package com.wstester.testFactory;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.wstester.elements.Dialog;
import com.wstester.main.MainLauncher;
import com.wstester.model.Response;
import com.wstester.model.Step;
import com.wstester.model.Execution;
import com.wstester.model.TestCase;
import com.wstester.model.TestSuite;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.ITestRunner;
import com.wstester.util.MainConstants;
import com.wstester.util.TestProjectService;

import javafx.fxml.FXMLLoader;

public class ExecutionUpdate {

	public void updateRunStatus() {
		
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(new UpdateStatusThread());
		executor.shutdown();
    }
}

class UpdateStatusThread implements Runnable{

	@Override
	public void run() {
		
		TestProjectService testProjectService = new TestProjectService();
		List<TestSuite> testSuiteList = testProjectService.getTestSuites();
		
    	for (TestSuite testSuite : testSuiteList) {
    		
    		if (testSuite.getTestCaseList() != null) {
    			for (TestCase testCase : testSuite.getTestCaseList()) {
    			
    				if (testCase.getStepList() != null) {
    					for (Step step : testCase.getStepList()) {

							ITestRunner testRunner = null;
							try {
								testRunner = ServiceLocator.getInstance().lookup(ITestRunner.class);
							} catch (Exception e) {
								e.printStackTrace();
								Dialog.errorDialog("The test couldn't be run. Please try again!", MainLauncher.stage);
							}
							
							Response response = testRunner.getResponse(step.getID(),25000L);

							Execution execution = new Execution();
							Date date = new Date();
							execution.setRunDate(date);
							execution.setResponse(response);
							step.addExecution(execution);

							testProjectService.setStepByUID(step, step.getID());
							
							// update the tree view from the TestMachineController
							FXMLLoader loader = new FXMLLoader(getClass().getResource(MainConstants.TEST_MACHINE.toString()));
							try {
								loader.load();
							} catch (IOException e) {
								Dialog.errorDialog("Couldn't update the tree view. Please do a manual click!", MainLauncher.stage);
								e.printStackTrace();
							}
							TestMachineController testMachineController = loader.<TestMachineController>getController();
							testMachineController.updateTree();
						}
    	    		}
    			}
    		}
    	}
	}
}
