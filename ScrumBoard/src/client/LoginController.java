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


class TranscriptCheck implements Runnable, chat.ChatConstants {
    private ChatGateway gateway; // Gateway to the server
    private StoryBook stories; // Where to info pass
    
    /** Construct a thread */
    public TranscriptCheck(ChatGateway gateway,StoryBook stories,boolean newchange) {
    		this.gateway = gateway;
    		this.stories = stories;
    		
    }

    /** Run a thread */
    public void run() {
      
    	if(gateway != null) {
    		// update the stories on the server
    		try {
				gateway.updateStories(stories);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		System.out.println("New Story pushed at loginConroller, size is now: " + stories.stories.size());
    	  	stories = gateway.getStories();
    	  	
          } else {
              try {
                  Thread.sleep(250);
              } catch(InterruptedException ex) {}
          }
              }
          }

		
  