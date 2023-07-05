package venkat.systemdesign.circuitbreaker.states;

import venkat.systemdesign.circuitbreaker.model.CircuitStateCommonStats;

public interface CircuitBreakerState {

	StateName getStateName();

	void startState(CircuitStateCommonStats prevStateStats);

	boolean permitRequest();

	void onSuccess();

	void onFailure();
	
	CircuitStateCommonStats getStats();
	
}
