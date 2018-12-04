package application.fxml;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


// help from https://www.callicoder.com/javafx-fxml-form-gui-tutorial/

public class ScrumBoardController {

    @FXML
    private Button backButton;
    

	 @FXML
	 private AnchorPane rootPane;
	 
	 @FXML
	 protected void goBacklog(ActionEvent event) throws IOException {
		 Parent scene2parent = FXMLLoader.load(getClass().getResource("Backlog.fxml"));
		 Scene scene2Scene = new Scene(scene2parent);
		 
	        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
	        
	        window.setScene(scene2Scene);
	        window.show();
		 }
	 @FXML
	 protected void goBurn(ActionEvent event) throws IOException {
		 Parent scene2parent = FXMLLoader.load(getClass().getResource("BurnDown.fxml"));
		 Scene scene2Scene = new Scene(scene2parent);
		 
	        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
	        
	        window.setScene(scene2Scene);
	        window.show();
		 }
	}
