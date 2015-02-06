package com.wstester.util;

import java.util.HashMap;
import java.util.Map;

public final class UtilityTool {

	private static Map<MainConstants, Object> cache = new HashMap<>();

	public static void addEntity(MainConstants key, Object obj) {
		cache.put(key, obj);
	}

	public static Object getEntity(MainConstants key) {
		return cache.get(key);
	}
}
