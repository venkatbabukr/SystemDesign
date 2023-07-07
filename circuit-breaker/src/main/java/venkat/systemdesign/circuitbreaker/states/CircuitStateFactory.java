package venkat.systemdesign.circuitbreaker.states;

import venkat.systemdesign.circuitbreaker.CircuitBreaker;
import venkat.systemdesign.circuitbreaker.states.impl.StateName;

public interface CircuitStateFactory {

	CircuitState newCircuitState(StateName stateName, CircuitBreaker cb);

}
