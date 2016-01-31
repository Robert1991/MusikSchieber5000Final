package ms5000.web.httputil.response;

/**
 * Interface to determine an response
 */
public interface Response {
	/*
	 * Getter and Setters
	 */
	String getResponse();
	void setResponse(String response);
	int getResponseCode();
	void setResponseCode(int responseCode);
}
