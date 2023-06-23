package venkat.systemdesign.ratelimiter.core;

import java.time.Duration;
import java.time.Instant;

import venkat.systemdesign.ratelimiter.application.ApiRequest;

public class TokenbucketRateLimiter implements RateLimiter {
	
	public int replenishRate;
	public int currentCapacity;
	public Instant lastReplenishTime;
	public int burstCapacity;
	
	public TokenbucketRateLimiter(int repRate, int burstCapacity) {
		this.replenishRate = repRate;
		this.burstCapacity = burstCapacity;
		
		this.currentCapacity = this.replenishRate;
		this.lastReplenishTime = Instant.now();
	}

	@Override
	public synchronized boolean grantRequest(ApiRequest req) {
		replenish();
		if (currentCapacity > 0) {
			currentCapacity--;
			return true;
		}
		return false;
	}

	private void replenish() {
		Instant currentTime = Instant.now();
		int numSeconds = (int) Duration.between(lastReplenishTime, currentTime).toSeconds();
		currentCapacity = Math.min(currentCapacity + numSeconds * replenishRate, burstCapacity);
		lastReplenishTime = currentTime;
	}

	@Override
	public synchronized void finishRequest() {
		this.currentCapacity++;
	}

}
