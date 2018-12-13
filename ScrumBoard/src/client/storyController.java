package client;

import java.io.IOException;
import java.util.ArrayList;

import application.UserStory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class storyController {
	
	@FXML
	private Label name;
	@FXML
	private Label description;
	@FXML
	private Label priority;
	@FXML
	private ChoiceBox<String> choiceStage;
	@FXML
	private ChoiceBox<String> choicePriority;
	
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
	@FXML
	private void openToEdit(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("storyEdit.fxml"));
   	 	Parent root = loader.load();
        Stage stage = new Stage ();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("New Story");
        stage.showAndWait();
        storyEditController storyEditController = loader.getController();
        UserStory newUser = storyEditController.getUpdatedStory();
        //added story is now in the main controller 

	}
	
}