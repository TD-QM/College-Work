// 
// CSCI 4311 Example Code: TCP 'CapsLock' Server
// Vassil Roussev
//
package csci4311.examples;

import java.io.*;
import java.net.*;

public class CapsLockTCPServer {
	public static void main(String argv[]) throws Exception {
    	String clientSentence, capitalizedSentence;
	    Socket connectionSocket = null;
		
		// Create welcoming socket using port 6789
   		ServerSocket welcomeSocket = new ServerSocket( 6789, 0);
    	System.out.println("Server Ready for Connection");

    	// While loop to handle arbitrary sequence of clients making requests
    	while(true) {
			// Wait for some client to connect and create new socket for connection
			if( connectionSocket == null) {
				connectionSocket = welcomeSocket.accept();
				System.out.println( "Client Made Connection");
			}

			// Create (buffered) input stream attached to connection socket
			BufferedReader inFromClient = new BufferedReader( 
				new InputStreamReader( connectionSocket.getInputStream()));

			// Create output stream attached to connection socket
			DataOutputStream outToClient = new DataOutputStream( 
				connectionSocket.getOutputStream());

			// Read input line from socket
			clientSentence = inFromClient.readLine();
			System.out.println("Client sent: " + clientSentence);
			capitalizedSentence = clientSentence.toUpperCase() + '\n';

			// Write output line to socket
			outToClient.writeBytes(capitalizedSentence);
			if( clientSentence.equals( ".")) {
				connectionSocket.close();
				System.out.println( "Closing connection!");
				connectionSocket = null;
			}
    	} // end while; loop back to accept a new client connection
	} // end main
} // end class
