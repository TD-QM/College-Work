// 
// CSCI 4311 Example Code: TCP 'CapsLock' Client
// Vassil Roussev
//
package csci4311.examples;

import java.io.*;
import java.net.*;

public class CapsLockTCPClient {
	public static void main(String argv[]) throws Exception {
		String sentence, modifiedSentence;
		Socket clientSocket = null;
		
		// Create (buffered) input stream using standard input    
		BufferedReader inFromUser = new BufferedReader( 
			new InputStreamReader( System.in));
    	System.out.println("Client ready for input");

    	// While loop to read and handle multiple input lines
    	while ((sentence = inFromUser.readLine()) != null) {
			// Create client socket with connection to server at port 6789; 
			// Uses DNS implicitly
			if( clientSocket == null)
				if( argv.length > 1)  // Is server name given on the command line? 
					clientSocket = new Socket( argv[1], 6789);
				else
					clientSocket = new Socket( "localhost", 6789);	

			// Create output stream attached to socket
			DataOutputStream outToServer = new DataOutputStream( 
				clientSocket.getOutputStream());
			// Create (buffered) input stream attached to socket
			BufferedReader inFromServer = new BufferedReader( 
				new InputStreamReader( clientSocket.getInputStream()));

			// Write line to server
			outToServer.writeBytes(sentence + '\n');

			// Read line from server
			modifiedSentence = inFromServer.readLine();
			System.out.println("FROM SERVER: " + modifiedSentence);
			//clientSocket.close();
		} // end while, loop to accept more lines from user
	} // end main
} // end class
 
