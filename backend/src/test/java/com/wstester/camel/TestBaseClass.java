package com.wstester.camel;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import com.wstester.log.CustomLogger;
import com.wstester.model.TestProject;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.ICamelContextManager;
import com.wstester.services.definition.IProjectFinder;
import com.wstester.services.definition.ITestRunner;

/*
In order to run all the tests the following setup needs to be made on localhost
1) Install Mongo
	a) Go to http://www.mongodb.org/downloads and download the arhived file
	b) Unzip the file in a folder: D:\Tools\mongo
	c) Open a power shell and go to bin folder in the mongo instalation path
	d) Run mongod.exe: ./mongod.exe. If an error occurs saying that /data/db is not set, then create in the mongo folder the two folders : /data/db and rerun
		./mongod.exe  --dbpath D:\Tools\mongo\mongodb-win32-i386-2.6.7\data\db
	e) Open another power shell and go to the same bin folder
	f) Run mongo.exe
	g) Create the DB: use test
	h) Create a collection: db.createCollection("customer")
	i) Insert the following documents: 
		db.customer.insert({id:"1",name:"HAC"})
		db.customer.insert({id:"2",name:"ROFLMAO"})
		db.customer.insert({id:"3",name:"ROFLMAO"})
		db.customer.insert({id:"4",name:"ROFLMAO"})
	j) Create the user for the collection:  db.createUser({user:"appuser", pwd:"apppass", roles: [{role:"readWrite", db:"test"}]})
	
2) Install a MySQL service (I installed xampp from: https://www.apachefriends.org/download.html   Install only apache and mysql)
	a) Start the service
	b) Access a console to MySQL(phpMyAdmin in xampp)
	c) Create a new DB: test
	d) Create a user for the DB: user-appuser pass-apppass server-localhost all privileges on db test
	e) Create a table angajati with 2 columns: id - int, primary key, autoincremented,  detalii - Varchar(255)
	f) Insert the following rows: 
		detalii-popescu
		detalii-ion
*/
public class TestBaseClass {

	protected ITestRunner testRunner;
	private static ICamelContextManager contextManager;
	private CustomLogger log = new CustomLogger(TestBaseClass.class);
	
	@BeforeClass
	public static void before(){
		
		try {
			contextManager = ServiceLocator.getInstance().lookup(ICamelContextManager.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		contextManager.startCamelContext();
	}
	
	@AfterClass
	public static void after() throws InterruptedException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		
		try {
			contextManager = ServiceLocator.getInstance().lookup(ICamelContextManager.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		contextManager.closeCamelContext();
	}

	
	@Rule
	public TestRule watcher = new TestWatcher() {
	   @Override
	protected void starting(Description description){
	        log.info("********************************************************************************");
	        log.info("Testing: " + description.getMethodName() + "(" + description.getClassName() + ")");
	        log.info("********************************************************************************");
	   }
	   
	   @Override
	protected void finished(Description description){
	        log.info("********************************************************************************");
	        log.info("Done testing: " + description.getMethodName() + "(" + description.getClassName() + ")");
	        log.info("********************************************************************************");
	   }
	};
	
	protected void setTestProject(TestProject testProject) throws Exception {
		
		IProjectFinder projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
		projectFinder.setProject(testProject);
	}
}