package venkat.systemdesign.common.model.impl;

import lombok.extern.slf4j.Slf4j;
import venkat.systemdesign.common.model.ApiProcessor;
import venkat.systemdesign.common.model.ApiRequest;
import venkat.systemdesign.common.model.ApiResponse;

@Slf4j
public class FallbackApiProcessor implements ApiProcessor {

	@Override
	public void process(ApiRequest req, ApiResponse resp) throws RuntimeException {
		log.info("Entering process(req={}, resp={})", req, resp);
		resp.setProcessedBy(getName());
	}

}
