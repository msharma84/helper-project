package com;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.json.simple.JSONObject;

public class CustomLogger {
	
	private static CustomLogger instance = null;
	private static final Logger logger = LogManager.getLogger(CustomLogger.class);	
	private final static CustomLogger log = CustomLogger.getLogger();
	
	private CustomLogger() {
		super();
	}
	
	public static CustomLogger getLogger(){	 
		if(instance==null){   
			instance = new CustomLogger();
			Configurator.setAllLevels(logger.getName(), org.apache.logging.log4j.Level.ALL);
		}	
		return instance;
	}
	
	public void info(String msg) {
		logger.info(msg);

	}
	
	public void info(JSONObject obj, String msg) {
		// Add your key/value 
		logger.info("["+obj+"]" + msg);
	}
}
