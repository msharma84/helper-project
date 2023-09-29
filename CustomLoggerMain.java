package com;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.json.simple.JSONObject;

public class CustomLoggerMain {
	
	private static final Logger logger = LogManager.getLogger(CustomLoggerMain.class);	
	
	public static void main(String[] args) {
		
		CustomLogger log = CustomLogger.getLogger();
		JSONObject obj = new JSONObject();
        obj.put("test", "logger-test");
		log.info(obj, "Msg with extra key pair");
		
		Configurator.setAllLevels(logger.getName(), org.apache.logging.log4j.Level.ALL);
		logger.info("Normal Logger");
	}
}
