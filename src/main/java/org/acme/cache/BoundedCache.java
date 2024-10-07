package org.acme.cache;

/**
 * A cache with a fixed maximum number of entries.
 * This class provides the foundation for implementing different eviction policies
 * when the cache reaches its maximum capacity.
 * <p>
 * Subclasses can optionally override {@link #validateCapacity(int)} to perform
 * validation tasks during construction.
 *
 * @param <K> cache key type
 * @param <V> cache value type
 */
public abstract class BoundedCache<K, V> implements Cache<K, V> {

	protected final int capacity;

	/**
	 * Constructs a BoundedCache with the specified maximum capacity.
	 *
	 * @param capacity the maximum number of entries this cache can hold
	 * @throws IllegalArgumentException if capacity is invalid
	 */
	protected BoundedCache(int capacity) {
		this.validateCapacity(capacity);
		this.capacity = capacity;
	}

	/**
	 * Validates the given capacity, throwing an exception for invalid capacities.
	 * This is a no-op by default.
	 *
	 * @param capacity the maximum number of entries this cache can hold
	 * @throws IllegalArgumentException if the specified capacity is not valid
	 */
	protected void validateCapacity(final int capacity) throws IllegalArgumentException {
		// no-op by default
	}

	/**
	 * Returns the maximum number of entries that the cache can hold.
	 *
	 * @return the cache's capacity
	 */
	public int getCapacity() {
		return this.capacity;
	}

}
