package venkat.systemdesign.circuitbreaker.model;

import java.time.Duration;

import lombok.Data;

@Data
public class CircuitBreakerConfig {

	@Data
	public static final class OpenStateConfig {
		private Duration openStateWaitDuration;
	}

	@Data
	public static final class ClosedStateConfig {
		private double failureRateThreshold;
		private int windowSlideRequestCount;
	}
	
	@Data
	public static final class HalfOpenStateConfig {
		private int permitRequestsCount;
		
		private double successRateThreshold;

		private double failureRateThreshold;

	}
	
	private OpenStateConfig openStateCfg;
	private ClosedStateConfig closedStateCfg;
	private HalfOpenStateConfig halfOpenStateCfg;

}
