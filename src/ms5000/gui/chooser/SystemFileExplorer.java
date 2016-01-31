package ms5000.gui.chooser;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import ms5000.gui.alert.ErrorAlert;

public class SystemFileExplorer {
	private String dirPath;
	
	public SystemFileExplorer(String dirPath) {
		this.dirPath = dirPath;
		
		show();
	}

	private void show() {
		try {
			if (System.getProperty("os.name").toLowerCase().contains("win")) {
				Runtime.getRuntime().exec("explorer.exe /select," + dirPath);
			} else {
				if (Desktop.isDesktopSupported()) {
				    Desktop.getDesktop().open(new File(dirPath));
				}
			}
			
		} catch (IOException e) {
			new ErrorAlert("Can't open File Explorer", "The File explorer coundln't be oppend","");
		}
	}
}
