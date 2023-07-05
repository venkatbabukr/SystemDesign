package venkat.systemdesign.circuitbreaker.model.export;

import java.time.Instant;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(value = AccessLevel.NONE)
public class ApiRequest {

	private long requestId;
	
	private Instant requestTime;
	
	public ApiRequest(long id) {
		this.requestId = id;
		this.requestTime = Instant.now();
	}

}
