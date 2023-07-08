package venkat.systemdesign.ratelimiter;

import venkat.systemdesign.ratelimiter.model.export.ApiRequest;

/*
 * For now, we have {@link LeakyBucketRateLimiter} extend this class. Consider other classes also
 * to extend this one if required later...
 */
public abstract class AbstractRateLimiter implements RateLimiter {
	
	protected long currentCapacity;

	@Override
	public boolean grantRequest(ApiRequest req) {
		boolean granted = currentCapacity > 0;
		if (granted) {
			currentCapacity--;
		}
		return granted;
	}

	protected void setCapacity(long capacity) {
		this.currentCapacity = capacity;
	}

	protected void incrementCapacity(long maxPermittedCapacity) {
		currentCapacity = Math.min(currentCapacity + 1, maxPermittedCapacity);
	}

}
