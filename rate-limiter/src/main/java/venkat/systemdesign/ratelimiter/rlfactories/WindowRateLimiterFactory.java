package venkat.systemdesign.ratelimiter.rlfactories;

import java.lang.reflect.InvocationTargetException;
import java.time.temporal.TemporalUnit;

import venkat.systemdesign.ratelimiter.RateLimiter;
import venkat.systemdesign.ratelimiter.RateLimiterFactory;
import venkat.systemdesign.ratelimiter.model.windowrls.WindowSize;

public class WindowRateLimiterFactory<WRL extends RateLimiter> implements RateLimiterFactory<WRL> {

	private long rlWindowCapacity;
	
	private WindowSize rlWindowSize;
	
	Class<WRL> wrlClass;

	public WindowRateLimiterFactory(long rlwc, long windowDuration, TemporalUnit durationUnit, Class<WRL> wrlClass) {
		this(rlwc, new WindowSize(windowDuration, durationUnit), wrlClass);
	}

	public WindowRateLimiterFactory(long rlwc, WindowSize rlws, Class<WRL> wrlClass) {
		this.rlWindowCapacity = rlwc;
		this.rlWindowSize = rlws;
		this.wrlClass = wrlClass;
	}
	
	@Override
	public WRL newRateLimiter() {
		WRL newWRL = null;
		try {
			newWRL = wrlClass.getConstructor(Long.TYPE, WindowSize.class).newInstance(rlWindowCapacity, rlWindowSize);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
		        | NoSuchMethodException | SecurityException e) {
		}
		return newWRL;
	}

}
