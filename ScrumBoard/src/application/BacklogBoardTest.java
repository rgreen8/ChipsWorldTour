package application;

import org.junit.jupiter.api.Test;

public class BacklogBoardTest {
	
	// Instantiate data members required for testing
	BacklogBoard backlogBoard = new BacklogBoard();
	
	/*
	 * Testing
	 */
	@Test
	void test() {
		// Assert sprintBoard is not null
		assert(backlogBoard != null);
		reset();
		
		// Assert oblist data member is instantiated
		assert(backlogBoard.oblist != null);
		reset();
		
		// Assert getImages method works as expected
		assert(backlogBoard.getImages() == backlogBoard.oblist);
		reset();
	}
	
	/*
	 * Reset
	 */
	private void reset() {
		backlogBoard = new BacklogBoard();
	}
	
}
