package application.fxml;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class ProgressBoardController {
	
	@FXML
	private Button Task1;
	
	@FXML
	protected void Task1DragDetected(ActionEvent event) {
		this.Task1.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				System.out.println("Task 1 Button Dragged");
			}
		});
	}

}
