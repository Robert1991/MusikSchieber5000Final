package ms5000.web.acusticid;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Class to store the meta data ids received from the acoustid web service
 */
public class MetaDataIDs {
	
	/**
	 * Boolean indicating wheter the object is empty
	 */
	private boolean isEmpty = false;
	
	
	/**
	 * Boolean indicating wheter there are featured artists
	 */
	private boolean feature = false;
	
	/**
	 * List for storing the feature artist ids
	 */
	private ArrayList<String> featureArtists = new ArrayList<String>();
	
	/**
	 * List for storing the recording ids
	 */
	private ArrayList<String> recordings = new ArrayList<String>();
	
	/**
	 * List for storing the release ids
	 */
	private ArrayList<String> releases = new ArrayList<String>();
	
	/**
	 * String for storing the lead artist id
	 */
	private String leadArtist = "";

	/**
	 * Method to add artist ids
	 * 
	 * @param artist the artist id that gets stored
	 */
	public void addArtist(String artist) {
		if (leadArtist.equals("") && featureArtists.size() == 0) {
			this.leadArtist = artist;
		} else {
			this.featureArtists.add(artist);
			featureArtists = removeDuplicates(featureArtists);
			this.feature = true;
		}

	}
	
	/**
	 * Method to add recording ids
	 * 
	 * @param recording the recording id that gets stored
	 */
	public void addRecordings(String recording) {
		this.recordings.add(recording);
		recordings = removeDuplicates(recordings);
	}

	
	/**
	 * Method to add release ids
	 * 
	 * @param release the release id that gets stored
	 */
	public void addReleases(String release) {
		this.releases.add(release);
		releases = removeDuplicates(releases);
	}
	
	/**
	 * Clears the duplicates out of the received list
	 * 
	 * @param list the list
	 * @return duplicate free list
	 */
	private ArrayList<String> removeDuplicates(ArrayList<String> list) {
		Set<String> s = new HashSet<String>(list);
		list.clear();

		for (Object string : s.toArray()) {
			list.add((String) string);
		}

		return list;
	}

	/*
	 * General getter methods
	 */
	public ArrayList<String> getFeatureArtists() {
		return featureArtists;
	}

	public ArrayList<String> getRecordings() {
		return recordings;
	}

	public ArrayList<String> getReleases() {
		return releases;
	}

	public boolean isFeature() {
		return feature;
	}


	public String getLeadArtist() {
		return leadArtist;
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}
}
