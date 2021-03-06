package ms5000.web.acusticid;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.Gson;

import ms5000.properties.PropertiesUtils;
import ms5000.properties.web.WebProperties;
import ms5000.web.acousticid.result.Artist;
import ms5000.web.acousticid.result.IdMetaData;
import ms5000.web.acousticid.result.Recording;
import ms5000.web.acousticid.result.Release;
import ms5000.web.acousticid.result.Result;
import ms5000.web.httputil.HTTPUtil;
import ms5000.web.httputil.WebService;
import ms5000.web.httputil.response.Standard_Response;

/**
 * This class is a helper class to generate the acoustic id of a track
 * It also calls the corresponding web service and determines the needed musicbrainz 
 * identifieres 
 *
 */
public class AcoustID {
	
	/**
	 * Calls the fpcalc tool and builds a Chromaprint object which captures the 
	 * acoustic id and the duration
	 * 
	 * @param file the File from which the acoustic id will be generated
	 * @return a chromaprint object
	 * 
	 * @throws IOException Exception that gets thrown if the file coundn't be red
	 */
	public static ChromaPrint chromaprint(File file) throws IOException {
		String line;
		String chromaprint = null;
		String duration = null;
		
		ProcessBuilder processBuilder;
	    // Setting the PropertyType for the FpCalc - Process
		if (System.getProperty("os.name").toLowerCase().contains("win")) {
			if (System.getenv("ProgramFiles(x86)") != null) {
				processBuilder = new ProcessBuilder(PropertiesUtils.getProperty(WebProperties.FPCALCWIN64), null);
			} else {
				processBuilder = new ProcessBuilder(PropertiesUtils.getProperty(WebProperties.FPCALCWIN32), null);
			}
			
		} else {
			if (System.getProperty("sun.arch.data.model").equals("64")) {
				processBuilder = new ProcessBuilder(PropertiesUtils.getProperty(WebProperties.FPCALCMACOS64), null);
			} else {
				processBuilder = new ProcessBuilder(PropertiesUtils.getProperty(WebProperties.FPCALCMACOS32), null);
			}
				
		}
		
		processBuilder.redirectErrorStream(true);
		processBuilder.command().set(1, file.getAbsolutePath());
		
		// Launching the Process
		final Process fpcalcProc = processBuilder.start();
		final BufferedReader br = new BufferedReader(new InputStreamReader(fpcalcProc.getInputStream()));
		
		// Reading the input-stream received by the process
		while ((line = br.readLine()) != null) {
			if (line.startsWith("FINGERPRINT=")) {
				chromaprint = line.substring("FINGERPRINT=".length());
			} else if (line.startsWith("DURATION=")) {
				duration = line.substring("DURATION=".length());
			}
		}
		
		// Returning new ChromaPrint
		return new ChromaPrint(chromaprint, duration);
	}
	
	/**
	 * Method to get the result with the highest rating 
	 
	 * @param results Results received from the acoustId web service
	 * @return the best result
	 */
	private static Result getBestResult(IdMetaData results) {
		if (results.getResults().size() > 0) {
			Result bestResult = results.getResults().get(0);
			double currentScore = bestResult.getScore();

			for (Result result : results.getResults()) {

				double score = result.getScore();

				if (score > currentScore) {
					bestResult = result;
					currentScore = score;
				}

			}
			return bestResult;
		} else {
			return null;
		}
	}

	/**
	 * Method to convert a string representing a json object in an actual object 
	 * @param json the string representing the json object
	 * @return
	 */
	private static IdMetaData getResults(String json) {
		Gson gson = new Gson();
		IdMetaData results = gson.fromJson(json, IdMetaData.class);
		return results;
	}
	
	/**
	 * Queries the acoustId web service and delivers a result containing the identifiers with the 
	 * highest score
	 * 
	 * @param chromaprint a chromaprint object
	 * @return the result with the highest score
	 * 
	 * @throws IOException Exception that gets thrown if the file coundn't be red
	 */
	public static Result lookup(ChromaPrint chromaprint) throws IOException {
		String url = null;
		
		// Building the url
		url = PropertiesUtils.getProperty(WebProperties.ACOUSTID_URL) + "?client="
				+ PropertiesUtils.getProperty(WebProperties.ACOUSTID_CLIENT) + "&meta=recordings+releaseids+compress"
				+ "&fingerprint=" + chromaprint.chromaprint + "&duration="
				+ chromaprint.duration;
		System.out.println(url);
		
		// Querying the web service
		Standard_Response response = (Standard_Response) HTTPUtil.get(url,WebService.ACOUSTID);
		String json = response.getResponse();
		
		// Converting the json string to object
		IdMetaData results = getResults(json);
		
		/*
		 * check status
		 */
		if (results.getStatus().compareTo("ok") == 0) {

			if (results.getResults().size() > 0) {
				/*
				 * get the best match
				 */
				Result bestResult = getBestResult(results);

				if (bestResult != null) {
					/*
					 * return the best result
					 */
					if (bestResult.getRecordings().size() > 0) {
						return bestResult;
					} else {
						return null;
					}
				} else {
					return null;
				}
			} else {
				return null;
			}
		} else {
			return null;
		}

	}
	
	/**
	 * Method which builds an meta data object out of the received result
	 * 
	 * @param res the result from the acoustid webservice
	 * @return MetaDataIDs object
	 */
	public static MetaDataIDs generateMetaDataIds(Result res) {
		MetaDataIDs mdi = new MetaDataIDs();
		
		for (Recording rec : res.getRecordings()) {
			mdi.addRecordings(rec.getId());
			
			for (Release rel : rec.getReleases()) {
				mdi.addReleases(rel.getId());
			}
			
			for (Artist art : rec.getArtists()) {
				mdi.addArtist(art.getId());
			}
		}
		
		return mdi;
	}
}
