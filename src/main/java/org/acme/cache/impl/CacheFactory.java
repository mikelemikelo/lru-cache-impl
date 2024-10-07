package org.acme.cache.impl;

import org.acme.cache.Cache;

/**
 * Utility class for getting cache instances.
 */
public final class CacheFactory {

	public static <K, V> Cache<K, V> getLruCache(final int capacity) {
		return new LRUCache<>(capacity);
	}

}
