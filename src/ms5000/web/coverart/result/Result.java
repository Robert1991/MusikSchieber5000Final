package ms5000.web.coverart.result;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Generated to store the result received from the cover art archive
 */
@Generated("org.jsonschema2pojo")
public class Result {
	@SerializedName("images")
	@Expose
	private List<Image> images = new ArrayList<Image>();
	@SerializedName("release")
	@Expose
	private String release;

	/**
	 * 
	 * @return The images
	 */
	public List<Image> getImages() {
		return images;
	}

	/**
	 * 
	 * @return The release
	 */
	public String getRelease() {
		return release;
	}

}
