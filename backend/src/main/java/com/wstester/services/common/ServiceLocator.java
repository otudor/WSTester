package com.wstester.services.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import com.wstester.services.definition.IService;

public final class ServiceLocator {

	private static Map<Class<? extends IService>, Object> cache = null;
	private static ServiceLocator instance = null;
	
	public static ServiceLocator getInstance() {
		synchronized(ServiceLocator.class) {
			if(instance == null) {
				instance = new ServiceLocator();
				cache = new HashMap<>();
			}
		}
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	public synchronized <T> T lookup(Class<? extends IService> clazz) throws Exception {
		Object service = null;
		
		if(clazz.isAnnotationPresent(Stateful.class)) {
			if(!cache.containsKey(clazz)) {
					cache.put(clazz, getImplementation(clazz));
			}
			service = cache.get(clazz);
		}
		
		else if(clazz.isAnnotationPresent(Stateless.class)) {
			service = getImplementation(clazz);
		}		
	
		if(service != null) {
			return (T) service;
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public synchronized <T> T lookup(Class<? extends IService> clazz, Object constructorParameter) throws Exception {
		Object service = null;
		
		if(clazz.isAnnotationPresent(Stateful.class)) {
			if(!cache.containsKey(clazz)) {
					cache.put(clazz, getImplementation(clazz, constructorParameter));
			}
			service = cache.get(clazz);
		}
		
		else if(clazz.isAnnotationPresent(Stateless.class)) {
			service = getImplementation(clazz, constructorParameter);
		}
				
		if(service != null) {
			return (T) service;
		}
		
		return null;
	}
	
	private Object getImplementation(Class<? extends IService> clazz, Object... constructorParameter) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{
		
		Set<?> classes = new Reflections("com.wstester.services.impl").getSubTypesOf(clazz);
		
		if(classes.size() != 1){
			throw new ClassNotFoundException("Two or more implementations for class: " + clazz + " were found: " + classes);
		}
		
		Class<?> cla= (Class<?>) classes.iterator().next();
		
		if(constructorParameter.length == 0) {
			return cla.newInstance();
		}
		
		else {
			Class<?>[] parameterClasses = new Class<?>[constructorParameter.length];
			for(int i = 0; i < constructorParameter.length; i++){
				parameterClasses[i] = constructorParameter[i].getClass();
			}
			
			Constructor<?> contructor = cla.getDeclaredConstructor(parameterClasses);
			return contructor.newInstance(constructorParameter);
		}
	}
}
