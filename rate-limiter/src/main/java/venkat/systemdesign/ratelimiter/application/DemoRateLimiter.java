package venkat.systemdesign.ratelimiter.application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import venkat.systemdesign.ratelimiter.core.filters.RateLimitFilter;

public class DemoRateLimiter {

	public static void main(String[] args) {
		RateLimitFilter testRLFilter = new RateLimitFilter();
		
		int MAX_USERS = 2;
		int MAX_USER_REQUESTS = 10;

		ExecutorService threadExecutors = Executors.newFixedThreadPool(MAX_USERS * MAX_USER_REQUESTS);

		for (Long userCnt = 1L ; userCnt <= MAX_USERS ; userCnt++) {
			final Long userId = userCnt;
			for (Long i = 1L ; i <= MAX_USER_REQUESTS ; i++) {
				final Long requestId = i;
				threadExecutors.submit(() -> {
					ApiResponse response = testRLFilter.processRequest(userId, new ApiRequest(requestId));
					System.out.format("{userId: %s, response: %s}%n", userId, response);
				});
			}
		}
		threadExecutors.shutdown();
	}

}
