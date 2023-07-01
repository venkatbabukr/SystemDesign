package venkat.systemdesign.ratelimiter.model.windowrls;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.Optional;

public class WindowSize {

	private long durationAmt;
	private TemporalUnit durationUnit;

	public WindowSize(long amt, TemporalUnit unit) {
		if (!unit.isTimeBased())
			throw new IllegalArgumentException("Only time based units supported...");
		this.durationAmt = amt;
		this.durationUnit = unit;
	}

	public long getDurationAmount() {
		return durationAmt;
	}

	public TemporalUnit getUnit() {
		return durationUnit;
	}

	public Duration getWindowSizeDuration() {
		return durationUnit.getDuration().multipliedBy(durationAmt);
	}

	public Instant getWindowEndTime(Instant windowStartInstant) {
		return Optional.ofNullable(windowStartInstant)
					.map(startTime -> durationUnit.addTo(startTime, durationAmt))
						.orElse(null);
	}

}
