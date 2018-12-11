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
    private boolean newchange;
    
    /** Construct a thread */
    public TranscriptCheck(ChatGateway gateway,StoryBook stories,boolean newchange) {
    		this.gateway = gateway;
    		this.stories = stories;
    		this.newchange = newchange;
    }

    /** Run a thread */
    public void run() {
      
    	if(newchange) {
    		// update the stories on the server
    		gateway.updateStories(stories);
    		System.out.println("New Story pushed: " + stories.stories.size());
    		
    	} else if(newchange != true && gateway != null) {
         //     
        	  	stories = gateway.getStories();
        	  	System.out.println("Gatewat Stories: " + stories.stories.get(1).name);
          } else {
              try {
                  Thread.sleep(250);
              } catch(InterruptedException ex) {}
          }
              }
          }

		
  