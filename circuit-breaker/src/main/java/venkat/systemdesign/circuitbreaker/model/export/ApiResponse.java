package venkat.systemdesign.circuitbreaker.model.export;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class ApiResponse {
	
	@Setter(value = AccessLevel.NONE)
	private ApiRequest request;

	private ApiProcessor processedBy;
	
	public ApiResponse(ApiRequest req) {
		this.request = req;
	}
	
}
