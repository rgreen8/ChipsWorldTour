/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.ResourceBundle;

import application.StoryBook;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

/**
 *
 * @author Joe Gregg
 */
public class LoginController implements Initializable {
    private ChatGateway gateway;
    private StoryBook stories;
    @FXML
    private TextField comment;
       
    @FXML
    public void toScrum(ActionEvent event) throws IOException {
		 Parent testparent = FXMLLoader.load(getClass().getResource("ScrumBoard.fxml"));
		 Scene testScene = new Scene(testparent);
		 
	        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
	        
	        window.setScene(testScene);
	        window.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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

        // Start the transcript check thread
        new Thread(new TranscriptCheck(gateway,stories)).start();
    }        
}

class TranscriptCheck implements Runnable, chat.ChatConstants {
    private ChatGateway gateway; // Gateway to the server
    private StoryBook stories; // Where to info pass
    private int N; // How many comments we have read
    
    /** Construct a thread */
    public TranscriptCheck(ChatGateway gateway,StoryBook stories) {
    		this.gateway = gateway;
    		this.stories = stories;
    		this.N = 0;
    }

    /** Run a thread */
    public void run() {
      
          if(gateway != null) {
         //     String newComment = gateway.getComment(N);
        	  	stories = gateway.getStories();
        	  	System.out.println("Gatewat Stories: " + stories.stories.get(0).name);
          } else {
              try {
                  Thread.sleep(250);
              } catch(InterruptedException ex) {}
          }
              }
          }

		
  