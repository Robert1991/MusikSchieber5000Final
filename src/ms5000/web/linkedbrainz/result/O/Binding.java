package ms5000.web.linkedbrainz.result.O;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Generated class to capture the result of the linked brainz web service
 */
@Generated("org.jsonschema2pojo")
public class Binding {

	@SerializedName("object")
	@Expose
	private Object_ object;

	/**
	 * 
	 * @return The object
	 */
	public Object_ getObject() {
		return object;
	}

}