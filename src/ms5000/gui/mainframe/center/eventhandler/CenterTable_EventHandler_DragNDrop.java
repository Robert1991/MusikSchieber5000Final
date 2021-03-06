package ms5000.gui.mainframe.center.eventhandler;

import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import ms5000.tasks.readdir.ImportMode;
import ms5000.tasks.readdir.ReadDirTaskManager;

public class CenterTable_EventHandler_DragNDrop implements EventHandler<DragEvent>{
	
	/**
	 * Method to handle the drag events received from the center table
	 */
	@Override
	public void handle(DragEvent event) {
		// Preparing the Drag
		if (event.getEventType() == DragEvent.DRAG_OVER) {
			Dragboard db = event.getDragboard();
			
			if (db.hasFiles()) {
				event.acceptTransferModes(TransferMode.MOVE);
			} 

			event.consume();
		// Launching the ImportFilesTask with the files from the drag	
		} else if (event.getEventType() == DragEvent.DRAG_DROPPED) {
			Dragboard db = event.getDragboard();
			boolean success = false;

			if (db.hasFiles()) {
				success = true;
				if (db.hasFiles()) {
					// Starting the task to import
					ReadDirTaskManager.startTask(db.getFiles(), ImportMode.DRAGNDROP);
				}

			}
			
			event.setDropCompleted(success);
			event.consume();
		}
	}
}
