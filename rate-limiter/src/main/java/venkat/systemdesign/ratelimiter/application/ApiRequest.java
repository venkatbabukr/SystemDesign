package venkat.systemdesign.ratelimiter.application;

public class ApiRequest {
	
	Long requestId;
	
	public ApiRequest(Long id) {
		this.requestId = id;
	}
	
	public Long getRequestId() {
		return requestId;
	}
	
	public String toString() {
		return String.format("{requestId: %d}", requestId);
	}

}
