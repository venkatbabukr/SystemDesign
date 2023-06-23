package venkat.systemdesign.parkinglot.core.model;

import lombok.Data;
import venkat.systemdesign.parkinglot.common.enums.VehicleType;

@Data
public class Vehicle {
	
	private VehicleType type;
	private String number;

}
