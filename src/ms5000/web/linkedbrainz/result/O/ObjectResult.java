package ms5000.web.linkedbrainz.result.O;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Generated class to capture the result of the linked brainz web service
 */
@Generated("org.jsonschema2pojo")
public class ObjectResult {

	@SerializedName("head")
	@Expose
	private Head head;
	@SerializedName("results")
	@Expose
	private Results results;

	/**
	 * 
	 * @return The head
	 */
	public Head getHead() {
		return head;
	}

	/**
	 * 
	 * @return The results
	 */
	public Results getResults() {
		return results;
	}

}