package com.wstester.services.impl;


import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.wstester.log.CustomLogger;
import com.wstester.model.TestProject;
import com.wstester.services.definition.ITestProjectActions;

public class TestProjectActions implements ITestProjectActions{

	private CustomLogger log = new CustomLogger(TestProjectActions.class);
	
	@Override
	public void save(String path, TestProject testProject) throws IOException {

		log.info("Saving " + testProject);
		try {

			JAXBContext context = JAXBContext.newInstance(TestProject.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			File file = new File(path);
			m.marshal(testProject, file);
		} catch (JAXBException e) {

			e.printStackTrace();
			throw new IOException("Test Plan couldn't be saved! " + e.getMessage());
		}
		log.info("Saved to " + path);
	}

	@Override
	public TestProject open(String path) throws IOException {

		log.info("Loading from " + path);
		TestProject testProject = null;
		
		try {
			JAXBContext jc = JAXBContext.newInstance(TestProject.class);
			Unmarshaller u = jc.createUnmarshaller();

			File file = new File(path);
			testProject = (TestProject) u.unmarshal(file);
		} catch (JAXBException e) {
			
			e.printStackTrace();
			throw new IOException("Test Plan couldn't be opened! " + e.getMessage());
		}
		
		log.info("Loaded " + testProject);
		return testProject;
	}
}
