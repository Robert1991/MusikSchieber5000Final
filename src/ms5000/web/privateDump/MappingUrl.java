package ms5000.web.privateDump;

import ms5000.properties.PropertiesUtils;

/**
 * This enum has all the owl settings which are needed for 
 * reading and commiting the meta information to the MS5000
 * dump
 */
public enum MappingUrl {
	/**
	 * The name space
	 */
	OWLNS(PropertiesUtils.getString("sparql.lb.ns")),
	
	/**
	 * The prefix
	 */
	PREFIX(PropertiesUtils.getString("sparql.lb.prefix")),
	
	/**
	 * The artist relation
	 */
	ARTIST(PropertiesUtils.getString("sparql.lb.artist")),
	
	/**
	 * The title relation
	 */
	TITLE(PropertiesUtils.getString("sparql.lb.title")),
	
	/**
	 * The comment relation
	 */
	COMMENT(PropertiesUtils.getString("sparql.lb.comment")),
	
	/**
	 * The album artist relation
	 */
	ALBUMARTIST(PropertiesUtils.getString("sparql.lb.album.artist")),
	
	/**
	 * The cover url relation
	 */
	COVERURL(PropertiesUtils.getString("sparql.lb.cover.url")),
	
	/**
	 * The track count relation
	 */
	TRACKCOUNT(PropertiesUtils.getString("sparql.lb.track.count")),
	
	/**
	 * The track number relation
	 */
	TRACKNUMBER(PropertiesUtils.getString("sparql.lb.track.num")),
	
	/**
	 * The year relation
	 */
	YEAR(PropertiesUtils.getString("sparql.lb.year")),
	
	/**
	 * The album relation
	 */
	ALBUM(PropertiesUtils.getString("sparql.lb.album")),
	
	/**
	 * The genre relation
	 */
	GENRE(PropertiesUtils.getString("sparql.lb.genre")),
	
	/**
	 * The composer relation
	 */
	COMPOSER(PropertiesUtils.getString("sparql.lb.composer")),
	
	/**
	 * The disc number relation
	 */
	DISCNUM(PropertiesUtils.getString("sparql.lb.disc.num")),
	
	/**
	 * The total disc number relation
	 */
	TOTDISC(PropertiesUtils.getString("sparql.lb.disc.tot")),
	
	/**
	 * The tag relation
	 */
	TAG(PropertiesUtils.getString("sparql.lb.tag"));
	
	/**
	 * String representing the relation
	 */
	private String relation;
	
	/**
	 * Instantiates the Mapping Url enum object 
	 * 
	 * @param relation the string for the relation
	 */
	private MappingUrl(String relation) {
		this.relation = relation;
	}
	
	/**
	 * Returns the string representation of the relation
	 */
	public String toString() {
		return this.relation;
	}
}
