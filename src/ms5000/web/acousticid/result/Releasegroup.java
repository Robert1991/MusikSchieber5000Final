package ms5000.web.acousticid.result;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Generated class to capture the result of the acoustid web service
 */
@Generated("org.jsonschema2pojo")
public class Releasegroup {

	@SerializedName("releases")
	@Expose
	private List<Release> releases = new ArrayList<Release>();
	@SerializedName("title")
	@Expose
	private String title;
	@SerializedName("secondarytypes")
	@Expose
	private List<String> secondarytypes = new ArrayList<String>();
	@SerializedName("artists")
	@Expose
	private List<Artist> artists = new ArrayList<Artist>();
	@SerializedName("type")
	@Expose
	private String type;
	@SerializedName("id")
	@Expose
	private String id;

	/**
	 * 
	 * @return The releases
	 */
	public List<Release> getReleases() {
		return releases;
	}

	/**
	 * 
	 * @return The title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 
	 * @return The secondarytypes
	 */
	public List<String> getSecondarytypes() {
		return secondarytypes;
	}

	/**
	 * 
	 * @return The artists
	 */
	public List<Artist> getArtists() {
		return artists;
	}

	/**
	 * 
	 * @return The type
	 */
	public String getType() {
		return type;
	}

	/**
	 * 
	 * @return The id
	 */
	public String getId() {
		return id;
	}

}
