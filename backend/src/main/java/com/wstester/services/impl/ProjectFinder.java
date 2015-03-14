package com.wstester.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.wstester.log.CustomLogger;
import com.wstester.model.Assert;
import com.wstester.model.Environment;
import com.wstester.model.Server;
import com.wstester.model.Service;
import com.wstester.model.Step;
import com.wstester.model.TestCase;
import com.wstester.model.TestProject;
import com.wstester.model.TestSuite;
import com.wstester.model.Variable;
import com.wstester.services.definition.IProjectFinder;

public class ProjectFinder implements IProjectFinder {

	private CustomLogger log = new CustomLogger(ProjectFinder.class);
	private TestProject testProject;
	
	@Override
	public void setProject(TestProject testProject) {
		this.testProject = testProject;
	}


	@Override
	public TestProject getTestProject() {
		return this.testProject;
	}
	
	@Override
	public Environment getEnvironmentById(String id) {
		
		log.info("Searching for Environment with id: " + id);
		if(id == null) {
			return null;
		}
		else {
			List<Environment> environmentList = testProject.getEnvironmentList();
			List<Environment> filtered = environmentList.parallelStream().filter(environment -> environment.getId().equals(id)).collect(Collectors.toList());
			filtered.forEach(environment -> log.info("Found: " + environment.detailedToString()));
			if(filtered.size() == 1) {
				return filtered.get(0);
			}
		}
		log.info("Environment not found with id: " + id);
		return null;
	}

	@Override
	public Server getServerById(String id) {
		
		log.info("Searching for Server with id: " + id);
		if(id == null) {
			return null;
		}
		else {
			List<Environment> environmentList = testProject.getEnvironmentList();
			for(Environment environment : environmentList) {
				
				List<Server> serverList = environment.getServers();
				List<Server> filtered = serverList.parallelStream().filter(server -> server.getId().equals(id)).collect(Collectors.toList());
				filtered.forEach(server -> log.info("Found: " + server.detailedToString()));
				if(filtered.size() == 1) {
					return filtered.get(0);
				}
			}
			log.info("Server not found with id: " + id);
			return null;
		}
	}

	@Override
	public Service getServiceById(String id) {
		
		log.info("Searching for Service with id: " + id);
		if(id == null) {
			return null;
		}
		else {
			List<Environment> environmentList = testProject.getEnvironmentList();
			for(Environment environment : environmentList) {
				
				List<Server> serverList = environment.getServers();
				for(Server server : serverList) {
					
					List<Service> serviceList = server.getServices();
					List<Service> filtered = serviceList.parallelStream().filter(service -> service.getId().equals(id)).collect(Collectors.toList());
					filtered.forEach(service -> log.info("Found: " + service.detailedToString()));
					if(filtered.size() == 1) {
						return filtered.get(0);
					}
				}
			}
			log.info("Service not found with id: " + id);
			return null;
		}
	}

	@Override
	public TestSuite getTestSuiteById(String id) {
		
		log.info("Searching for TestSuite with id: " + id);
		if(id == null) {
			return null;
		}
		else {
			List<TestSuite> testSuiteList = testProject.getTestSuiteList();
			List<TestSuite> filtered = testSuiteList.parallelStream().filter(testSuite -> testSuite.getId().equals(id)).collect(Collectors.toList());
			filtered.forEach(testSuite -> log.info("Found: " + testSuite.detailedToString()));
			if(filtered.size() == 1) {
				return filtered.get(0);
			}
			
			log.info("TestSuite not found with id: " + id);
			return null;
		}
	}
	
	@Override
	public TestSuite getTestSuiteByStepId(String id) {
		
		log.info("Searching for TestSuite for stepId: " + id);
		if(id == null) {
			return null;
		} else {
			List<TestSuite> testSuiteList = testProject.getTestSuiteList();
			for(TestSuite testSuite : testSuiteList) {
				
				List<TestCase> testCaseList = testSuite.getTestCaseList();
				for(TestCase testCase : testCaseList) {
					
					List<Step> stepList = testCase.getStepList();
					List<Step> filtered = stepList.parallelStream().filter(step -> step.getId().equals(id)).collect(Collectors.toList());
					filtered.forEach(step -> log.info("Found: " + step.detailedToString()));
					if(filtered.size() == 1) {
						return testSuite;
					}
				}
			}
			log.info("TestSuite not found for stepId: " + id);
			return null;
		}
	}

	@Override
	public TestCase getTestCaseById(String id) {

		log.info("Searching for TestCase with id: " + id);
		if(id == null) {
			return null;
		} else {
			List<TestSuite> testSuiteList = testProject.getTestSuiteList();
			for(TestSuite testSuite : testSuiteList) {
				
				List<TestCase> testCaseList = testSuite.getTestCaseList();
				List<TestCase> filtered = testCaseList.parallelStream().filter(testCase -> testCase.getId().equals(id)).collect(Collectors.toList());
				filtered.forEach(testCase -> log.info("Found: " + testCase.detailedToString()));
				if(filtered.size() == 1) {
					return filtered.get(0);
				}
			}
		}
		log.info("TestCase not found with id: " + id);
		return null;
	}
	
	@Override
	public Step getStepById(String id) {
		
		log.info("Searching for Step with id: " + id);
		if(id == null) {
			return null;
		}
		else {
			List<TestSuite> testSuiteList = testProject.getTestSuiteList();
			for(TestSuite testSuite : testSuiteList) {
				
				List<TestCase> testCaseList = testSuite.getTestCaseList();
				for(TestCase testCase : testCaseList) {
					
					List<Step> stepList = testCase.getStepList();
					List<Step> filtered = stepList.parallelStream().filter(step -> step.getId().equals(id)).collect(Collectors.toList());
					filtered.forEach(step -> log.info("Found: " + step.detailedToString()));
					if(filtered.size() == 1) {
						return filtered.get(0);
					}
				}
			}
			log.info("Step not found with id: " + id);
			return null;
		}
	}


	@Override
	public void setTestSuiteById(TestSuite testSuite, String id) {
		
		log.info("Set TestSuite for id: " + id + "; " + testSuite.detailedToString());
		List<TestSuite> testSuiteList = testProject.getTestSuiteList();
		testSuiteList.forEach(loopTestSuite -> {
			
			if(loopTestSuite.getId().equals(id)) {
				loopTestSuite.setName(testSuite.getName());
				loopTestSuite.setEnvironmentId(testSuite.getEnvironmentId());
			}
		});
	}
	
	@Override
	public void setTestCaseById(TestCase testCase, String id) {

		log.info("Set TestCase for id: " + id + "; " + testCase.detailedToString());
		List<TestSuite> testSuiteList = testProject.getTestSuiteList();
		testSuiteList.forEach(testSuite -> {
			
			List<TestCase> testCaseList = testSuite.getTestCaseList();
			testCaseList.forEach(loopTestCase -> {
				
				if(loopTestCase.getId().equals(id))
					loopTestCase.setName(testCase.getName());								
				});
		});
	}

	@Override
	public void addTestSuite(TestSuite testSuite) {
		
		log.info("Adding TestSuite: " + testSuite);
		testProject.getTestSuiteList().add(testSuite);
	}

	@Override
	public void addTestCaseForSuite(TestCase testCase, String suiteId) {
		
		log.info("Adding TestCase for suiteId: " + suiteId + "; " + testCase);
		List<TestSuite> testSuiteList = testProject.getTestSuiteList();
		testSuiteList.forEach(testSuite -> {
			
			if(testSuite.getId().equals(suiteId))
				testSuite.addTestCase(testCase);
		});
	}

	@Override
	public void addStepForTestCase(Step step, String testCaseId) {
		
		log.info("Adding Step for testCaseId: " + testCaseId + "; " + step);
		List<TestSuite> testSuiteList = testProject.getTestSuiteList();
		testSuiteList.forEach(testSuite -> {
			
			List<TestCase> testCaseList = testSuite.getTestCaseList();
			testCaseList.forEach(testCase -> {
				
				if(testCase.getId().equals(testCaseId)) {
					testCase.getStepList().add(step);
				}
			});
		});
	}

	@Override
	public void addVariableForStep(String stepId, String variableId) {
		
		log.info(stepId, "Adding variable: " + variableId);
		getStepById(stepId).addVariable(variableId);
	}

	@Override
	public void addAssertForStep(String stepId, Assert asert) {
		
		log.info(stepId, "Adding assert: " + asert);
		getStepById(stepId).addAssert(asert);
	}
	
	@Override
	public void removeTestSuiteById(String id) {

		log.info("Removing TestSuite: " + id);
		testProject.getTestSuiteList().removeIf(testSuite -> testSuite.getId().equals(id));
	}

	@Override
	public void removeTestCaseById(String id) {
		
		log.info("Removing TestCase: " + id);
		List<TestSuite> testSuiteList = testProject.getTestSuiteList();
		testSuiteList.forEach(testSuite -> {
			
			List<TestCase> testCaseList = testSuite.getTestCaseList();
			testCaseList.removeIf(testCase -> testCase.getId().equals(id));
		});
	}
	
	@Override
	public void removeStepById(String id) {

		log.info("Removing Step: " + id);
		List<TestSuite> testSuiteList = testProject.getTestSuiteList();
		testSuiteList.forEach(testSuite -> {
			
			List<TestCase> testCaseList = testSuite.getTestCaseList();
			testCaseList.forEach(testCase -> {
			
				List<Step> steplist = testCase.getStepList();
				steplist.removeIf(step -> step.getId().equals(id));
			});
		});
	}

	@Override
	public void removeVariableFromStep(String stepId, String variableId) {
		
		log.info(stepId, "Removing from step the variable: " + variableId);
		getStepById(stepId).getVariableList().remove(variableId);
	}
	
	@Override
	public void removeAssertFromStep(String stepId, Assert asert) {

		log.info(stepId, "Removing from step the assert: " + asert.getId());
		getStepById(stepId).getAssertList().remove(asert);
	}
	
	@Override
	public Variable getVariableById(String id) {
		
		log.info("Searching for Variable with id: " + id);
		if(id == null) {
			return null;
		}
		else {
			List<Variable> variableList = testProject.getVariableList();
			List<Variable> filtered = variableList.parallelStream().filter(variable -> variable.getId().equals(id)).collect(Collectors.toList());
			filtered.forEach(variable -> log.info("Found:" + variable.toString()));
			if(filtered.size() == 1) {
				return filtered.get(0);
			}
		}
		return null;
	}

	@Override
	public Variable getVariableByName(String name) {
		
		log.info("Searching for Variable with name: " + name);
		if(name == null) {
			return null;
		}
		else {
			List<Variable> variableList = testProject.getVariableList();
			List<Variable> filtered = variableList.parallelStream().filter(variable -> variable.getName().equals(name)).collect(Collectors.toList());
			filtered.forEach(variable -> log.info("Found:" + variable.toString()));
			if(filtered.size() == 1) {
				return filtered.get(0);
			}
		}
		return null;
	}
}