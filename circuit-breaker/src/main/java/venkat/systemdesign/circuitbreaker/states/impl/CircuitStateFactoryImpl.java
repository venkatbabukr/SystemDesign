package venkat.systemdesign.circuitbreaker.states.impl;

import venkat.systemdesign.circuitbreaker.CircuitBreaker;
import venkat.systemdesign.circuitbreaker.states.CircuitState;
import venkat.systemdesign.circuitbreaker.states.CircuitStateFactory;
import venkat.systemdesign.circuitbreaker.states.impl.AllCircuitStates.ClosedState;
import venkat.systemdesign.circuitbreaker.states.impl.AllCircuitStates.HalfOpenState;
import venkat.systemdesign.circuitbreaker.states.impl.AllCircuitStates.OpenState;

public enum CircuitStateFactoryImpl implements CircuitStateFactory {
	INSTANCE;

	@Override
	public CircuitState newCircuitState(StateName stateName, CircuitBreaker cb) {
		return switch (stateName) {
			case CLOSED -> new ClosedState(cb, cb.getConfig().getClosedStateCfg());
			case HALF_OPEN -> new HalfOpenState(cb, cb.getConfig().getHalfOpenStateCfg());
			case OPEN -> new OpenState(cb, cb.getConfig().getOpenStateCfg());
		};
	}

}
