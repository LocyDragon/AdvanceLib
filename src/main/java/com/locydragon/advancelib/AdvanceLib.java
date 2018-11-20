package com.locydragon.advancelib;

import com.locydragon.advancelib.api.exception.PluginInitializeException;
import com.locydragon.advancelib.core.Advance;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.Arrays;

/**
 * @author LocyDragon
 */
public class AdvanceLib {
	public static File pluginFolder = new File(".//AdvancePlugins//");
	public static void premain(String agentArgs, Instrumentation inst) {
		Advance.getLogger().info("You are now using AdvanceLib plugin!");
		File targetFile = new File(".//AdvancePlugins//Default.exe");
		if (!targetFile.exists()) {
			try {
				if (targetFile.getParentFile().mkdirs() && targetFile.createNewFile()) {
					Advance.getLogger().info("Already load folder file!");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Arrays.stream(pluginFolder.listFiles()).filter(file -> file.getAbsolutePath().endsWith(".jar")).forEach(file -> {
			try {
				Advance.loadPlugin(file);
			} catch (PluginInitializeException | IOException e) {
				e.printStackTrace();
			}
		});
	}
}
