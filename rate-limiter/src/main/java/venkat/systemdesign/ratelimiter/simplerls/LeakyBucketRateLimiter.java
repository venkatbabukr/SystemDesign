package venkat.systemdesign.ratelimiter.simplerls;

import venkat.systemdesign.ratelimiter.AbstractRateLimiter;
import venkat.systemdesign.ratelimiter.RateLimiter;
import venkat.systemdesign.ratelimiter.model.export.ApiRequest;

public class LeakyBucketRateLimiter extends AbstractRateLimiter implements RateLimiter {
	
	private long maxCapacity;
	
	public LeakyBucketRateLimiter(long maxC) {
		this.maxCapacity = maxC;
		setCapacity(this.maxCapacity);
	}

	@Override
	public void finishRequest(ApiRequest req) {
		incrementCapacity(maxCapacity);
	}

}
