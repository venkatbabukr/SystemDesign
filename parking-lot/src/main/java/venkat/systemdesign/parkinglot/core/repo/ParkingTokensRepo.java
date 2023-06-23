package venkat.systemdesign.parkinglot.core.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import venkat.systemdesign.parkinglot.core.model.ParkingToken;

@Repository
public interface ParkingTokensRepo extends JpaRepository<ParkingToken, Long> {
}
