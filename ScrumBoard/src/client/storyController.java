package client;

import java.util.HashMap;

import application.UserStory;
import client.StoryDataHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;

public class storyController {
	
	@FXML
	private Pane storyPane;
	@FXML
	private Label name;
	@FXML
	private Label description;
	@FXML
	private Label priority;
	
	public void addStory(UserStory userStory) {
		name.setText(userStory.name);
		description.setText(userStory.des);
		priority.setText(userStory.priority);
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
	
	/*
	public void openToEdit() {
		//put info into edit pane on Scrum Board, pre populate, allow user to edit
		
		//need a "update button" to hit if you want information
		
		//update must be reflected in sories array and on board
	}
	*/
	
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