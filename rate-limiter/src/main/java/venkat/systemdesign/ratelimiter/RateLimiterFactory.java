package venkat.systemdesign.ratelimiter;

public interface RateLimiterFactory<T extends RateLimiter> {
	
	T newRateLimiter();

}
