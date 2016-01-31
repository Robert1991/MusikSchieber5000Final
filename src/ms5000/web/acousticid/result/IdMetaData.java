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
public class IdMetaData {

	@SerializedName("status")
	@Expose
	private String status;
	@SerializedName("results")
	@Expose
	private List<Result> results = new ArrayList<Result>();

	/**
	 * 
	 * @return The status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 
	 * @return The results
	 */
	public List<Result> getResults() {
		return results;
	}

}