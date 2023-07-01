package venkat.systemdesign.ratelimiter.simplerls;

import venkat.systemdesign.ratelimiter.RateLimiter;
import venkat.systemdesign.ratelimiter.model.ApiRequest;

public class LeakyBucketRateLimiter implements RateLimiter {
	
	private long maxCapacity;
	
	private long currentCapacity;
	
	public LeakyBucketRateLimiter(long maxC) {
		this.maxCapacity = maxC;
		this.currentCapacity = this.maxCapacity;
	}

	@Override
	public boolean grantRequest(ApiRequest req) {
		boolean grant = currentCapacity > 0;
		if (grant) {
			currentCapacity--;
		}
		return grant;
	}

	@Override
	public void finishRequest(ApiRequest req) {
		currentCapacity = Math.min(currentCapacity + 1, maxCapacity);
	}

}
