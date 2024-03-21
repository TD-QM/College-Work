import java.util.*;
import java.io.*;
import java.net.*;


public class Server {
	public static void main(String[] args){
		ServerSocket server;
		ClientThread clientThrd;
		boolean start = true;
		
		try{
			server = new ServerSocket(Integer.parseInt(args[0]));
			System.out.println("Server Started");
			while(true){
				System.out.println("Waiting for a client");
				try{
					Socket client = server.accept();
					System.out.println("Client Accepted");
					clientThrd = new ClientThread(client, start);
					Thread thrd = new Thread(clientThrd);
					thrd.start();
					start = false;
				} catch (Exception e){
					System.err.println("Error: " + e.getMessage());
				}
			}
		} catch(Exception e){
			System.err.println("Error: " + e.getMessage());
		}

	} // End main function
	
} // End Server


class ClientThread implements Runnable{
	private DataInputStream in;
	private DataOutputStream out;
	private String username;
	private static ArrayList<String> userList = new ArrayList<String>();
	private static ArrayList<DataOutputStream> outputList = new ArrayList<DataOutputStream>();
	private static Deck deck;
	private static Card[][] hands;
	private static Thread startThrd;
	private boolean start;

	public ClientThread(Socket socket, boolean start){
		try{
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
		} catch(Exception e){
			System.err.println("Error Line 46: " + e.getMessage());
		}
		this.start = start;
		if(start){
			deck = new Deck();
		}
	}

	
	public void run(){
		try{
			startup();
			
			startThrd = new Thread(new StartThread(in, out, start));
			startThrd.run();
			startThrd.join();	

			writeAll("The Game of Mao has officially begun");

			String line = "";
			while(!line.equals("Bye")){
				try{
					line = in.readUTF();

					switch(line.toLowerCase()){
						case "bye":
							int listIndex = userList.indexOf(username);
							writeAll("Server: Goodbye " + username);	
							userList.remove(listIndex);
							outputList.remove(listIndex);
							break;
						case "allusers":
							String userOutput = "";
							userOutput += "Server: Listing all users...\n";
							userOutput += "------------------------------------------------\n";
							for(int i = 0; i < userList.size(); i++){
								userOutput += userList.get(i) + "\n";
							}
							userOutput += "------------------------------------------------\n";
							out.writeUTF(userOutput);
							break;
						default:
							writeAll(username + ": " + line);
					}
				} catch (Exception e){
					//System.err.println("Error Line 79: " + e.getMessage());
				}
			}

			out.writeUTF("Server: Goodbye " + username);
		} catch (Exception e) {
			//System.err.println("Error Line 85: " + e.getMessage());
		}
	}

	private void writeAll(String str) throws IOException{
		for(int i = 0; i < outputList.size(); i++){
			outputList.get(i).writeUTF(str);
		}
	}
	
	
	private void startup() throws Exception{
		System.out.println("Thread created!");
		out.writeUTF("Sever: Enter a username: ");
		System.out.println("Server has asked for a username");
		out.flush();

		username = in.readUTF();
		while(username.equals("") || userList.contains(username)){
			if(username.equals("")){
				out.writeUTF("\nServer: Invalid username, try again: ");
			} else if(userList.contains(username)){
				out.writeUTF("\nServer: User already exists, try again: ");
			}
			out.flush();
			username = in.readUTF();
		}
		
		System.out.println("Server has accepted " + username + " into the game");

		userList.add(username);
		outputList.add(out);

		writeAll("Welcome, " + username + ", to the Game of Mao.");
	}
	
} // End Client Thread

class StartThread implements Runnable{
	private DataInputStream in;
	private DataOutputStream out;
	private boolean start;
	private static int players = 0;
	private static int maxPlayers;

	public StartThread(DataInputStream in, DataOutputStream out, boolean start){
		this.in = in;
		this.out = out;
		this.start = start;
		players++;
	}

	public void run(){
		if(start){
			try{
				out.writeUTF("Server: How many players? [2-5]");

				while(true){
					String line = in.readUTF();
					try{
						maxPlayers = Integer.parseInt(line);

						if(maxPlayers >= 2 && maxPlayers <= 5){
							break;
						}
						out.writeUTF("Please put in a valid number [2-5]");
					} catch (Exception NumberFormatException){
						out.writeUTF("Invalid value, enter a parsable int");
					}
				}
				
			} catch (Exception e){

			}
		}
		try{
			out.writeUTF("Waiting on other players...");
			while(players < maxPlayers){
			}
		} catch(Exception e) {

		}
	}

}
