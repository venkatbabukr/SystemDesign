package venkat.systemdesign.circuitbreaker.model;

import lombok.Getter;

public class CircuitStateCommonStats {
	
	@Getter
	private int totalRequestsCount;
	
	private int successRequestsCount;
	
	public CircuitStateCommonStats() {
		this(0, 0);
	}
	
	public CircuitStateCommonStats(int seedTotalCount, int seedSucessCount) {
		if (seedSucessCount < 0 || seedTotalCount < 0) {
			throw new IllegalArgumentException("Seed counts can't be negative!");
		}
		this.totalRequestsCount = seedTotalCount;
		this.successRequestsCount = seedSucessCount;
	}
	
	public CircuitStateCommonStats incrementRequestsCount() {
		totalRequestsCount++;
		return this;
	}

	public CircuitStateCommonStats incrementSuccessCount() {
		successRequestsCount++;
		return this;
	}

	public double getSuccessRate() {
		return 1.0 * successRequestsCount / totalRequestsCount;
	}
	
	public double getFailureRate() {
		return 1.0 - getSuccessRate();
	}

}
