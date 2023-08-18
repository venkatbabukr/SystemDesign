package venkat.systemdesign.common.model.impl;

import java.util.Random;

import lombok.extern.slf4j.Slf4j;
import venkat.systemdesign.common.model.ApiProcessor;
import venkat.systemdesign.common.model.ApiRequest;
import venkat.systemdesign.common.model.ApiResponse;

@Slf4j
public class RandomFailureApiProcessor extends SimpleApiProcessor implements ApiProcessor {
	
	private double successRate;
	
	private Random randomPicker;
	
	public RandomFailureApiProcessor(double sr) {
		super();
		this.successRate = sr;
		this.randomPicker = new Random();
	}

	@Override
	public void process(ApiRequest req, ApiResponse resp) throws RuntimeException {
		super.process(req, resp);
		double randomRate = randomPicker.nextDouble();
		if (randomRate > successRate) {
			resp.setProcessSuccess(false);
			log.warn("Randomly failing process due to randomRate {} > successRate {}", randomRate, successRate);
		}
	}

}
