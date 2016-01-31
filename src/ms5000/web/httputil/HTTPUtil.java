package ms5000.web.httputil;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import ms5000.properties.PropertiesUtils;
import ms5000.properties.web.WebProperties;
import ms5000.web.httputil.response.Response;
import ms5000.web.httputil.response.ResponseFactory;
import ms5000.web.httputil.response.Standard_Response;

/**
 * Class to launch http requests
 */
public class HTTPUtil {

	/**
	 * Launches an response to the api received in the url string
	 * 
	 * @param url the web api url 
	 * @param service the service
	 * 
	 * @return a Response object filled with teh response message
	 * 
	 * @throws IOException
	 */
	public static Response get(String url, WebService service) throws IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);

		// In case AcoustID was called
		if (service == WebService.ACOUSTID) {
			httpGet.setHeader("User-Agent", PropertiesUtils.getProperty(WebProperties.ACOUSTID_CLIENT));
		}

		CloseableHttpResponse httpResponse = httpclient.execute(httpGet);

		try {
			Response response = ResponseFactory.getInstance(service);
			HttpEntity httpEntity = httpResponse.getEntity();
			Standard_Response standard_response = (Standard_Response) response;
			standard_response.setResponse(EntityUtils.toString(httpEntity));
			standard_response.setResponseCode(httpResponse.getStatusLine().getStatusCode());
			
			return standard_response;
		} catch (ClientProtocolException e) {
			System.out.println(e.getMessage());
			return null;
		} finally {
			httpResponse.close();
		}
	}

}
