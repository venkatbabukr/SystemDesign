package venkat.systemdesign.ratelimiter;

import venkat.systemdesign.ratelimiter.model.ApiRequest;

public interface RateLimiter {
	
	public boolean grantRequest(ApiRequest req);
	
	public void finishRequest(ApiRequest req);

}
