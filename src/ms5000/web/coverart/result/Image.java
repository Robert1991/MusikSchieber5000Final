package ms5000.web.coverart.result;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Generated to store the result received from the cover art archive
 */
@Generated("org.jsonschema2pojo")
public class Image {
	@SerializedName("types")
	@Expose
	private List<String> types = new ArrayList<String>();
	@SerializedName("front")
	@Expose
	private Boolean front;
	@SerializedName("back")
	@Expose
	private Boolean back;
	@SerializedName("edit")
	@Expose
	private Integer edit;
	@SerializedName("image")
	@Expose
	private String image;
	@SerializedName("comment")
	@Expose
	private String comment;
	@SerializedName("approved")
	@Expose
	private Boolean approved;
	@SerializedName("thumbnails")
	@Expose
	private Thumbnails thumbnails;
	@SerializedName("id")
	@Expose
	private String id;

	/**
	 * 
	 * @return The types
	 */
	public List<String> getTypes() {
		return types;
	}

	/**
	 * 
	 * @return The front
	 */
	public Boolean getFront() {
		return front;
	}

	/**
	 * 
	 * @return The back
	 */
	public Boolean getBack() {
		return back;
	}

	/**
	 * 
	 * @return The edit
	 */
	public Integer getEdit() {
		return edit;
	}

	/**
	 * 
	 * @return The image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * 
	 * @return The comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * 
	 * @return The approved
	 */
	public Boolean getApproved() {
		return approved;
	}

	/**
	 * 
	 * @return The thumbnails
	 */
	public Thumbnails getThumbnails() {
		return thumbnails;
	}

	/**
	 * 
	 * @return The id
	 */
	public String getId() {
		return id;
	}

}
