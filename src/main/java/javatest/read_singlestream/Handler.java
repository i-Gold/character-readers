package javatest.read_singlestream;

import java.util.UUID;

import javatest.adapters.reader.StreamReader;
import javatest.adapters.storage.Storage;
import javatest.core.reader.CharacterReader;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
class Handler {

	StreamReader wordReader;

	void handle(CharacterReader characterReader, Storage storage) {
		String traceId = UUID.randomUUID().toString();
		log.info("Start handling single stream with id {{}}, traceId {{}}", characterReader.hashCode(), traceId);

		wordReader.read(characterReader, storage);

		log.info("Finish handling single stream with id {{}}, traceId {{}}", characterReader.hashCode(), traceId);
	}
}
