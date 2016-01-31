package ms5000.properties.linkedbrainz;

/**
 * This enumeration captures the properties needed for the music library import
 */
public enum LinkedBrainzProperty {
	
	/**
	 * Determines the way feature artits are treated
	 */
	FEATUREPROPERTY("feature_artist_support"),
	
	/**
	 * Enables/Disables the coverart load
	 */
	COVERART("coverart"),
	
	/**
	 * Enables/Disables the smart mode
	 */
	SMARTMODE("smart_mode"),
	
	/**
	 * Enables/Disables loading compilations
	 */
	COMPILATIONS("compilations"),
	
	/**
	 * Enables/Disables linked brainz support
	 */
	LINKEDBRAINZSUPPORT("linked_brainz_support");
	
	/**
	 * The property name in the file
	 */
	private String propertyName;
	
	/**
	 * Private constructor for the properties
	 * 
	 * @param propertyName the name of the property in the file
	 */
	private LinkedBrainzProperty(String propertyName) {
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
