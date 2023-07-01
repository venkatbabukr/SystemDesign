package venkat.systemdesign.connpool;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import venkat.systemdesign.connpool.model.Connection;
import venkat.systemdesign.connpool.model.ConnectionDetails;

@Slf4j
public class SimpleConnectionPool implements ConnectionPool {

	public static final Duration POOL_REFILL_DURATION = Duration.ofSeconds(5);

	private ConnectionDetails dbConnDetails;

	private int fillCapacity;

	private BlockingQueue<Connection> availableConnections;
	
	private Timer poolFillupScheduler;

	public SimpleConnectionPool(String dbUrl, int dbPort, int initialCapacity, int maxCapacity) {
		this(new ConnectionDetails(dbUrl, dbPort), initialCapacity, maxCapacity);
	}

	public SimpleConnectionPool(ConnectionDetails connDetails, int initialCapacity, int maxCapacity) {
		if (initialCapacity > maxCapacity)
			throw new IllegalArgumentException("Initial capacity can't exceed maximum capacity...");
		this.dbConnDetails = connDetails;
		this.fillCapacity = initialCapacity;
		this.availableConnections = new LinkedBlockingDeque<>(maxCapacity);
		poolFillupScheduler = new Timer();
		poolFillupScheduler.schedule(new TimerTask() {

			@Override
			public void run() {
				log.debug("Begin pool refill");
				fillupPool();
				log.debug("End pool refill");
			}
		}, 0L, POOL_REFILL_DURATION.toMillis());
	}

	private void fillupPool() {
		log.debug("Initial availableConnections size: {}", availableConnections.size());
		for (int i = 0 ; i < fillCapacity ; i++) {
			if (log.isDebugEnabled())
				log.debug("availableConnections size: {} < {}", availableConnections.size(), fillCapacity);
			availableConnections.offer(new Connection(dbConnDetails));
		}
		log.debug("Exiting");
	}

	@Override
	public Connection borrowConnection() throws InterruptedException {
		log.debug("Entering");
		return availableConnections.take();
	}

	@Override
	public Connection borrowConnection(long timeout, ChronoUnit timeoutUnit) throws InterruptedException {
		log.debug("Entering(timeout: {}, timeoutUnit: {})", timeout, timeoutUnit);
		return availableConnections.poll(timeout, TimeUnit.of(timeoutUnit));
	}

	@Override
	public synchronized void releaseConnection(Connection c) {
		log.debug("Entering(Connection: {})", c);
		if (c == null)
			throw new IllegalArgumentException("Connection can't be null!");
		availableConnections.offer(c);
		log.debug("Exiting");
	}

	@Override
	public void shutdown() {
		poolFillupScheduler.cancel();
	}

}
