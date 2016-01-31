package ms5000.musicfile.tag;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.jaudiotagger.tag.datatype.Artwork;

/**
 * Class to capture the results of the linked brainz web service for the
 * releases
 */
public class Release {

	/**
	 * the release title
	 */
	private String title = "";

	/**
	 * the release year
	 */
	private String year = "";

	/**
	 * the tracknumber of the recording on the release
	 */
	private String trackNumber = "";

	/**
	 * the release track count
	 */
	private String trackCount = "";

	/**
	 * the release MB id
	 */
	private String releaseId = "";

	/**
	 * the release album artist
	 */
	private String albumArtist = "";

	/**
	 * the release coverart url
	 */
	private String coverArtImageUrl = "";

	/**
	 * boolean indicating whether the release is a main release
	 */
	private boolean mainRelease = false;
	
	/**
	 * boolean indicating wheter the release is a user release
	 */
	private boolean userRelease = false;
	
	/**
	 * The disc number
	 */
	private String discNumber;
	
	/**
	 * The disc count
	 */
	private String discCount;
	
	/**
	 * The composer
	 */
	private String composer;
	
	/**
	 * Artwork
	 */
	private Artwork artwork;
	
	/**
	 * Constructs the release object
	 * 
	 * @param title the release title
	 * @param year the release year
	 * @param trackNumber the tracknumber of the recording on the release
	 * @param trackCount the release track count
	 */
	public Release(String title, String year, String trackNumber, String trackCount) {
		this.title = title;
		this.year = year;
		this.trackNumber = trackNumber;
		this.trackCount = trackCount;
	}
	
	public Release() {
		
	}

	/**
	 * to string method
	 */
	public String toString() {
		return this.title;
	}
	
	public boolean equals(Release rel) {
		if (this.title.toLowerCase().equals(rel.title.toLowerCase())) {
			return true;
		} else {
			return false;
		}
	}
	
	public int compareTo(Release rel) {
		int scoreThis = calculateScore(this);
		int scoreRel = calculateScore(rel);
		
		if (scoreRel == scoreThis) {
			return 0;
		} else if (scoreRel > scoreThis) {
			return -1;
		} else {
			return 1;
		}
	}
	
	private int calculateScore(Release rel) {
		int score = 0;
		
		if (!rel.getTitle().equals("")) {
			score += 3;
		}
		
		if (!rel.getTrackNumber().equals("")) {
			score += 2;
		}
		
		if (!rel.getTrackCount().equals("")) {
			score += 2;
		}
		
		if (!rel.getCoverArtImageUrl().equals("")) {
			score += 1;
		}
		
		if (!rel.getYear().equals("")) {
			score += 1;
		}
		
		return score;
	}
	
	/*
	 * General getter and setter methods
	 */
	public String getReleaseId() {
		return releaseId;
	}

	public void setReleaseId(String releaseId) {
		this.releaseId = releaseId;
	}

	public String getAlbumArtist() {
		return albumArtist;
	}

	public void setAlbumArtist(String albumArtist) {
		this.albumArtist = albumArtist;
	}

	public String getCoverArtImageUrl() {
		return coverArtImageUrl;
	}

	public void setCoverArtImageUrl(String coverArtImageUrl) {
		this.coverArtImageUrl = coverArtImageUrl;
		
		URL url;
		try {
			url = new URL(coverArtImageUrl);
			BufferedImage c = ImageIO.read(url);
			File tmp = new File("tmp.jpeg");
			ImageIO.write(c, "jpeg", tmp);
			this.artwork = Artwork.createArtworkFromFile(tmp);
			tmp.delete();
		} catch (MalformedURLException e1) {
			artwork = null;
		} catch (IOException e) {
			artwork = null;
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getTrackNumber() {
		return trackNumber;
	}

	public void setTrackNumber(String trackNumber) {
		this.trackNumber = trackNumber;
	}

	public String getTrackCount() {
		return trackCount;
	}

	public void setTrackCount(String trackCount) {
		this.trackCount = trackCount;
	}

	public boolean isMainRelease() {
		return mainRelease;
	}

	public void setMainRelease(boolean mainRelease) {
		this.mainRelease = mainRelease;
	}

	public boolean isUserRelease() {
		return userRelease;
	}

	public void setUserRelease(boolean userRelease) {
		this.userRelease = userRelease;
	}

	public Artwork getArtwork() {
		return artwork;
	}

	public void setArtwork(Artwork artwork) {
		this.artwork = artwork;
	}

	public String getDiscNumber() {
		return discNumber;
	}

	public void setDiscNumber(String discNumber) {
		this.discNumber = discNumber;
	}

	public String getDiscCount() {
		return discCount;
	}

	public void setDiscCount(String discCount) {
		this.discCount = discCount;
	}

	public String getComposer() {
		return composer;
	}

	public void setComposer(String composer) {
		this.composer = composer;
	}
}
