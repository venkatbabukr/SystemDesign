package venkat.systemdesign.parkinglot.front.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import venkat.systemdesign.parkinglot.core.exceptions.ParkingFullException;
import venkat.systemdesign.parkinglot.core.model.ParkingSpot;
import venkat.systemdesign.parkinglot.core.model.ParkingToken;
import venkat.systemdesign.parkinglot.core.model.Vehicle;
import venkat.systemdesign.parkinglot.core.repo.ParkingTokensRepo;
import venkat.systemdesign.parkinglot.core.services.ParkingManager;
import venkat.systemdesign.parkinglot.fare.core.services.FareCalculator;

@Service
public class ParkingGatesService {

	@Autowired
	private ParkingManager parkManager;

	@Autowired
	private FareCalculator fareCalculator;

	@Autowired
	private ParkingTokensRepo tokensRepo;

	public ParkingToken enterVehicleIntoParking(long entryGateNumber, Vehicle v) throws ParkingFullException {
		ParkingSpot parkingSpot = parkManager.searchAndAcquireParkingSpot(v.getType());
		return ParkingToken.builder()
				.entryGateNumber(entryGateNumber)
				.parkingSpot(parkingSpot)
				.vehicle(v)
				.intime(LocalDateTime.now())
				.build();
	}

	public void exitVehicleFromParking(long exitGateNumber, ParkingToken token) {
		parkManager.releaseParkingSpot(token.getParkingSpot());

		LocalDateTime exitTime = LocalDateTime.now();
		double totalFare = fareCalculator.calculateFare(token.getVehicle().getType(), token.getIntime(), exitTime);
		token.setExitGateNumber(exitGateNumber).setOuttime(exitTime).setTotalFare(totalFare);
		tokensRepo.save(token);
	}

}
