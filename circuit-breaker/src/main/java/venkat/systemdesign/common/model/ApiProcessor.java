package venkat.systemdesign.common.model;

public interface ApiProcessor {

	void process(ApiRequest req, ApiResponse resp) throws RuntimeException;

	default String getName() {
		return this.getClass().getName();
	}

}
