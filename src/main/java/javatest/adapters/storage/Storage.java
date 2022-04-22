package javatest.adapters.storage;

import java.util.List;

public interface Storage {

	void put(String word);

	List<String> getStatistic();
}
