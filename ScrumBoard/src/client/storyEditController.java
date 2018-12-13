package client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.UserStory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class storyEditController implements Initializable{
	
	@FXML
	private Label name;
	@FXML
	private TextField description;
	@FXML
	private Label priority;
	@FXML
	private ChoiceBox<String> choiceStage;
	@FXML
	private ChoiceBox<String> choicePriority;
	private UserStory updatedVersion;
	@FXML
	public Button saveButton;
	@FXML
	public Button deleteButton;
	private String stageSet;
	
	@FXML
	private void saveandUpdate(){
		Stage stage = (Stage)saveButton.getScene().getWindow();
		updatedVersion = new UserStory(name.getText(), description.getText(), stageSet, choicePriority.getValue());
        stage.close();
	}
	@FXML
	private void deleteStory(){
		
	}
	void initData(UserStory userStory) {
	    name.setText(userStory.name);
	    description.setText(userStory.des);
	    choicePriority.setValue(userStory.priority);
	    stageSet = userStory.stage;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Initialize drop down
		ArrayList<String> rankOptions = new ArrayList<String>();
		rankOptions.add("Low");rankOptions.add("Medium");rankOptions.add("High");rankOptions.add("Critical");
		ObservableList<String> rankList = FXCollections.observableArrayList(rankOptions);
		choicePriority.setItems(rankList);
	
	}
	public UserStory getUpdatedStory() {
		return updatedVersion;
	}
}