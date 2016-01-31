package ms5000.web.linkedbrainz;

import java.io.IOException;
import java.io.OutputStream;

/**
 * This class converts an output stream into a string
 */
public class JsonOutputStreamProvider extends OutputStream {
	/**
	 * The string builder that builds the string for the to string method
	 */
	private StringBuilder string = new StringBuilder();

	@Override
	/**
	 * Overrides the wirte method from the Output stream class and appends the
	 * chars in the string
	 * 
	 * @param b
	 *            int for char
	 */
	public void write(int b) throws IOException {
		this.string.append((char) b);
	}
	
	/**
	 * Returns the string received by the output stream
	 */
	public String toString() {
		return this.string.toString();
	}
}