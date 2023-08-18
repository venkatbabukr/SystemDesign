package venkat.systemdesign.common.model.impl;

import java.time.Duration;

import lombok.extern.slf4j.Slf4j;
import venkat.systemdesign.common.model.ApiProcessor;
import venkat.systemdesign.common.model.ApiRequest;
import venkat.systemdesign.common.model.ApiResponse;

@Slf4j
public class SimpleApiProcessor implements ApiProcessor {
	
	public static final Duration DEFAULT_PROCESS_DURATION = Duration.ofSeconds(2);
	
	private Duration processDuration;

	public SimpleApiProcessor() {
		this(DEFAULT_PROCESS_DURATION);
	}

	public SimpleApiProcessor(Duration procDuration) {
		this.processDuration = procDuration;
	}

	@Override
	public void process(ApiRequest req, ApiResponse resp) throws RuntimeException {
		try {
			Thread.sleep(processDuration.toMillis());
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			log.warn("Got interrupted: " + e.getMessage(), e);
		}
		resp.setProcessSuccess(true);
		log.debug("Request processed and set response!");
	}

}
