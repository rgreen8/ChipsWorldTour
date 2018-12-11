package client;

import java.io.IOException;

import application.UserStory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ScrumController {
	
	@FXML
	private HBox backLog;
	@FXML
	private VBox toDo;
	@FXML
	private VBox inProgress;
	@FXML
	private VBox complete;
	@FXML
	private Pane editPane;
	
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
    	 FXMLLoader loader = new FXMLLoader(getClass().getResource("newStory.fxml"));
    	 Parent root = loader.load();
         Stage stage = new Stage ();
         stage.setScene(new Scene(root));
         stage.initModality(Modality.APPLICATION_MODAL);
         stage.initStyle(StageStyle.UNDECORATED);
         stage.setTitle("New Story");
         stage.showAndWait();

         NewStoryController newStoryController = loader.getController();
         UserStory newUser = newStoryController.getNewStory();
         System.out.println(newUser.name);
    }
  
	public void loadStorytoBackLog(ActionEvent event) throws IOException  {
	  Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("story.fxml"));
	  backLog.getChildren().add(newLoadedPane);
	}

}
