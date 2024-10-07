package org.acme.cache.impl;

import org.acme.cache.BoundedCache;

import java.util.Optional;

/**
 * A Least Recently Used (LRU) cache implementation.
 * This cache evicts the least recently accessed entry
 * when the cache exceeds its {@link #getCapacity() maximum capacity}.
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of cached values
 */
class LRUCache<K, V> extends BoundedCache<K, V> {

	public LRUCache(final int capacity) {
		super(capacity);
	}

	@Override
	public void put(final K key, final V value) {
		// TODO: implement me
		throw new UnsupportedOperationException("put not implemented yet.");
	}

	@Override
	public Optional<V> get(final K key) {
		// TODO: implement me
		throw new UnsupportedOperationException("get not implemented yet.");
	}

	@Override
	public Optional<V> remove(final K key) {
		// TODO: implement me
		throw new UnsupportedOperationException("remove not implemented yet.");
	}

	@Override
	public int size() {
		// TODO: implement me
		throw new UnsupportedOperationException("size not implemented yet.");
	}

}
