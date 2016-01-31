package ms5000.gui.mainframe.center.eventhandler;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import ms5000.musicfile.tag.Release;

/**
 * Paints the combobox entries
 */
public class CenterGridPane_AlbumCB_CellFactory implements Callback<ListView<Release>, ListCell<Release>> {

	@Override
	public ListCell<Release> call(ListView<Release> arg0) {
		final ListCell<Release> cell = new ListCell<Release>() {
			{
				super.setPrefWidth(100);
			}

			@Override
			public void updateItem(Release item, boolean empty) {
				super.updateItem(item, empty);
				if (item != null) {
					setText(item.getTitle());
					if (item.isUserRelease()) {
						setTextFill(Color.GREEN);
					} else if (item.isMainRelease()) {
						setTextFill(Color.CORNFLOWERBLUE);
					} else {
						setTextFill(Color.BLACK);
					}
				} else {
					setText(null);
				}
			}
		};

		return cell;
	}
}
