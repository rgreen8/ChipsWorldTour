package client;


import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;
import java.net.UnknownHostException;

import application.StoryBook;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class ChatGateway implements chat.ChatConstants {

    // Establish the connection to the server.
    public ChatGateway(TextArea textArea) throws UnknownHostException,
    IOException, ClassNotFoundException {
        try {
        	System.out.println("Opening");
            // Create a socket to connect to the server
            Socket socket = new Socket("localhost", 8000);

            // Create an output stream to send data to the server
            ObjectOutputStream outputToSever = new ObjectOutputStream(socket.getOutputStream());
            StoryBook SB_Out = new StoryBook();
            outputToSever.writeObject(SB_Out);
            System.out.println("Incoming Info from server ...");

            // Create an input stream to read data from the server
            ObjectInputStream inputFromServer = new ObjectInputStream(socket.getInputStream());
            StoryBook SB_In = (StoryBook) inputFromServer.readObject();
            System.out.print(SB_In.stories.size());

        } catch (IOException ex) {
            Platform.runLater(() -> textArea.appendText("Exception in gateway constructor: " + ex.toString() + "\n"));
        }
    }
}
