package Serialization;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.wstester.ui.Assert;
import com.wstester.ui.Asset;
import com.wstester.ui.Environment;
import com.wstester.ui.MongoStep;
import com.wstester.ui.Server;
import com.wstester.ui.Service;
import com.wstester.ui.Step;
import com.wstester.ui.TestCase;
import com.wstester.ui.TestPlan;
import com.wstester.ui.TestSuite;

public class SerTest {


	public static void main(String[] args) throws Exception {

		JAXBContext context = JAXBContext.newInstance(TestPlan.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		
		//***add assets & test suite to the Test Plan
		TestPlan testPlan = new TestPlan();
		List<Asset> assets = new ArrayList<Asset>();
		testPlan.setAssets(assets);

		List<TestSuite> testSuiteList = new ArrayList<TestSuite>();
		TestSuite testSuite = new TestSuite();
		testSuiteList.add(testSuite);
		testPlan.setTestSuite(testSuiteList);

		//***add environment & test cases to the TestSuite
		Environment env = new Environment();
		testSuite.setEnv(env);

		List<TestCase> tc = new ArrayList<TestCase>();
		TestCase teest = new TestCase();
		List<Step> stepList = new ArrayList<Step>();
		teest.setStep(stepList);
		tc.add(teest);
		testSuite.setTc(tc);

		//***add server, assets, services & assets to the Step (Mongo Step)
		Step stepMongo = new MongoStep();
		List<Assert> assertsList = new ArrayList<Assert>();
		stepMongo.setAsserts(assertsList);
		Assert e = new Assert();
		assertsList.add(e );
		
		Asset assetsm = new Asset();
		stepMongo.setAssets(assetsm);
		
		Server server = new Server();
		stepMongo.setServer(server);
		
		Service services = new Service();
		stepMongo.setServices(services);
		
		stepList.add(stepMongo);

		m.marshal(testPlan, System.out);

	}

}
