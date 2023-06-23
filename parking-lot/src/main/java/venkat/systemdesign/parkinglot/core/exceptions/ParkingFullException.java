package venkat.systemdesign.parkinglot.core.exceptions;

public class ParkingFullException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public ParkingFullException() {
		super();
	}

	public ParkingFullException(String msg) {
		super(msg);
	}

	public ParkingFullException(String msg, Throwable t) {
		super(msg, t);
	}

}
