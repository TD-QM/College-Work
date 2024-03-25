import java.util.*;
import java.io.*;
import java.net.*;


public class Server {
	public static void main(String[] args) throws Exception{
		ServerSocket server;
		ClientThread clientThrd;
		ArrayList<ClientThread> players = new ArrayList<ClientThread>();
		String thrdOutput = "";
		boolean start = true;

		Deck deck = new Deck();
		deck.shuffle();
		ArrayList<ArrayList<Card>> hands = new ArrayList<ArrayList<Card>>();
		ArrayList<Boolean> skipped = new ArrayList<Boolean>();

		int numPlayers = 0;
		int turn;
		int userIndex;

		Scanner input = new Scanner(System.in);
		System.out.print("How many players? ");
		numPlayers = input.nextInt();
		
		try{
			server = new ServerSocket(Integer.parseInt(args[0]));
			System.out.println("Server Started");
			while(players.size() < numPlayers){
				System.out.println("Waiting for a client");
				try{
					Socket client = server.accept();
					System.out.println("Client Accepted");
					clientThrd = new ClientThread(client, thrdOutput);
					players.add(clientThrd);
				} catch (Exception e){
					System.err.println("Error: " + e.getMessage());
				}
			}
		} catch(Exception e){
			System.err.println("Error: " + e.getMessage());
		}

		// Drawing cards and sorting hands
		for(int i = 0; i < numPlayers; i++){
			hands.add(new ArrayList<Card>());
			for(int j = 0; j < 13; j++){
				hands.get(i).add(deck.draw());
			}
		}
		sortHands(hands);

		for(int i = 0; i < numPlayers; i++){
			skipped.add(false);
		}

		// Determining the first player
		int minVal = hands.get(0).get(0).getValue();
		turn = 0;
		for(int i = 1; i < hands.size(); i++){
			if(minVal > hands.get(i).get(0).getValue()){
				turn = i;
				minVal =  hands.get(i).get(0).getValue();
			}
		}


		// Starting the player threads
		for(int i = 0; i < players.size(); i++){
			Thread thrd = new Thread(players.get(i));
			thrd.start();
		}

		boolean begin = true;
		boolean startRound = true;
		boolean complete = false;
		// Main while loop for the game
		while(!complete){

			for(int i = 0; i < players.size(); i++){
				if(turn != i){
					players.get(i).wait();
				}
			}

			printHands(hands, players);

			while(!thrdOutput.equals("")){

			}

			while(true){
				try{
					int handIndex = Integer.parseInt(thrdOutput);

					// 99 is hardcoded to be a skip
					if(handIndex == 99){
						players.get(turn).getOutputStream().writeUTF("Skipped this round!");
						skipped.set(turn, true);

						int test = updateTurn(turn, skipped, numPlayers);
						if(turn == test){
							startRound = true;
						} else {
							turn = test;
						}
						continue;
					}

					// Seeing if the user put in a valid card in their hand
					if(handIndex < 0 || handIndex > hands.get(turn).size()){
						players.get(turn).getOutputStream().writeUTF("That's not a valid input!");
						continue;	
					}

					// Get the card the player played
					Card playedCard = hands.get(turn).get(handIndex);

					// In Hail/Thirteen, the player who starts must include the lowest card in the first hand played
					if(begin && handIndex != 0){
						players.get(turn).getOutputStream().writeUTF("You must play the lowest value card when starting the game!");
						continue;
					}

					// Player who starts a round can play any card
					if(startRound){
						deck.play(playedCard);
						writeAll(players, players.get(turn).getName() + " has played a " + playedCard.toString());
						begin = false;
						startRound = false;
						int test = updateTurn(turn, skipped, numPlayers);
						if(turn == test){
							startRound = true;
						} else {
							turn = test;
						}
						break;
					}

					// Seeing if the card can actually beat the current card on top of the discard pile
					if(playedCard.getValue() < deck.topDiscard().getValue()){
						players.get(turn).getOutputStream().writeUTF("That card doesn't beat the current one!");
						continue;
					}

					// Played card does beat the current card
					deck.play(playedCard);
					writeAll(players, players.get(turn).getName() + " has played a " + playedCard.toString());
					begin = false;
					startRound = false;
					int test = updateTurn(turn, skipped, numPlayers);
					if(turn == test){
						startRound = true;
					} else {
						turn = test;
					}
					break;


				} catch (NumberFormatException e){
					players.get(turn).getOutputStream().writeUTF("That's not a number!");
				}
			}

			thrdOutput = "";

		}

	} // End main function

	private static void sortHands(ArrayList<ArrayList<Card>> hands){
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

	private static void printHands(ArrayList<ArrayList<Card>> hands, ArrayList<ClientThread> players) throws IOException{
		for(int i = 0; i < hands.size(); i++){
			String hand = "Hand: \n";
			for(int j = 0; j < hands.get(i).size(); j++){
				hand += "[" + j + "] " + hands.get(i).get(j).toString() + "\n";
			}
			hand += "[99] Skip this round\n";
			players.get(i).getOutputStream().writeUTF(hand);
		}
	}

	private static int updateTurn(int currentTurn, ArrayList<Boolean> skipped, int numPlayers){
		int turn = (currentTurn+1) % numPlayers;

		while(skipped.get(turn)){
			if(turn == currentTurn){
				for(int i = 0; i < skipped.size(); i++){
					skipped.set(i, false);
				}
				break;
			}
			turn = (turn+1) % numPlayers;
		}

		return turn;
	}

	private static void writeAll(ArrayList<ClientThread> players, String str) throws IOException{
		for(int i = 0; i < players.size(); i++){
			players.get(i).getOutputStream().writeUTF(str);
		}
	}
	
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
	private static String thrdOutput;

	public ClientThread(Socket socket, String thrdOutput){
		try{
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			this.thrdOutput = thrdOutput;
		} catch(Exception e){
			System.err.println("Error Line 46: " + e.getMessage());
		}
	}

	
	public void run(){
		try{
			// Run startup code (get username)
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
							// for(int i = 0; i < outputList.size(); i++){
							// 	outputList.get(i).writeUTF(username + ": " + line);
							// }
							thrdOutput = line;
							break;
					}
				} catch (Exception e){
					//System.err.println("Error Line 79: " + e.getMessage());
				}
			}

		} catch (Exception e) {
			System.err.println("Error Line 85: " + e.getMessage());
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

		for(int i = 0; i < outputList.size(); i++){
			outputList.get(i).writeUTF("Welcome, " + username + ", to the game.");
		}
	}


	public DataOutputStream getOutputStream(){
		return out;
	}

	public String getName(){
		return username;
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
