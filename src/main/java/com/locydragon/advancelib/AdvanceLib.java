package com.locydragon.advancelib;

import com.locydragon.advancelib.api.exception.PluginInitializeException;
import com.locydragon.advancelib.api.inject.InjectJob;
import com.locydragon.advancelib.api.inject.InjectJobEnum;
import com.locydragon.advancelib.api.inject.InjectUnit;
import com.locydragon.advancelib.core.Advance;
import com.locydragon.advancelib.core.Core;
import com.locydragon.advancelib.core.plugin.FileJarVersionReader;
import javassist.*;

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
		Advance.getLogger().info("Core version: "+ FileJarVersionReader.getVersion());
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
			} catch (Throwable e) {
				e.printStackTrace();
			}
		});
		inst.addTransformer((loader, className, classBeingRedefined, protectionDomain, classfileBuffer) -> {
			String realName = className.replace("/", ".");
			CtClass targetClass = null;
			try {
				targetClass = ClassPool.getDefault().get(realName);
			} catch (NotFoundException e) {}
			if (targetClass != null) {
				for (InjectUnit unit : Core.units) {
					if (unit.getClassPath().equals(realName)) {
						ClassPool pool = ClassPool.getDefault();
						for (String imports : unit.getImports()) {
							pool.importPackage(imports);
						}
						try {
							targetClass = pool.get(realName);
						} catch (NotFoundException e) {
							e.printStackTrace();
						}
						for (InjectJob job : unit.getJobs()) {
							if (job.jobType == InjectJobEnum.INSERT_BEFORE) {
								try {
									CtMethod method = targetClass.getDeclaredMethod(job.jobArgs[0], job.classArgs);
									targetClass.removeMethod(method);
									try {
										method.insertBefore(job.jobArgs[1]);
										targetClass.addMethod(method);
									} catch (CannotCompileException e) {
										e.printStackTrace();
									}
								} catch (NotFoundException e) {
									e.printStackTrace();
								}
							} else if (job.jobType == InjectJobEnum.INSERT_AFTER) {
								try {
									CtMethod method = targetClass.getDeclaredMethod(job.jobArgs[0], job.classArgs);
									targetClass.removeMethod(method);
									try {
										method.insertAfter(job.jobArgs[1]);
										targetClass.addMethod(method);
									} catch (CannotCompileException e) {
										e.printStackTrace();
									}
								} catch (NotFoundException e) {
									e.printStackTrace();
								}
							} else if (job.jobType == InjectJobEnum.CHANGE_METHOD_CODE_JOB) {
								try {
									CtMethod method = targetClass.getDeclaredMethod(job.jobArgs[0], job.classArgs);
									targetClass.removeMethod(method);
									try {
										CtMethod newMethod = CtMethod.make(job.jobArgs[1], targetClass);
										targetClass.addMethod(newMethod);
									} catch (CannotCompileException e) {
										e.printStackTrace();
									}
								} catch (NotFoundException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
			try {
				return targetClass.toBytecode();
			} catch (IOException | CannotCompileException e) {
				e.printStackTrace();
			}
			return null;
		});
	}
}
