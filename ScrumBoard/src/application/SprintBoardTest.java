package application;

import org.junit.jupiter.api.Test;

import application.SprintBoard;

public class SprintBoardTest {
	
	// Instantiate data members required for testing
	SprintBoard sprintBoard = new SprintBoard();
	
	/*
	 * Testing
	 */
	@Test
	void test() {
		// Assert sprintBoard is not null
		assert(sprintBoard != null);
		reset();
		
		// Assert oblist data member is instantiated
		assert(sprintBoard.oblist != null);
		reset();
		
		// Assert getImages method works as expected
		assert(sprintBoard.getImages() == sprintBoard.oblist);
		reset();
	}
	
	/*
	 * Reset
	 */
	private void reset() {
		sprintBoard = new SprintBoard();
	}
	
}
