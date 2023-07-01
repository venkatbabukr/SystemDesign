package venkat.systemdesign.ratelimiter.windowrls;

import java.time.Instant;
import java.util.LinkedList;
import java.util.Queue;

import venkat.systemdesign.ratelimiter.RateLimiter;
import venkat.systemdesign.ratelimiter.model.ApiRequest;
import venkat.systemdesign.ratelimiter.model.windowrls.WindowSize;

public class SlidingWindowRateLimiter implements RateLimiter {

	private long windowCapacity;
	
	private WindowSize windowSize;
	
	private Queue<Instant> requestTimeStampsWindow;

	public SlidingWindowRateLimiter(long wc, WindowSize ws) {
		this.windowCapacity = wc;
		this.windowSize = ws;
		this.requestTimeStampsWindow = new LinkedList<>();
	}

	private void refreshSlidingWindow() {
		if (requestTimeStampsWindow.isEmpty())
			return;

		Instant minTsToRetain = Instant.now().minus(windowSize.getDurationAmount(), windowSize.getUnit());
		while (!requestTimeStampsWindow.isEmpty() && requestTimeStampsWindow.peek().isBefore(minTsToRetain)) {
			requestTimeStampsWindow.poll();
		}
	}

	@Override
	public synchronized boolean grantRequest(ApiRequest req) {
		refreshSlidingWindow();
		boolean grant = requestTimeStampsWindow.size() < windowCapacity;
		if (grant) {
			requestTimeStampsWindow.offer(Instant.from(req.getRequestTs()));
		}
		return grant;
	}

	@Override
	public synchronized void finishRequest(ApiRequest req) {
		requestTimeStampsWindow.remove(Instant.from(req.getRequestTs()));
	}

}
