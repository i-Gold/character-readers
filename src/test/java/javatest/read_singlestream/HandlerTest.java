package javatest.read_singlestream;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;

import javatest.adapters.reader.StreamReader;
import javatest.adapters.reader.WordReader;
import javatest.adapters.storage.WordStorage;
import javatest.core.reader.CharacterReader;

@TestInstance(Lifecycle.PER_CLASS)
class HandlerTest {

	private Handler handler;
	private WordReader wordReader;

	@BeforeEach
	void setUp() {
		wordReader = Mockito.mock(WordReader.class);

		handler = new Handler(StreamReader.of(wordReader));
	}

	@ParameterizedTest
	@MethodSource("test_HandleSuccessful_Data")
	void test_HandleSuccessful(List<String> sourceWordsStream, List<String> expectedOutput) {

		Mockito.doAnswer(AdditionalAnswers.returnsElementsOf(sourceWordsStream)).when(wordReader)
				.readWord(Mockito.any());

		final WordStorage storage = new WordStorage();
		handler.handle(Mockito.mock(CharacterReader.class), storage);

		final List<String> actualOutput = storage.getStatistic();

		Assertions.assertEquals(expectedOutput.size(), actualOutput.size());

		for (int i = 0; i < expectedOutput.size(); i++) {
			Assertions.assertEquals(expectedOutput.get(i), actualOutput.get(i).toString());
		}

	}

	static Stream<Arguments> test_HandleSuccessful_Data() {
		return Stream.of(Arguments.of(
				List.of("It", "was", "the", "best", "of", "times", "it", "was", "the", "worst", "of", "times", ""),
				List.of("It - 2", "Of - 2", "The - 2", "Times - 2", "Was - 2", "Best - 1", "Worst - 1")));

	}

}