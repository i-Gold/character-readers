package javatest.read_multiplystreams;

import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javatest.adapters.reader.StreamReader;
import javatest.adapters.storage.WordStorage;
import javatest.core.reader.CharacterReader;
import javatest.core.reader.SlowCharacterReader;
import javatest.read_multiplystreams.Workers.PrintStatisticTask;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Trigger {

	private static final long TIMER_DELAY = 10;
	private static final long TIMER_PERIOD = 10;

	public static void main(String[] args) {
		WordStorage storage = new WordStorage();
		final Handler handler = new Handler(StreamReader.of());

		final List<CharacterReader> readers =
				Stream.generate(SlowCharacterReader::new).limit(10).collect(Collectors.toList());

		Timer timer = new Timer();
		final PrintStatisticTask printStatisticTask = new PrintStatisticTask(storage);
		timer.schedule(printStatisticTask, TimeUnit.SECONDS.toMillis(TIMER_DELAY),
				TimeUnit.SECONDS.toMillis(TIMER_PERIOD));

		handler.handle(readers, storage);
		timer.cancel();
		printStatisticTask.run();
	}
}
