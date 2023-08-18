package venkat.systemdesign.common.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class ApiResponse {
	
	@Setter(value = AccessLevel.NONE)
	private ApiRequest request;

	private boolean processSuccess;

	private String processedBy;

	public ApiResponse(ApiRequest req) {
		this.request = req;
		this.processSuccess = false;
	}
	
}
