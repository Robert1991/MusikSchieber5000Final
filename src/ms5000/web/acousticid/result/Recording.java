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
public class Recording {

	@SerializedName("releases")
	@Expose
	private List<Release> releases = new ArrayList<Release>();
	@SerializedName("artists")
	@Expose
	private List<Artist> artists = new ArrayList<Artist>();
	@SerializedName("duration")
	@Expose
	private Integer duration;
	@SerializedName("title")
	@Expose
	private String title;
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
	 * @param releases
	 *            The releases
	 */
	public void setReleases(List<Release> releases) {
		this.releases = releases;
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
	 * @param artists
	 *            The artists
	 */
	public void setArtists(List<Artist> artists) {
		this.artists = artists;
	}

	/**
	 * 
	 * @return The duration
	 */
	public Integer getDuration() {
		return duration;
	}

	/**
	 * 
	 * @param duration
	 *            The duration
	 */
	public void setDuration(Integer duration) {
		this.duration = duration;
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
	 * @param title
	 *            The title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 
	 * @return The id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 *            The id
	 */
	public void setId(String id) {
		this.id = id;
	}

}