package ms5000.gui.mainframe.center.eventhandler;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import ms5000.gui.mainframe.center.CenterGridPane;
import ms5000.musicfile.tag.Release;

/**
 * Fills out the fields according to the selected relesase
 */
public class CenterGridPane_AlbumCB_IndexChangeListener implements ChangeListener<Number> {
	/**
	 * The album combo box
	 */
	private ComboBox<Release> albumComboBox;
	
	/**
	 * The center gridpane
	 */
	private CenterGridPane pane;

	/**
	 * 
	 * @param albumComboBox the album combo box
	 * @param pane the center grid pane
	 */
	public CenterGridPane_AlbumCB_IndexChangeListener(ComboBox<Release> albumComboBox, CenterGridPane pane) {
		this.albumComboBox = albumComboBox;
		this.pane = pane;
	}

	@Override
	public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number newValue) {
		if (newValue.intValue() != -1) {
			Release selectedRelease = albumComboBox.getItems().get(newValue.intValue());
			pane.getAlbumArtistTextField().setText(selectedRelease.getAlbumArtist());
			pane.getYearTextField().setText(selectedRelease.getYear());
			pane.getTitleNumberTextField().setText(selectedRelease.getTrackNumber());
			pane.getTotalTitleNumbersTextField().setText(selectedRelease.getTrackCount());
			pane.setArtWorkImage(selectedRelease.getArtwork());
			pane.getComposerTextField().setText(selectedRelease.getComposer());
			pane.getDiscNumberTextField().setText(selectedRelease.getDiscNumber());
			pane.getTotalDiscNumbersTextField().setText(selectedRelease.getDiscCount());
		}
	}

}
