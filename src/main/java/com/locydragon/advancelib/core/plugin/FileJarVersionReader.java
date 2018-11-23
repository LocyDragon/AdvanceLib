package com.locydragon.advancelib.core.plugin;

import org.bukkit.Bukkit;

import java.io.File;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FileJarVersionReader {
	public static String version;
	public static String getVersion() {
		if (version != null) {
			return version;
		}
		try {
			String pathLocation =
					java.net.URLDecoder.decode(Bukkit.class.getProtectionDomain().getCodeSource().getLocation().getFile(), "UTF-8");
			File jarFile = new File(pathLocation);
			JarFile jar = new JarFile(jarFile);
			Enumeration<JarEntry> entryEnumeration = jar.entries();
			while (entryEnumeration.hasMoreElements()) {
				JarEntry entry = entryEnumeration.nextElement();
				String classPath = entry.getName().replace("/", ".");
				try {
					if (classPath.startsWith("net.minecraft.server.")) {
						version = classPath.replace(".", ",").split(",")[3].trim();
						break;
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					continue;
				}
			}
			return version;
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
