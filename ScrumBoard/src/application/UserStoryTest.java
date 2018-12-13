package application;

import org.junit.jupiter.api.Test;

import application.UserStory;

public class UserStoryTest {
	
	// Instantiate data members required for testing
	String nameTest = "Chip";
	String nameChangeTest = "Chip's Brother";
	String destinationTest = "Chip's Destination";
	String stageTest = "Complete";
	String priorityLevelTest = "Critical";
	
	UserStory userStory = new UserStory(nameTest, destinationTest, stageTest, priorityLevelTest);
	
	/*
	 * Testing
	 */
	@Test
	void test() {
		// Assert UserStory is not null
		assert(userStory != null);
		reset();
		
		// Assert UserStory is initially unlocked
		assert(!userStory.isLocked());
		reset();
		
		// Assert editStory method works as desired
		assert(userStory.editStory());
		reset();
		
		// Assert custom constructor are correct
		assert(userStory.name.equals(nameTest) && userStory.des.equals(destinationTest) && userStory.stage.equals(stageTest)
				&& userStory.priority.equals(priorityLevelTest));
		reset();
		
		// Assert set method works as desired
		userStory.setName(nameChangeTest);
		assert(userStory.name.equals(nameChangeTest));
		reset();
	}
	
	/*
	 * Reset function for component
	 */
	private void reset() {
		userStory = new UserStory("Chip", "Chip's Destination", "Complete", "Critical");
	}
	
}
