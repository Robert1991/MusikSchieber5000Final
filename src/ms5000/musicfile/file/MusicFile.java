package ms5000.musicfile.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import ms5000.musicfile.tag.GeneratedTag;
import ms5000.musicfile.tag.MusicTag;
import ms5000.musicfile.tag.TagState;
import ms5000.musicfile.tag.TagUtils;
import ms5000.web.acousticid.result.Result;
import ms5000.web.acusticid.AcoustID;
import ms5000.web.acusticid.ChromaPrint;
import ms5000.web.acusticid.MetaDataIDs;
import ms5000.web.linkedbrainz.LinkedBrainzUtil;

public class MusicFile {
	/**
	 * The original file
	 */
	private File file;
	
	/**
	 * The music tag
	 */
	private MusicTag tag;
	
	/**
	 * The music file type
	 */
	private MusicFileType fileType;
	
	/**
	 * The original file path
	 */
	private String originalFilePath;
	
	/**
	 * The generated new file name
	 */
	private String newFileName;
	
	/**
	 * The generated new file path
	 */
	private String newFilePath;
	
	/**
	 * Boolean indicating whether the new file name is set
	 */
	private boolean newFileNameIsSet = false;
	
	/**
	 * Boolean indicating whether the new file path is set
	 */
	private boolean newFilePathIsSet = false;
	
	/**
	 * Boolean indicating whether the music file is a possible duplicate
	 */
	private boolean possibleDuplicate = false;
	
	/**
	 * The chromaprint object of the music file
	 */
	private ChromaPrint chromaPrint;
	
	/**
	 * The music brainz recording id
	 */
	private String musicBrainz_recordingID;
	
	/**
	 * The music brainz artist id
	 */
	private String musicBrainz_artistID;
	
	/**
	 * The featured artists
	 */
	private ArrayList<String> musicBrainz_feature_artistIDs = new ArrayList<String>();
	
	/**
	 * The id meta data received from the acousticid api
	 */
	private MetaDataIDs IdMetaData;
	
	/**
	 * The generated tag of this music file
	 */
	private GeneratedTag generatedTag;
	
	/**
	 * Constructor
	 * 
	 * @param originalFilePath the path to the original file
	 * 
	 * @throws CannotReadException
	 * @throws IOException
	 * @throws TagException
	 * @throws ReadOnlyFileException
	 * @throws InvalidAudioFrameException
	 */
	public MusicFile(String originalFilePath) throws CannotReadException,
			IOException, TagException, ReadOnlyFileException,
			InvalidAudioFrameException {
		this.originalFilePath = originalFilePath;
		this.file = new File(originalFilePath);
		this.fileType = determineFileType(originalFilePath);
		this.tag = TagUtils.generateTagFromFile(this.file);
		this.tag.setMusicFile(this);
		this.chromaPrint = AcoustID.chromaprint(this.file);
		
		
		// Querying the acoustid webservice but only if the linked brainz dump is online
		
		if (LinkedBrainzUtil.checkConnection()) {
			Result IdMetaData = AcoustID.lookup(this.chromaPrint);
			
			if(IdMetaData != null) {
				this.IdMetaData = AcoustID.generateMetaDataIds(IdMetaData);
			} else {
				this.IdMetaData = new MetaDataIDs();
				this.IdMetaData.setEmpty(true);
				
			}
		}
		
		// Determining the state of the tag
		TagUtils.determineTagState(this.tag);
	}
	
	/**
	 * Constructor
	 * 
	 * @param originalFilePath the path to the original file
	 * 
	 * @throws CannotReadException
	 * @throws IOException
	 * @throws TagException
	 * @throws ReadOnlyFileException
	 * @throws InvalidAudioFrameException
	 */
	public MusicFile(String originalFilePath, boolean dupluicateCheck) throws IOException {
		this.originalFilePath = originalFilePath;
		this.file = new File(originalFilePath);
		this.fileType = determineFileType(originalFilePath);
		this.chromaPrint = AcoustID.chromaprint(this.file);
	}
	
	
	/**
	 * Method to determine the file type of the music file
	 * 
	 * @param originalFilePath the path to the original file 
	 * @return the determined file type
	 */
	private MusicFileType determineFileType(String originalFilePath) {
		if (originalFilePath.contains("mp3")) {
			return MusicFileType.MP3;
		} else if (originalFilePath.contains("ogg")) {
			return MusicFileType.OGG;
		} else if (originalFilePath.contains("wma")) {
			return MusicFileType.WMA;
		} else if (originalFilePath.contains("flac")) {
			return MusicFileType.FLAC;
		} else if (originalFilePath.contains("m4a")) {
			return MusicFileType.M4A;
		} else if (originalFilePath.contains("aac")) {
			return MusicFileType.AAC;
		}

		return null;
	}	
	
	/**
	 * Method to get the original file object 
	 * 
	 * @return the original file object
	 */
	public File getFile() {
		return file;
	}
	
	/**
	 * Method to set the original file object 
	 * 
	 * @param the original file object
	 */
	public void setFile(File file) {
		this.file = file;
	}
	
	/**
	 * Method to return the music tag object
	 * 
	 * @return the music tag object
	 */
	public MusicTag getTag() {
		return tag;
	}
	
	/**
	 * Method to set the music tag object
	 * 
	 * @param the music tag object
	 */
	public void setTag(MusicTag tag) {
		this.tag = tag;
	}
	
	/**
	 * Method to return the music file type
	 *  
	 * @return the music file type
	 */
	public MusicFileType getFileType() {
		return fileType;
	}
	
	/**
	 * Method to set the music file type
	 *  
	 * @param the music file type
	 */
	public void setFileType(MusicFileType fileType) {
		this.fileType = fileType;
	}
	
	/**
	 * Method to return the original file path
	 *  
	 * @return the original file path
	 */
	public String getOriginalFilePath() {
		return originalFilePath;
	}
	
	/**
	 * Method to set the original file path
	 *  
	 * @param the original file path
	 */
	public void setOriginalFilePath(String originalFilePath) {
		this.originalFilePath = originalFilePath;
	}
	
	/**
	 * Method to return the new file name
	 * 
	 * @return the new file name
	 */
	public String getNewFileName() {
		return newFileName;
	}
	
	/**
	 * Method to return the new file path
	 * 
	 * @return the new file path
	 */
	public String getNewFilePath() {
		return newFilePath;
	}
	
	/**
	 * Method to return the boolean indicating whether the new file name was set
	 * 
	 * @return boolean indication whether the new file name was set
	 */
	public boolean isNewFileNameIsSet() {
		return newFileNameIsSet;
	}
	
	/**
	 * Method to return the boolean indicating whether the new path name was set
	 * 
	 * @return boolean indication whether the new path name was set
	 */
	public boolean isNewFilePathIsSet() {
		return newFilePathIsSet;
	}
	
	/**
	 * Method to set the boolean indicating whether the new path name was set
	 * 
	 * @param boolean indication whether the new path name was set
	 */
	public void setNewFileNameIsSet(boolean newFileNameIsSet) {
		this.newFileNameIsSet = newFileNameIsSet;
	}
	
	/**
	 * Method to set the boolean indicating whether the new path name was set
	 * 
	 * @param boolean indication whether the new path name was set
	 */
	public void setNewFilePathIsSet(boolean newFilePathIsSet) {
		this.newFilePathIsSet = newFilePathIsSet;
	}
	
	/**
	 * Method to return the ChromaPrint object of the music file
	 * 
	 * @return the chroma print object of the music file
	 */
	public ChromaPrint getChromaPrint() {
		return chromaPrint;
	}
	
	/**
	 * Method to set the ChromaPrint object of the music file
	 * 
	 * @param the chroma print object of the music file
	 */
	public void setChromaPrint(ChromaPrint chromaPrint) {
		this.chromaPrint = chromaPrint;
	}
	
	/**
	 * Method to return the boolean indicating whether the file is a possible duplicate
	 * 
	 * @return boolean indicating whether the file is a possible duplicate
	 */
	public boolean isPossibleDuplicate() {
		return possibleDuplicate;
	}

	/**
	 * Method to set the boolean indicating whether the file is a possible duplicate
	 * 
	 * @param boolean indicating whether the file is a possible duplicate
	 */
	public void setPossibleDuplicate(boolean possibleDuplicate) {
		this.possibleDuplicate = possibleDuplicate;
		
		if (possibleDuplicate) {
			this.getTag().setStatus(TagState.DUPLICATE);
		}
	}
	
	/**
	 * Method to return the music brainz recording id as string 
	 * 
	 * @return the music brainz recording id as string
	 */
	public String getMusicBrainz_recordingID() {
		return musicBrainz_recordingID;
	}

	/**
	 * Method to return the music brainz artist id as string 
	 * 
	 * @return the music brainz artist id as string
	 */
	public String getMusicBrainz_artistID() {
		return musicBrainz_artistID;
	}
	
	/**
	 * Method to return the list of the featured artists
	 * 
	 * @return the list of the featured artists id's
	 */
	public ArrayList<String> getMusicBrainz_feature_artistIDs() {
		return musicBrainz_feature_artistIDs;
	}
	
	/**
	 * Method to set the new file name 
	 * 
	 * @param newFileName the new file name 
	 */
	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}
	
	/**
	 * Method to set the new file path 
	 * 
	 * @param newFileName the new file path 
	 */
	public void setNewFilePath(String newFilePath) {
		this.newFilePath = newFilePath;
	}

	public MetaDataIDs getIdMetaData() {
		return IdMetaData;
	}

	/**
	 * Method to get the generated tag from the file
	 * 
	 * @return the generated tag
	 */
	public GeneratedTag getGeneratedTag() {
		return generatedTag;
	}
	
	/**
	 * Method to set the generated tag to the file
	 * 
	 * @param generatedTag the generated tag
	 */
	public void setGeneratedTag(GeneratedTag generatedTag) {
		this.generatedTag = generatedTag;
	}
	
}
