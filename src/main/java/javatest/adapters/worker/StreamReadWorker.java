package javatest.adapters.worker;

import java.util.concurrent.CountDownLatch;

import javatest.adapters.reader.StreamReader;
import javatest.adapters.storage.Storage;
import javatest.core.reader.CharacterReader;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StreamReadWorker implements Runnable{


	private CharacterReader characterReader;
	private StreamReader streamReader;
	private Storage storage;
	private CountDownLatch doneSignal;

	@Override
	public void run() {
		streamReader.read(characterReader, storage);
		doneSignal.countDown();
	}
}
