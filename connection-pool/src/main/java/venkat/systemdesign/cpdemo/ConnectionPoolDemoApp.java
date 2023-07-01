package venkat.systemdesign.cpdemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;
import venkat.systemdesign.connpool.ConnectionPool;
import venkat.systemdesign.connpool.SimpleConnectionPool;
import venkat.systemdesign.connpool.model.Connection;
import venkat.systemdesign.connpool.model.ConnectionDetails;

@Slf4j
public class ConnectionPoolDemoApp {

	public static void main(String[] args) {
		ConnectionDetails dbDetails = new ConnectionDetails("localhost", 3581);
		ConnectionPool demoPool = new SimpleConnectionPool(dbDetails, 3, 5);

		Runnable job = () -> {
			Connection c = null;
			try {
				c = demoPool.borrowConnection();
				log.info("Borrowed from pool connection: {}", c);
				Thread.sleep(2L);
			} catch (InterruptedException e) {
			} finally {
				if (c != null) {
					log.info("Releasing to pool connection: {}", c);
					demoPool.releaseConnection(c);
				}
			}
		};
		int NUM_THREADS = 10;
		ExecutorService executors = Executors.newFixedThreadPool(NUM_THREADS);
		IntStream.range(0, NUM_THREADS)
			.forEach(i -> executors.execute(job));
		executors.shutdown();
		demoPool.shutdown();
	}
}
