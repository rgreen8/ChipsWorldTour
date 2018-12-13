package client;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.io.StringReader;

public class ScrumController implements Initializable  {
    public StoryBook stories = new StoryBook();
    public ChatGateway gateway;
    public ArrayList<storyController> controlList = new ArrayList<storyController>();
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
	@FXML
	private Pane trashPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	System.out.println("trying to make gateway");
		try {
			gateway = new ChatGateway();
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
        try {
			stories = gateway.getStories();
		} catch (ClassNotFoundException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
    
    @FXML
	private void refreshStories(ActionEvent event) throws IOException, ClassNotFoundException {
    	this.gateway.updateStories();
    	System.out.println("New number of stories  at refresh is: " + this.gateway.stories.stories.size());
    	this.gateway.grabStories();
    	System.out.println("New number of stories  at refresh is: " + this.gateway.stories.stories.size());
		update(this.gateway.stories);
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
	        switch(stories.stories.get(i).stage) {
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
         }
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
         storyControl.setStory(newUser);
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
         }
         // add to stories
         this.gateway.addStoryToSever(newUser);
         this.controlList.add(storyControl);
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
	    	
	    	switch (map2.get("originPane")) {
	    		case "backLog":
	    			int matchingDescriptionFromBackLogIterator = 0;
	    			for (Node node : this.backLog.getChildren()) {
	    				Pane temporaryPane = (Pane) node;
	    				String matchingDescription = ((Labeled) temporaryPane.getChildren().get(0)).getText();
	    				if (map2.get("name").equals(matchingDescription)) {
	    					this.backLog.getChildren().remove(matchingDescriptionFromBackLogIterator);
	    					// Iterate through stories data element and delete story
	    					int storyBoardChangeIterator = 0;
	    					for (UserStory tempUserStory : this.stories.stories) {
	    						if (tempUserStory.name.equals(map2.get("name"))) {
	    							this.stories.stories.get(storyBoardChangeIterator).stage = "To Do";
	    							try {
										this.gateway.addStoriesToServer(this.stories);
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
	    						}
	    						storyBoardChangeIterator++;
	    					}
	    					break;
	    				}
	    				matchingDescriptionFromBackLogIterator++;
	    			}
	    			break;
	    		case "complete":
	    			int matchingDescriptionFromCompleteIterator = 0;
	    			for (Node node : this.complete.getChildren()) {
	    				Pane temporaryPane = (Pane) node;
	    				String matchingDescription = ((Labeled) temporaryPane.getChildren().get(0)).getText();
	    				if (map2.get("name").equals(matchingDescription)) {
	    					this.complete.getChildren().remove(matchingDescriptionFromCompleteIterator);
	    					// Iterate through stories data element and delete story
	    					int storyBoardChangeIterator = 0;
	    					for (UserStory tempUserStory : this.stories.stories) {
	    						if (tempUserStory.name.equals(map2.get("name"))) {
	    							this.stories.stories.get(storyBoardChangeIterator).stage = "To Do";
	    							try {
										this.gateway.addStoriesToServer(this.stories);
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
	    						}
	    						storyBoardChangeIterator++;
	    					}
	    					break;
	    				}
	    				matchingDescriptionFromCompleteIterator++;
	    			}
	    			break;
	    		case "inProgress":
	    			int matchingDescriptionFromInProgressIterator = 0;
	    			for (Node node : this.inProgress.getChildren()) {
	    				Pane temporaryPane = (Pane) node;
	    				String matchingDescription = ((Labeled) temporaryPane.getChildren().get(0)).getText();
	    				if (map2.get("name").equals(matchingDescription)) {
	    					this.inProgress.getChildren().remove(matchingDescriptionFromInProgressIterator);
	    					// Iterate through stories data element and delete story
	    					int storyBoardChangeIterator = 0;
	    					for (UserStory tempUserStory : this.stories.stories) {
	    						if (tempUserStory.name.equals(map2.get("name"))) {
	    							this.stories.stories.get(storyBoardChangeIterator).stage = "To Do";
	    							try {
										this.gateway.addStoriesToServer(this.stories);
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
	    						}
	    						storyBoardChangeIterator++;
	    					}
	    					break;
	    				}
	    				matchingDescriptionFromInProgressIterator++;
	    			}
	    			break;
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
 
 	        storyControl.setStory(newUserTemp);
 	        
 	        toDo.getChildren().add(newStoryTemp);
 	        this.stories.addStoryWhole(newUserTemp);
 	        try {
 				this.gateway.updateStories();
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
	    	switch (map2.get("originPane")) {
	    		case "backLog":
	    			int matchingDescriptionFromBackLogIterator = 0;
	    			for (Node node : this.backLog.getChildren()) {
	    				Pane temporaryPane = (Pane) node;
	    				String matchingDescription = ((Labeled) temporaryPane.getChildren().get(0)).getText();
	    				if (map2.get("name").equals(matchingDescription)) {
	    					this.backLog.getChildren().remove(matchingDescriptionFromBackLogIterator);
	    					// Iterate through stories data element and delete story
	    					int storyBoardChangeIterator = 0;
	    					for (UserStory tempUserStory : this.stories.stories) {
	    						if (tempUserStory.name.equals(map2.get("name"))) {
	    							this.stories.stories.get(storyBoardChangeIterator).stage = "In Progress";
	    							try {
										this.gateway.addStoriesToServer(this.stories);
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
	    						}
	    						storyBoardChangeIterator++;
	    					}
	    					break;
	    				}
	    				matchingDescriptionFromBackLogIterator++;
	    			}
	    			break;
	    		case "toDo":
	    			int matchingDescriptionFromToDoIterator = 0;
	    			for (Node node : this.toDo.getChildren()) {
	    				Pane temporaryPane = (Pane) node;
	    				String matchingDescription = ((Labeled) temporaryPane.getChildren().get(0)).getText();
	    				if (map2.get("name").equals(matchingDescription)) {
	    					this.toDo.getChildren().remove(matchingDescriptionFromToDoIterator);
	    					// Iterate through stories data element and delete story
	    					int storyBoardChangeIterator = 0;
	    					for (UserStory tempUserStory : this.stories.stories) {
	    						if (tempUserStory.name.equals(map2.get("name"))) {
	    							this.stories.stories.get(storyBoardChangeIterator).stage = "In Progress";
	    							try {
										this.gateway.addStoriesToServer(this.stories);
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
	    						}
	    						storyBoardChangeIterator++;
	    					}
	    					break;
	    				}
	    				matchingDescriptionFromToDoIterator++;
	    			}
	    			break;
	    		case "complete":
	    			int matchingDescriptionFromCompleteIterator = 0;
	    			for (Node node : this.complete.getChildren()) {
	    				Pane temporaryPane = (Pane) node;
	    				String matchingDescription = ((Labeled) temporaryPane.getChildren().get(0)).getText();
	    				if (map2.get("name").equals(matchingDescription)) {
	    					this.complete.getChildren().remove(matchingDescriptionFromCompleteIterator);
	    					// Iterate through stories data element and delete story
	    					int storyBoardChangeIterator = 0;
	    					for (UserStory tempUserStory : this.stories.stories) {
	    						if (tempUserStory.name.equals(map2.get("name"))) {
	    							this.stories.stories.get(storyBoardChangeIterator).stage = "In Progress";
	    							try {
										this.gateway.addStoriesToServer(this.stories);
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
	    						}
	    						storyBoardChangeIterator++;
	    					}
	    					break;
	    				}
	    				matchingDescriptionFromCompleteIterator++;
	    			}
	    			break;
	    	}
	    	
	    	// Place onto inProgress Pane
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
 
 	        storyControl.setStory(newUserTemp);
 	        
 	        inProgress.getChildren().add(newStoryTemp);
 	        this.stories.addStoryWhole(newUserTemp);
 	        try {
 				this.gateway.updateStories();
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
	    	switch (map2.get("originPane")) {
	    		case "backLog":
	    			int matchingDescriptionFromBackLogIterator = 0;
	    			for (Node node : this.backLog.getChildren()) {
	    				Pane temporaryPane = (Pane) node;
	    				String matchingDescription = ((Labeled) temporaryPane.getChildren().get(0)).getText();
	    				if (map2.get("name").equals(matchingDescription)) {
	    					this.backLog.getChildren().remove(matchingDescriptionFromBackLogIterator);
	    					// Iterate through stories data element and delete story
	    					int storyBoardChangeIterator = 0;
	    					for (UserStory tempUserStory : this.stories.stories) {
	    						if (tempUserStory.name.equals(map2.get("name"))) {
	    							this.stories.stories.get(storyBoardChangeIterator).stage = "Complete";
	    							try {
										this.gateway.addStoriesToServer(this.stories);
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
	    						}
	    						storyBoardChangeIterator++;
	    					}
	    					break;
	    				}
	    				matchingDescriptionFromBackLogIterator++;
	    			}
	    			break;
	    		case "toDo":
	    			int matchingDescriptionFromToDoIterator = 0;
	    			for (Node node : this.toDo.getChildren()) {
	    				Pane temporaryPane = (Pane) node;
	    				String matchingDescription = ((Labeled) temporaryPane.getChildren().get(0)).getText();
	    				if (map2.get("name").equals(matchingDescription)) {
	    					this.toDo.getChildren().remove(matchingDescriptionFromToDoIterator);
	    					// Iterate through stories data element and delete story
	    					int storyBoardChangeIterator = 0;
	    					for (UserStory tempUserStory : this.stories.stories) {
	    						if (tempUserStory.name.equals(map2.get("name"))) {
	    							this.stories.stories.get(storyBoardChangeIterator).stage = "Complete";
	    							try {
										this.gateway.addStoriesToServer(this.stories);
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
	    						}
	    						storyBoardChangeIterator++;
	    					}
	    					break;
	    				}
	    				matchingDescriptionFromToDoIterator++;
	    			}
	    			break;
	    		case "inProgress":
	    			int matchingDescriptionFromInProgressIterator = 0;
	    			for (Node node : this.inProgress.getChildren()) {
	    				Pane temporaryPane = (Pane) node;
	    				String matchingDescription = ((Labeled) temporaryPane.getChildren().get(0)).getText();
	    				if (map2.get("name").equals(matchingDescription)) {
	    					this.inProgress.getChildren().remove(matchingDescriptionFromInProgressIterator);
	    					// Iterate through stories data element and delete story
	    					int storyBoardChangeIterator = 0;
	    					for (UserStory tempUserStory : this.stories.stories) {
	    						if (tempUserStory.name.equals(map2.get("name"))) {
	    							this.stories.stories.get(storyBoardChangeIterator).stage = "Complete";
	    							try {
										this.gateway.addStoriesToServer(this.stories);
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
	    						}
	    						storyBoardChangeIterator++;
	    					}
	    					break;
	    				}
	    				matchingDescriptionFromInProgressIterator++;
	    			}
	    			break;
	    	}
	    	
	    	// Place onto complete Pane
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
 
 	        storyControl.setStory(newUserTemp);
 	        
 	        complete.getChildren().add(newStoryTemp);
 	        this.stories.addStoryWhole(newUserTemp);
 	        try {
 				this.gateway.updateStories();
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
	    	switch (map2.get("originPane")) {
	    		case "complete":
	    			int matchingDescriptionFromCompleteIterator = 0;
	    			for (Node node : this.complete.getChildren()) {
	    				Pane temporaryPane = (Pane) node;
	    				String matchingDescription = ((Labeled) temporaryPane.getChildren().get(0)).getText();
	    				if (map2.get("name").equals(matchingDescription)) {
	    					this.complete.getChildren().remove(matchingDescriptionFromCompleteIterator);
	    					// Iterate through stories data element and delete story
	    					int storyBoardChangeIterator = 0;
	    					for (UserStory tempUserStory : this.stories.stories) {
	    						if (tempUserStory.name.equals(map2.get("name"))) {
	    							this.stories.stories.get(storyBoardChangeIterator).stage = "backLog";
	    							try {
										this.gateway.addStoriesToServer(this.stories);
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
	    						}
	    						storyBoardChangeIterator++;
	    					}
	    					break;
	    				}
	    				matchingDescriptionFromCompleteIterator++;
	    			}
	    			break;
	    		case "toDo":
	    			int matchingDescriptionFromToDoIterator = 0;
	    			for (Node node : this.toDo.getChildren()) {
	    				Pane temporaryPane = (Pane) node;
	    				String matchingDescription = ((Labeled) temporaryPane.getChildren().get(0)).getText();
	    				if (map2.get("name").equals(matchingDescription)) {
	    					this.toDo.getChildren().remove(matchingDescriptionFromToDoIterator);
	    					// Iterate through stories data element and delete story
	    					int storyBoardChangeIterator = 0;
	    					for (UserStory tempUserStory : this.stories.stories) {
	    						if (tempUserStory.name.equals(map2.get("name"))) {
	    							this.stories.stories.get(storyBoardChangeIterator).stage = "backLog";
	    							try {
										this.gateway.addStoriesToServer(this.stories);
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
	    						}
	    						storyBoardChangeIterator++;
	    					}
	    					break;
	    				}
	    				matchingDescriptionFromToDoIterator++;
	    			}
	    			break;
	    		case "inProgress":
	    			int matchingDescriptionFromInProgressIterator = 0;
	    			for (Node node : this.inProgress.getChildren()) {
	    				Pane temporaryPane = (Pane) node;
	    				String matchingDescription = ((Labeled) temporaryPane.getChildren().get(0)).getText();
	    				if (map2.get("name").equals(matchingDescription)) {
	    					this.inProgress.getChildren().remove(matchingDescriptionFromInProgressIterator);
	    					// Iterate through stories data element and delete story
	    					int storyBoardChangeIterator = 0;
	    					for (UserStory tempUserStory : this.stories.stories) {
	    						if (tempUserStory.name.equals(map2.get("name"))) {
	    							this.stories.stories.get(storyBoardChangeIterator).stage = "backLog";
	    							try {
										this.gateway.addStoriesToServer(this.stories);
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
	    						}
	    						storyBoardChangeIterator++;
	    					}
	    					break;
	    				}
	    				matchingDescriptionFromInProgressIterator++;
	    			}
	    			break;
	    	}
	    	
	    	// Place onto backLog Pane
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
 
 	        storyControl.setStory(newUserTemp);
 	        
 	        backLog.getChildren().add(newStoryTemp);
 	        this.stories.addStoryWhole(newUserTemp);
 	        try {
 				this.gateway.updateStories();
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
 	protected void trashPaneDragOver(DragEvent event) {
		 if (event.getGestureSource() != backLog && event.getDragboard().hasString()) {
		        event.acceptTransferModes(TransferMode.ANY);
		 }
		 event.consume();
 	}
 	
 	@FXML
 	protected void trashPaneDragDropped(DragEvent event) {
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
	    	switch (map2.get("originPane")) {
	    		case "complete":
	    			int matchingDescriptionFromCompleteIterator = 0;
	    			for (Node node : this.complete.getChildren()) {
	    				Pane temporaryPane = (Pane) node;
	    				String matchingDescription = ((Labeled) temporaryPane.getChildren().get(0)).getText();
	    				if (map2.get("name").equals(matchingDescription)) {
	    					this.complete.getChildren().remove(matchingDescriptionFromCompleteIterator);
	    					// Iterate through stories data element and delete story
	    					int storyBoardDeleteIterator = 0;
	    					for (UserStory tempUserStory : this.stories.stories) {
	    						if (tempUserStory.name.equals(map2.get("name"))) {
	    							this.stories.stories.remove(storyBoardDeleteIterator);
	    							try {
										this.gateway.addStoriesToServer(this.stories);
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
	    						}
	    						storyBoardDeleteIterator++;
	    					}
	    					break;
	    				}
	    				matchingDescriptionFromCompleteIterator++;
	    			}
	    			break;
	    		case "toDo":
	    			int matchingDescriptionFromToDoIterator = 0;
	    			for (Node node : this.toDo.getChildren()) {
	    				Pane temporaryPane = (Pane) node;
	    				String matchingDescription = ((Labeled) temporaryPane.getChildren().get(0)).getText();
	    				if (map2.get("name").equals(matchingDescription)) {
	    					this.toDo.getChildren().remove(matchingDescriptionFromToDoIterator);
	    					// Iterate through stories data element and delete story
	    					int storyBoardDeleteIterator = 0;
	    					for (UserStory tempUserStory : this.stories.stories) {
	    						if (tempUserStory.name.equals(map2.get("name"))) {
	    							this.stories.stories.remove(storyBoardDeleteIterator);
	    							try {
										this.gateway.addStoriesToServer(this.stories);
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
	    						}
	    						storyBoardDeleteIterator++;
	    					}
	    					break;
	    				}
	    				matchingDescriptionFromToDoIterator++;
	    			}
	    			break;
	    		case "inProgress":
	    			int matchingDescriptionFromInProgressIterator = 0;
	    			for (Node node : this.inProgress.getChildren()) {
	    				Pane temporaryPane = (Pane) node;
	    				String matchingDescription = ((Labeled) temporaryPane.getChildren().get(0)).getText();
	    				if (map2.get("name").equals(matchingDescription)) {
	    					this.inProgress.getChildren().remove(matchingDescriptionFromInProgressIterator);
	    					// Iterate through stories data element and delete story
	    					int storyBoardDeleteIterator = 0;
	    					for (UserStory tempUserStory : this.stories.stories) {
	    						if (tempUserStory.name.equals(map2.get("name"))) {
	    							this.stories.stories.remove(storyBoardDeleteIterator);
	    							try {
										this.gateway.addStoriesToServer(this.stories);
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
	    						}
	    						storyBoardDeleteIterator++;
	    					}
	    					break;
	    				}
	    				matchingDescriptionFromInProgressIterator++;
	    			}
	    			break;
	    		case "backLog":
	    			int matchingDescriptionFromBackLogIterator = 0;
	    			for (Node node : this.backLog.getChildren()) {
	    				Pane temporaryPane = (Pane) node;
	    				String matchingDescription = ((Labeled) temporaryPane.getChildren().get(0)).getText();
	    				if (map2.get("name").equals(matchingDescription)) {
	    					this.backLog.getChildren().remove(matchingDescriptionFromBackLogIterator);
	    					// Iterate through stories data element and delete story
	    					int storyBoardDeleteIterator = 0;
	    					for (UserStory tempUserStory : this.stories.stories) {
	    						if (tempUserStory.name.equals(map2.get("name"))) {
	    							this.stories.stories.remove(storyBoardDeleteIterator);
	    							try {
										this.gateway.addStoriesToServer(this.stories);
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
	    						}
	    						storyBoardDeleteIterator++;
	    					}
	    					break;
	    				}
	    				matchingDescriptionFromBackLogIterator++;
	    			}
	    	}
 	    }
 	    // Complete and consume the event.
 	    event.setDropCompleted(success);
 	    event.consume();
 	}
	
}

class TranscriptCheck implements Runnable {
    private ChatGateway gateway; // Gateway to the server
    private StoryBook stories; // Where to info pass
    
    /** Construct a thread */
    public TranscriptCheck(ChatGateway gateway,StoryBook stories,boolean newchange) {
    		this.gateway = gateway;
    		this.stories = stories;
    		
    }

    /** Run a thread */
    public void run() {
      while(true) {
    	if(gateway != null) {
    		// update the stories on the server
    		try {
				gateway.grabStories();
				
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		System.out.println("New Story pushed at loginConroller, size is now: " + stories.stories.size());
    	  	try {
				stories = gateway.getStories();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	  	
          } else {
              try {
                  Thread.sleep(250);
              } catch(InterruptedException ex) {}
          }
              }
          }
	}
