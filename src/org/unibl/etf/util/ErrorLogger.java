package org.unibl.etf.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ErrorLogger {

	private Logger logger;

	public ErrorLogger() {
		logger = Logger.getLogger("");
		removeHandlers();
		configure();
	}

	private void configure() {
		try {
			String logsFolder = "logs";
			Files.createDirectories(Paths.get(logsFolder));
			FileHandler fileHandler = new FileHandler(logsFolder + File.separator + getCurrentTimeString() + ".log");
			logger.addHandler(fileHandler);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	private void removeHandlers(){
		Handler[] handlers = logger.getHandlers();
		for(Handler h : handlers){
			logger.removeHandler(h);
		}
	}

	private String getCurrentTimeString() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		return dateFormat.format(new Date());
	}

	public void log(Exception exception) {
		logger.log(Level.SEVERE, "", exception);
	}

}
