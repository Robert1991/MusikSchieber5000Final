package ms5000.web.linkedbrainz.result.S;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class SubjectResult {

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