package venkat.systemdesign.circuitbreaker.states;

import lombok.Getter;
import venkat.systemdesign.circuitbreaker.CircuitBreaker;
import venkat.systemdesign.circuitbreaker.states.CircuitBreakerStates.ClosedState;
import venkat.systemdesign.circuitbreaker.states.CircuitBreakerStates.HalfOpenState;
import venkat.systemdesign.circuitbreaker.states.CircuitBreakerStates.OpenState;

@Getter
public enum StateName {
    CLOSED(new CircuitBreakerStateFactory<ClosedState>() {

		@Override
		public ClosedState newStateImpl(CircuitBreaker cb) {
			return new ClosedState(cb, cb.getConfig().getClosedStateCfg());
		}
    	
    }),
    OPEN(new CircuitBreakerStateFactory<OpenState>() {

		@Override
		public OpenState newStateImpl(CircuitBreaker cb) {
			return new OpenState(cb, cb.getConfig().getOpenStateCfg());
		}
    	
    }),
    HALF_OPEN(new CircuitBreakerStateFactory<HalfOpenState>() {

		@Override
		public HalfOpenState newStateImpl(CircuitBreaker cb) {
			return new HalfOpenState(cb, cb.getConfig().getHalfOpenStateCfg());
		}
    	
    });

	private CircuitBreakerStateFactory<? extends CircuitBreakerState> cbStateFactory;

	private StateName(CircuitBreakerStateFactory<? extends CircuitBreakerState> cbs) {
		this.cbStateFactory = cbs;
	}

}
