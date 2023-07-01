package venkat.systemdesign.ratelimiter.windowrls;

import java.time.Duration;
import java.time.Instant;

import venkat.systemdesign.ratelimiter.RateLimiter;
import venkat.systemdesign.ratelimiter.model.ApiRequest;
import venkat.systemdesign.ratelimiter.model.windowrls.WindowSize;

public class FixedWindowRateLimiter implements RateLimiter {
	
	private long windowCapacity;
	
	private WindowSize fixedWindowSize;
	
	private long currentCapacity;

	private Instant windowStartInstant;

	private Instant windowEndInstant;

	public FixedWindowRateLimiter(long wc, WindowSize size) {
		this.windowCapacity = wc;
		this.fixedWindowSize = size;
		
		initWindow();
	}

	private void initWindow() {
		windowStartInstant = Instant.now().truncatedTo(fixedWindowSize.getUnit());
		currentCapacity = windowCapacity;
		windowEndInstant = fixedWindowSize.getWindowEndTime(windowStartInstant);
	}

	private synchronized void resetWindow() {
		Instant currentTime = Instant.now();
		if (windowEndInstant.isBefore(currentTime)) {
			initWindow();
		}
	}

	@Override
	public synchronized boolean grantRequest(ApiRequest req) {
		resetWindow();
		boolean grant = currentCapacity > 0;
		if (grant) {
			currentCapacity--;
		}
		return grant;
	}

	@Override
	public void finishRequest(ApiRequest req) {
		if (!Duration.between(windowStartInstant, req.getRequestTs()).isNegative()) {
			currentCapacity = Math.min(currentCapacity + 1, windowCapacity);
		}
	}

}
