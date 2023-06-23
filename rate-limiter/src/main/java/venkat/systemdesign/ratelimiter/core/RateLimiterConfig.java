package venkat.systemdesign.ratelimiter.core;

public class RateLimiterConfig {
	
	private RateLimiterConfig() { }

	public static final Integer TOKEN_REPLENISH_RATE = 5;

	public static final Integer BURST_CAPACITY = 10;

}
