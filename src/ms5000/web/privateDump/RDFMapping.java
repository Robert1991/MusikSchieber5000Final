package ms5000.web.privateDump;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

import ms5000.musicfile.tag.GeneratedTag;
import ms5000.musicfile.tag.MusicTag;
import ms5000.musicfile.tag.Release;
import ms5000.properties.PropertiesUtils;

/**
 * Util class for mapping incoming metadata to the internal file representation
 * and for mapping outcoming metadata to the schema of our private dump
 */
public class RDFMapping {

	/**
	 * Builds a rdf-representation to the received tag
	 * 
	 * @param tag the music tag to which the rdf reprenstation gets built
	 * @return the rdf model to the file representation
	 */
	public static Model rdfBuilder(MusicTag tag) {
		Model model = createDefaultModel();

		String artist = replaceURLChars(tag.getArtist());
		String title = replaceURLChars(tag.getTitlename());
		String album = replaceURLChars(tag.getAlbum());

		/*
		 * Building the tag id
		 */
		String tagID = title + "_" + album + "_" + artist + "_" + String.valueOf(System.currentTimeMillis() / 1000);

		/*
		 * Filling out the model for the trac
		 */
		Resource resource = model.createResource(MappingUrl.PREFIX + "" + tagID);

		resource.addProperty(model.getProperty(MappingUrl.PREFIX + "" + MappingUrl.ARTIST), artist);
		resource.addProperty(model.getProperty(MappingUrl.PREFIX + "" + MappingUrl.TITLE), title);
		resource.addProperty(model.getProperty(MappingUrl.PREFIX + "" + MappingUrl.ALBUM), album);
		resource.addProperty(model.getProperty(MappingUrl.PREFIX + "" + MappingUrl.COMMENT),replaceURLChars(tag.getComment()));
		resource.addProperty(model.getProperty(MappingUrl.PREFIX + "" + MappingUrl.ALBUMARTIST),replaceURLChars(tag.getRelease().getAlbumArtist()));
		resource.addProperty(model.getProperty(MappingUrl.PREFIX + "" + MappingUrl.COVERURL),replaceURLChars(tag.getRelease().getCoverArtImageUrl()));
		resource.addProperty(model.getProperty(MappingUrl.PREFIX + "" + MappingUrl.TRACKCOUNT),tag.getRelease().getTrackCount());
		resource.addProperty(model.getProperty(MappingUrl.PREFIX + "" + MappingUrl.TRACKNUMBER),tag.getRelease().getTrackNumber());
		resource.addProperty(model.getProperty(MappingUrl.PREFIX + "" + MappingUrl.YEAR), tag.getRelease().getYear());
		resource.addProperty(model.getProperty(MappingUrl.PREFIX + "" + MappingUrl.GENRE),replaceURLChars(tag.getGenre()));
		resource.addProperty(model.getProperty(MappingUrl.PREFIX + "" + MappingUrl.COMPOSER),replaceURLChars(tag.getRelease().getComposer()));
		resource.addProperty(model.getProperty(MappingUrl.PREFIX + "" + MappingUrl.DISCNUM),"" + tag.getRelease().getDiscNumber());
		resource.addProperty(model.getProperty(MappingUrl.PREFIX + "" + MappingUrl.TOTDISC),"" + tag.getRelease().getDiscCount());

		/*
		 * Fitting the tag to the recording ids
		 */
		for (String recordingId : tag.getMusicFile().getIdMetaData().getRecordings()) {
			Resource recID = model.createResource(MappingUrl.PREFIX + recordingId);
			recID.addProperty(model.getProperty(MappingUrl.PREFIX + "" + MappingUrl.TAG), resource);
			model.add(recID.getModel());
		}
		model.add(resource.getModel());
		return model;
	}

	/**
	 * Creates and returns a default model
	 * 
	 * @return the default model with all preferences
	 */
	private static Model createDefaultModel() {
		Model model = ModelFactory.createDefaultModel();

		model.setNsPrefix(MappingUrl.PREFIX.toString().replace(":", ""), MappingUrl.OWLNS.toString());
		model.createProperty("" + MappingUrl.PREFIX + MappingUrl.TAG);
		model.createProperty("" + MappingUrl.PREFIX + MappingUrl.ARTIST);
		model.createProperty("" + MappingUrl.PREFIX + MappingUrl.TITLE);
		model.createProperty("" + MappingUrl.PREFIX + MappingUrl.COMMENT);
		model.createProperty("" + MappingUrl.PREFIX + MappingUrl.ALBUMARTIST);
		model.createProperty("" + MappingUrl.PREFIX + MappingUrl.COVERURL);
		model.createProperty("" + MappingUrl.PREFIX + MappingUrl.TRACKCOUNT);
		model.createProperty("" + MappingUrl.PREFIX + MappingUrl.TRACKNUMBER);
		model.createProperty("" + MappingUrl.PREFIX + MappingUrl.YEAR);
		model.createProperty("" + MappingUrl.PREFIX + MappingUrl.ALBUM);
		model.createProperty("" + MappingUrl.PREFIX + MappingUrl.GENRE);
		model.createProperty("" + MappingUrl.PREFIX + MappingUrl.COMPOSER);
		model.createProperty("" + MappingUrl.PREFIX + MappingUrl.DISCNUM);
		model.createProperty("" + MappingUrl.PREFIX + MappingUrl.TOTDISC);

		return model;
	}

	/**
	 * Builds an generated tag from the model received from our dump
	 * 
	 * @param model the model received from the dump
	 * @return the generated tag containing all the informatio
	 */
	public static GeneratedTag buildMusicTagfromRDF(Model model) {
		GeneratedTag tags = new GeneratedTag();
		ArrayList<Release> releases = new ArrayList<Release>();
		ArrayList<Resource> resources = getTrackResource(model);

		if (resources != null) {
			for (Resource res : resources) {
				tags.addArtist(getStatementString(res, model, MappingUrl.ARTIST));
				tags.addTitleName(getStatementString(res, model, MappingUrl.TITLE));
				tags.addTag(getStatementString(res, model, MappingUrl.GENRE));

				if (getStatementString(res, model, MappingUrl.COMMENT).contains("http")) {
					tags.setWikiLink(getStatementString(res, model, MappingUrl.COMMENT));
				}

				Release release = new Release();
				release.setAlbumArtist(getStatementString(res, model, MappingUrl.ALBUMARTIST));
				release.setCoverArtImageUrl(getStatementString(res, model, MappingUrl.COVERURL));
				release.setTrackCount(getStatementString(res, model, MappingUrl.TRACKCOUNT));
				release.setTrackNumber(getStatementString(res, model, MappingUrl.TRACKNUMBER));
				release.setYear(getStatementString(res, model, MappingUrl.YEAR));
				release.setTitle(getStatementString(res, model, MappingUrl.ALBUM));
				release.setComposer(getStatementString(res, model, MappingUrl.COMPOSER));
				release.setDiscCount(getStatementString(res, model, MappingUrl.TOTDISC));
				release.setDiscNumber(getStatementString(res, model, MappingUrl.DISCNUM));

				if (releases.size() == 0) {
					releases.add(release);
				} else {
					for (int i = 1; i < releases.size(); i++) {
						if (releases.get(i).equals(release)) {
							if (releases.get(i).compareTo(release) < 0) {
								Release rel = releases.get(i);
								releases.remove(rel);
								releases.add(release);
							}
						} else {
							releases.add(release);
						}
					}

				}
			}
			releases = clearDuplicates(releases);

			for (Release rel : releases) {
				tags.addRelease(rel);
			}

			return tags;
		} else {
			return null;
		}
	}

	/**
	 * Clears the duplicates out of the received list
	 * 
	 * @param list the list
	 * @return duplicate free list
	 */
	private static ArrayList<Release> clearDuplicates(ArrayList<Release> list) {
		ArrayList<Release> newList = new ArrayList<Release>();

		Set<Release> hs = new HashSet<>();
		hs.addAll(list);
		newList.addAll(hs);
		return newList;
	}
	
	/**
	 * Returns the string to the relation in the resource
	 * 
	 * @param res the resource
	 * @param model the overall model
	 * @param mapping the relation
	 * @return the string corresponding to the entry
	 */
	private static String getStatementString(Resource res, Model model, MappingUrl mapping) {
		Statement statement = model.getProperty(res,
				model.getProperty(MappingUrl.PREFIX.toString() + mapping.toString()));
		RDFNode node = statement.getObject();
		return replaceURLCodes(node.toString());
	}
	
	/**
	 * Returns the whole model received from the ws
	 * 
	 * @param model the model bevor
	 * @return a array list containing all the information
	 */
	private static ArrayList<Resource> getTrackResource(Model model) {
		ArrayList<Resource> resources = new ArrayList<Resource>();
		StmtIterator iter = model.listStatements();
		while (iter.hasNext()) {
			Statement stmt = iter.nextStatement();
			Resource subject = stmt.getSubject();
			Property predicate = stmt.getPredicate();

			if (predicate.equals(model.getProperty(MappingUrl.PREFIX + "" + MappingUrl.ARTIST))) {
				resources.add(subject);
			}
		}

		return resources;
	}
	
	/**
	 * Replaces all the forbidden chars in an url with the correct
	 * identifieres, e.g. %20 for space
	 * 
	 * @param url the string which gets handeld
	 * @return the url without any forbidden chars
	 */
	private static String replaceURLChars(String rawUrl) {
		String[] forbiddenChars = PropertiesUtils.getArray("util.config.url.forbidden.chars");
		String[] validChars = PropertiesUtils.getArray("util.config.url.valid.chars");
		
		String url = rawUrl;
		
		if (url != null) {
			for (char c : url.toCharArray()) {
				for (int i = 0; i < forbiddenChars.length; i++) {
					if (i == 1) {
						if (c == " ".charAt(0)) {
							url = url.replaceAll("" + c, validChars[i]);
						}
					} else {
						if (c == forbiddenChars[i].charAt(0)) {
							if ("[]{}()*+".contains("" + c)) {
								url = url.replaceAll("\\" + c, validChars[i]);
							} else {
								url = url.replaceAll("" + c, validChars[i]);
							}
						}
					}
				}
			}
		} else {
			url = "";
		}
		return url;
	}
	
	/**
	 * Replaces the url codes in the style %20 with the corresponding
	 * char
	 * 
	 * @param url the url which gets handeld
	 * @return the url without the string representations
	 */
	private static String replaceURLCodes(String rawUrl) {
		String[] forbiddenChars = PropertiesUtils.getArray("util.config.url.forbidden.chars");
		String[] validChars = PropertiesUtils.getArray("util.config.url.valid.chars");
		
		String url = rawUrl;
		
		for (int i = 0; i < validChars.length; i++) {
			if (url.contains(validChars[i])) {
				url = url.replace(validChars[i], forbiddenChars[i]);
			}
		}

		return url;
	}

}
