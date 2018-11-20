package com.locydragon.advancelib.api.exception;

import java.lang.reflect.InvocationTargetException;

public class Validate {
	public static void notNull(Object obj,String reason, Class<?> exceptionClass) {
		if (obj == null) {
			try {
				try {
					exceptionClass.getConstructor(String.class).newInstance(reason);
				} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
	}
}
