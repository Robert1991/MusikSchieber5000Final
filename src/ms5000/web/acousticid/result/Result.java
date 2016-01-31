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
public class Result {

	@SerializedName("recordings")
	@Expose
	private List<Recording> recordings = new ArrayList<Recording>();
	@SerializedName("score")
	@Expose
	private Double score;
	@SerializedName("id")
	@Expose
	private String id;

	/**
	 * 
	 * @return The recordings
	 */
	public List<Recording> getRecordings() {
		return recordings;
	}

	/**
	 * 
	 * @return The score
	 */
	public Double getScore() {
		return score;
	}

	/**
	 * 
	 * @return The id
	 */
	public String getId() {
		return id;
	}

}