package venkat.systemdesign.parkinglot.core.repo;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import venkat.systemdesign.parkinglot.common.enums.VehicleType;
import venkat.systemdesign.parkinglot.core.model.ParkingSpace;
import venkat.systemdesign.parkinglot.core.model.ParkingSpot;

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
	
	public Optional<ParkingSpace> getParkingSpace(UUID spaceId) {
		return Optional.ofNullable(spacesById.get(spaceId));
	}

	public void freeParkingSpot(VehicleType spaceType, ParkingSpot spot) {
		Optional.ofNullable(spacesById.get(spot.getParkingSpaceId()))
			.ifPresent(space -> space.returnFreeSpot(spot));
	}

}
