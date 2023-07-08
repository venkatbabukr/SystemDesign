package venkat.systemdesign.ratelimiter.rlsuppliers;

import venkat.systemdesign.ratelimiter.RateLimiterSupplier;
import venkat.systemdesign.ratelimiter.simplerls.TokenbucketRateLimiter;

public class TokenbucketRateLimiterSupplier implements RateLimiterSupplier<TokenbucketRateLimiter> {
	
	private long rlReplenishRate;
	
	private long rlBurstCapacity;
	
	public TokenbucketRateLimiterSupplier(long rlReplRate, long rlBurstC) {
		this.rlReplenishRate = rlReplRate;
		this.rlBurstCapacity = rlBurstC;
	}

	@Override
	public TokenbucketRateLimiter newRateLimiter() {
		return new TokenbucketRateLimiter(rlReplenishRate, rlBurstCapacity);
	}

}
