package com.locydragon.advancelib.core;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author LocyDragon
 */
public class AdvanceLogger {
	private String prefix = null;
	public AdvanceLogger(String prefix) {
		this.prefix = prefix;
	}

	public AdvanceLogger() {
		this(null);
	}
	public void info(String message) {
		if (prefix == null) {
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			String time = dateFormat.format(date);
			String prefix = "["+time+" INFO]: ";
			System.out.println(prefix+message);
		} else {
			System.out.println("["+this.prefix+"] "+message);
		}
	}

	public void info(List<String> message) {
		message.forEach(x -> info(message));
	}

	public void info(String...message) {
		info(Arrays.asList(message));
	}
}
