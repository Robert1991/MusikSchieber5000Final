package ms5000.web.coverart.result;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Generated to store the result received from the cover art archive
 */
@Generated("org.jsonschema2pojo")
public class Thumbnails {
	@SerializedName("large")
	@Expose
	private String large;
	@SerializedName("small")
	@Expose
	private String small;

	/**
	 * 
	 * @return The large
	 */
	public String getLarge() {
		return large;
	}

	/**
	 * 
	 * @return The small
	 */
	public String getSmall() {
		return small;
	}

}
