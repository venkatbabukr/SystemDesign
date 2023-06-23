package venkat.systemdesign.parkinglot.fare.simple.services;

import java.time.Duration;
import java.time.LocalDateTime;

import venkat.systemdesign.parkinglot.common.enums.VehicleType;

public class SimpleFareCalculator {

	public double calculateFare(VehicleType vehicleType, LocalDateTime inTime, LocalDateTime outTime) {
		Duration parkingDuration = Duration.between(inTime, outTime);
		return 0.0;
	}

}
