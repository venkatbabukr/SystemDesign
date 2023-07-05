package venkat.systemdesign.ratelimiter.simplerls;

import java.time.Duration;
import java.time.Instant;

import venkat.systemdesign.ratelimiter.RateLimiter;
import venkat.systemdesign.ratelimiter.model.export.ApiRequest;

public class TokenbucketRateLimiter implements RateLimiter {
	
	public long replenishRate;
	public long currentCapacity;
	public Instant lastReplenishTime;
	public long burstCapacity;
	
	public TokenbucketRateLimiter(long repRate, long burstCapacity) {
		this.replenishRate = repRate;
		this.burstCapacity = burstCapacity;
		
		this.currentCapacity = this.replenishRate;
		this.lastReplenishTime = Instant.now();
	}

	@Override
	public synchronized boolean grantRequest(ApiRequest req) {
		replenish();
		boolean grant = currentCapacity > 0;
		if (grant) {
			currentCapacity--;
		}
		return grant;
	}

	private void replenish() {
		Instant currentTime = Instant.now();
		int numSeconds = (int) Duration.between(lastReplenishTime, currentTime).toSeconds();
		currentCapacity = Math.min(currentCapacity + numSeconds * replenishRate, burstCapacity);
		lastReplenishTime = currentTime;
	}

	@Override
	public synchronized void finishRequest(ApiRequest req) {
		currentCapacity = Math.min(currentCapacity + 1, burstCapacity);
	}

}
