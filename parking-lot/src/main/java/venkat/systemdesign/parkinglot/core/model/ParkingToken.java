package venkat.systemdesign.parkinglot.core.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
public class ParkingToken {
	private long id;
	private long entryGateNumber;
	private long exitGateNumber;
	
	/*
	 * Use @Embeddable and @Embedded to map these nested objects
	 * while saving into DB using hibernate.
	 */
	private transient Vehicle vehicle;
	private transient ParkingSpot parkingSpot;

	private LocalDateTime intime;
	private LocalDateTime outtime;
	
	private double totalFare;

}
