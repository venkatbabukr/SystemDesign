package venkat.systemdesign.connpool;

import java.time.temporal.ChronoUnit;

import venkat.systemdesign.connpool.model.Connection;

public interface ConnectionPool {

	Connection borrowConnection() throws InterruptedException;

	Connection borrowConnection(long timeout, ChronoUnit timeoutUnit) throws InterruptedException;

	void releaseConnection(Connection c);
	
	void shutdown();

}
