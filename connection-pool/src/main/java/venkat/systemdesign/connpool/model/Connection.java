package venkat.systemdesign.connpool.model;

import java.util.UUID;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class Connection {

	private UUID id;
	ConnectionDetails details;

	public Connection(ConnectionDetails details) {
		this.id = UUID.randomUUID();
		this.details = details;
		if (log.isDebugEnabled())
			log.debug("New connection object: {}", this);
	}

}
