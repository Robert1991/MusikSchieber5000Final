package ms5000.web.linkedbrainz.result.S;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Results {

	@SerializedName("bindings")
	@Expose
	private List<Binding> bindings = new ArrayList<Binding>();

	/**
	 * 
	 * @return The bindings
	 */
	public List<Binding> getBindings() {
		return bindings;
	}
}