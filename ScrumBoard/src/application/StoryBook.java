package application;

import java.util.Observable;

public class StoryBook extends Observable {

	public StoryBook() {
		//set up initial
	}
	public void updateOthers(){
		setChanged();		// The observable object has moved.  To include recent changes you *MUST* have this line
		notifyObservers();
	}
}
