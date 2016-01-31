package ms5000.web.linkedbrainz.result.O;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Generated class to capture the result of the linked brainz web service
 */
@Generated("org.jsonschema2pojo")
public class Head {

	@SerializedName("vars")
	@Expose
	private List<String> vars = new ArrayList<String>();

	/**
	 * 
	 * @return The vars
	 */
	public List<String> getVars() {
		return vars;
	}

}