package javatest.read_multiplystreams;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javatest.adapters.reader.StreamReader;
import javatest.adapters.reader.WordReader;
import javatest.adapters.storage.WordStorage;
import javatest.core.reader.CharacterReader;

public class HandlerTest {

	final int PRINT_BASE_DELAY = 1;

	private Handler handler;
	private WordReader wordReader;

	@BeforeEach
	void setUp() {
		wordReader = Mockito.mock(WordReader.class);

		handler = new Handler(StreamReader.of(wordReader));
	}

	@ParameterizedTest
	@MethodSource("test_SlowReader_Data")
	void test_SlowReader(List<String> sourceWordsStream, List<String> expectedIn10Seconds,
			List<String> expectedIn20Seconds) throws InterruptedException {

		Mockito.doAnswer(new Answer<String>() {

			final int MIDDLE_STREAM_INDEX = 6;
			private final Map<String, Integer> countByReader = new ConcurrentHashMap<>();

			@Override
			public String answer(InvocationOnMock invocationOnMock) {
				final String readerName = invocationOnMock.getArgument(0).toString();
				countByReader.compute(readerName, (k, v) -> v == null ? 0 : v + 1);

				final String word = sourceWordsStream.get(countByReader.get(readerName));

				if (Objects.equals(countByReader.get(readerName), MIDDLE_STREAM_INDEX)) {
					try {
						TimeUnit.SECONDS.sleep(PRINT_BASE_DELAY);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				return word;
			}
		}).when(wordReader).readWord(Mockito.any());

		final List<CharacterReader> readers =
				Stream.generate(() -> Mockito.mock(CharacterReader.class)).limit(10).collect(Collectors.toList());

		var storage = new WordStorage();
		new Thread(() -> handler.handle(readers, storage)).start();

		TimeUnit.SECONDS.sleep(PRINT_BASE_DELAY);

		Assertions.assertEquals(expectedIn10Seconds, storage.getStatistic());

		TimeUnit.SECONDS.sleep(PRINT_BASE_DELAY);

		Assertions.assertEquals(expectedIn20Seconds, storage.getStatistic());

	}

	static Stream<Arguments> test_SlowReader_Data() {
		return Stream.of(Arguments.of(
				List.of("It", "was", "the", "best", "of", "times", "it", "was", "the", "worst", "of", "times", ""),
				List.of("Best - 10", "It - 10", "Of - 10", "The - 10", "Times - 10", "Was - 10"),
				List.of("It - 20", "Of - 20", "The - 20", "Times - 20", "Was - 20", "Best - 10", "Worst - 10")));
	}
}
