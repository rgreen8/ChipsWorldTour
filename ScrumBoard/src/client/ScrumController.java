package client;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ScrumController {
	@FXML
	public Button closeButton;
	
    @FXML
    public void toScrum(ActionEvent event) throws IOException {
		 Parent testparent = FXMLLoader.load(getClass().getResource("ScrumBoard.fxml"));
		 Scene testScene = new Scene(testparent);
		 
	        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
	        
	        window.setScene(testScene);
	        window.show();
    }
    @FXML
    private void toBurn(ActionEvent event) throws IOException {
		 Parent testparent = FXMLLoader.load(getClass().getResource("BurnDown.fxml"));
		 Scene testScene = new Scene(testparent);
		 
	        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
	        
	        window.setScene(testScene);
	        window.show();
    }
    @FXML
    private void createNew(ActionEvent event) throws IOException {
	     
         Parent root1 = FXMLLoader.load(getClass().getResource("newStory.fxml"));
         Stage stage = new Stage();
         stage.initModality(Modality.APPLICATION_MODAL);
         stage.initStyle(StageStyle.UNDECORATED);
         stage.setTitle("New Story");
         stage.setScene(new Scene(root1));  
         stage.show();
    }
    @FXML
    private void closeAndSave(ActionEvent event) throws IOException {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void toBacklog(ActionEvent event) throws IOException {
		 Parent testparent = FXMLLoader.load(getClass().getResource("BackLog.fxml"));
		 Scene testScene = new Scene(testparent);
		 
	        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
	        
	        window.setScene(testScene);
	        window.show();
    }

}
