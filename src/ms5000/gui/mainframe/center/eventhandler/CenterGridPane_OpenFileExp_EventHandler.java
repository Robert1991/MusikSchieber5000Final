package ms5000.gui.mainframe.center.eventhandler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ms5000.gui.chooser.SystemFileExplorer;
import ms5000.gui.mainframe.Main_Frame;
import ms5000.gui.profile.ProfileSettings;

public class CenterGridPane_OpenFileExp_EventHandler implements EventHandler<ActionEvent>{
	private ButtonOpenFileExplorerEventHandler type;
	private ProfileSettings settings;
	
	public CenterGridPane_OpenFileExp_EventHandler(ButtonOpenFileExplorerEventHandler type, ProfileSettings settings) {
		this.type = type;
		this.settings = settings;
	}
	
	@Override
	public void handle(ActionEvent arg0) {
		if (type == ButtonOpenFileExplorerEventHandler.CENTERGRID_MUSIC_FILE_PATH) {
			String dirPath = Main_Frame.getBorderPane_Center().getCenterGridPane().getFilePathTextField().getText();
			
			if (!dirPath.equals("")) {
				new SystemFileExplorer(dirPath);
			}
			
		} else if (type == ButtonOpenFileExplorerEventHandler.PROPERTIES_MUSIC_LIB){
			new SystemFileExplorer(settings.getMusicLibraryPath().getText());
		} else {
			new SystemFileExplorer(settings.getPlayListExportPath().getText());
		}
	}

}
