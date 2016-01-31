package ms5000.web.linkedbrainz.result.S;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Binding {

	@SerializedName("subject")
	@Expose
	private Subject subject;

	/**
	 * 
	 * @return The subject
	 */
	public Subject getSubject() {
		return subject;
	}

}