package venkat.systemdesign.parkinglot.core.model;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import venkat.systemdesign.parkinglot.common.enums.VehicleType;

@Data
public class ParkingSpace implements Comparable<ParkingSpace> {
	private UUID id;
	private VehicleType spaceType;
	private int maxCapacity;
	private int slotStartNum;
	private String location;

	@Setter(AccessLevel.NONE)
	private transient Queue<Integer> freeSlotsQueue;
	
	public ParkingSpace(VehicleType type, int slotStartNum, int maxCapacity) {
		this.spaceType = type;
		this.slotStartNum = slotStartNum;
		this.maxCapacity = maxCapacity;
		initialize();
	}

	public void initialize() {
		this.id = UUID.randomUUID();
		this.freeSlotsQueue = IntStream.range(slotStartNum, slotStartNum + maxCapacity)
								.boxed()
								.collect(Collectors.toCollection(LinkedList::new));
	}
	
	public boolean isAvailable() {
		return !freeSlotsQueue.isEmpty();
	}

	public synchronized Optional<ParkingSpot> borrowFreeSpot() {
		Integer freeSlot = freeSlotsQueue.poll();
		return Optional.ofNullable(freeSlot)
		        .map(slotNum -> new ParkingSpot(this.id, slotNum));
	}

	public void returnFreeSpot(ParkingSpot spot) {
		if (this.id.equals(spot.getParkingSpaceId())) {
			int slotNum = spot.getSlotNumber();
			if (!freeSlotsQueue.contains(slotNum)) {
				synchronized(freeSlotsQueue) {
					freeSlotsQueue.offer(slotNum);
				}
			}
		}
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof ParkingSpace && Objects.equals(this.id, ((ParkingSpace) other).id);
	}

	@Override
	public int hashCode() {
		return this.id.hashCode();
	}

	@Override
	public int compareTo(ParkingSpace o) {
		return Objects.compare(this.id, o.id, Comparator.nullsFirst(Comparator.naturalOrder()));
	}

}
