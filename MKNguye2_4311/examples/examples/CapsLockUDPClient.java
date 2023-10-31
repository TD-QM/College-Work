// 
// CSCI 4311 Example Code: UDP 'CapsLock' Client
// Vassil Roussev
//
package csci4311.examples;

import java.io.*;
import java.net.*;

public class CapsLockUDPClient {
	public static void main(String argv[]) throws Exception {
		// Explicitly create array of bytes to hold message data
    	byte[] sendData, receiveData = new byte[1024];
    	String sentence;
		
    	// Create (buffered) input stream using standard input
    	BufferedReader inFromUser = new BufferedReader( 
			new InputStreamReader(System.in));

	    // Create client datagram socket
    	DatagramSocket clientSocket = new DatagramSocket();

    	// Use DNS to resolve domain name to IP address
    	InetAddress ipAddress;
		if( argv.length > 1)
			ipAddress = InetAddress.getByName( argv[1]);
		else
			ipAddress = InetAddress.getByName( "localhost");
    	System.out.println("Client ready for input");
    	
		// While loop to read and handle multiple input lines
    	while ((sentence = inFromUser.readLine()) != null) {
			sendData = sentence.getBytes();

			// Create message datagram from data and server addressing information
			DatagramPacket sendPacket = new DatagramPacket(
									sendData, sendData.length, ipAddress, 9876);

			// Send to server
			clientSocket.send(sendPacket);

			// Create message datagram to hold data from server
			DatagramPacket receivePacket = new DatagramPacket(
												receiveData, receiveData.length);

			// Receive from server
			clientSocket.receive(receivePacket);

			// Create a string from the data received
			String modifiedSentence = new String(receivePacket.getData());
			System.out.println("FROM SERVER:" + modifiedSentence);
    	} // end while, loop to accept more lines from user
      	clientSocket.close();
	} // end main
} // end class
