package ms5000.gui.mainframe.center;

import java.io.IOException;

import org.jaudiotagger.tag.datatype.Artwork;

import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import ms5000.gui.mainframe.Main_Frame;
import ms5000.gui.mainframe.center.eventhandler.ButtonOpenFileExplorerEventHandler;
import ms5000.gui.mainframe.center.eventhandler.CenterGridPane_AlbumCB_CellFactory;
import ms5000.gui.mainframe.center.eventhandler.CenterGridPane_AlbumCB_IndexChangeListener;
import ms5000.gui.mainframe.center.eventhandler.CenterGridPane_ButtonSave_EventHandler;
import ms5000.gui.mainframe.center.eventhandler.CenterGridPane_OpenFileExp_EventHandler;
import ms5000.gui.mainframe.center.eventhandler.CenterGridPane_OpenWebBrowse_EventHandler;
import ms5000.gui.mainframe.center.eventhandler.Import_Artwork_EventHandler;
import ms5000.musicfile.tag.Release;
import ms5000.properties.PropertiesUtils;
import ms5000.properties.icons.IconProperties;
import ms5000.properties.library.OrderingProperty;

/**
 * This class implements the detail view on the right hand side of the main frame
 *
 */
public class CenterGridPane extends GridPane {
	
	/**
	 * With this enum it is possible to iterate through the values of the textfields
	 * in the detail view 
	 */
	public enum FieldKey {
		TITLENAME, ALBUM, ARTIST, ALBUM_ARTIST, TITLENUMBER, TITLENUMBER_TOTAL, 
		DISCNUMBER, DISCNUMBER_TOTAL, COMPOSER, COMMENT, GENRE, YEAR
	}

	/**
	 * The general information
	 */
	private Label tagInformation_Label;
	private Label artWork_Label;
	
	private Label fileInformation_Label;
	private Label filePath_Label;
	private TextField filePathTextField;
	
	/**
	 * The buttons of the view
	 */
	private Button button_openFolder;
	private Button button_save_Tag;
	private Button button_open_ImageFile;
	
	/**
	 * Artwork
	 */
	private ImageView artWork_ImageView;
	private Artwork artworkTemp;
	
	/**
	 * Tag information textfields and labels
	 */
	private ComboBox<String> titlenameComboBox;
	private ComboBox<String> artistComboBox;
	private ComboBox<String> genreComboBox;
	private ComboBox<Release> albumComboBox;
	private TextField yearTextField;
	private TextField titleNumberTextField;
	private TextField totalTitleNumbersTextField;
	private TextField albumArtistTextField;
	private TextField composerTextField;
	private ComboBox<String> commentComboBox;
	private TextField discNumberTextField;
	private TextField totalDiscNumbersTextField;

	private Label titlename_Label;
	private Label artist_Label;
	private Label album_Label;
	private Label genre_Label;
	private Label year_Label;
	private Label titleNumber_Label;
	private Label totalTitleNumbers_Label;
	private Label albumArtist_Label;
	private Label composer_Label;
	private Label comment_Label;
	private Label discNumber_Label;
	private Label totalDiscNumbers_Label;
	private Button openWebBrowserOnComment;
	
	private String cssPath = PropertiesUtils.getString("center.section.config.css.path");
	private String labelLargeId = PropertiesUtils.getString("center.section.config.css.label.large.id");
	private String labelSmallId = PropertiesUtils.getString("center.section.config.css.label.small.id");
	
	/**
	 * Paths to the icon files and the correspondig image objects
	 */
	private final Image openFolder_Image = new Image(PropertiesUtils.getProperty(IconProperties.OPEN_FOLDER_SHOW));
	private final Image save_Image = new Image (PropertiesUtils.getProperty(IconProperties.SAVE));
	private final Image open_File_Image = new Image (PropertiesUtils.getProperty(IconProperties.OPEN_FOLDER_IMPORT));
	private final Image artworkDefault = new Image(PropertiesUtils.getProperty(IconProperties.ARTWORK));
	private final Image openWebBrowse = new Image(PropertiesUtils.getProperty(IconProperties.QUESTION_MARK));
	
	/**
	 * The default format for the text fields
	 */
	private final String default_format = PropertiesUtils.getString("center.section.config.css.color.textfield.default");
	
	/**
	 * The formats for the different cases 
	 */
	private final String missingCritical = PropertiesUtils.getString("center.section.config.css.color.textfield.missing.critical");
	private final String missingNonCritical = PropertiesUtils.getString("center.section.config.css.color.textfield.missing.non.critical");
	private final String missingWeak = PropertiesUtils.getString("center.section.config.css.color.textfield.missing.weak");
	
	/**
	 * Initializes the grid pane
	 */
	public CenterGridPane() {
		init();
	}
	
	/**
	 * Method used in the constructor to initialize the grid pane
	 */
	private void init() {
		// Styling the grid pane
		this.setHgap(15);
		this.setVgap(5);
		this.setPadding(new Insets(0, 10, 0, 10));

		this.setPrefWidth(Main_Frame.getPrefFrameWidth() / 2);
		this.setMinWidth(Main_Frame.getMinFrameWidth() / 2);
		this.setMaxWidth(Main_Frame.getPrefFrameWidth() / 2);
		
		// Applying the style sheet
		this.getStylesheets().add(this.getClass().getResource(cssPath).toExternalForm());
		
		// General informations
		tagInformation_Label = new Label(PropertiesUtils.getString("center.section.text.label.tag.information"));
		tagInformation_Label.setId(labelLargeId);

		fileInformation_Label = new Label(PropertiesUtils.getString("center.section.text.label.file.information"));
		fileInformation_Label.setId(labelLargeId);

		artWork_Label = new Label(PropertiesUtils.getString("center.section.text.label.artwork"));
		artWork_Label.setId(labelLargeId);
		
		filePath_Label = new Label(PropertiesUtils.getString("center.section.text.label.file.path"));
		filePath_Label.setId(labelSmallId);
		
		filePathTextField = new TextField("");
		filePathTextField.setEditable(false);
		
		// The buttons
		button_openFolder = new Button();
		button_openFolder.setGraphic(new ImageView(openFolder_Image));
		button_openFolder.setOnAction(
				new CenterGridPane_OpenFileExp_EventHandler(ButtonOpenFileExplorerEventHandler.CENTERGRID_MUSIC_FILE_PATH, null));
		button_save_Tag = new Button();
		button_save_Tag.setOnAction(new CenterGridPane_ButtonSave_EventHandler());
		button_save_Tag.setGraphic(new ImageView(save_Image));
		
		button_open_ImageFile = new Button();
		button_open_ImageFile.setGraphic(new ImageView(open_File_Image));
		button_open_ImageFile.setOnAction(new Import_Artwork_EventHandler());
		
		// Default image of the artwork view
		artWork_ImageView = new ImageView(artworkDefault);
		artWork_ImageView.setFitWidth(200);
		artWork_ImageView.setPreserveRatio(true);
		artWork_ImageView.setSmooth(true);
		artWork_ImageView.setCache(true);
		
		// The TextFields of the tag informations
		titlenameComboBox = new ComboBox<String>();
		titlenameComboBox.setEditable(true);
		artistComboBox = new ComboBox<String>();
		artistComboBox.setEditable(true);
		
		genreComboBox = new ComboBox<String>();
		genreComboBox.setEditable(true);
		yearTextField = new TextField("");
		
		titleNumberTextField = new TextField("");
		titleNumberTextField.setMaxWidth(45);
		titleNumberTextField.setMinWidth(45);
		
		totalTitleNumbersTextField = new TextField("");
		totalTitleNumbersTextField.setMaxWidth(45);
		totalTitleNumbersTextField.setMinWidth(45);
		
		albumComboBox = new ComboBox<Release>();
		albumComboBox.setEditable(true);		
		albumComboBox.getSelectionModel().selectedIndexProperty()
				.addListener(new CenterGridPane_AlbumCB_IndexChangeListener(albumComboBox, this));
		albumComboBox.setCellFactory(new CenterGridPane_AlbumCB_CellFactory());
		
		albumArtistTextField = new TextField("");
		composerTextField = new TextField("");
		commentComboBox = new ComboBox<String>();
		commentComboBox.setEditable(true);
		commentComboBox.setId("comboBox_Big");
		
		openWebBrowserOnComment = new Button();
		openWebBrowserOnComment.setGraphic(new ImageView(openWebBrowse));
		openWebBrowserOnComment.setOnAction(new CenterGridPane_OpenWebBrowse_EventHandler());
		
		discNumberTextField = new TextField("");
		discNumberTextField.setMaxWidth(45);
		discNumberTextField.setMinWidth(45);
		
		totalDiscNumbersTextField = new TextField("");
		totalDiscNumbersTextField.setMaxWidth(45);
		totalDiscNumbersTextField.setMinWidth(45);

		// The Labels for the tag information text fields
		artist_Label = new Label(PropertiesUtils.getString("center.section.text.label.artist"));
		artist_Label.setId(labelSmallId);

		titlename_Label = new Label(PropertiesUtils.getString("center.section.text.label.title.name"));
		titlename_Label.setId("labelSmall_Expanded");

		genre_Label = new Label(PropertiesUtils.getString("center.section.text.label.genre"));
		genre_Label.setId(labelSmallId);

		year_Label = new Label(PropertiesUtils.getString("center.section.text.label.year"));
		year_Label.setId(labelSmallId);

		titleNumber_Label = new Label(PropertiesUtils.getString("center.section.text.label.title.number"));
		titleNumber_Label.setId(labelSmallId);

		album_Label = new Label(PropertiesUtils.getString("center.section.text.label.album"));
		album_Label.setId(labelSmallId);

		albumArtist_Label = new Label(PropertiesUtils.getString("center.section.text.label.album.artist"));
		albumArtist_Label.setId(labelSmallId);

		composer_Label = new Label(PropertiesUtils.getString("center.section.text.label.composer"));
		composer_Label.setId(labelSmallId);

		comment_Label = new Label(PropertiesUtils.getString("center.section.text.label.comment"));
		comment_Label.setId(labelSmallId);

		discNumber_Label = new Label(PropertiesUtils.getString("center.section.text.label.disc.number"));
		discNumber_Label.setId(labelSmallId);

		totalTitleNumbers_Label = new Label(" - ");
		totalTitleNumbers_Label.setId(labelSmallId);
		totalTitleNumbers_Label.setMinWidth(15);

		totalDiscNumbers_Label = new Label(" - ");
		totalDiscNumbers_Label.setId(labelSmallId);
		totalDiscNumbers_Label.setMinWidth(15);
		
		// Putting text fields and labels on to the grid pane
		// Tag Information
		CenterGridPane.setConstraints(tagInformation_Label, 1, 1, 2, 2);
		CenterGridPane.setConstraints(button_save_Tag,3,1,1,2);
		
		// Titlename
		CenterGridPane.setConstraints(titlename_Label, 1, 3);
		CenterGridPane.setConstraints(titlenameComboBox, 2, 3, 6, 1);

		// Artist
		CenterGridPane.setConstraints(artist_Label, 8, 3);
		CenterGridPane.setConstraints(artistComboBox, 9, 3, 6, 1);
		
		// Album
		CenterGridPane.setConstraints(album_Label, 1, 4);
		CenterGridPane.setConstraints(albumComboBox, 2, 4, 6, 1);
		
		// Year
		CenterGridPane.setConstraints(year_Label, 8, 4);
		CenterGridPane.setConstraints(yearTextField, 9, 4, 5, 1);

		// Album Artist
		CenterGridPane.setConstraints(albumArtist_Label, 1, 5);
		CenterGridPane.setConstraints(albumArtistTextField, 2, 5, 6, 1);
		
		// Genre
		CenterGridPane.setConstraints(genre_Label, 8, 5);
		CenterGridPane.setConstraints(genreComboBox, 9, 5, 6, 1);

		// Titlenumber
		HBox boxTitleNumber = new HBox();
		boxTitleNumber.setAlignment(Pos.CENTER);
		boxTitleNumber.getChildren().addAll(titleNumberTextField, totalTitleNumbers_Label, totalTitleNumbersTextField);
		CenterGridPane.setConstraints(titleNumber_Label, 1, 6);
		CenterGridPane.setConstraints(boxTitleNumber, 2, 6,2,1);

		// DiscNumber
		HBox boxDiscNumber = new HBox();
		boxDiscNumber.setAlignment(Pos.CENTER);
		boxDiscNumber.getChildren().addAll(discNumberTextField,totalDiscNumbers_Label,totalDiscNumbersTextField);
		CenterGridPane.setConstraints(discNumber_Label, 8, 6);
		CenterGridPane.setConstraints(boxDiscNumber, 9, 6,2,1);

		// Composer
		CenterGridPane.setConstraints(composer_Label, 1, 7);
		CenterGridPane.setConstraints(composerTextField, 2, 7, 8, 1);

		// Comment
		CenterGridPane.setConstraints(comment_Label, 1, 8);
		CenterGridPane.setConstraints(commentComboBox, 2, 8, 8, 1);
		CenterGridPane.setConstraints(openWebBrowserOnComment,9,8);
		
		// Artwork
		CenterGridPane.setConstraints(artWork_Label,1,9, 1, 2);
		CenterGridPane.setConstraints(artWork_ImageView,1,11,9,1);
		CenterGridPane.setConstraints(button_open_ImageFile,2,9,1,2);
		
		// File Information
		CenterGridPane.setConstraints(fileInformation_Label, 1, 12, 9, 2);
		CenterGridPane.setConstraints(filePath_Label, 1, 14);
		CenterGridPane.setConstraints(filePathTextField, 2, 14, 7, 1);
		CenterGridPane.setConstraints(button_openFolder,9,14);

		// Adding the children to the pane
		this.getChildren().addAll(boxDiscNumber, boxTitleNumber, titlename_Label, titlenameComboBox, artist_Label,
				artistComboBox, album_Label, albumComboBox, year_Label, yearTextField, titleNumber_Label,
				discNumber_Label, composer_Label, composerTextField, commentComboBox, comment_Label, albumArtist_Label,
				albumArtistTextField, genreComboBox, genre_Label, tagInformation_Label, fileInformation_Label,
				filePath_Label, filePathTextField, button_openFolder, artWork_Label, artWork_ImageView,
				button_save_Tag, button_open_ImageFile,openWebBrowserOnComment);
		
		
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		System.out.println(primaryScreenBounds.getWidth());
		
		applyCSS(primaryScreenBounds.getWidth());
		
	}

	/**
	 * Getter and Setter methods for this class
	 */
	
	/**
	 * @return Instance of the filepath textfield
	 */
	public TextField getFilePathTextField() {
		return filePathTextField;
	}
	
	/**
	 * @return Instance of the artist combobox
	 */
	public ComboBox<String> getArtistComboBox() {
		return artistComboBox;
	}
	
	/**
	 * @return Instance of the titlename combobox
	 */
	public ComboBox<String> getTitlenameComboBox() {
		return this.titlenameComboBox;
	}
	
	/**
	 * @return Instance of the genre combobox
	 */
	public ComboBox<String> getGenreComboBox() {
		return genreComboBox;
	}
	
	/**
	 * @return Instance of the album textfield
	 */
	public ComboBox<Release> getAlbumComboBox() {
		return albumComboBox;
	}
	
	/**
	 * @return Instance of the album year textfield
	 */
	public TextField getYearTextField() {
		return yearTextField;
	}
	
	/**
	 * @return Instance of the title number textfield
	 */
	public TextField getTitleNumberTextField() {
		return titleNumberTextField;
	}
	
	/**
	 * @return Instance of the total title number textfield
	 */
	public TextField getTotalTitleNumbersTextField() {
		return totalTitleNumbersTextField;
	}
	
	/**
	 * @return Instance of the album artist textfield
	 */
	public TextField getAlbumArtistTextField() {
		return albumArtistTextField;
	}
	
	/**
	 * @return Instance of the composer textfield
	 */
	public TextField getComposerTextField() {
		return composerTextField;
	}
	
	/**
	 * @return Instance of the comment textfield
	 */
	public ComboBox<String> getCommentComboBox() {
		return commentComboBox;
	}
	
	/**
	 * @return Instance of the disc number textfield
	 */
	public TextField getDiscNumberTextField() {
		return discNumberTextField;
	}
	
	/**
	 * @return Instance of the total disc number textfield
	 */
	public TextField getTotalDiscNumbersTextField() {
		return totalDiscNumbersTextField;
	}
	
	/**
	 * @return Returns an instance of the temporarily stored artwork
	 */
	public Artwork getArtwork() {
		return artworkTemp;
	}
	
	/**
	 * @return Returns an instace of the save tag button to enable short key usage
	 */
	public Button getButtonSaveTag() {
		return button_save_Tag;
	}
	
	
	/**
	 * Method for setting the artwork view to an image delivered through the
	 * parameter artWork
	 * 
	 * @param artWork: Artwork which is used to set the artwork field
	 */
	public void setArtWorkImage(Artwork artWork) {
		if (artWork == null) {
			this.artWork_ImageView.setImage(artworkDefault);
			artworkTemp = null;
		} else {
			try {
				this.artWork_ImageView.setImage(SwingFXUtils.toFXImage(artWork.getImage(), null));
				artworkTemp = artWork;
			} catch (IOException e) {
				this.artWork_ImageView.setImage(artworkDefault);
				artworkTemp = null;
			}
		}
	}
	
	/**
	 * Method which delivers an instance of the textfield to the delivered key
	 * Makes it possible to iterate through the textfields of the grid pane
	 * 
	 * @param key Enum FieldKey
	 * @return instance of the corresponding text field
	 */
	public Object getField(FieldKey key) {
		switch (key) {
		case ALBUM:
			return getAlbumComboBox();
		case TITLENAME:
			return getTitlenameComboBox();
		case ARTIST:
			return getArtistComboBox();
		case ALBUM_ARTIST:
			return getAlbumArtistTextField();
		case TITLENUMBER:
			return getTitleNumberTextField();
		case TITLENUMBER_TOTAL:
			return getTotalTitleNumbersTextField();
		case DISCNUMBER:
			return getDiscNumberTextField();
		case DISCNUMBER_TOTAL:
			return getTotalDiscNumbersTextField();
		case COMPOSER:
			return getComposerTextField();
		case COMMENT:
			return getCommentComboBox();
		case YEAR:
			return getYearTextField();
		case GENRE:
			return getGenreComboBox();

		default:
			return null;
		}
	}
	
	/**
	 * Method to refresh the color profile of the detail view
	 */
	public void refershFieldColorProfile() {
		for (FieldKey key : FieldKey.values()) {
			if(this.getField(key) instanceof ComboBox) {
				ComboBox<?> tmp = (ComboBox<?>) this.getField(key);
				tmp.setStyle(default_format);
			} else {
				TextField tmp = (TextField) this.getField(key);
				tmp.setStyle(default_format);
			}	
		}
	}
	
	/**
	 * Method to clear the text fields in the detail view
	 */
	public void clearFields() {
		for (FieldKey key : FieldKey.values()) {
			if(this.getField(key) != null) {
				if (this.getField(key) instanceof ComboBox) {
					ComboBox<?> tmp = (ComboBox<?>) this.getField(key);
					tmp.getEditor().setText("");
					tmp.setItems(FXCollections.observableArrayList());
				} else {
					TextField tmp = (TextField) this.getField(key);
					tmp.setText("");
				}
				
				this.getFilePathTextField().setText("");
			}
		}
	}
	
	/**
	 * Method to enable certain text fields
	 */
	public void enableFields() {
		this.getTitlenameComboBox().setDisable(false);
		this.getTitleNumberTextField().setEditable(true);
		this.getFilePathTextField().setEditable(true);
	}
	
	/**
	 * Method to disable certain text fields
	 */
	public void disableFields() {
		this.getTitlenameComboBox().setDisable(true);
		this.getTitleNumberTextField().setEditable(false);
		this.getFilePathTextField().setEditable(false);
	}
	
	/**
	 * Method to set the color profile for the detail view
	 */
	public void setTextFieldColorProfile() {
		
		for (FieldKey key : FieldKey.values()) {
			if (key == FieldKey.ARTIST || key == FieldKey.ALBUM || key == FieldKey.TITLENAME) {
				if (((ComboBox<?>) this.getField(key)).getEditor().getText().equals("")) {
					((ComboBox<?>) this.getField(key)).setStyle(missingCritical);
				}
			} else if (key == FieldKey.TITLENUMBER || key == FieldKey.TITLENUMBER_TOTAL) {
				if (((TextField)this.getField(key)).getText().equals("0") || ((TextField)this.getField(key)).getText().equals("")) {
					((TextField)this.getField(key)).setStyle(missingNonCritical);
				}
			} else if (key == FieldKey.YEAR || key == FieldKey.ALBUM_ARTIST) {
				if (((TextField)this.getField(key)).getText().equals("")
						|| ((TextField)this.getField(key)).getText().equals("0")) {
					((TextField)this.getField(key)).setStyle(missingWeak);
				}
			} else if (key == FieldKey.GENRE) {
				if (PropertiesUtils.getProfile().getOrderingMode() == OrderingProperty.GAA) {
					if (((ComboBox<?>) this.getField(key)).getEditor().getText().equals("")) {
						((ComboBox<?>) this.getField(key)).setStyle(missingCritical);
					}
				} else {
					if (((ComboBox<?>) this.getField(key)).getEditor().getText().equals("")) {
						((ComboBox<?>) this.getField(key)).setStyle(missingNonCritical);
					}
				}
			}
		}
	}
	
	
	/**
	 * Applies the css accordint to the screen size
	 * 
	 * @param screenSize the screen size
	 */
	public void applyCSS(double screenSize) {
		if (screenSize > 1600.0) {
			for (FieldKey key : FieldKey.values()) {
				((Control)getField(key)).setId("textfield_Expanded");
				((Control)getField(key)).applyCss();
			}
			
			filePathTextField.setId("textfield_Expanded");
			filePathTextField.applyCss();
			
			titlename_Label.setId("labelSmall_Expanded");
			titlename_Label.applyCss();
			artist_Label.setId("labelSmall_Expanded");
			artist_Label.applyCss();
			
			album_Label.setId("labelSmall_Expanded");
			album_Label.applyCss();
			
			genre_Label.setId("labelSmall_Expanded");
			genre_Label.applyCss();
			
			year_Label.setId("labelSmall_Expanded");
			year_Label.applyCss();
			
			titleNumber_Label.setId("labelSmall_Expanded");
			titleNumber_Label.applyCss();
			
			totalTitleNumbers_Label.setId("labelSmall_Expanded");
			totalTitleNumbers_Label.applyCss();
			
			albumArtist_Label.setId("labelSmall_Expanded");
			albumArtist_Label.applyCss();
			
			composer_Label.setId("labelSmall_Expanded");
			composer_Label.applyCss();
			comment_Label.setId("labelSmall_Expanded");
			comment_Label.applyCss();
			discNumber_Label.setId("labelSmall_Expanded");
			discNumber_Label.applyCss();
			totalDiscNumbers_Label.setId("labelSmall_Expanded");
			totalDiscNumbers_Label.applyCss();
			
			tagInformation_Label.setId("labelLarge_Expanded");
			tagInformation_Label.getStyleClass().clear();
			
			artWork_Label.setId("labelLarge_Expanded");
			artWork_Label.applyCss();
			
			fileInformation_Label.setId("labelLarge_Expanded");
			fileInformation_Label.applyCss();
			
			filePath_Label.setId("labelLarge_Expanded");
			filePath_Label.applyCss();
			
			totalTitleNumbers_Label.setId("labelSmall_Expanded");
			totalTitleNumbers_Label.applyCss();
			
			totalDiscNumbers_Label.setId("labelSmall_Expanded");
			totalTitleNumbers_Label.applyCss();
		} else {
			for (FieldKey key : FieldKey.values()) {
				if (key == FieldKey.DISCNUMBER || key == FieldKey.DISCNUMBER_TOTAL
						|| key == FieldKey.TITLENUMBER || key == FieldKey.TITLENUMBER_TOTAL) {
					((Control)getField(key)).setId("textfield_Minimized_Small");
					((Control)getField(key)).applyCss();
				} else if (key != FieldKey.COMMENT || key != FieldKey.COMPOSER) {
					((Control)getField(key)).setId("textfield_Minimized_Middle");
					((Control)getField(key)).applyCss();
				} else {
					((Control)getField(key)).setId("");
					((Control)getField(key)).applyCss();
				}
				
				
			}
			
			composerTextField.setId("");
			commentComboBox.setId("");
			filePathTextField.setId("");
			filePathTextField.applyCss();
			
			titlename_Label.setId("labelSmall_Minimized");
			titlename_Label.applyCss();
			artist_Label.setId("labelSmall_Minimized");
			artist_Label.applyCss();
			
			album_Label.setId("labelSmall_Minimized");
			album_Label.applyCss();
			
			genre_Label.setId("labelSmall_Minimized");
			genre_Label.applyCss();
			
			year_Label.setId("labelSmall_Minimized");
			year_Label.applyCss();
			
			titleNumber_Label.setId("labelSmall_Minimized");
			titleNumber_Label.applyCss();
			
			totalTitleNumbers_Label.setId("labelSmall_Minimized");
			totalTitleNumbers_Label.applyCss();
			
			albumArtist_Label.setId("labelSmall_Minimized");
			albumArtist_Label.applyCss();
			
			composer_Label.setId("labelSmall_Minimized");
			composer_Label.applyCss();
			comment_Label.setId("labelSmall_Minimized");
			comment_Label.applyCss();
			discNumber_Label.setId("labelSmall_Minimized");
			discNumber_Label.applyCss();
			totalDiscNumbers_Label.setId("labelSmall_Minimized");
			totalDiscNumbers_Label.applyCss();
			
			tagInformation_Label.setId("labelLarge_Minimized");
			tagInformation_Label.getStyleClass().clear();
			
			artWork_Label.setId("labelLarge_Minimized");
			artWork_Label.applyCss();
			
			fileInformation_Label.setId("labelLarge_Minimized");
			fileInformation_Label.applyCss();
			
			filePath_Label.setId("labelLarge_Minimized");
			filePath_Label.applyCss();
			

			totalTitleNumbers_Label.setId("labelSmall_Minimized");
			totalTitleNumbers_Label.applyCss();
			
			artistComboBox.setId("comboBox_Middle");
			genreComboBox.setId("comboBox_Middle");
			albumComboBox.setId("comboBox_Middle");
			titlenameComboBox.setId("comboBox_Middle");
			commentComboBox.setId("comboBox_Big");
			
			totalDiscNumbers_Label.setId("labelSmall_Minimized");
			totalTitleNumbers_Label.applyCss();
		}
		
		
		
	}
	
}