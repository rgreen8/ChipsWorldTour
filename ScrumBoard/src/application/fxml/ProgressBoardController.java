package application.fxml;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public class ProgressBoardController {
	
	@FXML
	private Label Task1;
	
	@FXML
	private Label Task2;
	
	@FXML
	private Label Task3;

	@FXML
	protected void Task1DragDetected(MouseEvent event) {
        /* drag was detected, start a drag-and-drop gesture*/
        /* allow any transfer mode */
        Dragboard db = Task1.startDragAndDrop(TransferMode.ANY);

        /* Put a string on a dragboard */
        ClipboardContent content = new ClipboardContent();
        content.putString(Task1.getText());
        db.setContent(content);

        event.consume();
	}
	
	@FXML
	protected void Task1DragOver(DragEvent event) {
        /* data is dragged over the target */
        /* accept it only if it is not dragged from the same node 
         * and if it has a string data */
        if (event.getGestureSource() != Task1 &&
                event.getDragboard().hasString()) {
            /* allow for moving */
            event.acceptTransferModes(TransferMode.MOVE);
        }

        event.consume();
    }
	
	@FXML
	protected void Task1DragDropped(DragEvent event) {
		 /* data dropped */
        /* if there is a string data on dragboard, read it and use it */
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasString()) {
           Task2.setText(db.getString());
           success = true;
        }
        /* let the source know whether the string was successfully 
         * transferred and used */
        event.setDropCompleted(success);

        event.consume();
    }
	
	@FXML
	protected void Task2DragDetected(MouseEvent event) {
        /* drag was detected, start a drag-and-drop gesture*/
        /* allow any transfer mode */
        Dragboard db = Task2.startDragAndDrop(TransferMode.ANY);

        /* Put a string on a dragboard */
        ClipboardContent content = new ClipboardContent();
        content.putString(Task2.getText());
        db.setContent(content);

        event.consume();
	}
	
	@FXML
	protected void Task2DragOver(DragEvent event) {
		// System.out.println("Task 2 Drag Over");
		/* data is dragged over the target */
		/* accept it only if it is not dragged from same node
		 * and if it has a string data */
        if (event.getGestureSource() != Task2 &&
                event.getDragboard().hasString()) {
            /* allow for moving */
            event.acceptTransferModes(TransferMode.MOVE);
            Dragboard db = event.getDragboard();
        }

        event.consume();
	}
	
	@FXML
	protected void Task2DragDropped(DragEvent event) {
		/* data dropped */
        /* if there is a string data on dragboard, read it and use it */
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasString()) {
           Task2.setText(db.getString());
           Task1.setText("");
           success = true;
        }
        /* let the source know whether the string was successfully 
         * transferred and used */
        event.setDropCompleted(success);

        event.consume();
	}
	
	@FXML
	protected void Task3DragOver(DragEvent event) {
		// System.out.println("Task 2 Drag Over");
		/* data is dragged over the target */
		/* accept it only if it is not dragged from same node
		 * and if it has a string data */
        if (event.getGestureSource() != Task3 &&
                event.getDragboard().hasString()) {
            /* allow for moving */
            event.acceptTransferModes(TransferMode.MOVE);
            Dragboard db = event.getDragboard();
        }

        event.consume();
	}
	
	@FXML
	protected void Task3DragDropped(DragEvent event) {
		/* data dropped */
        /* if there is a string data on dragboard, read it and use it */
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasString()) {
           Task3.setText(db.getString());
           Task2.setText("");
           success = true;
        }
        /* let the source know whether the string was successfully 
         * transferred and used */
        event.setDropCompleted(success);

        event.consume();
	}
	

}
