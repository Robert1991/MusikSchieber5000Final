package ms5000.musicfile.tag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class to capture the results of the linked brainz web service in an object,
 * which gets mapped into the gui
 */
public class GeneratedTag {

	/**
	 * The main artist
	 */
	private ArrayList<String> mainArtists = new ArrayList<String>();

	/**
	 * The main artist MB id
	 */
	private String mainArtistId = "";

	/**
	 * The main artist wiki link
	 */
	private String wikiLink = "";

	/**
	 * The suggested title names
	 */
	private ArrayList<String> titleNames = new ArrayList<String>();

	/**
	 * The suggested tags
	 */
	private ArrayList<String> tags = new ArrayList<String>();

	/**
	 * Featured artists
	 */
	private ArrayList<String> featureArtists = new ArrayList<String>();

	/**
	 * The suggested releases
	 */
	private ArrayList<Release> relases = new ArrayList<Release>();

	/**
	 * Adds a release to the release list
	 * 
	 * @param relase
	 *            release that gets added
	 */
	public void addRelease(Release release) {
		this.relases.add(release);
	}
	
	/**
	 * Adds a release to the release list
	 * 
	 * @param relase
	 *            release that gets added
	 */
	public void addRelease(ArrayList<Release> releases) {
		this.relases.addAll(releases);
	}
	
	/**
	 * Adds a whole tags to the generated bunch
	 * 
	 * @param tags2 the tag that gets added
	 */
	public void addWholeTags(GeneratedTag tags) {
		if (tags != null) {
			this.addArtist(tags.getMainArtist());
			this.addTag(tags.getTags());
			this.addTitleName(tags.getTitleNames());
			this.addRelease(tags.getRelases());
		}
	}
	
	/**
	 * Adds a featured artist to the featured artist list and clears the
	 * duplicates
	 * 
	 * @param featureArtist
	 *            the featured artist that gets added
	 */
	public void addFeatureArtist(String featureArtist) {
		this.featureArtists.add(featureArtist);
		
		if (featureArtists.size() > 1) {
			this.featureArtists = clearDuplicates(this.featureArtists);
		}

		String duplicateArtist = "";
		for (String artist : featureArtists) {
			if (artist.equals(mainArtists)) {
				duplicateArtist = artist;
			}
		}

		if (!duplicateArtist.equals("")) {
			featureArtists.remove(duplicateArtist);
		}
	}

	/**
	 * Adds a titlename to the titlename list and clears the duplicates
	 * 
	 * @param titleName
	 *            the titlename that gets added
	 */
	public void addTitleName(String titleName) {
		this.titleNames.add(titleName);
		
		if (this.titleNames.size() > 1) {
			this.titleNames = clearDuplicates(this.titleNames);
		}
		
	}
	
	/**
	 * Adds a titlename to the titlename list and clears the duplicates
	 * 
	 * @param titleNames
	 *            the titlename that gets added
	 */
	public void addTitleName(ArrayList<String> titleNames) {
		this.titleNames.addAll(titleNames);
		
		if (this.titleNames.size() > 1) {
			this.titleNames = clearDuplicates(this.titleNames);
		}
		
	}

	/**
	 * Adds a tag to the tags list and clears the duplicates
	 * 
	 * @param tag
	 *            the tag that gets added
	 */
	public void addTag(String tag) {
		this.tags.add(tag);
		
		if (tags.size() > 1) {
			this.tags = clearDuplicates(this.tags);	
		}
		
	}
	
	/**
	 * Adds a tag to the tags list and clears the duplicates
	 * 
	 * @param tag
	 *            the tag that gets added
	 */
	public void addTag(ArrayList<String> tags) {
		this.tags.addAll(tags);
		
		if (tags.size() > 1) {
			this.tags = clearDuplicates(this.tags);	
		}
		
	}

	/**
	 * Adds a tag to the tags list and clears the duplicates
	 * 
	 * @param tag
	 *            the tag that gets added
	 */
	public void addArtist(String artist) {
		this.mainArtists.add(artist);
		
		if (mainArtists.size() > 1) {
			this.mainArtists = clearDuplicates(this.mainArtists);	
		}
		
	}
	
	/**
	 * Adds a tag to the tags list and clears the duplicates
	 * 
	 * @param tag
	 *            the tag that gets added
	 */
	public void addArtist(ArrayList<String> artists) {
		this.mainArtists.addAll(artists);
		
		if (tags.size() > 1) {
			this.mainArtists = clearDuplicates(this.mainArtists);	
		}
		
	}
	
	
	/**
	 * Clears the duplicates out of the received list
	 * 
	 * @param list the list
	 * @return duplicate free list
	 */
	private ArrayList<String> clearDuplicates(ArrayList<String> list) {
		
		List<String> al = new ArrayList<String>();
		ArrayList<String> newList = new ArrayList<String>();
		
		for (int i = 0; i < list.size(); i++) {
			String alEntry = list.get(i);
			alEntry = alEntry.replaceAll(" ","");
			alEntry = alEntry.replaceAll("-","");
			alEntry = alEntry.replaceAll("&","");
			alEntry = alEntry.replaceAll("/","");
			alEntry = alEntry.toLowerCase();
			al.add(alEntry);
		}
		
		Set<String> hs = new HashSet<>();
		hs.addAll(al);
		al.clear();
		al.addAll(hs);
		

		for (int i = 0; i < list.size(); i++) {
			String entryList = list.get(i);
			
			entryList = entryList.replaceAll("-","");
			entryList = entryList.replaceAll("&","");
			entryList = entryList.replaceAll("/","");
			entryList = entryList.toLowerCase();
			
			for (String entry : al) {
				if (entry.equals(entryList)) {
					newList.add(list.get(i));
				}
			}
		}
		
		return list;
	}

	/**
	 * Delivers a readable string containing all the entries
	 */
	public String toString() {
		String toString = "Titles: " + titleNames.toString() + "\n";
		toString += "Main Artist: " + mainArtists + "\n";
		toString += "Feature Artists: " + featureArtists.toString() + "\n";
		toString += "Releases:" + "\n";
		;

		for (Release rel : this.getRelases()) {
			toString += rel.toString() + "\n";
		}

		toString += "Main Artist Wikilink: " + this.getWikiLink();

		return toString;
	}

	/*
	 * General Getter and Setter methods
	 */
	
	public ArrayList<String> getTitleNames() {
		return titleNames;
	}
	
	public void setTitleNames(ArrayList<String> titleNames) {
		this.titleNames = titleNames;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

	public ArrayList<String> getFeatureArtists() {
		return featureArtists;
	}

	public void setFeatureArtists(ArrayList<String> featureArtists) {
		this.featureArtists = featureArtists;
	}

	public ArrayList<Release> getRelases() {
		return relases;
	}
	
	public void setRelases(ArrayList<Release> releases) {
		this.relases = releases;
	}


	public String getMainArtistId() {
		return mainArtistId;
	}

	public void setMainArtistId(String mainArtistId) {
		this.mainArtistId = mainArtistId;
	}

	public String getWikiLink() {
		return wikiLink;
	}

	public void setWikiLink(String wikiLink) {
		this.wikiLink = wikiLink;
	}

	public ArrayList<String> getMainArtist() {
		return mainArtists;
	}

}
