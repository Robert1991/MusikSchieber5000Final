package ms5000.musicfile.tag;

import javafx.beans.property.SimpleStringProperty;
import ms5000.musicfile.file.MusicFile;
import ms5000.musicfile.tag.genre.Genre;

/**
 * This class captures the entries which will be found in the music tag
 */
public class MusicTag {
	
	/**
	 * The music file itself
	 */
	private MusicFile musicFile;
	
	/**
	 * The title name
	 */
	private SimpleStringProperty titlename = new SimpleStringProperty("");
	
	/**
	 * The artist
	 */
	private SimpleStringProperty artist = new SimpleStringProperty("");
	
	/**
	 * The album name
	 */
	private SimpleStringProperty album = new SimpleStringProperty("");
	
	/**
	 * The genre
	 */
	private SimpleStringProperty genre = new SimpleStringProperty("");
	
	/**
	 * Comment
	 */
	private String comment = "";

	
	/**
	 * The tag status
	 */
	private TagState status;
	
	/**
	 * The release
	 */
	private Release release = new Release("","","","");
	
	/**
	 * Setter methods
	 */
	public void setTitlename(String titlename) {
		this.titlename.set(titlename);
	}
	
	public void setArtist(String artist) {
		this.artist.set(artist);
	}

	public void setAlbum(String album) {
		this.album.set(album);
	}

	public void setGenre(String genre) {
		if ((genre.length() == 4 || genre.length() == 3) && countCharacters(genre, "(".charAt(0)) == 1
				&& countCharacters(genre, ")".charAt(0)) == 1) {
			this.genre.set(convertGenre(genre));
		} else {
			this.genre.set(genre);
		}
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public TagState getStatus() {
		return status;
	}

	public void setStatus(TagState status) {
		this.status = status;
	}
	

	public void setRelease(Release release) {
		this.release = release;
	}
	
	/**
	 * Getter methods
	 */
	public String getTitlename() {
		return titlename.get();
	}
	
	public String getArtist() {
		return artist.get();
	}
	
	public Release getRelease() {
		return release;
	}
	
	public String getAlbum() {
		return album.get();
	}

	public String getGenre() {
		return genre.get();
	}
		
	public String getComment() {
		return comment;
	}
	
	
	/**
	 * Returns the music file to which the tag belongs
	 * 
	 * @return an object of the music file
	 */
	public MusicFile getMusicFile() {
		return musicFile;
	}
	
	/**
	 * Sets the music file belonging to the tag
	 * 
	 * @param musicFile: the music file object
	 */
	public void setMusicFile(MusicFile musicFile) {
		this.musicFile = musicFile;
	}
	
	/**
	 * Converts the received genre number (e.g. (17)) to the corresponding genre
	 * 
	 * @param genreNumber: looks like (17)
	 * @return the corresponding genre as string
	 */
	private String convertGenre(String genreNumber) {
		Genre gen = Genre.getGenre(genreNumber);

		if (gen != null) {
			return Genre.getGenreName(gen);
		} else {
			return "";
		}
	}
	
	/**
	 * Counts the appearance of the character in the string
	 * 
	 * @param string the string
	 * @param character the character 
	 * @return the amount of appearances of the character in the string
	 */
	private static int countCharacters(String string, char character) {
		int count = 0;
		for (char c : string.toCharArray()) {
			if (c == character) {
				count++;
			}
		}

		return count;
	}
	
	/**
	 * Returns the tag corresponding to the tag type received
	 * 
	 * @param tag: the tag type
	 * @return the corresponding String
	 */
	public String getString(Tags tag) {
		switch (tag) {
		case ALBUM:
			return this.release.getTitle();
		case ARTIST:
			return this.artist.get();
		case ALBUMARTIST:
			return this.release.getAlbumArtist();
		case TITLENAME:
			return this.titlename.get();
		case GENRE:
			return this.genre.get();
		case TOTALTRACKNUMBER:
			return this.getRelease().getTrackCount();
		case TRACKNUMBER:
			return this.getRelease().getTrackNumber();
		case DISCNUMBER:
			return this.getRelease().getDiscNumber();
		case TOTALDISCNUMBER:
			return this.getRelease().getDiscCount();
		case COMMENT:
			return this.comment;
		case COMPOSER:
			return this.getRelease().getComposer();
		case YEAR:
			return "" + this.getRelease().getYear();
		default:
			return "";
		}
	}
}
