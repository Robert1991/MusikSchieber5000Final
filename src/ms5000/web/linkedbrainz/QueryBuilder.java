package ms5000.web.linkedbrainz;

import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.Query;

import ms5000.properties.PropertiesUtils;

/**
 * This class is used for building queries that are executed by the sparql
 * service Queries are used in the LinkedBrainzUtil class
 * 
 */
public class QueryBuilder {

	/**
	 * Music brainz name space
	 */
	private final static String mBPrefix = PropertiesUtils.getString("sparql.music.brainz.ns");
	private final static String mBRec = PropertiesUtils.getString("sparql.music.brainz.recording");
	private final static String mBArtist = PropertiesUtils.getString("sparql.music.brainz.artist");
	private final static String mBTrack = PropertiesUtils.getString("sparql.music.brainz.track");
	private final static String mBRelease = PropertiesUtils.getString("sparql.music.brainz.release");
	private final static String mBRecord = PropertiesUtils.getString("sparql.music.brainz.record");

	/**
	 * Open vocab name space
	 */
	private final static String oVPrefix = PropertiesUtils.getString("sparql.open.vocab.ns");
	private final static String oVSortLabel = PropertiesUtils.getString("sparql.open.vocab.sort.label");

	/**
	 * FOAF name space
	 */
	private final static String foafPrefix = PropertiesUtils.getString("sparql.foaf.ns");
	private final static String foafTopicOf = PropertiesUtils.getString("sparql.foaf.primary.topic");
	private final static String foafMade = PropertiesUtils.getString("sparql.foaf.made");

	/**
	 * Music ontology name space
	 */
	private final static String moPrefix = PropertiesUtils.getString("sparql.music.ontology.ns");
	private final static String moPubOf = PropertiesUtils.getString("sparql.music.ontology.pub.of");
	private final static String moRecord = PropertiesUtils.getString("sparql.music.ontology.record");
	private final static String moTrackCount = PropertiesUtils.getString("sparql.music.ontology.track.count");
	private final static String moTrack = PropertiesUtils.getString("sparql.music.ontology.track");
	private final static String moTrackNumber = PropertiesUtils.getString("sparql.music.ontology.track.number");;

	/**
	 * PURL name space
	 */
	private final static String purlPrefix = PropertiesUtils.getString("sparql.purl.ns");
	private final static String purlTitle = PropertiesUtils.getString("sparql.purl.title");
	private final static String purlDate = PropertiesUtils.getString("sparql.purl.date");

	/**
	 * MUTO name space
	 */
	private final static String mutoPrefix = PropertiesUtils.getString("sparql.muto.ns");
	private final static String mutoTag = PropertiesUtils.getString("sparql.muto.tag");

	/**
	 * Builds a query for the track title
	 * 
	 * @param trackID
	 *            the track title
	 * 
	 * @return executable query
	 */
	public static Query getTrackTitle(String trackID) {
		ParameterizedSparqlString queryStr = new ParameterizedSparqlString();
		queryStr.append("SELECT ?object");
		queryStr.append("{");

		if (trackID.contains("http://musicbrainz.org/track/")) {
			queryStr.append("<" + trackID + ">" + "<" + purlPrefix + purlTitle + ">" + "?object");
		} else {
			queryStr.append(
					"<" + mBPrefix + mBTrack + trackID + "#_" + ">" + "<" + purlPrefix + purlTitle + ">" + "?object");
		}

		queryStr.append("}");
		Query q = queryStr.asQuery();
		return q;
	}

	/**
	 * Builds a query for the track number
	 * 
	 * @param trackID
	 *            the track id
	 * 
	 * @return executable query
	 */
	public static Query getTrackNumber(String trackID) {
		ParameterizedSparqlString queryStr = new ParameterizedSparqlString();
		queryStr.append("SELECT ?object");
		queryStr.append("{");
		if (trackID.contains("http://musicbrainz.org/track/")) {
			queryStr.append("<" + trackID + ">" + "<" + moPrefix + moTrackNumber + ">" + "?object");
		} else {
			queryStr.append(
					"<" + moPrefix + mBTrack + trackID + "#_" + ">" + "<" + moPrefix + moTrackNumber + ">" + "?object");
		}

		queryStr.append("}");
		Query q = queryStr.asQuery();

		return q;
	}
	
	/**
	 * Builds a query for the track number
	 * 
	 * @param trackID
	 *            the track id
	 * 
	 * @return executable query
	 */
	public static Query dummyQuery() {
		ParameterizedSparqlString queryStr = new ParameterizedSparqlString();
		queryStr.append("SELECT ?subject ?predicate ?object");
		queryStr.append("{");
		queryStr.append("?subject ?predicate ?object");
		queryStr.append("}");
		queryStr.append("LIMIT 1");
		Query q = queryStr.asQuery();

		return q;
	}

	/**
	 * Builds a query for the track id
	 * 
	 * @param recordingID
	 *            the recording id
	 * 
	 * @return executable query
	 */
	public static Query getTrackID(String recordingID) {
		ParameterizedSparqlString queryStr = new ParameterizedSparqlString();
		queryStr.append("SELECT ?subject");
		queryStr.append("{");
		queryStr.append(
				"?subject " + "<" + moPrefix + moPubOf + ">" + "<" + mBPrefix + mBRec + recordingID + "#_" + ">");
		queryStr.append("}");
		Query q = queryStr.asQuery();

		return q;
	}

	/**
	 * Builds a query for the recording title
	 * 
	 * @param recordingID
	 *            the recording id
	 * 
	 * @return executable query
	 */
	public static Query getRecordingTitle(String recordingID) {
		ParameterizedSparqlString queryStr = new ParameterizedSparqlString();
		queryStr.append("SELECT ?object");
		queryStr.append("{");
		queryStr.append(
				"<" + mBPrefix + mBRec + recordingID + "#_" + ">" + "<" + purlPrefix + purlTitle + ">" + "?object");
		queryStr.append("}");
		Query q = queryStr.asQuery();
		return q;
	}

	/**
	 * Builds a query for the artist name
	 * 
	 * @param artistID
	 *            the artist id
	 * 
	 * @return executable query
	 */
	public static Query getArtistName(String artistID) {
		ParameterizedSparqlString queryStr = new ParameterizedSparqlString();
		queryStr.append("SELECT ?object");
		queryStr.append("{");
		queryStr.append(
				"<" + mBPrefix + mBArtist + artistID + "#_" + ">" + "<" + oVPrefix + oVSortLabel + ">" + "?object");
		queryStr.append("}");

		Query q = queryStr.asQuery();

		return q;
	}

	/**
	 * Builds a query for the artist tags
	 * 
	 * @param artistID
	 *            the artist id
	 * 
	 * @return executable query
	 */
	public static Query getArtistTags(String artistID) {
		ParameterizedSparqlString queryStr = new ParameterizedSparqlString();
		queryStr.append("SELECT ?subject");
		queryStr.append("{");
		queryStr.append(
				"?subject" + "<" + mutoPrefix + mutoTag + ">" + "<" + mBPrefix + mBArtist + artistID + "#_" + ">");
		queryStr.append("}");
		Query q = queryStr.asQuery();

		return q;
	}

	/**
	 * Builds a query for the artist wiki link
	 * 
	 * @param artistID
	 *            the artist id
	 * 
	 * @return executable query
	 */
	public static Query getArtistWikiLink(String artistID) {
		ParameterizedSparqlString queryStr = new ParameterizedSparqlString();
		queryStr.append("SELECT ?object");
		queryStr.append("{");
		queryStr.append(
				"<" + mBPrefix + mBArtist + artistID + "#_" + ">" + "<" + foafPrefix + foafTopicOf + ">" + "?object");
		queryStr.append("}");
		Query q = queryStr.asQuery();

		return q;
	}

	/**
	 * Builds a query for the artist id
	 * 
	 * @param releaseID
	 *            the release id
	 * 
	 * @return executable query
	 */
	public static Query getArtistFromRelease(String releaseID) {
		ParameterizedSparqlString queryStr = new ParameterizedSparqlString();
		queryStr.append("SELECT ?subject");
		queryStr.append("{");
		queryStr.append("?subject" + "<" + foafPrefix + foafMade + ">" + "<" + releaseID + ">");
		queryStr.append("}");

		Query q = queryStr.asQuery();
		return q;
	}

	/**
	 * Builds a query for the release title
	 * 
	 * @param releaseID
	 *            the release id
	 * 
	 * @return executable query
	 */
	public static Query getReleaseTitle(String releaseID) {
		ParameterizedSparqlString queryStr = new ParameterizedSparqlString();
		queryStr.append("SELECT ?object");
		queryStr.append("{");

		if (releaseID.contains("http://musicbrainz.org/release/")) {
			queryStr.append("<" + releaseID + ">" + "<" + purlPrefix + purlTitle + ">" + "?object");
		} else {
			queryStr.append("<" + mBPrefix + mBRelease + releaseID + "#_" + ">" + "<" + purlPrefix + purlTitle + ">"
					+ "?object");
		}

		queryStr.append("}");
		Query q = queryStr.asQuery();

		return q;
	}

	/**
	 * Builds a query for the release date
	 * 
	 * @param releaseID
	 *            the release id
	 * 
	 * @return executable query
	 */
	public static Query getReleaseDate(String releaseID) {
		ParameterizedSparqlString queryStr = new ParameterizedSparqlString();
		queryStr.append("SELECT ?object");
		queryStr.append("{");

		if (releaseID.contains("http://musicbrainz.org/release/")) {
			queryStr.append("<" + releaseID + ">" + "<" + purlPrefix + purlDate + ">" + "?object");
		} else {
			queryStr.append("<" + mBPrefix + mBRelease + releaseID + "#_" + ">" + "<" + purlPrefix + purlDate + ">"
					+ "?object");
		}

		queryStr.append("}");
		Query q = queryStr.asQuery();

		return q;
	}

	/**
	 * Builds a query for the recording id
	 * 
	 * @param releaseID
	 *            the release id
	 * 
	 * @return executable query
	 */
	public static Query getRecordId(String releaseID) {
		ParameterizedSparqlString queryStr = new ParameterizedSparqlString();
		queryStr.append("SELECT ?object");
		queryStr.append("{");
		queryStr.append(
				"<" + mBPrefix + mBRelease + releaseID + "#_" + ">" + "<" + mBPrefix + mBRecord + ">" + "?object");
		queryStr.append("}");
		Query q = queryStr.asQuery();

		return q;
	}

	/**
	 * Builds a query for the record id
	 * 
	 * @param trackID
	 *            the track id
	 * 
	 * @return executable query
	 */
	public static Query getRecordFromTrackId(String trackID) {
		ParameterizedSparqlString queryStr = new ParameterizedSparqlString();
		queryStr.append("SELECT ?subject");
		queryStr.append("{");

		if (trackID.contains("http://musicbrainz.org/track/")) {
			queryStr.append("?subject" + "<" + moPrefix + moTrack + ">" + "<" + trackID + ">");
		} else {
			queryStr.append(
					"?subject" + "<" + moPrefix + moTrack + ">" + "<" + mBPrefix + mBTrack + trackID + "#_" + ">");
		}

		queryStr.append("}");

		Query q = queryStr.asQuery();

		return q;
	}

	/**
	 * Builds a query for the record track count
	 * 
	 * @param recordID
	 *            the record id
	 * 
	 * @return executable query
	 */
	public static Query getRecordTrackCount(String recordID) {
		ParameterizedSparqlString queryStr = new ParameterizedSparqlString();
		queryStr.append("SELECT ?object");
		queryStr.append("{");

		if (recordID.contains("http://musicbrainz.org/record/")) {
			queryStr.append("<" + recordID + ">" + "<" + moPrefix + moTrackCount + ">" + "?object");
		} else {
			queryStr.append("<" + mBPrefix + mBRecord + recordID + "#_" + ">" + "<" + moPrefix + moTrackCount + ">"
					+ "?object");
		}

		queryStr.append("}");
		Query q = queryStr.asQuery();

		return q;
	}

	/**
	 * Builds a query for the record title
	 * 
	 * @param recordID
	 *            the record id
	 * 
	 * @return executable query
	 */
	public static Query getRecordTitle(String recordID) {
		ParameterizedSparqlString queryStr = new ParameterizedSparqlString();
		queryStr.append("SELECT ?object");
		queryStr.append("{");

		if (recordID.contains("http://musicbrainz.org/record/")) {
			queryStr.append("<" + recordID + ">" + "<" + purlPrefix + purlTitle + ">" + "?object");
		} else {
			queryStr.append(
					"<" + mBPrefix + mBRecord + recordID + "#_" + ">" + "<" + purlPrefix + purlTitle + ">" + "?object");
		}

		queryStr.append("}");
		Query q = queryStr.asQuery();

		return q;
	}

	/**
	 * Builds a query for the release id
	 * 
	 * @param recordID
	 *            the record id
	 * 
	 * @return executable query
	 */
	public static Query getReleaseIDFromRecord(String recordID) {
		ParameterizedSparqlString queryStr = new ParameterizedSparqlString();
		queryStr.append("SELECT ?subject");
		queryStr.append("{");

		if (recordID.contains("http://musicbrainz.org/record/")) {
			queryStr.append("?subject" + "<" + moPrefix + moRecord + ">" + "<" + recordID + ">");
		} else {
			queryStr.append("?subject" + "<" + moPrefix + moRecord + ">" + "<" + mBPrefix + mBRecord + recordID + ">");
		}

		queryStr.append("}");
		Query q = queryStr.asQuery();

		return q;
	}

}
