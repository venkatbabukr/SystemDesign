package venkat.systemdesign.ratelimiter.core.filters;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import venkat.systemdesign.ratelimiter.application.ApiRequest;
import venkat.systemdesign.ratelimiter.application.ApiResponse;
import venkat.systemdesign.ratelimiter.core.RateLimiter;
import venkat.systemdesign.ratelimiter.core.RateLimiterConfig;
import venkat.systemdesign.ratelimiter.core.TokenbucketRateLimiter;

public class RateLimitFilter {

	private Map<Long, RateLimiter> userRateLimiter;
	
	public RateLimitFilter() {
		this.userRateLimiter = new HashMap<>();
	}
	
	public ApiResponse processRequest(Long userId, ApiRequest req) {
		RateLimiter userRL = getUserRateLimiter(userId);
		boolean requestGranted = userRL.grantRequest(req);
		if (requestGranted) {
			// If we implement this as part of filter chain, we'll simply say
			// chain.doNext(req);
			try {
				Thread.sleep(Duration.ofSeconds(2).toMillis());
			} catch (InterruptedException e) {
				System.out.println("Processing request (sleep) interrupted...");
				e.printStackTrace();
			}
			userRL.finishRequest();
		}
		return new ApiResponse(req, requestGranted);
	}

	private RateLimiter getUserRateLimiter(Long userId) {
		RateLimiter rl = this.userRateLimiter.get(userId);
		if (rl == null) {
			synchronized(this.userRateLimiter) {
				rl = this.userRateLimiter.get(userId);
				if (rl == null) {
					rl = new TokenbucketRateLimiter(RateLimiterConfig.TOKEN_REPLENISH_RATE, RateLimiterConfig.BURST_CAPACITY);
					this.userRateLimiter.put(userId, rl);
				}
			}
		}
		return rl;
	}

}
