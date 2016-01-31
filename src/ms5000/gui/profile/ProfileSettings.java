package ms5000.gui.profile;

import java.io.File;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ms5000.gui.alert.ConfirmationAlert;
import ms5000.gui.chooser.DirChooser;
import ms5000.gui.mainframe.Main_Frame;
import ms5000.gui.mainframe.center.CenterGridPane;
import ms5000.properties.ProfileProperties;
import ms5000.properties.PropertiesUtils;
import ms5000.properties.icons.IconProperties;
import ms5000.properties.library.OrderingProperty;
import ms5000.properties.playlist.Header;

/**
 * This class implements the profile view after pressing the settings button
 */
public class ProfileSettings extends GridPane {
	/**
	 * The path to the style css
	 */
	private final String cssPath = PropertiesUtils.getString("profile.settings.config.css.path");
	
	/**
	 * The label css id's
	 */
	private final String labelBigId = PropertiesUtils.getString("profile.settings.config.css.label.big.id");
	private final String labelSmallId = PropertiesUtils.getString("profile.settings.config.css.label.small.id");
	
	/**
	 * OpenFolder button css id
	 */
	private final String openFolderButtonId = PropertiesUtils.getString("profile.settings.config.css.openFolder.button.id");
	
	/**
	 * Dialog texts
	 */
	private final String header_text = PropertiesUtils.getString("profile.settings.text.dialog.header.text");
	private final String header = PropertiesUtils.getString("profile.settings.text.dialog.header");
	
	/**
	 * The labels
	 */
	private Label musicLibrarySettings_Label;
	private Label importSettings_Label;
	private Label playlistSettings_Label;	
	private Label musicLibraryPath_Label;
	private Label orderingMode_Label;
	private Label keepFiles_Label;
	private Label justTagFiles_Label;
	private Label playListExportPath_Label;
	private Label playListExport_Label;
	private Label playListName_Label;
	private Label playListHeader_Label;
	private Label linkedBrainzSupport_Label;
	private Label suggestions_Label;
	private Label featureArtist_Label;
	private Label smartMode_Label;
	private Label compilations_Label;
	private Label coverArt_Label;
	private Label fullAutomatic_Label;
	
	/**
	 * Music library text field and open folder button
	 */
	private TextField musicLibraryPath;
	private Button chooseDirToMusicLibrary;
	
	/**
	 * The choice box for the ordering mode
	 */
	private ChoiceBox<String> orderingMode;
	private final String AAA = PropertiesUtils.getString("profile.settings.text.choicebox.ordering.mode.aaa");
	private final String AA = PropertiesUtils.getString("profile.settings.text.choicebox.ordering.mode.aa");
	private final String GAA = PropertiesUtils.getString("profile.settings.text.choicebox.ordering.mode.gaa");
	
	/**
	 * The choice box for feature settings
	 */
	private ChoiceBox<String> featureSettings;
	private final String noInclude = PropertiesUtils.getString("profile.settings.text.choicebox.feature.artist.no.include");
	private final String artistInclude = PropertiesUtils.getString("profile.settings.text.choicebox.feature.artist.include.artist");
	private final String titleInclude = PropertiesUtils.getString("profile.settings.text.choicebox.feature.artist.include.title");
	
	/**
	 * Radio Button for smart mode
	 */
	private RadioButton smartMode;
	
	/**
	 * Radio button for keeping the files
	 */
	private RadioButton keepFiles;
	
	/**
	 * Radio button for just tagging the files
	 */
	private RadioButton justTagFiles;
	
	/**
	 * Radio button for enabling the full automatic mode
	 */
	private RadioButton fullAutomatic;
	
	/**
	 * Radio button for enabling the linked brainz support
	 */
	private RadioButton lbSupport;
	
	/**
	 * Radio button for enabling the compilations load
	 */
	private RadioButton compilations;
	
	/**
	 * Radio button for enabling the coverArt load
	 */
	private RadioButton coverArt;
	
	/**
	 * Text fields and radio button for playlist export configuration
	 */
	private TextField playListExportPath;
	private RadioButton playListExport;
	private Button chooseDirToExportPlayList;
	private TextField playListName;
	
	private ChoiceBox<String> playListHeaderMode;
	private final String germanHeader = PropertiesUtils.getString("profile.settings.text.choicebox.playlist.header.german");
	private final String englishHeader = PropertiesUtils.getString("profile.settings.text.choicebox.playlist.header.english");
	
	/**
	 * Instance of this
	 */
	private static ProfileSettings profileSettings = new ProfileSettings();
	
	/**
	 * Scene of the properties settings
	 */
	private Scene scene = new Scene(this);
	
	/**
	 * The apply button
	 */
	private Button applyButton;
	
	/**
	 * The restore button
	 */
	private Button restoreButton;
	
	/**
	 * The stage on which the grid pane gets shown
	 */
	private Stage propertiesStage;
	
	/**
	 * Constructs the profile grid pane
	 */
	private ProfileSettings(){
		// Setting the style sheet
		this.getStylesheets().add(this.getClass().getResource(cssPath).toExternalForm());
		this.getStyleClass().add(PropertiesUtils.getString("profile.settings.config.css.grid.id"));
		
		// Library Settings
		musicLibrarySettings_Label = new Label(PropertiesUtils.getString("profile.settings.text.label.music.library.settings"));
		musicLibrarySettings_Label.setId(labelBigId);
		
		musicLibraryPath_Label = new Label(PropertiesUtils.getString("profile.settings.text.label.music.library.path"));
		musicLibraryPath_Label.setId(labelSmallId);
		musicLibraryPath = new TextField("");
		musicLibraryPath.setEditable(false);
		
		orderingMode_Label = new Label(PropertiesUtils.getString("profile.settings.text.label.music.library.ordering.mode"));
		orderingMode_Label.setId(labelSmallId);
		orderingMode = new ChoiceBox<String>();
		orderingMode.setItems(FXCollections.observableArrayList(AAA, AA, GAA));	
		
		chooseDirToMusicLibrary = new Button();
		chooseDirToMusicLibrary.setId(openFolderButtonId);
		chooseDirToMusicLibrary.setGraphic(new ImageView(PropertiesUtils.getProperty(IconProperties.OPEN_FOLDER_IMPORT)));
		chooseDirToMusicLibrary.setOnAction(getButtonSetLibraryEventHandler());
		
		CenterGridPane.setConstraints(musicLibrarySettings_Label, 0, 0, 3, 1);
		CenterGridPane.setConstraints(musicLibraryPath_Label, 0, 1, 1, 1);
		CenterGridPane.setConstraints(musicLibraryPath, 1, 1, 1, 1);
		CenterGridPane.setConstraints(chooseDirToMusicLibrary, 2, 1, 1, 1);
		CenterGridPane.setConstraints(orderingMode_Label, 0, 2, 1, 1);
		CenterGridPane.setConstraints(orderingMode, 1, 2, 3, 1);
		
		// Import Settings
		importSettings_Label = new Label(PropertiesUtils.getString("profile.settings.text.label.import.settings"));
		importSettings_Label.setId(labelBigId);
		keepFiles_Label = new Label(PropertiesUtils.getString("profile.settings.text.label.import.settings.keep.files"));
		keepFiles_Label.setId(labelSmallId);
		keepFiles = new RadioButton();
		
		justTagFiles_Label = new Label(PropertiesUtils.getString("profile.settings.text.label.import.settings.tag.files"));
		justTagFiles_Label.setId(labelSmallId);
		justTagFiles = new RadioButton();
		
		fullAutomatic = new RadioButton();
		fullAutomatic_Label = new Label(PropertiesUtils.getString("profile.settings.text.label.full.automatic"));
		fullAutomatic_Label.setId(labelSmallId);
		
		CenterGridPane.setConstraints(importSettings_Label, 0, 4, 3, 1);
		CenterGridPane.setConstraints(keepFiles_Label, 0, 5, 1, 1);
		CenterGridPane.setConstraints(keepFiles, 1, 5, 1, 1);
		CenterGridPane.setConstraints(justTagFiles_Label, 0, 6, 1, 1);
		CenterGridPane.setConstraints(justTagFiles, 1, 6, 1, 1);
		CenterGridPane.setConstraints(fullAutomatic_Label, 0, 7, 1, 1);
		CenterGridPane.setConstraints(fullAutomatic, 1, 7, 1, 1);
		
		
		// Suggestions settings
		suggestions_Label = new Label(PropertiesUtils.getString("profile.settings.text.label.suggestions.settings"));
		suggestions_Label.setId(labelBigId);
		featureArtist_Label = new Label(PropertiesUtils.getString("profile.settings.text.label.suggestions.settings.feature"));
		featureArtist_Label.setId(labelSmallId);
		featureSettings = new ChoiceBox<String>();
		featureSettings.getItems().addAll(noInclude,artistInclude,titleInclude);
		linkedBrainzSupport_Label = new Label(PropertiesUtils.getString("profile.settings.text.label.suggestions.settings.lb.support"));
		linkedBrainzSupport_Label.setId(labelSmallId);
		lbSupport = new RadioButton();
		lbSupport.selectedProperty().addListener(getLbSupportChangeListener());
		
		smartMode_Label = new Label(PropertiesUtils.getString("profile.settings.text.label.suggestions.settings.smart.mode"));
		smartMode_Label.setId(labelSmallId);
		smartMode = new RadioButton();
		compilations_Label = new Label(PropertiesUtils.getString("profile.settings.text.label.suggestions.settings.compilations"));
		compilations_Label.setId(labelSmallId);
		compilations = new RadioButton();
		coverArt_Label = new Label(PropertiesUtils.getString("profile.settings.text.label.suggestions.settings.cover.art"));
		coverArt_Label.setId(labelSmallId);
		coverArt = new RadioButton();
		
		CenterGridPane.setConstraints(suggestions_Label, 0, 9, 3, 1);
		CenterGridPane.setConstraints(lbSupport, 1, 10, 1, 1);
		CenterGridPane.setConstraints(linkedBrainzSupport_Label, 0, 10, 1, 1);
		CenterGridPane.setConstraints(featureArtist_Label, 0, 11, 1, 1);
		CenterGridPane.setConstraints(featureSettings, 1, 11, 5, 1);
		CenterGridPane.setConstraints(smartMode_Label, 0, 12, 5, 1);
		CenterGridPane.setConstraints(smartMode, 1, 12, 5, 1);
		CenterGridPane.setConstraints(compilations_Label, 0, 13, 5, 1);
		CenterGridPane.setConstraints(compilations, 1, 13, 5, 1);
		CenterGridPane.setConstraints(coverArt_Label, 0, 14, 5, 1);
		CenterGridPane.setConstraints(coverArt, 1, 14, 5, 1);
		
		// Playlist export settings
		playlistSettings_Label = new Label(PropertiesUtils.getString("profile.settings.text.label.playlist.settings"));
		playlistSettings_Label.setId(labelBigId);
		
		playListExport_Label = new Label(PropertiesUtils.getString("profile.settings.text.label.playlist.settings.export"));
		playListExport_Label.setId(labelSmallId);
		playListExport = new RadioButton();
		
		playListName_Label = new Label(PropertiesUtils.getString("profile.settings.text.label.playlist.settings.name"));
		playListName_Label.setId(labelSmallId);
		playListName = new TextField();
		
		playListExportPath_Label = new Label(PropertiesUtils.getString("profile.settings.text.label.playlist.settings.export.dir"));
		playListExportPath_Label.setId(labelSmallId);
		playListExportPath = new TextField("");
		playListExportPath.setEditable(false);
		chooseDirToExportPlayList = new Button();
		chooseDirToExportPlayList.setId(openFolderButtonId);
		chooseDirToExportPlayList.setGraphic(new ImageView(PropertiesUtils.getProperty(IconProperties.OPEN_FOLDER_IMPORT)));
		chooseDirToExportPlayList.setOnAction(getButtonSetPlayListDirEventHandler());
		playListHeader_Label = new Label(PropertiesUtils.getString("profile.settings.text.label.playlist.settings.header.language"));
		playListHeader_Label.setId(labelSmallId);
		playListHeaderMode = new ChoiceBox<String>();
		playListHeaderMode.setItems(FXCollections.observableArrayList(germanHeader, englishHeader));	
		
		CenterGridPane.setConstraints(playlistSettings_Label, 0, 16, 3, 1);
		CenterGridPane.setConstraints(playListExport_Label, 0, 17, 1, 1);
		CenterGridPane.setConstraints(playListExport, 1, 17, 1, 1);
		CenterGridPane.setConstraints(playListName_Label, 0, 18, 1, 1);
		CenterGridPane.setConstraints(playListName, 1, 18, 1, 1);
		CenterGridPane.setConstraints(playListExportPath_Label, 0, 19, 1, 1);
		CenterGridPane.setConstraints(playListExportPath, 1, 19, 1, 1);
		CenterGridPane.setConstraints(chooseDirToExportPlayList, 2, 19, 1, 1);
		CenterGridPane.setConstraints(playListHeader_Label, 0, 20, 1, 1);
		CenterGridPane.setConstraints(playListHeaderMode, 1, 20, 1, 1);
		
		/**
		 * Restore and apply button
		 */
		applyButton = new Button(PropertiesUtils.getString("profile.settings.text.button.apply"));
		applyButton.setOnAction(getButtonApplyEventHandler());
		
		restoreButton = new Button(PropertiesUtils.getString("profile.settings.text.button.restore"));
		restoreButton.setOnAction(getButtonRestoreEventHandler());
		
		CenterGridPane.setConstraints(restoreButton, 1, 23, 1, 1, HPos.RIGHT, VPos.CENTER);
		CenterGridPane.setConstraints(applyButton, 2, 23, 1, 1);
		
		this.getChildren().addAll(fullAutomatic, fullAutomatic_Label, compilations, compilations_Label, coverArt,
				coverArt_Label, smartMode_Label, smartMode, featureSettings, featureArtist_Label, suggestions_Label,
				musicLibrarySettings_Label, musicLibraryPath_Label, musicLibraryPath, chooseDirToMusicLibrary,
				orderingMode_Label, orderingMode, importSettings_Label, keepFiles_Label, keepFiles, justTagFiles_Label,
				justTagFiles, playlistSettings_Label, playListExport_Label, playListExport, playListName_Label,
				playListName, playListExportPath_Label, playListExportPath, chooseDirToExportPlayList, restoreButton,
				applyButton, playListHeader_Label, playListHeaderMode, lbSupport, linkedBrainzSupport_Label);

		/**
		 * Reading the properties and adding them to the pane
		 */
		readProperties();
	}
	
	/**
	 * Method to show the profile gridpane
	 */
	public void showPropertiesPage() {
		/**
		 * Reading the properties and adding them to the pane
		 */
		readProperties();
		
		propertiesStage = new Stage();
		propertiesStage.setTitle(PropertiesUtils.getString("profile.settings.text.frame.title"));

		propertiesStage.initModality(Modality.APPLICATION_MODAL);
		propertiesStage.setScene(scene);
		propertiesStage.show();
	}
	
	/**
	 * Method to read the properties and adding them to the pane
	 */
	private void readProperties() {
		ProfileProperties profile = PropertiesUtils.getProfile();
		
		// Musiclibrary properties
		musicLibraryPath.setText(profile.getPathToMusicLibrary());
		OrderingProperty orderIngProperty = profile.getOrderingMode();
		
		if (orderIngProperty == OrderingProperty.GAA) {
			orderingMode.setValue(GAA);
		} else if (orderIngProperty == OrderingProperty.AA) {
			orderingMode.setValue(AA);
		} else {
			orderingMode.setValue(AAA);
		}
		
		if (profile.getPlayListHeader().contains("german")) {
			playListHeaderMode.setValue(germanHeader);
		} else {
			playListHeaderMode.setValue(englishHeader);
		}
		
		// Import properties
		keepFiles.setSelected(profile.isKeepOriginalFiles());
		justTagFiles.setSelected(profile.isJustTagFiles());
		fullAutomatic.setSelected(profile.isFullAutomatic());
		
		// Playlist properties
		playListExport.setSelected(profile.isPlayListExport());
		playListExportPath.setText(profile.getPlayListExportDir());
		
		// Suggestions properties
		coverArt.setSelected(profile.isCoverArtLoad());
		smartMode.setSelected(profile.isSmartMode());
		compilations.setSelected(profile.isCompilations());
		lbSupport.setSelected(profile.isLinkedBrainzSupport());
		
		if (lbSupport.isSelected()) { 
            coverArt.setDisable(false);
            compilations.setDisable(false);
            featureSettings.setDisable(false);
            smartMode.setDisable(false);
        } else {
        	coverArt.setDisable(true);
        	compilations.setDisable(true);
        	featureSettings.setDisable(true);
            smartMode.setDisable(true);
        }
		
		
		int featureArtistSupport = profile.getFeatureArtistSupport();
		
		if (featureArtistSupport == 1) {
			featureSettings.setValue(artistInclude);
		} else if (featureArtistSupport == 2) {
			featureSettings.setValue(titleInclude);
		} else {
			featureSettings.setValue(noInclude);
		}
	}
	
	/**
	 * Returns the event handler for the apply button
	 * 
	 * @return the event handler for the apply button
	 */
	private EventHandler<ActionEvent> getButtonApplyEventHandler() {
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ProfileProperties profile = PropertiesUtils.getProfile();

				Boolean proceed;
				
				// Ask the user if he wants to proceed if music library properties are changed
				if (!musicLibraryPath.getText().equals(profile.getPathToMusicLibrary())
						|| differentOrderingMode(profile)) {
					proceed = showAlert();
				} else {
					proceed = true;
				}

				if (proceed) {
					// Writing the properties
					profile.setPathToMusicLibrary(musicLibraryPath.getText());

					if (orderingMode.getValue().equals(GAA)) {
						profile.setOrderingMode(OrderingProperty.GAA);
					} else if (orderingMode.getValue().equals(AA)) {
						profile.setOrderingMode(OrderingProperty.AA);
					} else {
						profile.setOrderingMode(OrderingProperty.AAA);
					}
					
					if (playListHeaderMode.getValue().toLowerCase().contains("english")) {
						profile.setPlayListHeader(Header.ENGLISH.toString());
					} else {
						profile.setPlayListHeader(Header.GERMAN.toString());
					}
					
					profile.setKeepOriginalFiles(keepFiles.isSelected());
					profile.setJustTagFiles(justTagFiles.isSelected());
					profile.setFullAutomatic(fullAutomatic.isSelected());
					
					profile.setPlayListExport(playListExport.isSelected());
					profile.setPlayListExportDir(playListExportPath.getText());
					
					profile.setLinkedBrainzSupport(lbSupport.isSelected());
					profile.setCompilations(compilations.isSelected());
					profile.setCoverArtLoad(coverArt.isSelected());
					profile.setSmartMode(smartMode.isSelected());
					
					if (featureSettings.getValue().equals(artistInclude)) {
						profile.setFeatureArtistSupport(1);
					} else if (featureSettings.getValue().equals(titleInclude)) {
						profile.setFeatureArtistSupport(2);
					} else {
						profile.setFeatureArtistSupport(3);
					}
					
					PropertiesUtils.saveProfile();
				} else {
					// Restores the settings
					restoreButton.fire();
				}

			}

		};
	}
	
	/**
	 * Method to determine if a different ordering mode was chosen
	 * 
	 * @param profile the original profile
	 * @return boolean indicating whether a different ordering mode was chosen
	 */
	private boolean differentOrderingMode(ProfileProperties profile) {
		OrderingProperty newProperty;
		
		if (orderingMode.getValue().equals(GAA)) {
			newProperty = OrderingProperty.GAA;
		} else if (orderingMode.getValue().equals(AA)) {
			newProperty = OrderingProperty.AA;
		} else {
			newProperty = OrderingProperty.AAA;
		}
		
		if (newProperty.equals(profile.getOrderingMode())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the event handler for the restore button
	 * 
	 * @return the event handler for the restore button
	 */
	private EventHandler<ActionEvent> getButtonRestoreEventHandler() {
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				readProperties();
			}
			
		};
	}
	
	/**
	 * Returns the event handler for the set library button
	 * 
	 * @return the event handler for the set library button
	 */
	private EventHandler<ActionEvent> getButtonSetLibraryEventHandler() {
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				File library = new File(PropertiesUtils.getProfile().getPathToMusicLibrary());
				
				File selectedDir = new DirChooser(library,
						PropertiesUtils.getString("profile.settings.text.dir.chooser.title.library"),
						propertiesStage).getSelectedDir();

				if (selectedDir != null) {
					musicLibraryPath.setText(selectedDir.getAbsolutePath());
				}
			}
			
		};
	}
	
	/**
	 * Returns the event handler for the set playlist export dir button
	 * 
	 * @return the event handler for the playlist export dir button
	 */
	private EventHandler<ActionEvent> getButtonSetPlayListDirEventHandler() {
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				File exportDir = new File(PropertiesUtils.getProfile().getPlayListExportDir());
				File selectedDir = new DirChooser(exportDir,
						PropertiesUtils.getString("profile.settings.text.dir.chooser.title.playlist"),
						propertiesStage).getSelectedDir();
				
				if (selectedDir != null) {
					Main_Frame.getPrimaryStage().toBack();
					
					playListExportPath.setText(selectedDir.getAbsolutePath());
				}
			}
			
		};
	}
	
	/**
	 * Method to show a dialog when music library properties are changed
	 * 
	 * @return boolean indicating wheter the user wants to proceed
	 */
	private boolean showAlert() {
		// Setting the dialog text
		ConfirmationAlert alert = new ConfirmationAlert(header, header_text);
		alert.showDialog();
		return alert.getResponse();
	}
	
	/**
	 * Returns the name of the playlist
	 * 
	 * @return the name of the playlist
	 */
	public String getPlaListyName() {
		return this.playListName.getText();
	}
	
	/**
	 * Returns an instance of the Profile Settings
	 * 
	 * @return instance of the Profile Settings
	 */
	public static ProfileSettings getInstance() {
		return profileSettings;
	}
	
	private ChangeListener<Boolean> getLbSupportChangeListener() {
		return new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if (isNowSelected) { 
		            coverArt.setDisable(false);
		            compilations.setDisable(false);
		            featureSettings.setDisable(false);
		            smartMode.setDisable(false);
		        } else {
		        	coverArt.setDisable(true);
		        	compilations.setDisable(true);
		        	featureSettings.setDisable(true);
		            smartMode.setDisable(true);
		        }
		    }
		};
	}

	public TextField getMusicLibraryPath() {
		return musicLibraryPath;
	}

	public TextField getPlayListExportPath() {
		return playListExportPath;
	}
}
