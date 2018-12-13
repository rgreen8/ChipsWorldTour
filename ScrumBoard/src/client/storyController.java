package client;

import java.io.IOException;
import java.util.HashMap;
import application.UserStory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Labeled;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public class storyController {
	@FXML
	private Pane storyPane;
	@FXML
	public Label name;
	@FXML
	public Label description;
	@FXML
	public Label priority;
	public String stage;
	
	@FXML
	private ChoiceBox<String> choicePriority;
	
	
	public void setStory(UserStory userStory) {
		name.setText(userStory.name);
		description.setText(userStory.des);
		priority.setText(userStory.priority);
		stage = userStory.stage;
	}
	
	public void setName(String string){
        name.setText(string);
    }
	public void setDes(String string){
		description.setText(string);
    }
	public void setPriority(String string){
        priority.setText(string);
    }
	@FXML
	private void openToEdit(ActionEvent event) throws IOException {
		UserStory oldState = new UserStory(name.getText(), description.getText(), "toDo", priority.getText());
		FXMLLoader loader = new FXMLLoader(getClass().getResource("storyEdit.fxml"));
   	 	Parent root = loader.load();
        Stage stage = new Stage ();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Edit Story");
        //send information to new window
        storyEditController storyEditController = loader.<storyEditController>getController();
        storyEditController.initData(oldState);
        stage.showAndWait();
        UserStory updatedUser = storyEditController.getUpdatedStory();
        //edit updated story information
        System.out.println(updatedUser.name);
        //update values in story controller
        setStory(updatedUser);
        //update values in Scrum Controller
        
	}
	@FXML
	protected void storyOnDragDetected(MouseEvent event) {
	    //We want the textArea to be dragged. Could also be copied.
	    Dragboard db = storyPane.startDragAndDrop(TransferMode.MOVE);
	    
	    // Retrieve data from storyPane
	    String tempName = ((Labeled) storyPane.getChildren().get(0)).getText();
	    String tempDescription = ((Labeled) storyPane.getChildren().get(1)).getText();
	    String tempPriorityLevel = ((Labeled) storyPane.getChildren().get(3)).getText();
	    
	    // Place string(s) data into JSON format
	    HashMap<String, String> storyData = new HashMap<String, String>();
	    storyData.put("name", tempName);
	    storyData.put("description", tempDescription);
	    storyData.put("priorityLevel", tempPriorityLevel);
	    storyData.put("originPane",  storyPane.getParent().getId());
	    
	    // Put a string on a drag-board as an identifier
	    ClipboardContent content = new ClipboardContent();
	    content.putString(storyData.toString());	    
	    db.setContent(content);
	    
	    //Consume the event
	    event.consume();
	}
	
	@FXML
	protected void storyOnDragDone(DragEvent event) {
		System.out.println("Drag done");
	}
}