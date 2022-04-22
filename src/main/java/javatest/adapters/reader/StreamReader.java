package javatest.adapters.reader;

import javatest.adapters.storage.Storage;
import javatest.core.reader.CharacterReader;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StreamReader {

	private WordReader reader;

	public static StreamReader of() {
		return new StreamReader(new WordReader());
	}

	public static StreamReader of(WordReader wordReader) {
		return new StreamReader(wordReader);
	}

	public void read(CharacterReader characterReader, Storage storage) {
		boolean readNext = true;
		while (readNext) {

			final String word = reader.readWord(characterReader);
			if (word.length() == 0) {
				readNext = false;
				continue;
			}

			storage.put(word);
		}
	}
}
