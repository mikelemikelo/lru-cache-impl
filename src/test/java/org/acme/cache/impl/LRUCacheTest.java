package org.acme.cache.impl;

import org.acme.cache.Cache;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class LRUCacheTest {

	@Test
	void givenCapacityIsZero_whenInstantiating_thenThrowException() {
		assertThatIllegalArgumentException()
				.describedAs("LRUCache should reject capacity 0")
				.isThrownBy(() -> new LRUCache<>(0));
	}

	@Test
	void givenCapacityIsNegative_whenInstantiating_thenThrowException() {
		assertThatIllegalArgumentException()
				.describedAs("LRUCache should reject negative capacity")
				.isThrownBy(() -> new LRUCache<>(-1));
	}

	@Test
	public void givenEmptyCache_whenAnyKeyAccessed_thenReturnEmpty() {
		Cache<String, Integer> cache = new LRUCache<>(2);
		assertThat(cache.get("missing"))
				.describedAs("Empty cache should not return a value for missing key")
				.isNotPresent();
	}

	@Test
	public void givenCapacityIsOne_whenNewEntryIsAdded_thenExistingEntryIsRemoved() {
		Cache<String, String> cache = new LRUCache<>(1);

		cache.put("key1", "value1");
		assertThat(cache.get("key1"))
				.describedAs("Expected cache.get('key1') to return value from preceding call cache.put('key1', 'value1')")
				.hasValue("value1");

		cache.put("key2", "value2");
		assertThat(cache.get("key1"))
				.describedAs("Entry for 'key1' should no longer be in cache")
				.isNotPresent();
		assertThat(cache.get("key2"))
				.describedAs("New cache entry 'key2' should have correct value")
				.hasValue("value2");
	}

	@Test
	public void givenExceedingMaxCapacity_whenPutCache_thenLeastRecentlyUsedEntryIsEvicted() {
		Cache<String, Integer> cache = new LRUCache<>(2);
		cache.put("key1", 1);
		cache.put("key2", 2);
		cache.put("key3", 3);

		assertThat(cache.get("key1"))
				.describedAs("key1 should have been evicted")
				.isNotPresent();
		assertThat(cache.get("key2"))
				.describedAs("key2 should still be present")
				.hasValue(2);
		assertThat(cache.get("key3"))
				.describedAs("key3 should have been inserted")
				.hasValue(3);
	}

	@Test
	public void givenExceedingMaxCapacity_whenLeastRecentlyUsedEntryIsAccessed_thenItIsNotEvictedNext() {
		Cache<String, Integer> cache = new LRUCache<>(2);
		cache.put("key1", 1);
		cache.put("key2", 2);
		// This should "reset" key1 so it is recently used
		cache.get("key1");
		// This should evict key2
		cache.put("key3", 3);

		assertThat(cache.get("key1"))
				.describedAs("key1 should not have been evicted")
				.hasValue(1);
		assertThat(cache.get("key2"))
				.describedAs("key2 should have been evicted")
				.isNotPresent();
		assertThat(cache.get("key3"))
				.describedAs("key3 should have been inserted")
				.hasValue(3);

	}

	@Test
	public void givenExceedingMaxCapacity_whenLeastRecentlyUsedEntryIsModified_thenItIsNotEvictedNext() {
		Cache<String, Integer> cache = new LRUCache<>(2);
		cache.put("key1", 1);
		cache.put("key2", 2);
		// This should update "key1" value and make it recently used
		cache.put("key1", 10);
		// This should evict "key2"
		cache.put("key3", 3);


		assertThat(cache.get("key1"))
				.describedAs("key1 should have been updated and not evicted")
				.hasValue(10);
		assertThat(cache.get("key2"))
				.describedAs("key2 should have been evicted")
				.isNotPresent();
		assertThat(cache.get("key3"))
				.describedAs("key3 should have been inserted")
				.hasValue(3);
	}

	@Test
	void givenEmptyCache_thenRemoveShouldReturnEmpty() {
		// arrange
		Cache<String, Integer> cache = new LRUCache<>(2);

		// assert
		assertThat(cache.remove("does_not_exist"))
				.describedAs("remove should return empty for non-existent key")
				.isEmpty();
	}

	@Test
	void givenPopulatedCache_thenRemoveShouldReturnRemovedValue() {
		// arrange
		Cache<String, Integer> cache = new LRUCache<>(2);
		cache.put("key1", 1);

		// assert
		assertThat(cache.remove("key1"))
				.describedAs("remove('key1') should return the value that was removed")
				.hasValue(1);
	}

	@Test
	void givenPopulatedCache_whenRecentEntryIsRemoved_thenNewEntryShouldNotCauseEviction() {
		// arrange
		Cache<String, Integer> cache = new LRUCache<>(2);
		cache.put("key1", 1);
		cache.put("key2", 2);

		// act
		// remove most recently-used key "key2", so an entry remains for least recently-used key "key1"
		cache.remove("key2");

		// assert
		cache.put("key3", 3);

		assertThat(cache.get("key2"))
				.describedAs("entry for 'key2' should have been removed by previous call to remove('key2')")
				.isNotPresent();
		assertThat(cache.get("key1"))
				.describedAs("entry for 'key1' should still be present")
				.hasValue(1);
		assertThat(cache.get("key3"))
				.describedAs("new entry for 'key3' should be present")
				.hasValue(3);
	}

	@Test
	void givenEmptyCache_thenSizeEqualsZero() {
		Cache<String, String> cache = new LRUCache<>(4);
		assertThat(cache.size())
				.describedAs("size() should be 0 for empty cache")
				.isZero();
	}

	@Test
	void givenNonEmptyCache_thenSizeEqualsCurrentTotalEntries() {
		Cache<String, String> cache = new LRUCache<>(4);
		cache.put("key1", "value1");
		cache.put("key2", "value2");
		assertThat(cache.size())
				.describedAs("size() should return 2 when 2 entries have been inserted")
				.isEqualTo(2);
	}

	@Test
	void givenNonEmptyCache_whenEntryIsRemoved_thenSizeShouldDecreaseByOne() {
		// arrange
		Cache<String, String> cache = new LRUCache<>(4);
		cache.put("key1", "value1");
		cache.put("key2", "value2");

		// act
		cache.remove("key1");

		// assert
		assertThat(cache.size())
				.describedAs("size() should decrease when entry is removed")
				.isEqualTo(1);
	}

}
