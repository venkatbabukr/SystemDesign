package venkat.systemdesign.rlsdemo.fixedwindow;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import venkat.systemdesign.ratelimiter.model.export.ApiRequest;
import venkat.systemdesign.ratelimiter.model.export.ApiResponse;
import venkat.systemdesign.ratelimiter.rlfilters.RateLimitFilter;
import venkat.systemdesign.ratelimiter.rlsuppliers.WindowRateLimiterSupplier;
import venkat.systemdesign.ratelimiter.windowrls.FixedWindowRateLimiter;

public class DemoRateLimiter {

	public static void main(String[] args) {
		RateLimitFilter testRLFilter = new RateLimitFilter(
		        new WindowRateLimiterSupplier<FixedWindowRateLimiter>(DemoConfig.WINDOW_CAPACITY, DemoConfig.TWO_SECOND_WINDOW_SIZE, FixedWindowRateLimiter.class));

		int MAX_USERS = 2;
		int MAX_USER_REQUESTS = 10;

		ExecutorService threadExecutors = Executors.newFixedThreadPool(MAX_USERS * MAX_USER_REQUESTS);
		// ExecutorService threadExecutors = Executors.newFixedThreadPool(2);

		for (Long userCnt = 1L; userCnt <= MAX_USERS; userCnt++) {
			final Long userId = userCnt;
			for (Long i = 1L; i <= MAX_USER_REQUESTS; i++) {
				final Long requestId = i;
				threadExecutors.submit(() -> {
					ApiResponse response = testRLFilter.processRequest(userId, new ApiRequest(requestId), (req, resp) -> {
						try {
							Thread.sleep(Duration.ofSeconds(2).toMillis());
						} catch (InterruptedException e) {
						}
						resp.signalSuccess();
						return resp;
					});
					System.out.format("{userId: %s, response: %s}%n", userId, response);
				});
			}
		}
		threadExecutors.shutdown();
	}

}