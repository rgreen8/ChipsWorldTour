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
	StoryBook stories;
    // Establish the connection to the server.
    public ChatGateway(StoryBook Stories) throws UnknownHostException,
    IOException, ClassNotFoundException {
        try {
        	System.out.println("Opening");
            // Create a socket to connect to the server
            Socket socket = new Socket("localhost", 8000);
            System.out.println("socket made");
            // Create an input stream to read data from the server
            ObjectInputStream inputFromServer = new ObjectInputStream(socket.getInputStream());
            System.out.println("Incoming Info from server 1 ...");
            StoryBook SB_In = (StoryBook) inputFromServer.readObject();
            System.out.println("Incoming Info from server 2 ...");
            //System.out.print(SB_In.stories.size());

            // Create an output stream to send data to the server
            ObjectOutputStream outputToSever = new ObjectOutputStream(socket.getOutputStream());
            StoryBook SB_Out = new StoryBook();
            outputToSever.writeObject(SB_Out);
            System.out.print(SB_Out.stories.size());

        } catch (IOException ex) {
            Platform.runLater(() -> System.out.println("Exception in gateway constructor: " + ex.toString() + "\n"));
        }
    }
}
