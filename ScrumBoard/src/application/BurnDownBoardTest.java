package application;

import org.junit.jupiter.api.Test;

import application.BurnDownBoard;

public class BurnDownBoardTest {
	
	// Instantiate data members required for testing
	BurnDownBoard burnDownBoard = new BurnDownBoard();
	
	/*
	 * Testing
	 */
	@Test
	void test() {
		// Assert sprintBoard is not null
		assert(burnDownBoard != null);
		reset();
		
		// Assert oblist data member is instantiated
		assert(burnDownBoard.oblist != null);
		reset();
		
		// Assert getImages method works as expected
		assert(burnDownBoard.getImages() == burnDownBoard.oblist);
		reset();
	}
	
	/*
	 * Reset
	 */
	private void reset() {
		burnDownBoard = new BurnDownBoard();
	}
	
}
