package venkat.systemdesign.ratelimiter.model;

public class ApiResponse {
	
	private ApiRequest request;
	private boolean processed;
	
	public ApiResponse(ApiRequest req, boolean process) {
		this.request = req;
		this.processed = process;
	}

	public ApiRequest getRequest() {
		return request;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	public boolean isProcessed() {
		return processed;
	}

	public String toString() {
		return String.format("{request: %s, processed: %s }", request, processed);
	}

}
