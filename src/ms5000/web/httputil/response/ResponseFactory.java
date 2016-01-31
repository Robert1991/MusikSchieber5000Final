package ms5000.web.httputil.response;

import ms5000.web.httputil.WebService;

/**
 * Returns an Response
 */
public class ResponseFactory {
	/**
	 * Instantiates and returns an response object
	 * 
	 * @param service
	 * @return response object
	 */
	public static Response getInstance(WebService service) {
		return new Standard_Response();
	}
}
