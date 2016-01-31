package ms5000.properties.web;

/**
 * This enumeration captures the properties needed for web applications 
 */
public enum WebProperties {
	
	/**
	 * FpCalc property
	 */
	FPCALCWIN32("fpcalcWin32"),
	FPCALCWIN64("fpcalcWin64"),
	FPCALCMACOS32("fpcalcMacOs32"),
	FPCALCMACOS64("fpcalcMacOs64"),
	
	/**
	 * UserAgent property
	 */
	USERAGENT("useragent"),
	
	/**
	 * AcoustId Url property
	 */
	ACOUSTID_URL("url_acoustid"),
	
	/**
	 * AcoustId Client property
	 */
	ACOUSTID_CLIENT("client_acoustid"),
	
	/**
	 * LyricWikia Url property
	 */
	LYRICWIKIA_URL("url_lyricswiki"),
	
	/**
	 * CoverartArchive Url Property
	 */
	COVERART_URL("url_coverart"),
	
	/**
	 * Private dump data endpoint
	 */
	PRIVATE_DUMP_DATA("private_dump_data"),
	
	/**
	 * Private dump data upload endpoint
	 */
	PRIVATE_DUMP_UPLOAD("private_dump_upload"),
	
	/**
	 * Private dump data update endpoint
	 */
	PRIVATE_DUMP_UPDATE("private_dump_update"),
	
	/**
	 * The linked brainz sparql service
	 */
	LINKEDBRAINZ_URL("linked_brainz_service");
	
	/**
	 * The name of the property in the file
	 */
	private String propertyName;
	
	/**
	 * Private constructor for the properties
	 * 
	 * @param propertyName the name of the property in the file
	 */
	private WebProperties(String propertyName) {
		this.propertyName = propertyName;
	}
	
	/**
	 * Method to return the property name in the file 
	 * 
	 * @return the property name in the file
	 */
	public String returnName() {
		return propertyName;
	}
}
