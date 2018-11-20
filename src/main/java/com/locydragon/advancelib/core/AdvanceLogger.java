package com.locydragon.advancelib.core;

import java.util.Arrays;
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
			System.out.println(message);
		} else {
			System.out.println("["+this.prefix+"]"+message);
		}
	}

	public void info(List<String> message) {
		message.forEach(x -> info(message));
	}

	public void info(String...message) {
		info(Arrays.asList(message));
	}
}
