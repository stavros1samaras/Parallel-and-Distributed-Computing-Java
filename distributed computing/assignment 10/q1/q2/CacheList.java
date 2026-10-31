package q2;

import java.util.HashMap;

public class CacheList {

	// αυτη ειναι η synchronized δομη μου
	private static HashMap<Long, Double> cacheList;

	public CacheList() {
		cacheList = new HashMap<>();
	}

	public synchronized Double getCachedValue(long index) {
		return cacheList.get(index);
	}

	public synchronized void cacheValue(long index, double value) {
		cacheList.put(index, value);
	}
}