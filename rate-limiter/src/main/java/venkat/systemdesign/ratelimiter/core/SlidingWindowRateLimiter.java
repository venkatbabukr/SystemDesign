package venkat.systemdesign.ratelimiter.core;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.Queue;

import venkat.systemdesign.ratelimiter.application.ApiRequest;

public class SlidingWindowRateLimiter implements RateLimiter {

	private long windowCapacity;
	private long windowSizeInSeconds;
	private Queue<Instant> requestTimeStampsWindow;

	public SlidingWindowRateLimiter(long wc, long ws) {
		this.windowCapacity = wc;
		this.windowSizeInSeconds = ws;
		this.requestTimeStampsWindow = new LinkedList<>();
	}

	@Override
	public synchronized boolean grantRequest(ApiRequest req) {
		refreshSlidingWindow();
		boolean grant = requestTimeStampsWindow.size() < windowCapacity;
		if (grant) {
			requestTimeStampsWindow.offer(Instant.now());
		}
		return grant;
	}

	@Override
	public synchronized void finishRequest() {
		// For now this is small hack. Ideally we need to remove the request timestamp that got completed...
		requestTimeStampsWindow.poll();
	}

	private void refreshSlidingWindow() {
		if (requestTimeStampsWindow.isEmpty())
			return;

		Instant minTsToRetain = Instant.now().minus(windowSizeInSeconds, ChronoUnit.SECONDS);
		while (!requestTimeStampsWindow.isEmpty() && requestTimeStampsWindow.peek().isBefore(minTsToRetain)) {
			requestTimeStampsWindow.poll();
		}
	}

}
