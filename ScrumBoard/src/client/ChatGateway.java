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
	private ObjectOutputStream outputToServer;
	private ObjectInputStream inputFromServer;
    // Establish the connection to the server.
    public ChatGateway(StoryBook Stories) throws UnknownHostException,
    IOException, ClassNotFoundException {
        try {
        	System.out.println("Opening");
            // Create a socket to connect to the server
            Socket socket = new Socket("localhost", 8000);
            System.out.println("socket made");
            // Create an output stream to send data out
            this.outputToServer = new ObjectOutputStream(socket.getOutputStream());
            // Create an input stream to read data from the server
            this.inputFromServer = new ObjectInputStream(socket.getInputStream());

            // grab data from server
            grabStories();
            stories.storyAdd("Shit", "2", "3", "please push");
            // send data to the server
            updateStories(stories);
           
            System.out.println(stories.stories.size());

        } catch (IOException ex) {
            Platform.runLater(() -> System.out.println("Exception in gateway constructor: " + ex.toString() + "\n"));
        }
    }
    public StoryBook getStories() {
    	return stories;
    	
    }
    public void addStoryToSever(UserStory US) {
    	stories.addStoryWhole(US);
    	System.out.println("Add story to server called");
    	try {
			this.updateStories(stories);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void updateStories(StoryBook N_stories) throws IOException {
    	this.stories = N_stories;
    	this.outputToServer.writeObject(stories);
    	System.out.println("Updating the gateway, size there is: " + this.stories.stories.size());
    }
    public void grabStories() throws ClassNotFoundException, IOException {
    	System.out.println("Incoming Info from server 1 ...");
        StoryBook SB_In = (StoryBook) this.inputFromServer.readObject();
        stories = SB_In;
        System.out.println("Incoming Info from server 2 ...");
        System.out.println("SB_In Size " + SB_In.stories.size());
    }
}
