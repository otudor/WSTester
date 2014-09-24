package com.wstester.services.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

import com.wstester.log.CustomLogger;
import com.wstester.services.definition.IService;

public final class ServiceLocator {

	private static enum Classes {
		ICAMEL(com.wstester.services.definition.ICamelContextManager.class, com.wstester.services.impl.CamelContextManager.class),
		ITestRunner(com.wstester.services.definition.ITestRunner.class, com.wstester.services.impl.TestRunner.class);
		
		private Class<?> classPath;
		private Class<?> interfaceName;
		
		Classes(Class<?> interf, Class<?> path) {
			this.classPath = path;
			this.interfaceName = interf;
		};
		
		public Class<?> getClassPath() {
			return classPath;
		}
		
		public Class<?> getInterfaceName() {
			return interfaceName;
		}
	}
	
	private CustomLogger LOGGER = new CustomLogger(ServiceLocator.class);

	private static Map<Class<? extends IService>, Object> cache = null;
	
	private Map<String, Object> implementations = null;
	
	private static ServiceLocator instance = null;
	
	private ServiceLocator() {
		implementations = new HashMap<>();
		try {
			Object obj = null;
			for(Classes clazz : Classes.values()) {
				Constructor<?>[] declaredConstructors = clazz.getClassPath().getDeclaredConstructors();
				for(Constructor<?> ctor : declaredConstructors) {
					Parameter[] params = ctor.getParameters();
					if(params.length == 0) {
						obj = ctor.newInstance(null);
					}
				}
				implementations.put(clazz.getInterfaceName().getSimpleName(), obj);
			}
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| SecurityException e) {
			LOGGER.error(this.getClass().getName() + ": Exception occured while instantiating service class. " + e);
		}
	}
	
	public static ServiceLocator getInstance() {
		if(instance == null) {
			synchronized(ServiceLocator.class) {
				instance = new ServiceLocator();
				cache = new HashMap<>();
			}
		}
		return instance;
	}
	
	public synchronized <T> T lookup(Class<? extends IService> clazz) {
		Object service = null;
		if(clazz.isAnnotationPresent(Stateful.class)) {
			if(!cache.containsKey(clazz)) {
					cache.put(clazz, implementations.get(clazz.getSimpleName()));
			}
			service = cache.get(clazz);
		}
		else if(clazz.isAnnotationPresent(Stateless.class)) {
			implementations.get(clazz.getSimpleName());
		}
		else {
			//nothing happens
		}
		if(service != null) {
			return (T) service;
		}
		
		return null;
	}
	
}
