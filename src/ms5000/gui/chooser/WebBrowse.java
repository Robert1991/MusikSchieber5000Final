package ms5000.gui.chooser;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import ms5000.gui.alert.ErrorAlert;

public class WebBrowse {
	private String url;
	
	
	public WebBrowse(String url) {
		this.url = url;
		show();
	}


	private void show() {
		if (Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();
			try {
				desktop.browse(new URI(url));
			} catch (IOException | URISyntaxException e) {
				// TODO Auto-generated catch block
				new ErrorAlert("Can't open Web browser", "There was an error opening the web browser","Url: " + url);
			}
		} else {
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec("xdg-open " + url);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				new ErrorAlert("Can't open Web browser", "There was an error opening the web browser","Url: " + url);
			}
		}
	}
}
