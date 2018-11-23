package com.locydragon.advancelib.api;

import com.locydragon.advancelib.api.exception.PluginInitializeException;
import com.locydragon.advancelib.api.exception.Validate;
import com.locydragon.advancelib.api.inject.InjectUnit;
import com.locydragon.advancelib.core.Advance;
import com.locydragon.advancelib.core.AdvanceLogger;
import com.locydragon.advancelib.core.Core;
import com.locydragon.advancelib.core.plugin.AdvancePluginLoader;

public abstract class AdvancePlugin {
	public PluginInfo pluginInfo;
	public AdvancePluginLoader pluginLoader;
	private AdvanceLogger advanceLogger = new AdvanceLogger();

	/**
	 * 当插件被加载时调用
	 */
	public abstract void onLoad();
	public void bind(PluginInfo pluginInfo) {
		this.pluginInfo = pluginInfo;
		Validate.notNull(pluginInfo, "You bind a null plugin info!", NullPointerException.class);
		logPluginInfo();
		this.advanceLogger = new AdvanceLogger(pluginInfo.pluginName);
	}

	public void logPluginInfo() {
		Validate.notNull(this.pluginInfo, "Null plugin Info, please bind it!", PluginInitializeException.class);
		Advance.showMessage("[" + pluginInfo.pluginName+"] Loading AdvancePlugin " +
				""+this.pluginInfo.pluginName+" v"+this.pluginInfo.version);
	}

	public AdvanceLogger getLogger() {
		return this.advanceLogger;
	}

	public void uploadUnit(InjectUnit unit) {
		Core.units.add(unit);
	}

	public AdvancePlugin() {}
}
