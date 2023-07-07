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
		CircuitState newState = null;
		switch (stateName) {
			case CLOSED:
				newState = new ClosedState(cb, cb.getConfig().getClosedStateCfg());
				break;
			case HALF_OPEN:
				newState = new HalfOpenState(cb, cb.getConfig().getHalfOpenStateCfg());
				break;
			case OPEN:
				newState = new OpenState(cb, cb.getConfig().getOpenStateCfg());
				break;
		}
		return newState;
	}

}
