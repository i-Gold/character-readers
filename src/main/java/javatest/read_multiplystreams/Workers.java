package javatest.read_multiplystreams;

import java.util.TimerTask;

import javatest.adapters.storage.Storage;
import lombok.AllArgsConstructor;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
class Workers {

	@AllArgsConstructor
	static class PrintStatisticTask extends TimerTask {

		private Storage storage;

		@Override
		public void run() {
			log.info("Printing statistic in current state");
			storage.getStatistic().forEach(log::info);
		}
	}
}
