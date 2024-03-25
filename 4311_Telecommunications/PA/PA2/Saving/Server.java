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
	private int userIndex;
	private static int players = 0;
	private static int turn;
	private static ArrayList<String> userList = new ArrayList<String>();
	private static ArrayList<DataOutputStream> outputList = new ArrayList<DataOutputStream>();
	private static ArrayList<ArrayList<Card>> hands = new ArrayList<ArrayList<Card>>();
	private static ArrayList<Boolean> skipped = new ArrayList<Boolean>();
	private static Deck deck;
	private Thread startThrd;
	private static boolean complete = false;
	private boolean start;
	private boolean startRound;

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
			deck.shuffle();
		}
		userIndex = players;
		players++;
		hands.add(new ArrayList<Card>());
		skipped.add(false);
	}

	
	public void run(){
		try{
			// Run startup code (get username)
			startup();
			
			// Run the starting thread
			// Used to both get the number of players and pause the game until all players have joined
			startThrd = new Thread(new StartThread(in, out, start));
			startThrd.run();
			startThrd.join();	

			writeAll("The game has officially begun");

			// Drawing cards for all the players (13 per player)
			for(int i = 0; i < players; i++){
				for(int j = 0; j < 13; j++){
					hands.get(i).add(deck.draw());
				}
			}

			// Sort the order of the hands for clarity while playing
			sortHands();

			// In Hail/Thirteen, the player with the lowest value card (typically the Three of Spades) goes first
			int minVal = hands.get(0).get(0).getValue();
			turn = 0;
			for(int i = 1; i < players; i++){
				if(minVal > hands.get(i).get(0).getValue()){
					turn = i;
				}
			}


			String line = "";
			boolean reprint = true;
			boolean begin = true;
			startRound = true;
			while(!complete){

				if(reprint){
					printHands();
					System.out.println(username + ": Printed Hands");
					writeAll("\nIt is currently " + userList.get(turn) + "'s turn");
					if(!startRound){
						writeAll("Current hand to beat: " + deck.topDiscard().toString());
					} else {
						writeAll(userList.get(turn) + " can play anything");
					}
					System.out.println(username + ": Printed Status");
					reprint = false;
				}

				try{
					writeAll("Waiting on input from " + username);
					line = in.readUTF();
					writeAll("Gotten input from " + username);

					System.out.println(username + " has typed " + line);

					// Listing the players in the game (useful for checking turn order)
					if(line.equals("allusers")){
						String userOutput = "";
						userOutput += "Server: Listing all users...\n";
						userOutput += "------------------------------------------------\n";
						for(int i = 0; i < userList.size(); i++){
							userOutput += "Player " + (i+1) + ": " + userList.get(i) + "\n";
						}
						userOutput += "------------------------------------------------\n";
						out.writeUTF(userOutput);
						continue;
					}

					// Not the player's turn
					if(turn != userIndex){
						out.writeUTF("It's not your turn!");
						continue;
					}

					// try/catch block for seeing if the user inputted an actual int
					try{
						int handIndex = Integer.parseInt(line);

						// 99 is hardcoded to be a skip
						if(handIndex == 99){
							out.writeUTF("Skipped this round!");
							skipped.set(userIndex, true);
							turn = (turn + 1) % players;
							continue;
						}

						// Seeing if the user put in a valid card in their hand
						if(handIndex < 0 || handIndex > hands.get(userIndex).size()){
							out.writeUTF("That's not a valid input!");
							continue;	
						}

						// Get the card the player played
						Card playedCard = hands.get(userIndex).get(handIndex);

						// In Hail/Thirteen, the player who starts must include the lowest card in the first hand played
						if(begin && handIndex != 0){
							out.writeUTF("You must play the lowest value card when starting the game!");
							continue;
						}

						if(startRound){
							deck.play(playedCard);
							writeAll(username + " has played a " + playedCard.toString());
							begin = false;
							startRound = false;
							updateTurn();
							continue;
						}

						// Seeing if the card can actually beat the current card on top of the discard pile
						if(playedCard.getValue() < deck.topDiscard().getValue()){
							out.writeUTF("That card doesn't beat the current one!");
							continue;
						}

						// Played card does beat the current card
						deck.play(playedCard);
						writeAll(username + " has played a " + playedCard.toString());
						begin = false;
						startRound = false;
						updateTurn();


					} catch (NumberFormatException e){
						out.writeUTF("That's not a number!");
						continue;
					} catch (Exception e){
						System.err.println("Error Line 198: " + e.getMessage());
					}


				} catch (Exception e){
					System.err.println("Error Line 203: " + e.getMessage());
				}
			}

			out.writeUTF("Server: Goodbye " + username);
		} catch (Exception e) {
			System.err.println("Error Line 85: " + e.getMessage());
		}
	}

	private void updateTurn(){
		turn = (turn+1) % players;
		while(skipped.get(turn)){
			if(turn == userIndex){
				startRound = true;
				for(int i = 0; i < skipped.size(); i++){
					skipped.set(i, false);
				}
				break;
			}
			turn = (turn+1) % players;
			startRound = true;
		}
	}

	private void sortHands(){
		for(int i = 0; i < hands.size(); i++){ 
			for(int j = 0; j < hands.get(i).size()-1; j++){
				int min = hands.get(i).get(j).getValue();
				int minIndex = j;
				for(int k = j+1; k < hands.get(i).size(); k++){
					if(min > hands.get(i).get(k).getValue()){
						min = hands.get(i).get(k).getValue();
						minIndex = k;
					}
				}

				Card tempCard = hands.get(i).get(j);
				hands.get(i).set(j, hands.get(i).get(minIndex));
				hands.get(i).set(minIndex, tempCard);
			}
		}

	}

	private void printHands() throws IOException{
		for(int i = 0; i < hands.size(); i++){
			String hand = "Hand: \n";
			for(int j = 0; j < hands.get(i).size(); j++){
				hand += "[" + j + "] " + hands.get(i).get(j).toString() + "\n";
			}
			hand += "[99] Skip this round\n";
			outputList.get(i).writeUTF(hand);
		}
	}

	private void writeAll(String str) throws IOException{
		for(int i = 0; i < outputList.size(); i++){
			outputList.get(i).writeUTF(str);
		}
	}
	
	
	private void startup() throws IOException{
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

		userIndex = userList.size();
		userList.add(username);
		outputList.add(out);

		writeAll("Welcome, " + username + ", to the game.");
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
				out.writeUTF("Server: How many players? [2-4]");

				while(true){
					String line = in.readUTF();
					try{
						maxPlayers = Integer.parseInt(line);

						if(maxPlayers >= 2 && maxPlayers <= 4){
							break;
						}
						out.writeUTF("Please put in a valid number [2-4]");
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
