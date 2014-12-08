package com.wstester.util;

public enum MainConstants {
	
	TEST_PROJECT,
	TEST_MACHINE("/fxml/TestFactory/TestMachine.fxml"),
	RESPONSE("/fxml/TestFactory/Response.fxml"),
	TEST_SUITE("/fxml/TestFactory/TestSuite.fxml"),
	TEST_SUITE_ICON("/images/treeIcon_TestSuite.png"),
	TEST_CASE("/fxml/TestFactory/TestCase.fxml"),
	TEST_CASE_ICON("/images/treeIcon_TestCase.png"),
	MONGO_STEP("/fxml/TestFactory/MongoStep.fxml"),
	MONGO_STEP_ICON("/images/treeIcon_TestStep.png"),
	REST_STEP("/fxml/TestFactory/RestStep.fxml"),
	REST_STEP_ICON("/images/treeIcon_TestStep.png"),
	MYSQL_STEP("/fxml/TestFactory/MySQLStep.fxml"),
	MYSQL_STEP_ICON("/images/treeIcon_TestStep.png"),
	SOAP_STEP("/fxml/TestFactory/SoapStep.fxml"),
	SOAP_STEP_ICON("/images/treeIcon_TestStep.png"),
	SOAP_DEFINITION("/fxml/SOAP/SOAPDefinition.fxml"),
	SOAP_WINDOW_ICON("/images/DropBox-Folder.png"),
	STEP_PASSED_ICON("/images/treeIcon_step_passed.png"),
	STEP_ERROR_ICON("/images/treeIcon_step_error.png"),
	SAVE_ICON("/images/save.png"),
	HOMEPAGE_ICON("/images/home.png"),
	START_ICON("/images/VLC.png"),
	ASSETS_ICON("/images/Applications-Folder.png"), 
	ENVIRONMENTS_ICON("/images/Globe-Folder.png"),
	SOAP_ICON("/images/DropBox-Folder.png"),
	REST_ICON("/images/Downloads-Folder.png"),
	TEST_FACTORY_ICON("/images/Smart-Folder.png"),
	VARIABLES_ICON("/images/Documents-Folder.png"),
	ASSET_MENU_ICON("/images/asset2.png"),
	APPLICATION_STYLE_CSS("/styles/application.css")
	;
	
	private String value;
	
	private MainConstants() {}
	
	private MainConstants(String value) {
		this.value = value;
	}
	
	@Override
	public String toString(){
		return this.value;
	}
}
