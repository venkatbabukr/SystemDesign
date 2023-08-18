package venkat.systemdesign.parkinglot.core.repo;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import venkat.systemdesign.parkinglot.common.enums.VehicleType;
import venkat.systemdesign.parkinglot.core.model.ParkingSpace;
import venkat.systemdesign.parkinglot.core.model.ParkingSpot;

@Component
public class ParkingLotRepo {
	
	private Map<UUID, ParkingSpace> spacesById;

	private Map<VehicleType, List<ParkingSpace>> spacesByType;

	public ParkingLotRepo(Set<ParkingSpace> parkingSpaces) {
		this.spacesById = parkingSpaces.stream().collect(Collectors.toMap(ParkingSpace::getId, Function.identity()));
		this.spacesByType = parkingSpaces.stream()
								.collect(Collectors.groupingBy(ParkingSpace::getSpaceType));
	}

	public Optional<List<ParkingSpace>> getParkingSpaces(VehicleType vehicleType) {
		return Optional.ofNullable(spacesByType.get(vehicleType));
	}
	
	public ParkingSpace getParkingSpace(UUID spaceId) {
		return spacesById.get(spaceId);
	}

	public ParkingSpot pollParkingSpot(VehicleType spaceType) {
		return Optional.ofNullable(spacesByType.get(spaceType))
					.flatMap(parkingSpaces -> parkingSpaces.stream().filter(ParkingSpace::isAvailable).findFirst())
					.map(space -> space.pollFreeSpot())
					.orElse(null);
	}

	public boolean releaseParkingSpot(ParkingSpot spot) {
		boolean parkingReleased = false;
		ParkingSpace space = getParkingSpace(spot.getParkingSpaceId());
		if (space != null) {
			space.returnFreeSpot(spot);
			parkingReleased = true;
		}
		return parkingReleased;
	}

}
