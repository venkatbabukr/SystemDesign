package venkat.systemdesign.ratelimiter.rlfactories;

import venkat.systemdesign.ratelimiter.RateLimiterFactory;
import venkat.systemdesign.ratelimiter.simplerls.TokenbucketRateLimiter;

public class TokenbucketRateLimiterFactory implements RateLimiterFactory<TokenbucketRateLimiter> {
	
	private long rlReplenishRate;
	
	private long rlBurstCapacity;
	
	public TokenbucketRateLimiterFactory(long rlReplRate, long rlBurstC) {
		this.rlReplenishRate = rlReplRate;
		this.rlBurstCapacity = rlBurstC;
	}

	@Override
	public TokenbucketRateLimiter newRateLimiter() {
		return new TokenbucketRateLimiter(rlReplenishRate, rlBurstCapacity);
	}

}
