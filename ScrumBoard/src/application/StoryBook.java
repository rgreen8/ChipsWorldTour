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

	public StoryBook() {
	}
	public void storyAdd(String name, String stage, String priority, String des) {
		UserStory US = new UserStory(name, des, stage, priority);
		stories.add(US);
	}
	public void addStoryWhole(UserStory newS) {
		stories.add(newS);
	}
	public void storyDelete(UserStory US) {
		if(US == null || stories.contains(US) == false) {
			System.out.println("Story not Made, try again");
		} else {
			stories.remove(US);
		}
	}
}
