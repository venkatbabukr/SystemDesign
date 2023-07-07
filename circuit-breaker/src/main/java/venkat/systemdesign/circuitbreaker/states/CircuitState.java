package venkat.systemdesign.circuitbreaker.states;

import venkat.systemdesign.circuitbreaker.model.CircuitStateCommonStats;
import venkat.systemdesign.circuitbreaker.states.impl.StateName;

public interface CircuitState {

	StateName getStateName();

	void startState(CircuitStateCommonStats prevStateStats);

	boolean permitRequest();

	void onSuccess();

	void onFailure();
	
	CircuitStateCommonStats getStats();
	
}
