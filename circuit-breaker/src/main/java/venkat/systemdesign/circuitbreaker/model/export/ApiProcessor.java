package venkat.systemdesign.circuitbreaker.model.export;

public interface ApiProcessor {

	String getName();

	void process(ApiRequest req, ApiResponse resp) throws RuntimeException;

}
