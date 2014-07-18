package com.wstester.actions;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.wstester.model.TestProject;

public class TestPlanActions {

	public void save(String path, TestProject testPlan) throws IOException {

		try {

			JAXBContext context = JAXBContext.newInstance(TestProject.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			File file = new File(path);
			m.marshal(testPlan, file);
		} catch (JAXBException e) {

			e.printStackTrace();
			throw new IOException("Test Plan couldn't be saved! " + e.getMessage());
		}
	}

	public TestProject open(String path) throws IOException {

		TestProject testPlan = null;
		
		try {
			JAXBContext jc = JAXBContext.newInstance(TestProject.class);
			Unmarshaller u = jc.createUnmarshaller();

			File file = new File(path);
			testPlan = (TestProject) u.unmarshal(file);
		} catch (JAXBException e) {
			
			e.printStackTrace();
			throw new IOException("Test Plan couldn't be opened! " + e.getMessage());
		}
		
		return testPlan;
	}
}
