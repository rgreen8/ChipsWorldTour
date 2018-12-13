package application;

import org.junit.jupiter.api.Test;

import application.StoryBook;
import application.UserStory;

public class StoryBookTest {

	// Instantiate data members required for testing
	StoryBook storyBook = new StoryBook();

	/*
	 * Testing
	 */
	@Test
	void test() {
		// Assert storyBook is not null
		assert(storyBook != null);
		reset();
		
		// Assert storyBook is initially empty
		assert(storyBook.stories.size() == 0);
		reset();
		
		// Assert storyAdd method works as desired
		storyBook.storyAdd("Chip", "Complete", "Critical", "Chip's Destination");
		assert(storyBook.stories.size() == 1);
		reset();
		
		// Assert addStoryWhole method works as desired
		UserStory userStory = new UserStory("Chip", "Chip's Destination", "Complete", "Critical");
		storyBook.addStoryWhole(userStory);
		assert(storyBook.stories.size() == 1);
		reset();
		
		// Assert storyDelete method works as desired
		UserStory userStoryDeleteTest = new UserStory("Chip", "Chip's Destination", "Complete", "Critical");
		storyBook.addStoryWhole(userStoryDeleteTest);
		assert(storyBook.stories.size() == 1);
		storyBook.storyDelete(userStoryDeleteTest);
		assert(storyBook.stories.size() == 0);
		reset();
	}
	
	/*
	 * Reset function for component
	 */
	private void reset() {
		storyBook = new StoryBook();
	}
	
}
