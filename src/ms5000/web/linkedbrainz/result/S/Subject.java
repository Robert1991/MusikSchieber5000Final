package ms5000.web.linkedbrainz.result.S;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Subject {

	@SerializedName("type")
	@Expose
	private String type;
	@SerializedName("value")
	@Expose
	private String value;

	/**
	 * 
	 * @return The type
	 */
	public String getType() {
		return type;
	}

	/**
	 * 
	 * @return The value
	 */
	public String getValue() {
		return value;
	}

}