package javatest.read_singlestream;

import javatest.adapters.reader.StreamReader;
import javatest.adapters.storage.WordStorage;
import javatest.core.reader.SimpleCharacterReader;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Trigger {

	public static void main(String[] args) {
		final WordStorage storage = new WordStorage();
		new Handler(StreamReader.of()).handle(new SimpleCharacterReader(), storage);
		log.info("Printing statistic");
		storage.getStatistic().forEach(log::info);
	}
}
