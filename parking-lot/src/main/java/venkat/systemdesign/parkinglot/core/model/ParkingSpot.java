package venkat.systemdesign.parkinglot.core.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ParkingSpot {
	
	private UUID parkingSpaceId;
	private int slotNumber;

}
