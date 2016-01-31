package ms5000.web.linkedbrainz;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;

import com.google.gson.Gson;

import ms5000.musicfile.tag.GeneratedTag;
import ms5000.musicfile.tag.Release;
import ms5000.properties.PropertiesUtils;
import ms5000.properties.linkedbrainz.LinkedBrainzProperty;
import ms5000.properties.web.WebProperties;
import ms5000.web.acusticid.MetaDataIDs;
import ms5000.web.coverart.CoverArt;
import ms5000.web.coverart.Request;
import ms5000.web.coverart.result.Image;
import ms5000.web.coverart.result.Result;
import ms5000.web.linkedbrainz.result.O.ObjectResult;
import ms5000.web.linkedbrainz.result.S.SubjectResult;

/**
 * This class is for querying the sparql service and generating a recommandation tag
 */
public class LinkedBrainzUtil {
	
	/**
	 * The sparql service url
	 */
	private final static String sparqlService = PropertiesUtils.getProperty(WebProperties.LINKEDBRAINZ_URL);
	
	/**
	 * Music brainz name space
	 */
	private final static String mBNS = PropertiesUtils.getString("sparql.music.brainz.ns");
	private final static String mBRel = PropertiesUtils.getString("sparql.music.brainz.release");
	private final static String mBArt = PropertiesUtils.getString("sparql.music.brainz.artist");
	private final static String mBTag = PropertiesUtils.getString("sparql.music.brainz.tag");
	
	/**
	 * Method to generate a tag containing recommedations for everything
	 * 
	 * @param ids the id meta data package received from the music file
	 * @return the tag
	 */
	public static GeneratedTag getTag(MetaDataIDs ids) {
		GeneratedTag tag = new GeneratedTag();
		buildFullReleases(tag,ids);
		
		// Adding additional not full releases
		for (String releaseId : ids.getReleases()) {
			String releaseTitle = getObjectString(QueryBuilder.getReleaseTitle(releaseId));
			String releaseDate = getObjectString(QueryBuilder.getReleaseDate(releaseId));
			if (releaseTitle != null && releaseDate != null) {
				if (!releaseTitle.equals("")) {
					boolean take = true;
					for (Release rel : tag.getRelases()) {
						if (rel.getTitle().equals(releaseTitle)) {
							take = false;
						}
					}
					
					if (take == true) {
						Release rel = new Release(releaseTitle, releaseDate, "", "");
						rel.setReleaseId(mBNS + mBRel + releaseId + "#_");		
						tag.addRelease(rel);
					}
					
				}
			}
		}
		
		// Checking if there is a title, if not try it with the recording titles
		for (String recordingID : ids.getRecordings()) {
			String recTitle = getObjectString(QueryBuilder.getRecordingTitle(recordingID));
			tag.addTitleName(recTitle);
		}

		// Getting the main artist
		String mainArtistName = getObjectString(QueryBuilder.getArtistName(ids.getLeadArtist()));
		
		if (mainArtistName.contains(",")) {
			mainArtistName = convertArtistName(mainArtistName);
		} 
		
		
		tag.addArtist(mainArtistName);
		tag.setMainArtistId(ids.getLeadArtist());
		
		// Setting the feature Artists if necessary
		if (PropertiesUtils.getProfile().getFeatureArtistSupport() != 3) {
			setFeatureArtists(mainArtistName,ids,tag);
		}
		
		// Sorting out the main releases and adding the album artist
		boolean takeCompilations = true;
		
		try {
			takeCompilations = Boolean.parseBoolean(PropertiesUtils.getProperty(LinkedBrainzProperty.COMPILATIONS));
		} catch (NumberFormatException e) {
			takeCompilations = true;
		}
		
		ArrayList<Release> mainReleases = new ArrayList<Release>();
		
		for (Release entry : tag.getRelases()) {
			if (entry.getReleaseId() != null) {
				String artistId = getSubjectString(QueryBuilder.getArtistFromRelease(entry.getReleaseId()));
				String albumArtist = getObjectString(QueryBuilder.getArtistName(ids.getLeadArtist()));

				if (albumArtist.contains(",")) {
					albumArtist = convertArtistName(albumArtist);
				}

				entry.setAlbumArtist(albumArtist);
				if (artistId.contains(tag.getMainArtistId())) {
					entry.setMainRelease(true);
					mainReleases.add(entry);
				} 
			}
			
			
		}
		
		// Only set main releases
		if (!takeCompilations) {
			tag.setRelases(mainReleases);
		}
		
		// getting the tags
		ArrayList<String> tags = new ArrayList<String>();
		tags = getSubjectString(QueryBuilder.getArtistTags(tag.getMainArtistId()), tags);
		for (String tagName : tags) {
			tagName = convertTagUrl(tagName, tag.getMainArtistId());
			tag.addTag(tagName);
		}
		
		// Getting the wiki data of the main artist
		String wikiLink = getObjectString(QueryBuilder.getArtistWikiLink(tag.getMainArtistId()));
		if (wikiLink != null && !wikiLink.equals("")) {
			tag.setWikiLink(wikiLink);
		}
		
		// Getting the Coverart
		if (Boolean.parseBoolean(PropertiesUtils.getProperty(LinkedBrainzProperty.COVERART)) == true) {
			getCoverArt(tag);
		}
	
		return tag;
	}
	
	/**
	 * Method to set the feature artists in the title or the artist name
	 * 
	 * @param mainArtistName the main artist
	 * @param ids the meta data ids
	 * @param tag the tag
	 */
	private static void setFeatureArtists(String mainArtistName, MetaDataIDs ids, GeneratedTag tag) {
		// Getting the feature artists
		for (String artistId : ids.getFeatureArtists()) {
			String artistName = getObjectString(QueryBuilder.getArtistName(artistId));

			if (artistName.contains(",")) {
				artistName = convertArtistName(artistName);
			}
			tag.addFeatureArtist(artistName);

		}

		// Setting the feature artists to the track or artist name
		if (tag.getFeatureArtists().size() > 0) {

			boolean featAppended = false;

			if (PropertiesUtils.getProfile().getFeatureArtistSupport() == 1) {
				for (String featureArtist : tag.getFeatureArtists()) {
					if (!featAppended) {
						featAppended = true;
						mainArtistName += " feat. ";
					}

					if (!mainArtistName.contains(featureArtist)) {
						mainArtistName += featureArtist + ", ";
					}
				}

				mainArtistName = mainArtistName.substring(0, mainArtistName.length() - 2);
			} else if (PropertiesUtils.getProfile().getFeatureArtistSupport() == 2) {
				ArrayList<String> trackTitles = new ArrayList<String>();

				for (String trackTitle : tag.getTitleNames()) {
					for (String featureArtist : tag.getFeatureArtists()) {
						if (!featAppended) {
							featAppended = true;
							trackTitle += " feat. ";
						}
						if (!featureArtist.equals(mainArtistName)) {
							trackTitle += featureArtist + " ,";
						}
					}
					trackTitle = trackTitle.substring(0, trackTitle.length() - 1);
					trackTitles.add(trackTitle);
					featAppended = false;
				}

				tag.setTitleNames(trackTitles);
				tag.addArtist(mainArtistName);
				tag.setMainArtistId(ids.getLeadArtist());
			}
		}
	}
	
	
	/**
	 * Method to get the cover art for the main releases stored in the tag
	 * 
	 * @param tag the tag to which the coverart url's are loaded
	 */
	private static void getCoverArt(GeneratedTag tag) {
		// Getting the Coverart
		for (Release rel : tag.getRelases()) {
			if (rel.isMainRelease()) {
				Request req = new Request();
				String releaseId = rel.getReleaseId().replace(mBNS + mBRel, "");
				releaseId += releaseId.replace("#_", "");

				req.setReleaseID(releaseId);

				try {
					Result res = CoverArt.lookupCoverArt(req);

					if (res != null && res.getImages().size() > 0) {

						for (Image img : res.getImages()) {
							if (img.getThumbnails() != null) {
								if (img.getThumbnails().getLarge() != null) {
									rel.setCoverArtImageUrl(img.getThumbnails().getLarge());
								} else {
									if (img.getImage() != null) {
										if (img.getFront() == true) {
											rel.setCoverArtImageUrl(img.getImage());
										}
									}
								}
							} else if (img.getImage() != null) {
								if (img.getFront() == true) {
									rel.setCoverArtImageUrl(img.getImage());
								}
							}
						}
					}
				} catch (IOException e) {
					rel.setCoverArtImageUrl("");
				}
			}
		}

	}
	
	/**
	 * Method to build full releases with the sparql service
	 * 
	 * @param tag the tag where the releases are stored
	 * @param ids the id meta data
	 * 
	 * @return string containing the release names
	 */
	private static String buildFullReleases (GeneratedTag tag, MetaDataIDs ids) {
		ArrayList<String> trackIds = new ArrayList<String>();
		String releaseTitles = "";
		// Start with trackIds
		for (String recId : ids.getRecordings()) {
			ArrayList<String> trackIdRec = new ArrayList<String>();
			trackIdRec = getSubjectString(QueryBuilder.getTrackID(recId), trackIdRec);
			trackIds.addAll(trackIdRec);
		}

		for (String trackId : trackIds) {
			String trackNumber = getObjectString(QueryBuilder.getTrackNumber(trackId));
			String recordId = getSubjectString(QueryBuilder.getRecordFromTrackId(trackId));
			String trackCount = getObjectString(QueryBuilder.getRecordTrackCount(recordId));
			String releaseId = getSubjectString(QueryBuilder.getReleaseIDFromRecord(recordId));
			String releaseName = getObjectString(QueryBuilder.getReleaseTitle(releaseId));
			String year = getObjectString(QueryBuilder.getReleaseDate(releaseId));
			String title = getObjectString(QueryBuilder.getTrackTitle(trackId));

			if (title != null) {
				if (!title.equals("")) {
					tag.addTitleName(title);
				}
			}

			// Check for duplicates
			if (!releaseTitles.contains(releaseName)) {
				releaseTitles += releaseName;
				if (!releaseName.equals("") && releaseName != null) {
					Release rel = new Release(releaseName, year, trackNumber, trackCount);
					rel.setReleaseId(releaseId);
					tag.addRelease(rel);
				}
			}
		}

		return releaseTitles;
	}
	
	/**
	 * Method to query the sparql service and receive a single string
	 * 
	 * @param q the query which delivers an object
	 * @return the object string
	 */
	private static String getObjectString(Query q) {
		String json = "";
		String object = "";

		QueryExecution e = QueryExecutionFactory.sparqlService(sparqlService, q);
		ResultSet rs = e.execSelect();
		JsonOutputStreamProvider stream = new JsonOutputStreamProvider();

		ResultSetFormatter.outputAsJSON(stream, rs);
		json = stream.toString();
		ObjectResult obj = getResultObject(json);
		
		if (obj.getResults().getBindings().size() > 0) {
			if (obj.getResults().getBindings().get(0).getObject().getValue() != null) {
				object = obj.getResults().getBindings().get(0).getObject().getValue();
			}
		}
		

		return object;

	}
	
	/**
	 * Method to query the sparql service and receive a single string
	 * 
	 * @param q the query which delivers an subject
	 * @return the subject string
	 */
	private static String getSubjectString(Query q) {
		QueryExecution e = QueryExecutionFactory.sparqlService(sparqlService, q);
		
		ResultSet rs=e.execSelect();
		String recordingID = "";
		String json = "";
        JsonOutputStreamProvider stream = new JsonOutputStreamProvider();
        
        ResultSetFormatter.outputAsJSON(stream,rs);
        json = stream.toString();
        SubjectResult obj = getResultSubject(json);
        
        if (obj.getResults().getBindings().size() > 0) {
        	if (obj.getResults().getBindings().get(0).getSubject().getValue() != null) {
            	recordingID = obj.getResults().getBindings().get(0).getSubject().getValue();
            }
        }
        
        return recordingID;
        
	}
	
	/**
	 * Method to query the sparql service and receive a multiple strings
	 * 
	 * @param q the query which delivers multiple subjects
	 * @return the subject strings
	 */
	private static ArrayList<String> getSubjectString(Query q, ArrayList<String> trackIds) {
		QueryExecution e = QueryExecutionFactory.sparqlService(sparqlService, q);
		
		ResultSet rs=e.execSelect();
        
		String json = "";
        JsonOutputStreamProvider stream = new JsonOutputStreamProvider();
        
        ResultSetFormatter.outputAsJSON(stream,rs);
        json = stream.toString();
        SubjectResult obj = getResultSubject(json);
        
        if (obj.getResults().getBindings().size() > 0) {

        	for (ms5000.web.linkedbrainz.result.S.Binding result : obj.getResults().getBindings()) {
        		trackIds.add(result.getSubject().getValue());
        	}
        }
        
        return trackIds;
        
	}
	
	/**
	 * Method to convert a string representing a json object in an actual object 
	 * @param json the string representing the json object
	 * @return an object of Subject Result
	 */
	private static SubjectResult getResultSubject(String json) {
		Gson gson = new Gson();
		SubjectResult results = gson.fromJson(json,SubjectResult.class);
		return results;
	}
	
	/**
	 * Method to convert a string representing a json object in an actual object 
	 * @param json the string representing the json object
	 * @return an object of Object Result
	 */
	private static ObjectResult getResultObject(String json) {
		Gson gson = new Gson();
		ObjectResult results = gson.fromJson(json,ObjectResult.class);
		return results;
	}
	
	/**
	 * Method to convert the artist name from Huber, Helmut to Helmut Huber
	 * @param artistName the artist name
	 * @return the converted artist name
	 */
	private static String convertArtistName(String artistName) {
		String familyName = artistName.substring(0, artistName.indexOf(","));
		String name = artistName.substring(artistName.indexOf(",")+2,artistName.length());
		
		String artistName_ = name.substring(0,1).toUpperCase() + name.substring(1,name.length());
		
		if (artistName.length() > artistName.indexOf(",") + 1) {
			artistName_ += " " + familyName.substring(0,1).toUpperCase() + familyName.substring(1,familyName.length());
		}
	
		return artistName_;
	}
	
	/**
	 * Method to convert the tag from http://test.com/artist/tag/pop%20rock to Pop Rock
	 * @param tagUrl the tag url
	 * @param mainArtistId the corresponding artist id
	 * @return the converted tag
	 */
	private static String convertTagUrl(String tagUrl, String mainArtistId) {
		String tagName = tagUrl.replace(mBNS + mBArt + mainArtistId + mBTag, "");
		
		if (tagName.contains("%20")) {
			while (tagName.contains("%")) {
				int index = tagName.indexOf("%");
				tagName = tagName.replaceFirst("%20", " ");
				
				if (!tagName.contains("%")) {
					tagName = tagName.substring(0, index + 1)
							+ tagName.substring(index + 1, index + 2).toUpperCase()
							+ tagName.substring(index + 2, tagName.length());  
				} else {
					tagName = tagName.substring(0, index + 1)
							+ tagName.substring(index + 1, index + 2).toUpperCase()
							+ tagName.substring(index + 2, tagName.length());
				}
				
				tagName = tagName.substring(0,1).toUpperCase() + tagName.substring(1,tagName.length());
			}
		} else {
			tagName = tagName.substring(0,1).toUpperCase() + tagName.substring(1,tagName.length());
		}
		
		if (tagName.contains("/")) {
			tagName = tagName.substring(0, tagName.indexOf("/"))
					+ tagName.substring(tagName.indexOf("/"), tagName.indexOf("/") + 2).toUpperCase()
					+ tagName.substring(tagName.indexOf("/") + 2, tagName.length());
		}
		
		if (tagName.contains("&")) {
			tagName = tagName.substring(0, tagName.indexOf("&"))
					+ tagName.substring(tagName.indexOf("&"), tagName.indexOf("&") + 2).toUpperCase()
					+ tagName.substring(tagName.indexOf("&") + 2, tagName.length());
		}
		
		
		return tagName;
	}
	
	/**
	 * Sending out an dummy query to check whether the ws is online
	 */
	public static boolean checkConnection() {
		try {
			QueryExecution e = QueryExecutionFactory.sparqlService(sparqlService, QueryBuilder.dummyQuery());
			e.execSelect();
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
} 
