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

// help from https://github.com/JaretWright/GUIDemo/blob/master/src/guidemo/ExampleOfTableViewController.java

public class BurnDownController {
	
	 @FXML
	 private Button submitButton;
	 
	 @FXML
	 private AnchorPane rootPane;
	 
	 @FXML
	 protected void goToScrum(ActionEvent event) throws IOException {
		 Parent testparent = FXMLLoader.load(getClass().getResource("ScrumBoard.fxml"));
		 Scene testScene = new Scene(testparent);
		 
	        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
	        
	        window.setScene(testScene);
	        window.show();
	 }
	 
	 

}
