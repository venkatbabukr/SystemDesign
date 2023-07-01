package venkat.systemdesign.connpool.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConnectionDetails {

	private String dbUrl;
	private int dbPort;

}
