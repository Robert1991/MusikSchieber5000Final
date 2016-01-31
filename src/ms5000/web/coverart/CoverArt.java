package ms5000.web.coverart;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ms5000.properties.PropertiesUtils;
import ms5000.properties.web.WebProperties;
import ms5000.web.coverart.result.Result;
import ms5000.web.httputil.HTTPUtil;
import ms5000.web.httputil.WebService;
import ms5000.web.httputil.response.Standard_Response;

/**
 * Class which implements the functionality to query the cover art archive
 */
public class CoverArt {
	
	/**
	 * Queries the coverart archive for the received request
	 * @param request the request containing the release id
	 * 
	 * @return a result object containing the url to the cover art image
	 * 
	 * @throws IOException
	 */
	public static Result lookupCoverArt(Request request) throws IOException {
		String url = null;
		url = PropertiesUtils.getProperty(WebProperties.COVERART_URL) + request.getReleaseID();
		
		Standard_Response response = (Standard_Response) HTTPUtil.get(url,WebService.COVERART);
		String json = response.getResponse();
		
		if (response.getResponseCode() != 200) {
			return null;
		} else {
			return getResult(json);
		}
		
	}
	
	
	/**
	 * Converts the json string in to an result object
	 * @param json the string containing the json object
	 * 
	 * @return the result object
	 */
	private static Result getResult(String json) {
		Gson gson = new GsonBuilder().create();
		Result result = gson.fromJson(json, Result.class);
		return result;
	}
}
