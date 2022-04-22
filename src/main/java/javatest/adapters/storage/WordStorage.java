package javatest.adapters.storage;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class WordStorage implements Storage {

	private static final String OUTPUT_FORMAT = "%s - %d";

	private final Map<String, Integer> storage = new ConcurrentHashMap<>();

	public void put(String word) {
		storage.compute(StringUtils.capitalize(word), (k, v) -> v == null ? 1 : v + 1);
	}

	public List<String> getStatistic() {
		return storage.entrySet().stream().sorted(new EntrySetComparator())
				.map(it -> String.format(OUTPUT_FORMAT, it.getKey(), it.getValue())).collect(Collectors.toList());
	}

	static class EntrySetComparator implements Comparator<Entry<String, Integer>> {

		@Override
		public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2) {
			if (e1.getValue() < e2.getValue()) {
				return 1;
			} else if (e1.getValue().equals(e2.getValue())) {
				return e1.getKey().compareToIgnoreCase(e2.getKey());
			} else {
				return -1;
			}
		}
	}
}
