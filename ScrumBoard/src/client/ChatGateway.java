package client;


import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;
import java.net.UnknownHostException;

import application.StoryBook;
import application.UserStory;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ChatGateway implements chat.ChatConstants {
	
	public StoryBook stories;
	public ObjectOutputStream outputToServer;
	public ObjectInputStream inputFromServer;
	
    // Establish the connection to the server.
    public ChatGateway() throws UnknownHostException, IOException, ClassNotFoundException {
        try {
        	
            // Create a socket to connect to the server
            Socket socket = new Socket("localhost", 8000);
            
            // Create an output stream to send data out
            this.outputToServer = new ObjectOutputStream(socket.getOutputStream());
            
            // Create an input stream to read data from the server
            this.inputFromServer = new ObjectInputStream(socket.getInputStream());

            // grab data from server
            grabStories();
            
            // send data to the server
            updateStories();

        } catch (Exception ex) {
            Platform.runLater(() -> System.out.println("Exception in gateway constructor: " + ex.toString() + "\n"));
        }
    }
    
    public StoryBook getStories() throws ClassNotFoundException, IOException {
    	grabStories();
    	return this.stories;
    	
    }
    
    public void addStoryToSever(UserStory US) throws IOException {
    	this.stories.addStoryWhole(US);
		this.updateStories();
    }
    
    public void addStoriesToServer(StoryBook storyB) throws IOException {
    	this.stories = storyB;
		this.updateStories();
    }
    
    public void updateStories() throws IOException {
    	this.outputToServer.writeObject(stories);
    }
    
    public void grabStories() throws ClassNotFoundException, IOException {
    	StoryBook SB_In = (StoryBook) this.inputFromServer.readObject();
        this.stories = SB_In;
    }
    
}
