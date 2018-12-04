package chatclient;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class DisplayController {
	
	@FXML
	private void backToChat(ActionEvent event) throws IOException {
		Parent scene2parent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
		 Scene scene2Scene = new Scene(scene2parent);
		 
	        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
	        
	        window.setScene(scene2Scene);
	        window.show();
	}

}
