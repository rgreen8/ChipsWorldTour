package application;

import java.util.ArrayList;

import java.io.Serializable;

public class StoryBook implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5961439281379779580L;
	/**
	 * 
	 */
	
	public ArrayList<UserStory> stories = new ArrayList<UserStory>();

	public StoryBook() {}
	
	public void storyAdd(String name, String stage, String priority, String des) {
		UserStory userStory = new UserStory(name, des, stage, priority);
		stories.add(userStory);
	}
	
	public void addStoryWhole(UserStory wholeNewStory) {
		stories.add(wholeNewStory);
	}
	
	public void storyDelete(UserStory userStory) {
		if(userStory == null || stories.contains(userStory) == false) {
			System.out.println("Story not Made, try again");
		} else {
			stories.remove(userStory);
		}
	}
	
}
