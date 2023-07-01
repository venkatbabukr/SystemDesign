package venkat.systemdesign.rlsdemo.tokenbucket;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import venkat.systemdesign.ratelimiter.model.ApiRequest;
import venkat.systemdesign.ratelimiter.model.ApiResponse;
import venkat.systemdesign.ratelimiter.rlfactories.TokenbucketRateLimiterFactory;
import venkat.systemdesign.ratelimiter.rlfilters.RateLimitFilter;

public class DemoRateLimiterWithCompletableFuture {

	public static void main(String[] args) {
		RateLimitFilter testRLFilter = new RateLimitFilter(
		        new TokenbucketRateLimiterFactory(DemoConfig.TOKEN_REPLENISH_RATE, DemoConfig.BURST_CAPACITY));

		int MAX_USERS = 2;
		int MAX_USER_REQUESTS = 10;
		int NUM_PARALLEL_THREADS = MAX_USERS * MAX_USER_REQUESTS;

		ExecutorService threadExecutors = Executors.newFixedThreadPool(NUM_PARALLEL_THREADS);
		// ExecutorService threadExecutors = Executors.newFixedThreadPool(4);

		List<Supplier<ApiResponse>> allFunctions = LongStream
		        .range(1, MAX_USERS)
		        .boxed()
		        .flatMap(userId -> LongStream.range(1, MAX_USER_REQUESTS).boxed()
		                .map(requestId -> (Supplier<ApiResponse>) () -> testRLFilter.processRequest(userId,
		                        new ApiRequest(requestId))))
		        .collect(Collectors.toList());

		List<CompletableFuture<ApiResponse>> futureApiResponses = allFunctions.stream()
		        .map(fn -> CompletableFuture.supplyAsync(fn, threadExecutors)).collect(Collectors.toList());

		Map<Boolean, List<ApiResponse>> allResponses = futureApiResponses.stream().map(CompletableFuture::join)
		        .collect(Collectors.partitioningBy(ApiResponse::isProcessed));

		System.out.println(allResponses);
		threadExecutors.shutdown();

	}
}
