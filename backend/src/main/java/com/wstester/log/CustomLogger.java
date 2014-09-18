package com.wstester.log;

import org.apache.log4j.Logger;

public class CustomLogger extends Logger {

	private Logger logger;

	public CustomLogger(Class<?> clazz){
		
		super(clazz.getName());
		logger = Logger.getLogger(name);
	}

	public void debug(String stepID, String logMessage){
		logger.debug("[" + stepID + "] " + logMessage);
	}
	
	public void error(String message){
		logger.error(message);
	}

	public void error(String stepID, String logMessage){
		logger.error("[" + stepID + "] " + logMessage);
	}

	public void info(String message){
		logger.info(message);
	}
	
	public void info(String stepID, String logMessage){
		logger.info("[" + stepID + "] " + logMessage);
	}

	public void warn(String stepID, String logMessage){
		logger.warn("[" + stepID + "] " + logMessage);
	}
}