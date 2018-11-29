package application;
	
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

//Task Manager of Board
public class Main extends Application {
	ObservableList<Node> oblist = FXCollections.observableArrayList();
	AnchorPane myPane = new AnchorPane();
	private Scene scene;
	//initialize boards to be used throughout class
	BurnDownBoard burnDown = new BurnDownBoard();
	SprintBoard sprint = new SprintBoard();
	BacklogBoard backlog = new BacklogBoard();
	StoryBook storyBook = new StoryBook();
	
	@Override
	public void start(Stage stage) {
		try {
			scene = new Scene(myPane,1000,600);
			stage.setTitle("SCRUM Board");
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
			//Set observers
			storyBook.addObserver(backlog);
			storyBook.addObserver(sprint);
			storyBook.addObserver(burnDown);
			//set up initial JavaFX (Open to Main Page)
			setUpPage(1);
			System.out.println("setup complete");
			//run loop to look for input
			startTool();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
	
	private void startTool() {
		//look for input to switch pages and add information
		scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent mouseEvent) {	 
		        double x=mouseEvent.getX();
		        double y=mouseEvent.getY();
		        //check if switch box is clicked
		        if(x < 150 && x > 50 && y > 525 && y < 575) { //pull up backlog
		        	setUpPage(2);
		        }else if(x < 550 && x > 450 && y > 525 && y < 575) { //pull up sprint
		        	setUpPage(1);
		        }else if(x < 950 && x > 850 && y > 525 && y < 575) { //pull up burndown
		        	setUpPage(3);
		        }
		        //if not, check to see if other items are clicked based on page we are on
		    }
		});
	}

	public void setUpPage(int type) {
		//clear scene
		myPane.getChildren().clear();
		//add required elements to scene
		if(type == 1) { //pull up SprintBoard
			myPane.getChildren().addAll(sprint.getImages());
		} else if(type == 2) { //pull up BacklogBoard
			myPane.getChildren().addAll(backlog.getImages());	
		} else if(type == 3) { //pull up BurnDownBoard
			myPane.getChildren().addAll(burnDown.getImages());
		}
	}
}
