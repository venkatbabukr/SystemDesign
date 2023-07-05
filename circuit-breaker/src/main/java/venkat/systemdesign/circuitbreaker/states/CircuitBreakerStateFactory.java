package venkat.systemdesign.circuitbreaker.states;

import venkat.systemdesign.circuitbreaker.CircuitBreaker;

public interface CircuitBreakerStateFactory<CBS extends CircuitBreakerState> {
	
	CBS newStateImpl(CircuitBreaker cb);

}
