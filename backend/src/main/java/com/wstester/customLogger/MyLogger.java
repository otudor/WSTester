package com.wstester.customLogger;

import org.apache.log4j.Logger;

public class MyLogger extends Logger {

	static Logger logger;

	public MyLogger(String name) {
			super (name);
			logger = Logger.getLogger(name);

	}

	public void debug(Object message) { 
		logger.debug("[" + message + "]");
	}
	
	public void debug(Object message, Object message1) { 
		logger.debug("[" + message + "] " + message1);
	}

	public void error(Object message) { 
		logger.error("[" + message + "]");
	}
	
	public void error(Object message, Object message1) { 
		logger.error("[" + message + "] " + message1);
		}

	public void info(Object message, Object message1) { 
		logger.info("[" + message + "] " + message1);
	}
	
	public void info(Object message) { 
		logger.info("[" + message + "] ");
	}

	public void warn(Object message) { 
		logger.warn("[" + message + "] ");
	}
	public void warn(Object message, Object message1) { 
		logger.warn("[" + message + "] " + message1);
	}
}