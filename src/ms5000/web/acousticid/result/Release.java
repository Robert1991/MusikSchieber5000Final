
package ms5000.web.acousticid.result;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Generated class to capture the result of the acoustid web service
 */
@Generated("org.jsonschema2pojo")
public class Release {

	@SerializedName("id")
	@Expose
	private String id;

	/**
	 * 
	 * @return The id
	 */
	public String getId() {
		return id;
	}
}