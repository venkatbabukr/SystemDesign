package venkat.systemdesign.circuitbreaker;

import lombok.Getter;
import venkat.systemdesign.circuitbreaker.model.CircuitBreakerConfig;
import venkat.systemdesign.circuitbreaker.model.CircuitStateCommonStats;
import venkat.systemdesign.circuitbreaker.states.CircuitState;
import venkat.systemdesign.circuitbreaker.states.impl.CircuitStateFactoryImpl;
import venkat.systemdesign.circuitbreaker.states.impl.StateName;
import venkat.systemdesign.common.model.ApiProcessor;
import venkat.systemdesign.common.model.ApiRequest;
import venkat.systemdesign.common.model.ApiResponse;

@Getter
public class CircuitBreaker {

	private CircuitBreakerConfig config;
	
	private CircuitState activeState;
	
	private ApiProcessor fallbackProcessor;

	public CircuitBreaker(CircuitBreakerConfig cfg, ApiProcessor fallback) {
		this.config = cfg;
		this.fallbackProcessor = fallback;
		transitionTo(StateName.CLOSED, null);
	}
	
	public void transitionTo(StateName newStateName, CircuitStateCommonStats oldStateStats) {
		activeState = CircuitStateFactoryImpl.INSTANCE.newCircuitState(newStateName, this);
		activeState.startState(oldStateStats);
	}

	public ApiResponse processRequest(ApiRequest req, ApiProcessor proc) {
		ApiResponse resp = new ApiResponse(req);
		boolean requestProcessed = false;
		if (activeState.permitRequest()) {
			try {
				proc.process(req, resp);
				requestProcessed = resp.isProcessSuccess();
				if (requestProcessed) {
					activeState.onSuccess();
				} else {
					activeState.onFailure();
				}
			} catch (RuntimeException e) {
				activeState.onFailure();
			}
		}
		if (!requestProcessed) {
			// In all cases of request not processed
			fallbackProcessor.process(req, resp);
		}
		return resp;
	}

}
