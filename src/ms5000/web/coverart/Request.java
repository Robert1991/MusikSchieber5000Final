package ms5000.web.coverart;

/**
 * Request which gets send to the cover art web api
 */
public class Request {
	
	/**
	 * The release id
	 */
	private String releaseID;

	/*
	 * Getter and Setters
	 */
	public String getReleaseID() {
		return releaseID;
	}

	public void setReleaseID(String releaseID) {
		this.releaseID = releaseID;
	}
	
}
