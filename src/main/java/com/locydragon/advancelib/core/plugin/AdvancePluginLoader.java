package com.locydragon.advancelib.core.plugin;

import com.locydragon.advancelib.api.AdvancePlugin;
import com.locydragon.advancelib.core.Advance;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class AdvancePluginLoader extends URLClassLoader {
	public AdvancePlugin instance;
	static {
		try {
			Method method = ClassLoader.class.getDeclaredMethod("registerAsParallelCapable", new Class[0]);
			if (method != null) {
				boolean oldAccessible = method.isAccessible();
				method.setAccessible(true);
				try {
					method.invoke(null, new Object[0]);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
				method.setAccessible(oldAccessible);
			}
		} catch (NoSuchMethodException localNoSuchMethodException) {
			Advance.showMessage("Cannot set class loader.");
		}
	}

	public AdvancePluginLoader(File file, ClassLoader parent) throws MalformedURLException {
		super(new URL[] { file.toURI().toURL() }, parent);
		try {
			JarFile jarFile = new JarFile(file);
			Enumeration enu = jarFile.entries();
			while (enu.hasMoreElements()) {
				JarEntry entry = (JarEntry)enu.nextElement();
				String name = entry.getName();
				if (name != null && name.endsWith(".class")) {
					String realClass = name.replace("/", ".").replace(".class", "");
					try {
						Class<?> classTarget = Class.forName(realClass, true, this);
						if (classTarget.getSuperclass().equals(AdvancePlugin.class) ||
								classTarget.getSuperclass() == AdvancePlugin.class) {
							try {
								this.instance = (AdvancePlugin) classTarget.newInstance();
								this.instance.onLoad();
								this.instance.pluginInfo.jarFile = file;
							} catch (InstantiationException | IllegalAccessException e) {
								e.printStackTrace();
							}
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
			} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
