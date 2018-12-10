package application;

import java.util.ArrayList;
import java.util.Observable;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.Serializable;

public class StoryBook implements Serializable  {
	ArrayList<UserStory> stories = new ArrayList<UserStory>();

	public StoryBook() {
		//set up initial
	}
	public void storyAdd(String name, int stage, int priority, String des) {
		UserStory US = new UserStory();
		US.des = des;
		US.name = name;
		US.priority = priority;
		US.stage = stage;
		stories.add(US);
	}
}
