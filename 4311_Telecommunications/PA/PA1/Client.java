import java.util.*;
import java.io.*;
import java.net.*;

public class Client{
	
	
	public static void main(String[] args){
		Socket socket;
		DataInputStream in;
		DataOutputStream out;
		
		try{
			socket = new Socket("localhost", Integer.parseInt(args[0]));
			System.out.println("Connected to server");
			
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			
			sendThread sendMsg = new sendThread(out);
			Thread sndthrd = new Thread(sendMsg);
			
			readThread readMsg = new readThread(in, sndthrd);
			Thread rdthrd = new Thread(readMsg);
			
			rdthrd.start();
			sndthrd.start();
			
		} catch (Exception e){
			System.err.println("Error: " + e.getMessage());
		}
		
	} // End main	
} // End Client


class sendThread implements Runnable{
	Scanner input;
	DataOutputStream output;
	
	public sendThread(DataOutputStream output){
		input = new Scanner(System.in);
		this.output = output;
	}
	
	public void run(){
		System.out.println("Send Thread created!");
		while(true){
			String line = input.nextLine();
			try{
				output.writeUTF(line);
				output.flush();
									
				if(line.equals("Bye")){
					break;
				}
			} catch (Exception e){
				//System.err.println("Error Line 62: " + e.getMessage());
			}
		}
	}
}

class readThread implements Runnable{
	DataInputStream input;
	Thread sndThrd;
	
	public readThread(DataInputStream input, Thread sndThrd){
		this.input = input;
		this.sndThrd = sndThrd;
	}
	
	public void run(){
		System.out.println("Read Thread created!");
		while(true){
			//System.out.println("Reading...");
			try{
				String line = input.readUTF();
				System.out.println(line);
				if(!sndThrd.isAlive()){
					break;
				}
			} catch (Exception e) {
				//System.err.println("Error Line 87: " + e.getMessage());
			}
		}
	}
}
