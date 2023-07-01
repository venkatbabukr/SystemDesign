package venkat.systemdesign.ratelimiter.model;

import java.time.Instant;
import java.time.temporal.Temporal;

public class ApiRequest {
	
	Long requestId;
	
	Temporal requestTs;
	
	public ApiRequest(Long id) {
		this(id, Instant.now());
	}
	
	public ApiRequest(Long id, Temporal ts) {
		this.requestId = id;
		this.requestTs = ts;
	}
	
	public Long getRequestId() {
		return requestId;
	}
	
	public Temporal getRequestTs() {
		return requestTs;
	}
	
	public String toString() {
		return String.format("{requestId: %d, ts: %s}", requestId, requestTs);
	}

}
