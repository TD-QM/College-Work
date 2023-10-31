// 
// CSCI 4311 Example Code: TCP 'CapsLock' Server
// Multi-threaded version
// Vassil Roussev
//
package csci4311.examples;

import java.io.*;
import java.net.*;

public class CapsLockTCPServerMT {
	public static void main(String argv[]) throws Exception {
	    Socket connectionSocket = null;
		
		// Create welcoming socket using port 6789
   		ServerSocket welcomeSocket = new ServerSocket( 6789, 0);
    	System.out.println("Server Ready for Connection");

    	// While loop to handle arbitrary sequence of clients making requests
    	while(true) {
			connectionSocket = welcomeSocket.accept();
			System.out.println( "Client Made Connection");

			ServiceThread worker = new ServiceThread( connectionSocket);
			worker.start();
    	} // end while; loop back to accept a new client connection
	} // end main

} // end class

class ServiceThread extends Thread {
	Socket connectionSocket;
	
	public ServiceThread( Socket connectionSocket) {
		this.connectionSocket = connectionSocket;
	}
	
	public void run() {
    	String clientSentence, capitalizedSentence;

		try {
			// Create (buffered) input stream attached to connection socket
			BufferedReader inFromClient = new BufferedReader( 
				new InputStreamReader( connectionSocket.getInputStream()));

			// Create output stream attached to connection socket
			DataOutputStream outToClient = new DataOutputStream( 
				connectionSocket.getOutputStream());

			while( (clientSentence = inFromClient.readLine()) != null) {
				System.out.println("Client sent: " + clientSentence);
				capitalizedSentence = clientSentence.toUpperCase() + '\n';

				// Write output line to socket
				outToClient.writeBytes(capitalizedSentence);
				if( clientSentence.equals( ".")) {
					connectionSocket.close();
					System.out.println( "Closing connection!");
					return;
				}
			}
		} catch( IOException ex) {
			ex.printStackTrace();
		}
	}
}
