package venkat.systemdesign.ratelimiter.rlfilters;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import venkat.systemdesign.ratelimiter.RateLimiter;
import venkat.systemdesign.ratelimiter.RateLimiterFactory;
import venkat.systemdesign.ratelimiter.model.ApiRequest;
import venkat.systemdesign.ratelimiter.model.ApiResponse;

public class RateLimitFilter {

	private Map<Long, RateLimiter> userRateLimiter;
	
	private RateLimiterFactory<? extends RateLimiter> rlFactory;
	
	public RateLimitFilter(RateLimiterFactory<? extends RateLimiter> rlf) {
		if (rlf == null)
			throw new IllegalArgumentException("Rate limiter factory required!");
		this.userRateLimiter = new HashMap<>();
		this.rlFactory = rlf;
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
			userRL.finishRequest(req);
		}
		return new ApiResponse(req, requestGranted);
	}

	private RateLimiter getUserRateLimiter(Long userId) {
		RateLimiter rl = this.userRateLimiter.get(userId);
		if (rl == null) {
			synchronized(this.userRateLimiter) {
				rl = this.userRateLimiter.get(userId);
				if (rl == null) {
					rl = rlFactory.newRateLimiter();
					this.userRateLimiter.put(userId, rl);
				}
			}
		}
		return rl;
	}

}
