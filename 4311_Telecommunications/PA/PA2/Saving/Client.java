import java.util.*;
import java.io.*;
import java.net.*;

public class Client{
	
	
	public static void main(String[] args){
		Socket socket;
		DataInputStream in;
		DataOutputStream out;
		
		try{
			socket = new Socket(args[0], Integer.parseInt(args[1]));
			System.out.println("Connected to " + args[0] + " server");
			
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
		//System.out.println("Send Thread created!");
		while(true){
			System.out.print("");
			String line = input.nextLine();
			System.out.println("You have said: " + line);
			try{
				output.writeUTF(line);
				System.out.println("You have sent: " + line);
				//output.flush();
									
				if(line.equals("Bye")){
					break;
				}
			} catch (Exception e){
				System.err.println("Error Line 62: " + e.getMessage());
			}
		}
	}
}

class readThread implements Runnable{
	DataInputStream input;
	Thread sndThrd;
	String[] buffer;
	int bufferStartIndex;
	
	public readThread(DataInputStream input, Thread sndThrd){
		this.input = input;
		this.sndThrd = sndThrd;
		buffer = new String[30];
		for(int i = 0; i < 30; i++){
			buffer[i] = "";
		}
		bufferStartIndex = 0;
	}
	
	public void run(){
		//System.out.println("Read Thread created!");
		while(true){
			try{
				String line = input.readUTF();

				buffer[bufferStartIndex] = line;

				System.out.print("\033[H\033[2J");
				System.out.flush();

				int i = bufferStartIndex+1;
				while(i != bufferStartIndex){
					if(i >= 29){
						i = 0;
					} else {
						i++;
					}
					System.out.println(buffer[i]);	
				}

				bufferStartIndex = (bufferStartIndex+1) % 30;

				//System.out.println(line);
				
				if(!sndThrd.isAlive()){
					break;
				}
			} catch (Exception e) {
				System.err.println("Error Line 109: " + e.getMessage());
			}
		}
	}
}
