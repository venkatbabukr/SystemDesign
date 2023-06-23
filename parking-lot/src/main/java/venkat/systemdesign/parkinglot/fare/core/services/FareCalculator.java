package venkat.systemdesign.parkinglot.fare.core.services;

import java.time.LocalDateTime;

import venkat.systemdesign.parkinglot.common.enums.VehicleType;

public interface FareCalculator {

	double calculateFare(VehicleType vehicleType, LocalDateTime inTime, LocalDateTime outTime);

}
