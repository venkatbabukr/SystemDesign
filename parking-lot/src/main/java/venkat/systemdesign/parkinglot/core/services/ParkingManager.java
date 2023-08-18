package venkat.systemdesign.parkinglot.core.services;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import venkat.systemdesign.parkinglot.common.enums.VehicleType;
import venkat.systemdesign.parkinglot.core.model.ParkingSpot;
import venkat.systemdesign.parkinglot.core.repo.ParkingLotRepo;

@Slf4j
@Service
public class ParkingManager {

	@Autowired
	private ParkingLotRepo repo;
	
	private Lock parkingLock;
	private Condition parkingSpaceAvaialble;
	
	public ParkingManager() {
		this.parkingLock = new ReentrantLock(true);
		parkingSpaceAvaialble = parkingLock.newCondition();
	}

	public ParkingSpot searchAndAcquireParkingSpot(VehicleType vehicleType) {
		parkingLock.lock();
		ParkingSpot vehicleParkingSpot = repo.pollParkingSpot(vehicleType);
		while (vehicleParkingSpot == null) {
			try {
				parkingSpaceAvaialble.await();
			} catch (InterruptedException e) {
				log.debug("Got interrupted while waiting for free vehicle parking spot - " + e.getMessage(), e);
			}
			vehicleParkingSpot = repo.pollParkingSpot(vehicleType);
		}
		parkingLock.unlock();
		return vehicleParkingSpot;
	}
	
	public synchronized void releaseParkingSpot(ParkingSpot spot) {
		if (repo.releaseParkingSpot(spot)) {
			parkingSpaceAvaialble.signalAll();
		}
	}

}
