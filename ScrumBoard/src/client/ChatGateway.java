package client;


import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;
import java.net.UnknownHostException;

import application.StoryBook;
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
            outputToServer = new ObjectOutputStream(socket.getOutputStream());
            // Create an input stream to read data from the server
            inputFromServer = new ObjectInputStream(socket.getInputStream());

            // grab data from server
            grabStories();
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
    public void updateStories(StoryBook N_stories) throws IOException {
    	this.stories = N_stories;
    	stories.storyAdd("Ryan", "2", "2", "trial 2");
    	outputToServer.writeObject(stories);
    	System.out.println("Updating the gateway, size is: " + this.stories.stories.size());
    }
    public void grabStories() throws ClassNotFoundException, IOException {
    	System.out.println("Incoming Info from server 1 ...");
        StoryBook SB_In = (StoryBook) inputFromServer.readObject();
        stories = SB_In;
        System.out.println("Incoming Info from server 2 ...");
        System.out.println("SB_In Size " + SB_In.stories.size());
    }
}
