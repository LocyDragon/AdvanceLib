package com.locydragon.advancelib.core;

import com.locydragon.advancelib.api.exception.PluginInitializeException;
import com.locydragon.advancelib.core.plugin.AdvancePluginLoader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

public class Advance {
	private static final AdvanceLogger advanceLogger = new AdvanceLogger();
	public static void loadPlugin(File jarFile) throws PluginInitializeException, IOException {
		for (AdvancePluginLoader loader : Core.loaderList) {
			if (loader.instance.pluginInfo.jarFile.getAbsolutePath()
					.equals(jarFile.getAbsolutePath())) {
				throw new PluginInitializeException("Plugin already inited!");
			}
		}
		if (!jarFile.getAbsolutePath().endsWith(".jar")) {
			throw new IOException("Not a plugin file: "+jarFile.getAbsolutePath());
		}
		try {
			AdvancePluginLoader loader = new AdvancePluginLoader(jarFile, Advance.class.getClassLoader());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public static AdvanceLogger getLogger() {
		return advanceLogger;
	}

	public static boolean showMessage(List<String> message) {
		message.forEach(x -> advanceLogger.info(x));
		return true;
	}

	public static boolean showMessage(String... message) {
		Arrays.stream(message).forEach(x -> advanceLogger.info(x));
		return true;
	}
}
