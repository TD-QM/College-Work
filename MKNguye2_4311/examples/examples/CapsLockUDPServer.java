// 
// CSCI 4311 Example Code: UDP 'CapsLock' Server
// Vassil Roussev
//
package csci4311.examples;

import java.io.*;
import java.net.*;

public class CapsLockUDPServer {
	public static void main(String argv[]) throws Exception {
    	// Explicitly create array of bytes to hold message data
    	byte[] receiveData = new byte[1024], sendData = new byte[1024];

    	// Create server datagram socket addressed by port 9876
    	DatagramSocket serverSocket = new DatagramSocket(9876);
    	System.out.println("Server Ready for Datagrams");

    	// Create message datagram to hold data from client
    	while(true) {
			DatagramPacket receivePacket = new DatagramPacket(
													receiveData, receiveData.length);
	  		// Receive from client
	  		serverSocket.receive(receivePacket);

	  		// Create a string from the data received
	  		String sentence = new String(receivePacket.getData());
	  		System.out.println("Client sent: " + sentence);

	  		// Get IP address and port of the client
	  		InetAddress IPAddress = receivePacket.getAddress();
	  		int port = receivePacket.getPort();
	  		String capitalizedSentence = sentence.toUpperCase();
	  		sendData = capitalizedSentence.getBytes();

	  		// Create message datagram from data and client addressing information
	  		DatagramPacket sendPacket = new DatagramPacket(
										sendData, sendData.length,IPAddress, port);
	  		// Send to client
	  		serverSocket.send(sendPacket);
      	} // end while, loop to receive another message from the client
  	} // end main
} // end class

