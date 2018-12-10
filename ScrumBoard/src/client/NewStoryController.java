package client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewStoryController implements Initializable{
	
	@FXML
	private ChoiceBox<String> choiceStage;
	@FXML
	private ChoiceBox<String> choicePriority;
	@FXML
	private TextField storyNameIn;
	@FXML
	private TextField storyDescriptionIn;
	@FXML
	public Button closeButton;
	
    @FXML
    private void closeAndSave(ActionEvent event) throws IOException {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        //get info, don't know what to do with it yet
        System.out.println(storyNameIn.getText());
        System.out.println(storyDescriptionIn.getText());
        System.out.println(choiceStage.getValue());
        System.out.println(choicePriority.getValue());
        stage.close();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ArrayList<String> stageOptions = new ArrayList<String>();
		stageOptions.add("To Do");stageOptions.add("In Progress");stageOptions.add("Complete");stageOptions.add("Backlog");
		ObservableList<String> stageList = FXCollections.observableArrayList(stageOptions);
		choiceStage.setItems(stageList);
		ArrayList<String> rankOptions = new ArrayList<String>();
		rankOptions.add("Low");rankOptions.add("Medium");rankOptions.add("High");rankOptions.add("Critical");
		ObservableList<String> rankList = FXCollections.observableArrayList(rankOptions);
		choicePriority.setItems(rankList);
	}
	

}

