package venkat.systemdesign.ratelimiter.model.export;

@FunctionalInterface
public interface ApiProcessor {

	ApiResponse process(ApiRequest req, ApiResponse resp);

}
