package com.locydragon.advancelib.api.util;

import com.locydragon.advancelib.core.plugin.FileJarVersionReader;

public class NMSClassFinder {
	public static String getNMSClass(String name) {
		return "net.minecraft.server."+ FileJarVersionReader.getVersion()+"."+name;
	}

	public static String getOBCClass(String name) {
		return "org.bukkit.craftbukkit."+FileJarVersionReader.getVersion()+"."+name;
	}
}
