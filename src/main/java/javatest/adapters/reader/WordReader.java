package javatest.adapters.reader;

import javatest.core.exception.EndOfStreamException;
import javatest.core.reader.CharacterReader;

public class WordReader {

	public String readWord(CharacterReader reader) {
		StringBuilder builder = new StringBuilder();
		boolean readNext = true;
		while (readNext) {
			try {
				final char nextChar = reader.getNextChar();
				if (Character.isAlphabetic(nextChar) /*nextChar > 64 && nextChar < 123*/) {
					builder.append(nextChar);
				} else if (builder.length() > 0) {
					readNext = false;
				}
			} catch (EndOfStreamException e) {
				readNext = false;
			}
		}
		return builder.toString();
	}
}
