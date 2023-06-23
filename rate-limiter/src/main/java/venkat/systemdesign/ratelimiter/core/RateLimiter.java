package venkat.systemdesign.ratelimiter.core;

import venkat.systemdesign.ratelimiter.application.ApiRequest;

public interface RateLimiter {
	
	public boolean grantRequest(ApiRequest req);
	
	public void finishRequest();

}
