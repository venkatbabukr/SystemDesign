package venkat.systemdesign.ratelimiter.rlsuppliers;

import venkat.systemdesign.ratelimiter.RateLimiterSupplier;
import venkat.systemdesign.ratelimiter.simplerls.LeakyBucketRateLimiter;

public class LeakyBucketRateLimiterSupplier implements RateLimiterSupplier<LeakyBucketRateLimiter> {

	private long rlMaxCapacity;

	public LeakyBucketRateLimiterSupplier(long rlMaxC) {
		this.rlMaxCapacity = rlMaxC;
	}

	@Override
	public LeakyBucketRateLimiter newRateLimiter() {
		return new LeakyBucketRateLimiter(rlMaxCapacity);
	}

}
