package com.locydragon.advancelib.api.inject;

import com.locydragon.advancelib.core.plugin.FileJarVersionReader;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import java.util.ArrayList;
import java.util.List;

public class InjectUnit {
	private String fakeName;
	private String realPath;
	private List<String> imports;
	private List<InjectJob> jobList = new ArrayList<>();
	private InjectUnit(String classLocation, String name) {
		this.realPath = classLocation;
		this.fakeName = name;
	}

	public String getFakeName() {
		return this.fakeName;
	}

	public String getClassPath() {
		return this.realPath;
	}

	public void methodInsertBefore(String methodName, String code, Class<?>... inputType) {
		String[] argMagicValue = new String[] {methodName, code};
		InjectJob job = new InjectJob();
		job.jobArgs = argMagicValue;
		job.jobType = InjectJobEnum.INSERT_BEFORE;
		ClassPool pool = ClassPool.getDefault();
		List<CtClass> ctClasses = new ArrayList<>();
		for (Class<?> classTarget : inputType) {
			try {
				ctClasses.add(pool.get(classTarget.getName()));
			} catch (NotFoundException e) {
				continue;
			}
		}
		job.classArgs = ctClasses.toArray(new CtClass[ctClasses.size()]);
		jobList.add(job);
	}

	public void methodInsertAfter(String methodName, String code, Class<?>... inputType) {
		String[] argMagicValue = new String[] {methodName, code};
		InjectJob job = new InjectJob();
		job.jobArgs = argMagicValue;
		job.jobType = InjectJobEnum.INSERT_AFTER;
		ClassPool pool = ClassPool.getDefault();
		List<CtClass> ctClasses = new ArrayList<>();
		for (Class<?> classTarget : inputType) {
			try {
				ctClasses.add(pool.get(classTarget.getName()));
			} catch (NotFoundException e) {
				continue;
			}
		}
		job.classArgs = ctClasses.toArray(new CtClass[ctClasses.size()]);
		jobList.add(job);
	}

	public void methodReplaceBody(String methodName, String code, Class<?>... inputType) {
		String[] argMagicValue = new String[] {methodName, code};
		InjectJob job = new InjectJob();
		job.jobArgs = argMagicValue;
		job.jobType = InjectJobEnum.CHANGE_METHOD_CODE_JOB;
		ClassPool pool = ClassPool.getDefault();
		List<CtClass> ctClasses = new ArrayList<>();
		for (Class<?> classTarget : inputType) {
			try {
				ctClasses.add(pool.get(classTarget.getName()));
			} catch (NotFoundException e) {
				continue;
			}
		}
		job.classArgs = ctClasses.toArray(new CtClass[ctClasses.size()]);
		jobList.add(job);
	}

	public void addImport(String classLocation) {
		this.imports.add(classLocation);
	}

	public List<String> getImports() {
		return this.imports;
	}

	public List<InjectJob> getJobs() {
		return this.jobList;
	}

	public static InjectUnit from(ClassLocationEnum location, String name) {
		if (location == null) {
			throw new NullPointerException("Class Location cannot be null.");
		}
		if (location == ClassLocationEnum.NORMAL_CLASS) {
			return new InjectUnit(name, name);
		} else if (location == ClassLocationEnum.NMS) {
			String coreVersion = FileJarVersionReader.getVersion();
			return new InjectUnit("net.minecraft.server."+coreVersion+"."+name, name);
		} else if (location == ClassLocationEnum.OBC) {
			String coreVersion = FileJarVersionReader.getVersion();
			return new InjectUnit("org.bukkit.craftbukkit."+coreVersion+"."+name, name);
		}
		return null;
	}
}
