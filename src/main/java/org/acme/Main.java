package org.acme;

import org.acme.cache.Cache;
import org.acme.cache.impl.CacheFactory;

public class Main {

	// feel free to use this as a playground area
	public static void main(String[] args) {
		Cache<String, Object> cache = CacheFactory.getLruCache(3);
		cache.put("key1", "value1");
		System.out.println("key1 value is " + cache.get("key1"));
		System.out.println("cache size is " + cache.size());
		cache.remove("key1");
		System.out.println("cache size is " + cache.size());
	}

}
