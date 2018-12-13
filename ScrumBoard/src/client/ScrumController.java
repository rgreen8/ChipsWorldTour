package client;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;


import application.StoryBook;
import application.UserStory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ScrumController implements Initializable  {
    public StoryBook stories = new StoryBook();;
    public ChatGateway gateway;
   public  boolean changemade = false;
    @FXML
    private TextField comment;
    @FXML
	private HBox backLog;
	@FXML
	private VBox toDo;
	@FXML
	private VBox inProgress;
	@FXML
	private VBox complete;
	@FXML
	private Pane editPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	System.out.println("trying to make gateway");
		try {
			gateway = new ChatGateway(stories);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        System.out.println("gateway made");
        stories = gateway.getStories();
        try {
			update(stories);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // Start the transcript check thread
        new Thread(new TranscriptCheck(gateway,stories,changemade)).start();
        System.out.println("New number of stories  at scrum Control is: " + stories.stories.size());
    } 
    
    private void update(StoryBook stories) throws IOException {
    
    	for(int i = 0; i < stories.stories.size(); i++) {
	    	FXMLLoader loader2 = new  FXMLLoader(getClass().getResource("story.fxml"));
	        Pane newStory = loader2.load();
	        storyController storyControl = loader2.getController();
	        storyControl.setName(stories.stories.get(i).name);
	        storyControl.setDes(stories.stories.get(i).des);
	        storyControl.setPriority(stories.stories.get(i).priority);
	        // add pane to Hbox
	        backLog.getChildren().add(newStory);
	    }
	}
    
    @FXML
    public void toScrum(ActionEvent event) throws IOException {
		 Parent testparent = FXMLLoader.load(getClass().getResource("ScrumBoard.fxml"));
		 Scene testScene = new Scene(testparent);
		 
	     Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
	        
        window.setScene(testScene);
        window.show();
    }      
    @FXML
    private void toBurn(ActionEvent event) throws IOException {
		 Parent testparent = FXMLLoader.load(getClass().getResource("BurnDown.fxml"));
		 Scene testScene = new Scene(testparent);
	     Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
	     window.setScene(testScene);
	     window.show();
    }
    @FXML
    private void createNew(ActionEvent event) throws IOException {
    	changemade = true;
    	System.out.println(changemade + " inside call");
    	 FXMLLoader loader = new FXMLLoader(getClass().getResource("newStory.fxml"));
    	 Parent root = loader.load();
         Stage stage = new Stage ();
         stage.setScene(new Scene(root));
         stage.initModality(Modality.APPLICATION_MODAL);
         stage.initStyle(StageStyle.UNDECORATED);
         stage.setTitle("New Story");
         stage.showAndWait();
         NewStoryController newStoryController = loader.getController();
         UserStory newUser = newStoryController.getNewStory();
         //added story is now in the main controller 
         //put information into a story pane
         FXMLLoader loader2 = new  FXMLLoader(getClass().getResource("story.fxml"));
         Pane newStory = loader2.load();
         storyController storyControl = loader2.getController();
         storyControl.addStory(newUser);
         
         // Figure Out What Pane to Add to
         System.out.println(newUser.stage);
         switch(newUser.stage) {
         	case "To Do":
         		toDo.getChildren().add(newStory);
         		break;
         	case "In Progress":
         		inProgress.getChildren().add(newStory);
         		break;
         	case "Complete":
         		complete.getChildren().add(newStory);
         		break;
         	default: //backlog
         		backLog.getChildren().add(newStory);
         		break;
         }
         // add to stories
         this.gateway.addStoryToSever(newUser);
         System.out.println("new user added in scrum controller");
        
    }
  
	public void loadStorytoBackLog(ActionEvent event) throws IOException  {
	  Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("story.fxml"));
	  backLog.getChildren().add(newLoadedPane);
	}
	
	@FXML
	protected void toDoOnDragOver(DragEvent event) {
		 if (event.getGestureSource() != toDo && event.getDragboard().hasString()) {
		        event.acceptTransferModes(TransferMode.ANY);
		 }
		 event.consume();
	}
	
	@FXML
	protected void toDoOnDragDropped(DragEvent event) {
		System.out.println("Drag dropped");
	    //Get the drag-board back
	    Dragboard db = event.getDragboard();
	    boolean success = false;
	    if (db.hasString()) {
	    	String storyDataString = db.getString();
	    	Properties props = new Properties();
	    	try {
				props.load(new StringReader(storyDataString.substring(1, storyDataString.length() - 1).replace(", ", "\n")));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}       
	    	Map<String, String> map2 = new HashMap<String, String>();
	    	for (Map.Entry<Object, Object> e : props.entrySet()) {
	    	    map2.put((String)e.getKey(), (String)e.getValue());
	    	}
	    	
	    	// Place onto toDo Pane
	        FXMLLoader loader2Temp = new FXMLLoader(getClass().getResource("story.fxml"));
	        Pane newStoryTemp = null;
	        try {
				newStoryTemp = loader2Temp.load();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        storyController storyControl = loader2Temp.getController();
	        
	        UserStory newUserTemp = new UserStory(map2.get("name"), map2.get("description"), "toDo", map2.get("priorityLevel"));

	        storyControl.addStory(newUserTemp);
	        
	        toDo.getChildren().add(newStoryTemp);
	        this.stories.addStoryWhole(newUserTemp);
	        try {
				this.gateway.updateStories(stories);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	
	    	// Mark success
	        success = true;
	    }
	    // Complete and consume the event.
	    event.setDropCompleted(success);
	    event.consume();
	    
	    // Remove child from original pane
	    //toDo.getChildren().
	}

	@FXML
	protected void inProgressDragOver(DragEvent event) {
		 if (event.getGestureSource() != inProgress && event.getDragboard().hasString()) {
		        event.acceptTransferModes(TransferMode.ANY);
		 }
		 event.consume();
	}
	
	@FXML
	protected void inProgressDragDropped(DragEvent event) {
		System.out.println("Drag dropped");
	    //Get the drag-board back
	    Dragboard db = event.getDragboard();
	    boolean success = false;
	    if (db.hasString()) {
	    	String storyDataString = db.getString();
	    	Properties props = new Properties();
	    	try {
				props.load(new StringReader(storyDataString.substring(1, storyDataString.length() - 1).replace(", ", "\n")));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}       
	    	Map<String, String> map2 = new HashMap<String, String>();
	    	for (Map.Entry<Object, Object> e : props.entrySet()) {
	    	    map2.put((String)e.getKey(), (String)e.getValue());
	    	}
	    	
	    	// Place onto toDo Pane
	        FXMLLoader loader2Temp = new FXMLLoader(getClass().getResource("story.fxml"));
	        Pane newStoryTemp = null;
	        try {
				newStoryTemp = loader2Temp.load();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        storyController storyControl = loader2Temp.getController();
	        
	        UserStory newUserTemp = new UserStory(map2.get("name"), map2.get("description"), "inProgress", map2.get("priorityLevel"));

	        storyControl.addStory(newUserTemp);
	        
	        inProgress.getChildren().add(newStoryTemp);
	        this.stories.addStoryWhole(newUserTemp);
	        try {
				this.gateway.updateStories(stories);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	
	    	// Mark success
	        success = true;
	    }
	    // Complete and consume the event.
	    event.setDropCompleted(success);
	    event.consume();
	    
	    // Remove child from original pane
	    //toDo.getChildren().
	}
	
	@FXML
	protected void completedDragOver(DragEvent event) {
		 if (event.getGestureSource() != complete && event.getDragboard().hasString()) {
		        event.acceptTransferModes(TransferMode.ANY);
		 }
		 event.consume();
	}
	
	@FXML
	protected void completedDragDropped(DragEvent event) {
		System.out.println("Drag dropped");
	    //Get the drag-board back
	    Dragboard db = event.getDragboard();
	    boolean success = false;
	    if (db.hasString()) {
	    	String storyDataString = db.getString();
	    	Properties props = new Properties();
	    	try {
				props.load(new StringReader(storyDataString.substring(1, storyDataString.length() - 1).replace(", ", "\n")));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}       
	    	Map<String, String> map2 = new HashMap<String, String>();
	    	for (Map.Entry<Object, Object> e : props.entrySet()) {
	    	    map2.put((String)e.getKey(), (String)e.getValue());
	    	}
	    	
	    	// Place onto toDo Pane
	        FXMLLoader loader2Temp = new FXMLLoader(getClass().getResource("story.fxml"));
	        Pane newStoryTemp = null;
	        try {
				newStoryTemp = loader2Temp.load();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        storyController storyControl = loader2Temp.getController();
	        
	        UserStory newUserTemp = new UserStory(map2.get("name"), map2.get("description"), "complete", map2.get("priorityLevel"));

	        storyControl.addStory(newUserTemp);
	        
	        complete.getChildren().add(newStoryTemp);
	        this.stories.addStoryWhole(newUserTemp);
	        try {
				this.gateway.updateStories(stories);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	
	    	// Mark success
	        success = true;
	    }
	    // Complete and consume the event.
	    event.setDropCompleted(success);
	    event.consume();
	    
	    // Remove child from original pane
	    //toDo.getChildren().
	}
	
	@FXML
	protected void backLogDragOver(DragEvent event) {
		 if (event.getGestureSource() != backLog && event.getDragboard().hasString()) {
		        event.acceptTransferModes(TransferMode.ANY);
		 }
		 event.consume();
	}
	
	@FXML
	protected void backLogDragDropped(DragEvent event) {
		System.out.println("Drag dropped");
	    //Get the drag-board back
	    Dragboard db = event.getDragboard();
	    boolean success = false;
	    if (db.hasString()) {
	    	String storyDataString = db.getString();
	    	Properties props = new Properties();
	    	try {
				props.load(new StringReader(storyDataString.substring(1, storyDataString.length() - 1).replace(", ", "\n")));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}       
	    	Map<String, String> map2 = new HashMap<String, String>();
	    	for (Map.Entry<Object, Object> e : props.entrySet()) {
	    	    map2.put((String)e.getKey(), (String)e.getValue());
	    	}
	    	
	    	// Place onto toDo Pane
	        FXMLLoader loader2Temp = new FXMLLoader(getClass().getResource("story.fxml"));
	        Pane newStoryTemp = null;
	        try {
				newStoryTemp = loader2Temp.load();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        storyController storyControl = loader2Temp.getController();
	        
	        UserStory newUserTemp = new UserStory(map2.get("name"), map2.get("description"), "backLog", map2.get("priorityLevel"));

	        storyControl.addStory(newUserTemp);
	        
	        backLog.getChildren().add(newStoryTemp);
	        this.stories.addStoryWhole(newUserTemp);
	        try {
				this.gateway.updateStories(stories);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	
	    	// Mark success
	        success = true;
	    }
	    // Complete and consume the event.
	    event.setDropCompleted(success);
	    event.consume();
	    
	    // Remove child from original pane
	    //toDo.getChildren().
	}
	
}
