/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import application.StoryBook;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

/**
 *
 * @author Joe Gregg
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TextArea textArea;
    
    private int clientNo = 0;
    
    public static StoryBook stories = new StoryBook();
    
    private ServerSocket serverSocket;
    
    public static void getStoryUpdates(StoryBook Update) {
    	stories = Update;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
      new Thread( () -> {
      try {
        // Create a server socket
        serverSocket = new ServerSocket(8000);
        while (true) {
        	System.out.println("listing....");
          // Listen for a new connection request
          Socket socket = serverSocket.accept();    
          // Increment clientNo
          clientNo++;
          // Create and start a new thread for the connection
          new Thread(new HandleAClient(socket,stories)).start();
        }
      }
      catch(IOException ex) {
        System.err.println(ex);
      }
    }).start();
    }    
    
}

class HandleAClient implements Runnable, chat.ChatConstants {
    private Socket socket; // A connected socket
	private StoryBook stories;

    public HandleAClient(Socket socket, StoryBook stories) {
      this.socket = socket;
      this.stories = stories;
    }

    public void run() {
      try {
        // Create reading and writing streams
    	 ObjectOutputStream outputToClient = new ObjectOutputStream(socket.getOutputStream());
    	 ObjectInputStream inputFromClient = new ObjectInputStream(socket.getInputStream());
        // Continuously serve the client
        while (true) {
        	// try to send an output 
            outputToClient.writeObject(stories);
            outputToClient.flush();
	        // Receive request code from the client
	        StoryBook SB_In = (StoryBook) inputFromClient.readObject();
	        if(SB_In != null){
	        	System.out.println("Received elements at server is: " + SB_In.stories.size());
	        }
	       this.stories = SB_In;
	       System.out.println("Received stories at server part 2 is: " + stories.stories.size());
	       FXMLDocumentController.getStoryUpdates(stories);
        }
      }
      catch(IOException ex) {
          Platform.runLater(()->System.out.println("Exception in client thread: "+ex.toString()+"\n"));
      } catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      try {
		socket.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }
  }