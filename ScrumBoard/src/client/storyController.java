package client;

import application.UserStory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class storyController {
	
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
	
	
}