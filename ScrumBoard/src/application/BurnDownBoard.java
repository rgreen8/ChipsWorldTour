package application;

import java.util.Observable;
import java.util.Observer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class BurnDownBoard implements Observer{
	ObservableList<Node> oblist = FXCollections.observableArrayList();
	
	public BurnDownBoard() {
		//set up 
	}
	
	public ObservableList<Node> getImages() {
		//create buttons to navigate
		System.out.println("creating boxes");
		Rectangle sprintButton = new Rectangle(50, 525, 100, 50);
		sprintButton.setFill(Color.BLUE);
		Rectangle backlogButton = new Rectangle(450, 525, 100, 50);
		backlogButton.setFill(Color.BLUE);
		Rectangle burndownButton = new Rectangle(850, 525, 100, 50);
		burndownButton.setFill(Color.BLUE);
		Text sprintLabel = new Text(482, 550, "Sprint");
		sprintLabel.setFill(Color.WHITE);
		Text backlogLabel = new Text(76, 550, "Backlog");
		backlogLabel.setFill(Color.WHITE);
		Text burndownLabel = new Text(870, 550, "Burndown");
		burndownLabel.setFill(Color.WHITE);
		oblist.addAll(sprintButton, backlogButton, burndownButton, sprintLabel, backlogLabel, burndownLabel);
		return oblist;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}


}
