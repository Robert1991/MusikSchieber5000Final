package test;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import ms5000.web.linkedbrainz.LinkedBrainzUtil;

public class TestClass {
	static String mBPrefix = "http://musicbrainz.org/";
	static String mBPrefixRec = "recording/";
	static String mBPrefixArtist = "artist/";
	static String mBPrefixTrack = "track/";
	static String foafPrefix = "http://xmlns.com/foaf/0.1/";
	static String foafMade = "made";
	static String moPrefix = "http://purl.org/ontology/mo/";
	static String moPubOf = "publication_of";
	
	public static void main(String[] args) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		/*
		System.out.println(PropertiesUtils.getString("sparql.lb.ns"));
		System.out.println(PropertiesUtils.getString("sparql.lb.prefix"));
		System.out.println(PropertiesUtils.getString("sparql.lb.artist"));
		System.out.println(PropertiesUtils.getString("sparql.lb.title"));
		System.out.println(PropertiesUtils.getString("sparql.lb.comment"));
		System.out.println(PropertiesUtils.getString("sparql.lb.album.artist"));
		System.out.println(PropertiesUtils.getString("sparql.lb.cover.url"));
		System.out.println(PropertiesUtils.getString("sparql.lb.track.count"));
		System.out.println(PropertiesUtils.getString("sparql.lb.track.num"));
		System.out.println(PropertiesUtils.getString("sparql.lb.year"));
		System.out.println(PropertiesUtils.getString("sparql.lb.album"));
		System.out.println(PropertiesUtils.getString("sparql.lb.genre"));
		System.out.println(PropertiesUtils.getString("sparql.lb.composer"));
		System.out.println(PropertiesUtils.getString("sparql.lb.disc.num"));
		System.out.println(PropertiesUtils.getString("sparql.lb.disc.tot"));
		System.out.println(PropertiesUtils.getString("sparql.lb.tag"));
		
		
		String test = "C:\\musicTest\\test.mp3";
		
		MusicFile file = new MusicFile(test);
		System.out.println(file.getTag().getArtist());
		Model m = RDFMapping.rdfBuilder(file.getTag());
	    m.write(System.out,"Turtle");
	    
	    //TagToModelService.uploadModel(m);
	    Model m1 = ModelFactory.createDefaultModel();
	    for (String recId : file.getIdMetaData().getRecordings()) {
	    	Model newMod = TagToModelService.getModelFromRecId(recId);
	    	System.out.println(newMod.isEmpty());
	    	if (!newMod.isEmpty())
	    	m1.add(TagToModelService.getModelFromRecId(recId));
	    }
	    System.out.println(m1.isEmpty());
	    GeneratedTag tags = RDFMapping.buildMusicTagfromRDF(m1);
	    
	    System.out.println(tags);
	    for (Release rel : tags.getRelases()) {
			System.out.println(rel.getTitle() + " " + "" + " " + rel.getTrackNumber() + " "
					+ "" + " " + rel.getDiscCount() + " " + rel.getDiscNumber() + " " + rel.getYear());
	    }
	    
	    */
		
		System.out.println(LinkedBrainzUtil.checkConnection());
		
		
    }
	      
}
