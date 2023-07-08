package venkat.systemdesign.ratelimiter.rlfilters;

import java.util.HashMap;
import java.util.Map;

import venkat.systemdesign.ratelimiter.RateLimiter;
import venkat.systemdesign.ratelimiter.RateLimiterSupplier;
import venkat.systemdesign.ratelimiter.model.export.ApiProcessor;
import venkat.systemdesign.ratelimiter.model.export.ApiRequest;
import venkat.systemdesign.ratelimiter.model.export.ApiResponse;

public class RateLimitFilter {

	private Map<Long, RateLimiter> userRateLimiter;
	
	private RateLimiterSupplier<? extends RateLimiter> rlSupplier;
	
	public RateLimitFilter(RateLimiterSupplier<? extends RateLimiter> rls) {
		if (rls == null)
			throw new IllegalArgumentException("Rate limiter supplier required!");
		this.userRateLimiter = new HashMap<>();
		this.rlSupplier = rls;
	}
	
	public ApiResponse processRequest(Long userId, ApiRequest req, ApiProcessor proc) {
		RateLimiter userRL = getUserRateLimiter(userId);
		ApiResponse resp = new ApiResponse(req);
		if (userRL.grantRequest(req)) {
			proc.process(req, resp);
			userRL.finishRequest(req);
		}
		return resp;
	}

	private RateLimiter getUserRateLimiter(Long userId) {
		RateLimiter rl = this.userRateLimiter.get(userId);
		if (rl == null) {
			synchronized(this.userRateLimiter) {
				rl = this.userRateLimiter.get(userId);
				if (rl == null) {
					rl = rlSupplier.newRateLimiter();
					this.userRateLimiter.put(userId, rl);
				}
			}
		}
		return rl;
	}

}
