package com.wstester.model;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.codehaus.jettison.json.JSONException;
import org.junit.AfterClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.wstester.services.impl.TestProjectActions;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SerializationTest {

	private static String filePath = "src/test/resources/Output.xml";
	private static File file = new File(filePath);
	private static TestProject testPlanBefore;
	private static TestProject testPlanAfter;
	
	@Test
	public void firstTestToXML() throws IOException, JSONException{
		
		// construct test plan
		testPlanBefore = TestUtils.getTestPlan();

		TestProjectActions actions = new TestProjectActions();
		actions.save(filePath, testPlanBefore);
	}
	
	@Test()
	public void secondTestFromXML() throws IOException {

		TestProjectActions actions = new TestProjectActions();
        testPlanAfter = actions.open(filePath);
        
        assertEquals(testPlanBefore, testPlanAfter);
	}
	
	@AfterClass
	public static void delete(){
		
		if(file.delete()){
			System.out.println(file.getName() + " is deleted!");
		}else{
			System.out.println("Delete operation is failed.");
		}
	}

}
