package ms5000.musicfile.tag;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.datatype.Artwork;
import org.jaudiotagger.tag.id3.ID3v1Tag;

import ms5000.musicfile.file.MusicFile;
import ms5000.musicfile.file.MusicFileType;
import ms5000.properties.PropertiesUtils;
import ms5000.properties.library.OrderingProperty;

/**
 * Helper class for tag operations
 */
public class TagUtils {

	/**
	 * Reads the tag from the music file and stores them in a tag object
	 * 
	 * @param musicFile
	 *            the music file from which the tag will be read
	 * @return the corresponding tag object
	 * 
	 * @throws CannotReadException
	 * @throws IOException
	 * @throws TagException
	 * @throws ReadOnlyFileException
	 * @throws InvalidAudioFrameException
	 */
	public static MusicTag generateTagFromFile(File musicFile)
			throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		MusicTag musicTag = new MusicTag();
		AudioFile f = AudioFileIO.read(musicFile);
		Tag tag = f.getTag();
		
		musicTag.setAlbum(getTagField(FieldKey.ALBUM,tag));
		musicTag.setArtist(getTagField(FieldKey.ARTIST,tag));
		musicTag.setTitlename(getTagField(FieldKey.TITLE,tag));
		musicTag.getRelease().setComposer(getTagField(FieldKey.COMPOSER,tag));
		musicTag.setGenre(getTagField(FieldKey.GENRE,tag));
		musicTag.getRelease().setTitle(getTagField(FieldKey.ALBUM,tag));
		musicTag.getRelease().setTrackCount("" + stringToInt(getTagField(FieldKey.TRACK_TOTAL,tag)));
		musicTag.getRelease().setTrackNumber("" + stringToInt(getTagField(FieldKey.TRACK,tag)));
		musicTag.getRelease().setYear("" + stringToInt(getTagField(FieldKey.YEAR,tag)));
		
		
		if (!getTagField(FieldKey.ALBUM_ARTIST,tag).equals("")) {
			musicTag.getRelease().setAlbumArtist(getTagField(FieldKey.ALBUM_ARTIST,tag));
		} else {
			musicTag.getRelease().setAlbumArtist(getTagField(FieldKey.ARTIST,tag));
		}
		
		if (getTagField(FieldKey.DISC_TOTAL,tag).equals("") && getTagField(FieldKey.DISC_NO,tag).equals("")) {
			musicTag.getRelease().setDiscCount("1");
			musicTag.getRelease().setDiscNumber("1");
		} else if (getTagField(FieldKey.DISC_TOTAL,tag).equals("") && !getTagField(FieldKey.DISC_NO,tag).equals("")) {
			musicTag.getRelease().setDiscCount("" + stringToInt(getTagField(FieldKey.DISC_NO,tag)));
			musicTag.getRelease().setDiscNumber("" + stringToInt(getTagField(FieldKey.DISC_NO,tag)));
		} else {
			musicTag.getRelease().setDiscNumber("" + stringToInt(getTagField(FieldKey.DISC_NO,tag)));
			musicTag.getRelease().setDiscCount("" + stringToInt(getTagField(FieldKey.DISC_TOTAL,tag)));
		}

		musicTag.getRelease().setUserRelease(true);
		musicTag.setComment(getTagField(FieldKey.COMMENT,tag));
		musicTag.getRelease().setArtwork(getTagFieldArt(tag));

		return musicTag;
	}
	
	/**
	 * Returns the string value of the field with the received key
	 * 
	 * @param key of the field to which the string value will be delivered
	 * @param tag the tag that is read
	 * 
	 * @return the string value of the filed
	 */
	private static String getTagField(FieldKey key, Tag tag) {
		int count = tag.getFields(key).size();
		for (int i = 0; i < count; i++) {
			String fieldStr = tag.getValue(key, i);
			
			if (!fieldStr.equals("")) {
				return fieldStr;
			}
		}
		
		return "";
	}
	
	/**
	 * Returns the artwork of the received tag
	 * 
	 * @param tag the tag which will be read
	 * 
	 * @return the artwork object
	 */
	private static Artwork getTagFieldArt(Tag tag) {
		
		for (Artwork field : tag.getArtworkList()) {
			
			if (field != null) {
				return field;
			}			
		}
		
		return null;
	}
	
	/**
	 * Writes the received tag to the music file
	 * 
	 * @param musicFile the music file which will be changed
	 * @param musicTag the music tag
	 * 
	 * @throws CannotReadException
	 * @throws IOException
	 * @throws TagException
	 * @throws ReadOnlyFileException
	 * @throws InvalidAudioFrameException
	 * @throws CannotWriteException
	 */
	public static void commitTagToFile(MusicFile musicFile, MusicTag musicTag) throws CannotReadException, IOException,
			TagException, ReadOnlyFileException, InvalidAudioFrameException, CannotWriteException {
		AudioFile f = AudioFileIO.read(musicFile.getFile());
		Tag tag = f.getTag();
		
		if (tag instanceof ID3v1Tag) {
			tag = f.createDefaultTag();
		}

		tag.setField(FieldKey.ALBUM, musicTag.getAlbum());
		tag.setField(FieldKey.ARTIST, musicTag.getArtist());
		tag.setField(FieldKey.TITLE, musicTag.getTitlename());

		System.out.println(tag);
		if (musicTag.getRelease().getComposer() != null) {
			tag.setField(FieldKey.COMPOSER, musicTag.getRelease().getComposer());
		}

		if (musicFile.getFileType() == MusicFileType.M4A) {
			tag.setField(FieldKey.GROUPING, musicTag.getGenre());
		}

		tag.setField(FieldKey.ALBUM, musicTag.getRelease().getTitle());
		tag.setField(FieldKey.ALBUM_ARTIST, musicTag.getRelease().getAlbumArtist());
		tag.setField(FieldKey.YEAR, musicTag.getRelease().getYear());
		
		try {
			tag.setField(FieldKey.TRACK, musicTag.getRelease().getTrackNumber());
			tag.setField(FieldKey.TRACK_TOTAL, musicTag.getRelease().getTrackCount());
			tag.setField(FieldKey.DISC_NO,"" + stringToInt(musicTag.getRelease().getDiscNumber()));
			tag.setField(FieldKey.DISC_TOTAL,"" + stringToInt(musicTag.getRelease().getTrackNumber()));
			
		} catch (NumberFormatException e) {
			tag.setField(FieldKey.TRACK, "0");
			tag.setField(FieldKey.TRACK_TOTAL, "0");
			tag.setField(FieldKey.DISC_NO, "1");
			tag.setField(FieldKey.DISC_TOTAL, "1");
		}
		tag.setField(FieldKey.GENRE, musicTag.getGenre());
		tag.setField(FieldKey.COMMENT, musicTag.getComment());

		Artwork artwork = musicTag.getRelease().getArtwork();
		if (artwork != null) {
			tag.setField(artwork);
		} else {
			tag.deleteArtworkField();
		}

		// Commiting Changes to File
		f.commit();
		
		
	}
	
	/**
	 * Builds a full tag if possible by iterating through the delivered suggestions
	 * 
	 * @param musicFile the musicfile to which the smart tag gets generated
	 */
	public static MusicTag buildSmartTag(MusicFile musicFile) {
		MusicTag fileTag = musicFile.getTag();
		GeneratedTag genTag = musicFile.getGeneratedTag();
		Release fileTagRel = musicFile.getTag().getRelease();
		ArrayList<Release> genTagRel = genTag.getRelases(); 
		
		// Titlesnames
		if (fileTag.getTitlename().equals("")) {
			if (genTag.getTitleNames().size() > 0) {
				fileTag.setTitlename(genTag.getTitleNames().get(0));
			}
		}
		
		// Main artist
		if (fileTag.getArtist().equals("")) {
			if (genTag.getMainArtist().size() > 0) {
				fileTag.setArtist(genTag.getMainArtist().get(0));
			}
		}
		
		// Comment
		if (fileTag.getComment().equals("") || fileTag.getComment() == null) {
			if (!genTag.getWikiLink().equals("")) {
				fileTag.setComment(genTag.getWikiLink());
			}
		}
		
		// Genre
		if (fileTag.getGenre().equals("")) {
			if (genTag.getTags().size() > 0) {
				fileTag.setGenre(genTag.getTags().get(0));
			}
		}
		
		// The releases
		if (genTagRel.size() > 0) {
			String fileRelTitle = fileTagRel.getTitle();
			
			if (!fileRelTitle.equals("")) {
				// Prepare for combination
				fileRelTitle = fileRelTitle.replaceAll(" ","");
				fileRelTitle = fileRelTitle.replaceAll("-","");
				fileRelTitle = fileRelTitle.replaceAll("&","");
				fileRelTitle = fileRelTitle.replaceAll("/","");
				fileRelTitle = fileRelTitle.toLowerCase();
				
				boolean done = false;
				for (Release rel : genTagRel) {
					if (rel.isMainRelease() && !done) {
						String relTitle = rel.getTitle();
						relTitle = relTitle.replaceAll(" ","");
						relTitle = relTitle.replaceAll("-","");
						relTitle = relTitle.replaceAll("&","");
						relTitle = relTitle.replaceAll("/","");
						relTitle = relTitle.toLowerCase();
						
						if (fileRelTitle.contains(relTitle)) {
							fileTagRel.setReleaseId(rel.getReleaseId());
							fileTagRel.setTitle(rel.getTitle());
							fileTag.setAlbum(rel.getTitle());
							
							if (!rel.getTrackCount().equals("") || rel.getTrackCount().equals("0")) {
								fileTagRel.setTrackCount(rel.getTrackCount());
							}
							
							if (!rel.getTrackNumber().equals("") || rel.getTrackNumber().equals("0")) {
								fileTagRel.setTrackNumber(rel.getTrackNumber());
							}
							
							if (!rel.getAlbumArtist().equals("")) {
								fileTagRel.setAlbumArtist(rel.getAlbumArtist());
							}
							
							if (!rel.getYear().equals("")) {
								fileTagRel.setYear(rel.getYear());
							}
							
							if (fileTagRel.getArtwork() == null) {
								if (rel.getArtwork() != null) {
									fileTagRel.setArtwork(rel.getArtwork());
									fileTagRel.setCoverArtImageUrl(rel.getCoverArtImageUrl());
								}
							}
							
							done = true;
						}
					}
				}
			}
			
			
			if (fileRelTitle.equals("")) {
				boolean done = false;
				for (Release rel : genTagRel) {
					if (rel.isMainRelease() && rel.getArtwork() != null) {
						fileTagRel = rel;
						fileTag.setAlbum(rel.getTitle());
						done=true;
						break;
					}
				}
				
				if (!done) {
					// there is no main release with artwork
					for (Release rel : genTagRel) {
						if (rel.isMainRelease()) {
							fileTag.setAlbum(rel.getTitle());
							fileTagRel = rel;
							done=true;
							break;
						}
					}
					
					// there is no main release
					if (!done) {
						fileTagRel = genTagRel.get(0);
						fileTag.setAlbum(genTagRel.get(0).getTitle());
					}
				}
				
				
			}
			
			fileTag.setRelease(fileTagRel);
			
		}
		determineTagState(fileTag);
		
		return fileTag;
	}
	
	/**
	 * Converts the received string to an integer
	 * 
	 * @param number the number as string
	 * @return the corresponding integer
	 */
	public static int stringToInt(String number) {
		try {
			return Integer.parseInt(number);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	/**
	 * Converts the received integer to a string
	 * 
	 * @param number the integer which will be converted
	 * @return the converted number as string
	 */
	public static String intToString(int number) {
		if (number == 0) {
			return "0";
		} else {
			return "" + number;
		}
	}

	/**
	 * Method which determines the state of the received tag and stores it in
	 * the tag
	 * 
	 * @param tag for which the state will be determined
	 */
	public static void determineTagState(MusicTag tag) {
		// Critical
		String artist = tag.getArtist();
		String album = tag.getRelease().getTitle();
		String titlename = tag.getTitlename();
		String genre = tag.getGenre();
		
		// Non-critical
		int titleNumber = stringToInt(tag.getRelease().getTrackNumber());
		int totalTitleNumber = stringToInt(tag.getRelease().getTrackCount());
		
		OrderingProperty orderingMode = PropertiesUtils.getOrderingProperty();
		// Weak infos
		int year;
		try {
			year = Integer.parseInt(tag.getRelease().getYear());
		} catch (NumberFormatException e) {
			year = 0;
		}
		
		String albumArtist = tag.getRelease().getAlbumArtist();

		if (tag.getMusicFile().isPossibleDuplicate()) {
			tag.setStatus(TagState.DUPLICATE);
		}

		if (orderingMode == OrderingProperty.GAA) {
			if (artist.equals("") || album.equals("") || genre.equals("") || titlename.equals("")) {
				tag.setStatus(TagState.MISSINGCRITICAL);
				return;
			} else if (titleNumber == 0 || totalTitleNumber == 0) {
				tag.setStatus(TagState.MISSINGNONCRITICAL);
				return;
			} else if (year == 0 || albumArtist.equals("")) {
				tag.setStatus(TagState.MISSINGWEAKINFOS);
				return;
			} else {
				tag.setStatus(TagState.COMPLETE);
				return;
			}
		} else {
			if (artist.equals("") || album.equals("") || titlename.equals("")) {
				tag.setStatus(TagState.MISSINGCRITICAL);
				return;
			} else if (titleNumber == 0 || totalTitleNumber == 0 || genre.equals("")) {
				tag.setStatus(TagState.MISSINGNONCRITICAL);
				return;
			} else if (year == 0 || albumArtist.equals("")) {
				tag.setStatus(TagState.MISSINGWEAKINFOS);
				return;
			} else {
				tag.setStatus(TagState.COMPLETE);
				return;
			}
		}

	}
}
