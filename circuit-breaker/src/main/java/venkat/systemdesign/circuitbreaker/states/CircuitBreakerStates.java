package venkat.systemdesign.circuitbreaker.states;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import venkat.systemdesign.circuitbreaker.CircuitBreaker;
import venkat.systemdesign.circuitbreaker.model.CircuitStateCommonStats;
import venkat.systemdesign.circuitbreaker.model.CircuitBreakerConfig.ClosedStateConfig;
import venkat.systemdesign.circuitbreaker.model.CircuitBreakerConfig.HalfOpenStateConfig;
import venkat.systemdesign.circuitbreaker.model.CircuitBreakerConfig.OpenStateConfig;

public class CircuitBreakerStates {

	static abstract class AbstractCircuitBreakerState implements CircuitBreakerState {
		
		protected StateName stateName;
		
		protected CircuitBreaker circuitBreaker;
		
		protected CircuitStateCommonStats stateStats;

		protected AbstractCircuitBreakerState(StateName sn, CircuitBreaker cb) {
			this.stateName = sn;
			this.circuitBreaker = cb;
		}

		@Override
		public void startState(CircuitStateCommonStats prevStateStats) {
			stateStats = Optional.ofNullable(prevStateStats)
							.orElse(new CircuitStateCommonStats());
		}

		@Override
		public StateName getStateName() {
			return stateName;
		}

		@Override
		public CircuitStateCommonStats getStats() {
			return stateStats;
		}

	}

	static class ClosedState extends AbstractCircuitBreakerState implements CircuitBreakerState {

		private ClosedStateConfig stateConfig;
		
		public ClosedState(CircuitBreaker cb, ClosedStateConfig config) {
			super(StateName.CLOSED, cb);
			this.stateConfig = config;
		}
		
		@Override
		public boolean permitRequest() {
			return true;
		}

		@Override
		public void onSuccess() {
			stateStats.incrementRequestsCount();
			stateStats.incrementSuccessCount();
			if (stateStats.getTotalRequestsCount() > stateConfig.getWindowSlideRequestCount()) {
				startState(null);
			}
		}

		@Override
		public void onFailure() {
			stateStats.incrementRequestsCount();
			if (stateStats.getFailureRate() > stateConfig.getFailureRateThreshold()) {
				circuitBreaker.transitionTo(StateName.OPEN, new CircuitStateCommonStats(1, 0));
			}
		}

	}

	static class OpenState extends AbstractCircuitBreakerState implements CircuitBreakerState {

		private OpenStateConfig stateConfig;
		
		private Instant stateStartInstant;

		public OpenState(CircuitBreaker cb, OpenStateConfig config) {
			super(StateName.OPEN, cb);
			this.stateConfig = config;
		}

		@Override
		public void startState(CircuitStateCommonStats stateStartStats) {
			super.startState(stateStartStats);
			this.stateStartInstant = Instant.now();
		}

		@Override
		public boolean permitRequest() {
			return (Duration.between(stateStartInstant, Instant.now()).compareTo(stateConfig.getOpenStateWaitDuration()) > 0);
		}

		@Override
		public void onSuccess() {
			circuitBreaker.transitionTo(StateName.HALF_OPEN, new CircuitStateCommonStats(1, 1));
		}

		@Override
		public void onFailure() {
			// Restart...
			startState(new CircuitStateCommonStats(1, 0));
		}

	}

	static class HalfOpenState extends AbstractCircuitBreakerState implements CircuitBreakerState {

		private HalfOpenStateConfig stateConfig;

		public HalfOpenState(CircuitBreaker cb, HalfOpenStateConfig config) {
			super(StateName.HALF_OPEN, cb);
			this.stateConfig = config;
		}

		@Override
		public boolean permitRequest() {
			return stateStats.getTotalRequestsCount() + 1 < stateConfig.getPermitRequestsCount();
		}

		@Override
		public void onSuccess() {
			stateStats.incrementRequestsCount();
			stateStats.incrementSuccessCount();
			if (stateStats.getSuccessRate() > stateConfig.getSuccessRateThreshold()) {
				circuitBreaker.transitionTo(StateName.CLOSED, stateStats);
			}
		}

		@Override
		public void onFailure() {
			stateStats.incrementRequestsCount();
			if (stateStats.getFailureRate() < stateConfig.getFailureRateThreshold()) {
				circuitBreaker.transitionTo(StateName.OPEN, stateStats);
			}
		}

	}

}
