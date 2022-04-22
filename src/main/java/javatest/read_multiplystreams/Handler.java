package javatest.read_multiplystreams;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javatest.adapters.reader.StreamReader;
import javatest.adapters.storage.Storage;
import javatest.adapters.worker.StreamReadWorker;
import javatest.core.reader.CharacterReader;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
class Handler {

	private StreamReader streamReader;

	void handle(List<CharacterReader> readers, Storage storage) {

		String traceId = UUID.randomUUID().toString();
		log.info("Start handling multiply streams with readers count {{}}, traceId {{}}", readers.size(), traceId);

		final ExecutorService executorService = Executors.newFixedThreadPool(readers.size());

		CountDownLatch doneSignal = new CountDownLatch(readers.size());

		readers.forEach(it -> executorService.execute(new StreamReadWorker(it, streamReader, storage, doneSignal)));

		try {
			doneSignal.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("Finished handling multiply streams with readers count {{}}, traceId {{}}", readers.size(), traceId);
		executorService.shutdown();
	}
}
