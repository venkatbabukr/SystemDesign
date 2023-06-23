package venkat.systemdesign.parkinglot.core.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import venkat.systemdesign.parkinglot.common.enums.VehicleType;
import venkat.systemdesign.parkinglot.core.model.ParkingSpace;
import venkat.systemdesign.parkinglot.core.model.ParkingSpot;
import venkat.systemdesign.parkinglot.core.repo.ParkingLotRepo;

@Service
public class ParkingManager {

	@Autowired
	private ParkingLotRepo repo;

	public Optional<ParkingSpot> searchAndAcquireParkingSpot(VehicleType vehicleType) {
		return repo.getParkingSpaces(vehicleType)
				.map(vehicleTypeSpaces -> vehicleTypeSpaces.stream().parallel()
						.filter(ParkingSpace::isAvailable).findFirst().map(ParkingSpace::borrowFreeSpot))
				.map(ParkingSpot.class::cast);
	}
	
	public void releaseParkingSpot(ParkingSpot spot) {
		repo.getParkingSpace(spot.getParkingSpaceId()).ifPresent(space -> space.returnFreeSpot(spot));
	}

}
