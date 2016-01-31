package ms5000.gui.mainframe.center.eventhandler;

import java.net.MalformedURLException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ms5000.gui.chooser.WebBrowse;
import ms5000.gui.mainframe.Main_Frame;

public class CenterGridPane_OpenWebBrowse_EventHandler implements EventHandler<ActionEvent>{
	@Override
	public void handle(ActionEvent arg0) {
		String stringUrl = Main_Frame.getBorderPane_Center().getCenterGridPane().getCommentComboBox().getEditor().getText();
		boolean isUrl = true;
		try {
			URL url = new URL(stringUrl);
			stringUrl = url.toString();
		} catch (MalformedURLException e) {
			isUrl = false;
		}
		
		if (isUrl) {
			new WebBrowse(stringUrl);
		}
	}

}
