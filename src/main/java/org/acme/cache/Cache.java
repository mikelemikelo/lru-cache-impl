package org.acme.cache;

import java.util.Optional;

/**
 * Generic cache with key-value pair entries.
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of cached values
 */
public interface Cache<K, V> {

	/**
	 * Unconditionally inserts a key-value pair into the cache,
	 * overriding any existing entry with the same key.
	 *
	 * @param key the key with which the specified value is to be associated
	 * @param value the value to be associated with the specified key
	 */
	void put(K key, V value);

	/**
	 * Retrieves the value associated with the given key from the cache.
	 *
	 * @param key the key whose associated value is to be returned
	 * @return an {@link Optional} containing the value if it is present, or empty otherwise
	 */
	Optional<V> get(K key);

	/**
	 * Removes the entry for the specified key if it is present in the cache.
	 *
	 * @param key the key whose entry is to be removed from the cache
	 * @return an {@link Optional} containing the value associated with the key if it was present, or empty otherwise
	 */
	Optional<V> remove(K key);

	/**
	 * Returns the number of entries currently in the cache.
	 *
	 * @return the number of key-value mappings in the cache
	 */
	int size();

}
