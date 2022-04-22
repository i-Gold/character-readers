package javatest.adapter.reader;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javatest.adapters.reader.WordReader;
import javatest.core.exception.EndOfStreamException;
import javatest.core.reader.CharacterReader;

public class WordReaderTest {

	WordReader reader;

	@BeforeEach
	void setUp() {
		reader = new WordReader();
	}

	@ParameterizedTest
	@MethodSource("test_ReadFromStream_Data")
	public void test_ReadFromStream(String source, String expected) {
		final CharacterReader mock = Mockito.mock(CharacterReader.class);
		Mockito.doAnswer(new Answer<Character>() {

			private int count = 0;

			@Override
			public Character answer(InvocationOnMock invocationOnMock) {
				if (source == null || count >= source.length()) {
					throw new EndOfStreamException();
				}
				return source.charAt(count++);
			}
		}).when(mock).getNextChar();

		final String actual = reader.readWord(mock);

		Assertions.assertEquals(expected, actual);
	}

	public static Stream<Arguments> test_ReadFromStream_Data() {
		return Stream.of(Arguments.of(null, ""), Arguments.of("", ""), Arguments.of("TEXT", "TEXT"),
				Arguments.of(" TEXT ", "TEXT"), Arguments.of("TEXT, ", "TEXT"), Arguments.of("`without ", "without"));
	}
}
