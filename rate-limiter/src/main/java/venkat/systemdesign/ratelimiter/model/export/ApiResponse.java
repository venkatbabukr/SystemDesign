package venkat.systemdesign.ratelimiter.model.export;

public class ApiResponse {
	
	private ApiRequest request;
	private Boolean success;
	
	public ApiResponse(ApiRequest req) {
		this.request = req;
		this.success = Boolean.FALSE;
	}

	public ApiRequest getRequest() {
		return request;
	}

	public void signalSuccess() {
		this.success = true;
	}

	public Boolean isSuccess() {
		return success;
	}

	public String toString() {
		return String.format("{request: %s, processed: %s }", request, success);
	}

}
