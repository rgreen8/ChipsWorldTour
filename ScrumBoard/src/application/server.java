package application;

import java.net.*;
import java.io.*;

// help from https://www.baeldung.com/a-guide-to-java-sockets
public class server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
 
    public void start(int port) {
        try {
			serverSocket = new ServerSocket(port);
			clientSocket = serverSocket.accept();
	        out = new PrintWriter(clientSocket.getOutputStream(), true);
	        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	        String greeting = in.readLine();
	            if ("hello server".equals(greeting)) {
	                out.println("hello client");
	            }
	            else {
	                out.println("unrecognised greeting");
	            }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();}
		}
  
 
    public void stop() {
        try {
        	in.close();
        	out.close();
			clientSocket.close();
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public static void main(String[] args) {
        server serve =new server();
        serve.start(6656);
    }
}

