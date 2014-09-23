package com.wstester.services.common;

import java.util.HashMap;
import java.util.Map;

import com.wstester.log.CustomLogger;
import com.wstester.services.definition.IService;

public final class ServiceLocator {

	private CustomLogger LOGGER = new CustomLogger(ServiceLocator.class);

	private Map<Class<? extends IService>, Object> cache = null;
	
	private static ServiceLocator instance;
	
	private ServiceLocator() {}
	
	public ServiceLocator getInstance() {
		if(instance == null) {
			synchronized(this) {
				instance = new ServiceLocator();
				cache = new HashMap<>();
			}
		}
		return instance;
	}
	
	public synchronized IService lookup(Class<? extends IService> clazz) {
		Object service = null;
		if(clazz.isAnnotationPresent(Stateful.class)) {
			if(!cache.containsKey(clazz)) {
				if(clazz.isInstance(IService.class)) {
					try {
						cache.put(clazz, clazz.getClass().newInstance());
					} catch (InstantiationException | IllegalAccessException e) {
						LOGGER.error(this.getClass().getSimpleName() + 
								": Service type being looked up does not offer Reflection features implementation.");
						throw new RuntimeException("Following exception is occuring while creating Service instance: " + e);
					}
				}
				else {
					throw new RuntimeException(this.getClass().getSimpleName() + ": Please provide an IService typo service to lookup.");
				}
			}
			service = cache.get(clazz);
		}
		else if(clazz.isAnnotationPresent(Stateless.class)) {
			try {
				service = clazz.getClass().newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				LOGGER.error(this.getClass().getSimpleName() + 
						": Service type being looked up does not offer Reflection features implementation.");
			}
		}
		
		return (IService) service;
	}
	
}
