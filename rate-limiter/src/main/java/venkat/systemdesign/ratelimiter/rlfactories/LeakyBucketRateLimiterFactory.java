package venkat.systemdesign.ratelimiter.rlfactories;

import venkat.systemdesign.ratelimiter.RateLimiterFactory;
import venkat.systemdesign.ratelimiter.simplerls.LeakyBucketRateLimiter;

public class LeakyBucketRateLimiterFactory implements RateLimiterFactory<LeakyBucketRateLimiter> {

	private long rlMaxCapacity;

	public LeakyBucketRateLimiterFactory(long rlMaxC) {
		this.rlMaxCapacity = rlMaxC;
	}

	@Override
	public LeakyBucketRateLimiter newRateLimiter() {
		return new LeakyBucketRateLimiter(rlMaxCapacity);
	}

}
