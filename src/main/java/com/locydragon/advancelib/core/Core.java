package com.locydragon.advancelib.core;

import com.locydragon.advancelib.api.inject.InjectUnit;
import com.locydragon.advancelib.core.plugin.AdvancePluginLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class Core {
	public static List<AdvancePluginLoader> loaderList = new ArrayList<>();
	public static List<InjectUnit> units = new ArrayList<>();
}
