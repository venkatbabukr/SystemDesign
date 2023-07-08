package venkat.systemdesign.ratelimiter;

public interface RateLimiterSupplier<T extends RateLimiter> {
	
	T newRateLimiter();

}
