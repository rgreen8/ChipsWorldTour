package application;

import java.util.ArrayList;

import java.io.Serializable;

public class StoryBook implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	public void storyDelete(UserStory US) {
		if(US == null || stories.contains(US) == false) {
			System.out.println("Story not Made, try again");
		} else {
			stories.remove(US);
		}
	}
}
