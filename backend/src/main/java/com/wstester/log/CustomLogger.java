package com.wstester.log;

import org.apache.log4j.Logger;

public class CustomLogger extends Logger {

	private Logger logger;

	public CustomLogger(Class<?> clazz){
		
		super(clazz.getName());
		logger = Logger.getLogger(name);
	}

	@Override
	public void debug(Object message){
		logger.debug(message);
	}
	
	public void debug(String stepId, String logMessage){
		logger.debug("[" + stepId + "] " + logMessage);
	}
	
	@Override
	public void error(Object message){
		logger.error(message);
	}

	public void error(String stepId, String logMessage){
		logger.error("[" + stepId + "] " + logMessage);
	}

	@Override
	public void info(Object message){
		logger.info(message);
	}
	
	public void info(String stepId, String logMessage){
		logger.info("[" + stepId + "] " + logMessage);
	}
	
	@Override
	public void warn(Object message){
		logger.warn(message);
	}
	
	public void warn(String stepId, String logMessage){
		logger.warn("[" + stepId + "] " + logMessage);
	}
}