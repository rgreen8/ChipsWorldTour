package client;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;


import application.StoryBook;
import application.UserStory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ScrumController implements Initializable  {
	private ChatGateway gateway;
    private StoryBook stories;
    @FXML
    private TextField comment;
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	System.out.println("trying to make gateway");
		try {
			gateway = new ChatGateway(stories);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        System.out.println("gateway made");

        // Start the transcript check thread
        new Thread(new TranscriptCheck(gateway,stories)).start();
    } 
    
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
         //added story is now in the main controller 
         //put information into a story pane
         FXMLLoader loader2 = new  FXMLLoader(getClass().getResource("story.fxml"));
         Pane newStory = loader2.load();
         storyController storyControl = loader2.getController();
         storyControl.setName(newUser.name);
         storyControl.setDes(newUser.des);
         storyControl.setPriority(newUser.priority);
         // add pane to Hbox
         backLog.getChildren().add(newStory);
    }
  
	public void loadStorytoBackLog(ActionEvent event) throws IOException  {
	  Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("story.fxml"));
	  backLog.getChildren().add(newLoadedPane);
	}

}
