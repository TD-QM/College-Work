import java.util.*;
import java.io.*;
import java.net.*;

public class Server {
	public static void main(String[] args){
		ServerSocket server;
		ClientThread clientThrd;
		
		try{
			server = new ServerSocket(Integer.parseInt(args[0]));
			System.out.println("Server Started");
			while(true){
				System.out.println("Waiting for a client");
				try{
					Socket client = server.accept();
					System.out.println("Client Accepted");
					clientThrd = new ClientThread(client);
					Thread thrd = new Thread(clientThrd);
					thrd.start();
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
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	private String username;
	private static ArrayList<String> userList = new ArrayList<String>();
	private static ArrayList<DataOutputStream> outputList = new ArrayList<DataOutputStream>();

	public ClientThread(Socket socket){
		try{
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			outputList.add(out);
		} catch(Exception e){
			System.err.println("Error Line 46: " + e.getMessage());
		}
	}

	
	public void run(){
		try{
			startup();

			String line = "";
			while(!line.equals("Bye")){
				try{
					line = in.readUTF();

					switch(line){
						case "Bye":
							int listIndex = userList.indexOf(username);
							for(int i = 0; i < outputList.size(); i++){
								outputList.get(i).writeUTF("Server: Goodbye " + username);
							}
							userList.remove(listIndex);
							outputList.remove(listIndex);
							break;
						case "AllUsers":
							out.writeUTF("Server: Listing all users...");
							out.writeUTF("------------------------------------------------");
							for(int i = 0; i < userList.size(); i++){
								out.writeUTF(userList.get(i));
							}
							out.writeUTF("------------------------------------------------");
							break;
						default:
							for(int i = 0; i < outputList.size(); i++){
								outputList.get(i).writeUTF(username + ": " + line);
							}
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
	
	
	public void startup() throws Exception{
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
		
		for(int i = 0; i < outputList.size(); i++){
			outputList.get(i).writeUTF("Server: Welcome " + username);
		}
		System.out.println("Server has accepted " + username + " into the group chat");
		userList.add(username);
	}
	
} // End Client Thread