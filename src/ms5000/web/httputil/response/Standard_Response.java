package ms5000.web.httputil.response;

/**
 * Class to capture the response received by any WS
 */
public class Standard_Response implements Response{
	
	/**
	 * Response message
	 */
	private String response;
	
	/**
	 * Reponse code
	 */
	private int responseCode;
	
	/*
	 * getter and setters
	 */
    public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	
}
