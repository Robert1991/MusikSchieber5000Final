package ms5000.gui.mainframe.center.eventhandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ms5000.audio.player.AudioPlayer;
import ms5000.gui.mainframe.Main_Frame;
import ms5000.gui.mainframe.center.CenterGridPane;
import ms5000.gui.mainframe.center.CenterTable;
import ms5000.musicfile.tag.MusicTag;
import ms5000.musicfile.tag.Release;
import ms5000.musicfile.tag.Tags;

/**
 * This class implements the functionalities which appear if there was a change
 * in center table
 */
public class CenterTable_ChangeListener implements ChangeListener<MusicTag> {

	/**
	 * Instance of the center table
	 */
	private CenterTable centerTable;

	/**
	 * Instance of the center grid pane with the detail view
	 */
	private CenterGridPane centerGridPane;

	@Override
	public void changed(ObservableValue<? extends MusicTag> observable, MusicTag oldValue, MusicTag newValue) {
		// Getting the instances
		centerGridPane = Main_Frame.getBorderPane_Center().getCenterGridPane();
		centerTable = Main_Frame.getBorderPane_Center().getCentertable();

		// Case: only one entry was choosen
		if (centerTable.getSelectionModel().getSelectedIndices().size() == 1) {
			// Reverting changes made
			centerGridPane.enableFields();

			// Adding the information of a single entry to the detail view
			addSingleEntryToDetails(centerTable.getSelectionModel().getSelectedItem());

			// Enabling the player and setting the audio file
			AudioPlayer.getInstance().enablePlayer();
			AudioPlayer.getInstance().setMedia(centerTable.getSelectionModel().getSelectedItem().getMusicFile());

			// Case: More than one entry was choosen
		} else if (centerTable.getSelectionModel().getSelectedIndices().size() > 1) {
			// Disabling the audio player
			AudioPlayer.getInstance().disablePlayer();

			// Getting the selected tags
			MusicTag[] tags = new MusicTag[0];
			tags = Main_Frame.getBorderPane_Center().getCentertable().getSelectionModel().getSelectedItems()
					.toArray(tags);

			// Adding the common information of the tags to the detail view
			addMultipleEntryToDetails(tags);

			// Case: No entry was choosen
		} else if (centerTable.getSelectionModel().getSelectedIndices().get(0) == -1) {
			// Reseting the detail view and disabling the player
			AudioPlayer.getInstance().disablePlayer();
			centerGridPane.refershFieldColorProfile();
			centerGridPane.clearFields();
			centerGridPane.setArtWorkImage(null);
		}

	}

	/**
	 * Method to add a single entry to the detail view
	 * 
	 * @param tag
	 *            tag that will be red
	 */
	private void addSingleEntryToDetails(MusicTag tag) {
		// Refreshing the color profile
		centerGridPane.refershFieldColorProfile();
		centerGridPane.clearFields();
		
		// Adding the releases
		addReleases(tag);

		// Adding the tags
		addTags(tag);

		// Adding the titles
		addTitles(tag);

		// Adding the artists
		addArtists(tag);

		// Adding the comments
		addComments(tag);
		
		//centerGridPane.getDiscNumberTextField().setText("" + tag.getRelease().getDiscNumber());
		//centerGridPane.getTotalDiscNumbersTextField().setText("" + tag.getRelease().getDiscCount());
		//centerGridPane.getComposerTextField().setText(tag.getRelease().getComposer());
		centerGridPane.getFilePathTextField().setText(tag.getMusicFile().getOriginalFilePath());

		if (tag.getRelease().getArtwork() != null) {
			centerGridPane.setArtWorkImage(tag.getRelease().getArtwork());
		} else {
			centerGridPane.setArtWorkImage(null);
		}

		// Setting the color profile of the text fields
		centerGridPane.setTextFieldColorProfile();
	}
	
	/**
	 * Adds the releases to the detail view
	 * 
	 * @param tag the tag
	 */
	private void addReleases(MusicTag tag) {
		// Adding the releases
		ArrayList<Release> listRelease = new ArrayList<Release>();
		Release firstRel = null;
		boolean userRelease = true;

		if (!tag.getRelease().getTitle().equals("")) {
			listRelease.add(tag.getRelease());
			firstRel = tag.getRelease();
		} else {
			userRelease = false;
		}

		if (tag.getMusicFile().getGeneratedTag() != null) {
			listRelease.addAll(tag.getMusicFile().getGeneratedTag().getRelases());
		}

		ObservableList<Release> releaseList = FXCollections.observableArrayList(listRelease);

		if (!releaseList.isEmpty()) {
			if (userRelease) {
				centerGridPane.getAlbumComboBox().setItems(releaseList);
				centerGridPane.getAlbumComboBox().getSelectionModel().select(firstRel);
				centerGridPane.getAlbumArtistTextField().setText(releaseList.get(0).getAlbumArtist());
				centerGridPane.getYearTextField().setText(releaseList.get(0).getYear());
				centerGridPane.getTitleNumberTextField().setText(releaseList.get(0).getTrackNumber());
				centerGridPane.getTotalTitleNumbersTextField().setText(releaseList.get(0).getTrackCount());
				centerGridPane.setArtWorkImage(releaseList.get(0).getArtwork());
				System.out.println(releaseList.get(0).getComposer());
				centerGridPane.getComposerTextField().setText(releaseList.get(0).getComposer());
				centerGridPane.getDiscNumberTextField().setText(releaseList.get(0).getDiscNumber());
				centerGridPane.getTotalDiscNumbersTextField().setText(releaseList.get(0).getDiscCount());
			} else {
				Release firstMainRelease = null;

				int index = 0;
				for (Release rel : releaseList) {
					if (rel.isMainRelease()) {
						firstMainRelease = rel;
						break;
					}
					index++;
				}

				if (firstMainRelease != null) {
					centerGridPane.getAlbumComboBox().setItems(releaseList);
					centerGridPane.getAlbumComboBox().getSelectionModel().select(index);
					centerGridPane.getAlbumArtistTextField().setText(releaseList.get(index).getAlbumArtist());
					centerGridPane.getYearTextField().setText(releaseList.get(index).getYear());
					centerGridPane.getTitleNumberTextField().setText(releaseList.get(index).getTrackNumber());
					centerGridPane.getTotalTitleNumbersTextField().setText(releaseList.get(index).getTrackCount());
					centerGridPane.setArtWorkImage(releaseList.get(index).getArtwork());
					centerGridPane.getComposerTextField().setText(releaseList.get(index).getComposer());
					centerGridPane.getDiscNumberTextField().setText(releaseList.get(index).getDiscNumber());
					centerGridPane.getTotalDiscNumbersTextField().setText(releaseList.get(index).getDiscCount());
					System.out.println(releaseList.get(index).getComposer());
				} else {
					centerGridPane.getAlbumComboBox().setItems(releaseList);
					centerGridPane.getAlbumComboBox().getSelectionModel().select(0);
					centerGridPane.getAlbumArtistTextField().setText(releaseList.get(0).getAlbumArtist());
					centerGridPane.getYearTextField().setText(releaseList.get(0).getYear());
					centerGridPane.getTitleNumberTextField().setText(releaseList.get(0).getTrackNumber());
					centerGridPane.getTotalTitleNumbersTextField().setText(releaseList.get(0).getTrackCount());
					centerGridPane.setArtWorkImage(releaseList.get(0).getArtwork());
					centerGridPane.getComposerTextField().setText(releaseList.get(0).getComposer());
					centerGridPane.getDiscNumberTextField().setText(releaseList.get(0).getDiscNumber());
					centerGridPane.getTotalDiscNumbersTextField().setText(releaseList.get(0).getDiscCount());
				}
			}

		}

	}
	
	/**
	 * Adds the tags to the genre combox box
	 * 
	 * @param tag the music tag
	 */
	private void addTags(MusicTag tag) {
		// Adding the releases
		ArrayList<String> listGenre = new ArrayList<String>();
		String firstGenre = null;
		if (!tag.getGenre().equals("")) {
			listGenre.add(tag.getGenre());
			firstGenre = tag.getGenre();
		}

		if (tag.getMusicFile().getGeneratedTag() != null) {
			if (!tag.getMusicFile().getGeneratedTag().getTags().isEmpty()) {
				for (String genre : tag.getMusicFile().getGeneratedTag().getTags()) {
					if (!genre.equals("")) {
						listGenre.add(genre);
					}
				}
				listGenre = clearDuplicates(listGenre);
			}
		}

		if (!listGenre.isEmpty()) {
			centerGridPane.getGenreComboBox().setItems(FXCollections.observableArrayList(listGenre));
			System.out.println(firstGenre);
			if (!firstGenre.equals("")) {
				centerGridPane.getGenreComboBox().getSelectionModel().select(firstGenre);
				tag.setGenre(firstGenre);
			} else {
				centerGridPane.getGenreComboBox().getSelectionModel().select(0);
			}

		}
	}
	
	/**
	 * Adds the comments to the comment combox box
	 * 
	 * @param tag the music tag
	 */
	private void addComments(MusicTag tag) {
		// Adding the Comments
		ArrayList<String> listComments = new ArrayList<String>();
		String firstComment = null;
		if (!tag.getComment().equals("")) {
			listComments.add(tag.getComment());
			firstComment = tag.getComment();
		}

		if (tag.getMusicFile().getGeneratedTag() != null) {
			if (!tag.getMusicFile().getGeneratedTag().getWikiLink().equals("")
					&& tag.getMusicFile().getGeneratedTag().getWikiLink() != null) {
				listComments.add(tag.getMusicFile().getGeneratedTag().getWikiLink());
				listComments = clearDuplicates(listComments);
			}
		}

		if (!listComments.isEmpty()) {
			centerGridPane.getCommentComboBox().setItems(FXCollections.observableArrayList(listComments));

			if (firstComment != null) {
				centerGridPane.getCommentComboBox().getSelectionModel().select(firstComment);
			} else {
				centerGridPane.getCommentComboBox().getSelectionModel().select(0);
			}

		}
	}
	
	/**
	 * Adds the artists to the artist combox box
	 * 
	 * @param tag the music tag
	 */
	private void addArtists(MusicTag tag) {
		// Adding the artists
		ArrayList<String> artists = new ArrayList<String>();
		String firstArtist = null;
		if (!tag.getArtist().equals("")) {
			artists.add(tag.getArtist());
			firstArtist = tag.getArtist();
		}

		if (tag.getMusicFile().getGeneratedTag() != null) {
			if (!tag.getMusicFile().getGeneratedTag().getMainArtist().equals("")
					&& tag.getMusicFile().getGeneratedTag().getMainArtist() != null) {
				
				for (String artist : tag.getMusicFile().getGeneratedTag().getMainArtist()) {
					artists.add(artist);
				}
				
				artists = clearDuplicates(artists);
			}
		}

		if (!artists.isEmpty()) {
			centerGridPane.getArtistComboBox().setItems(FXCollections.observableArrayList(artists));

			if (firstArtist != null) {
				centerGridPane.getArtistComboBox().getSelectionModel().select(firstArtist);
			} else {
				centerGridPane.getArtistComboBox().getSelectionModel().select(0);
			}

		}
	}
	
	/**
	 * Adds the title to the title combo box
	 * 
	 * @param tag the tag
	 */
	private void addTitles(MusicTag tag) {
		ArrayList<String> listTitle = new ArrayList<String>();
		String firstTitle = null;
		;
		if (!tag.getTitlename().equals("")) {
			listTitle.add(tag.getTitlename());
			firstTitle = tag.getTitlename();
		}

		if (tag.getMusicFile().getGeneratedTag() != null) {
			if (!tag.getMusicFile().getGeneratedTag().getTitleNames().isEmpty()) {
				for (String title : tag.getMusicFile().getGeneratedTag().getTitleNames()) {
					if (!title.equals("")) {
						listTitle.add(title);
					}
				}

				listTitle = clearDuplicates(listTitle);
			}
		}

		centerGridPane.getTitlenameComboBox().setItems(FXCollections.observableArrayList(listTitle));

		if (!listTitle.isEmpty()) {
			centerGridPane.getTitlenameComboBox().setItems(FXCollections.observableArrayList(listTitle));
			if (firstTitle != null) {
				centerGridPane.getTitlenameComboBox().getSelectionModel().select(firstTitle);
			} else {
				centerGridPane.getTitlenameComboBox().getSelectionModel().select(0);
			}

		}
	}

	/**
	 * Method to add more than one entry to the detail view
	 * 
	 * @param tags the tags which will be added
	 */
	private void addMultipleEntryToDetails(MusicTag[] tags) {
		centerGridPane.refershFieldColorProfile();
		OriginalState state = OriginalState.getInstance();

		// Getting the entries that are the same
		String album = getSameEntries(tags, Tags.ALBUM);
		String artist = getSameEntries(tags, Tags.ARTIST);
		String genre = getSameEntries(tags, Tags.GENRE);
		String albumArtist = getSameEntries(tags, Tags.ALBUMARTIST);
		String maxTitleNumber = getSameEntries(tags, Tags.TOTALTRACKNUMBER);
		String discNumber = getSameEntries(tags, Tags.DISCNUMBER);
		String maxDiscNumber = getSameEntries(tags, Tags.TOTALDISCNUMBER);
		String comment = getSameEntries(tags, Tags.COMMENT);
		String composer = getSameEntries(tags, Tags.COMPOSER);
		String year = getSameEntries(tags, Tags.YEAR);

		// Writing the original state into a singleton, to determine what has
		// changed
		state.setAlbum(album);
		state.setAlbum_artist(albumArtist);
		state.setGenre(genre);
		state.setArtist(artist);
		state.setTitlesTotal(maxTitleNumber);
		state.setDiscNumber(discNumber);
		state.setComment(comment);
		state.setComposer(composer);
		state.setYear(year);
		state.setTotalDiscNumber(maxDiscNumber);

		// Setting the entries
		centerGridPane.getTitlenameComboBox().getItems().clear();
		centerGridPane.getTitleNumberTextField().setText("");
		centerGridPane.getAlbumComboBox().getItems().clear();
		centerGridPane.getAlbumComboBox().getItems().add(new Release(album, "", "", ""));
		centerGridPane.getAlbumComboBox().getSelectionModel().select(0);

		centerGridPane.getAlbumArtistTextField().setText(albumArtist);
		centerGridPane.getGenreComboBox().getItems().clear();
		centerGridPane.getGenreComboBox().getItems().add(genre);
		centerGridPane.getGenreComboBox().getSelectionModel().select(0);

		centerGridPane.getComposerTextField().setText(composer);
		centerGridPane.getCommentComboBox().getItems().clear();
		centerGridPane.getCommentComboBox().getItems().add(comment);
		centerGridPane.getCommentComboBox().getSelectionModel().select(0);
		centerGridPane.getTotalTitleNumbersTextField().setText(maxTitleNumber);
		centerGridPane.getDiscNumberTextField().setText(discNumber);
		centerGridPane.getTotalDiscNumbersTextField().setText(maxDiscNumber);
		centerGridPane.getYearTextField().setText(year);
		centerGridPane.getArtistComboBox().getItems().clear();
		centerGridPane.getArtistComboBox().getItems().add(artist);
		centerGridPane.getArtistComboBox().getSelectionModel().select(0);
		;

		centerGridPane.disableFields();

		// Setting the artwork (If there is a common album)
		if (album.equals("")) {
			centerGridPane.setArtWorkImage(null);
		} else {
			boolean thereIsArtwork = false;
			int tagWithArtwork = 0;

			for (int i = 0; i < tags.length; i++) {
				if (tags[i].getRelease().getArtwork() != null) {
					thereIsArtwork = true;
					tagWithArtwork = i;
				}
			}

			if (thereIsArtwork) {
				centerGridPane.setArtWorkImage(tags[tagWithArtwork].getRelease().getArtwork());
			} else {
				centerGridPane.setArtWorkImage(null);
			}

		}

	}

	/**
	 * Method to return the common string if there is one
	 * 
	 * @param tags Tags, which will be compared
	 * @param tagType the tag type (needed for iteration)
	 * 
	 * @return nothing or the common string
	 */
	private String getSameEntries(MusicTag[] tags, Tags tagType) {
		String tmp = tags[0].getString(tagType);

		for (int i = 1; i < tags.length; i++) {
			if (!tags[i].getString(tagType).equals(tmp)) {
				return "";
			}
		}

		return tmp;
	}

	/**
	 * Clears the duplicates out of the received list
	 * 
	 * @param list the list
	 * @return duplicate free list
	 */
	private ArrayList<String> clearDuplicates(ArrayList<String> list) {
		List<String> al = list;
		Set<String> hs = new HashSet<>();
		hs.addAll(al);
		al.clear();
		al.addAll(hs);

		return list;
	}

}
