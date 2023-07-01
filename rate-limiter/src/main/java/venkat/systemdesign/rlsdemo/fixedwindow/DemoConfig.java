package venkat.systemdesign.rlsdemo.fixedwindow;

import java.time.temporal.ChronoUnit;

import venkat.systemdesign.ratelimiter.model.windowrls.WindowSize;

public class DemoConfig {
	
	private DemoConfig() { }

	public static final Long WINDOW_CAPACITY = 5L;

	public static final WindowSize TWO_SECOND_WINDOW_SIZE = new WindowSize(2, ChronoUnit.SECONDS);
	public static final WindowSize FIVE_MINUTE_WINDOW_SIZE = new WindowSize(5, ChronoUnit.MINUTES);
	public static final WindowSize ONE_HOUR_WINDOW_SIZE = new WindowSize(1, ChronoUnit.HOURS);

}
