package com.locydragon.advancelib.api;

import java.io.File;

/**
 * @author LocyDragon
 */
public class PluginInfo {
	public String pluginName;
	public String[] author;
	public String version;
	public File jarFile;
	public PluginInfo(String pluginName, String version, String... author) {
		this.pluginName = pluginName;
		this.version = version;
		this.author = author;
	}

	public String getPluginName() {
		return pluginName;
	}

	public File getJarFile() {
		return jarFile;
	}

	public String getVersion() {
		return version;
	}

	public String[] getAuthor() {
		return author;
	}
}
