package com.wstester.actions;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.wstester.model.TestPlan;

public class TestPlanActions {

	public void save(String path, TestPlan testPlan) throws IOException {

		try {

			JAXBContext context = JAXBContext.newInstance(TestPlan.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			File file = new File(path);
			m.marshal(testPlan, file);
		} catch (JAXBException e) {

			e.printStackTrace();
			throw new IOException("Test Plan couldn't be saved! " + e.getMessage());
		}
	}

	public TestPlan open(String path) throws IOException {

		TestPlan testPlan = null;
		
		try {
			JAXBContext jc = JAXBContext.newInstance(TestPlan.class);
			Unmarshaller u = jc.createUnmarshaller();

			File file = new File(path);
			testPlan = (TestPlan) u.unmarshal(file);
		} catch (JAXBException e) {
			
			e.printStackTrace();
			throw new IOException("Test Plan couldn't be opened! " + e.getMessage());
		}
		
		return testPlan;
	}
}
