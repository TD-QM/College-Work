import java.util.*;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.CyclicBarrier;
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
	private static List<DataOutputStream> outputList = Collections.synchronizedList(new ArrayList<DataOutputStream>());
	private static ArrayList<ArrayList<Card>> hands = new ArrayList<ArrayList<Card>>();
	private static ArrayList<ArrayList<Card>> discardPile = new ArrayList<ArrayList<Card>>();
	private static ArrayList<Boolean> skipped = new ArrayList<Boolean>();
	private static CyclicBarrier barrier;
	private static Deck deck;
	private static boolean begin;
	private Thread startThrd;
	private static boolean complete = false;

	private boolean reprint;
	private boolean start;
	private static boolean startRound;

	public ClientThread(Socket socket, boolean start){
		try{
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
		} catch(Exception e){
			System.err.println("Error Line 59: " + e.getMessage());
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
		barrier = new CyclicBarrier(players);
	}

	
	public void run(){
		try{
			// Run startup code (get username)
			startup();
			
			// Run the starting thread
			// Used to both get the number of players and pause the game until all players have joined
			startThrd = new Thread(new StartThread(in, out, start));
			startThrd.run();	
			
			//if(!start){
				startThrd.join();
			//}

			//out.writeUTF("\033[H\033[2J");
			out.writeUTF("The game has officially begun");

			if(start){
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
						minVal = hands.get(i).get(0).getValue();
					}
				}
			}


			String line = "";
			reprint = true;
			begin = true;
			complete = false;
			startRound = true;
			String winner = "";

			//System.out.println(username + " is just before the while loop");
			outerloop:
			while(!complete){

				//System.out.println(username + " entered the while loop");
				
				while(turn != userIndex){
					barrier.await();
					for(int i = 0; i < hands.size(); i++){
						if(hands.get(i).size() == 0){
							complete = true;
							winner = userList.get(i);
							break outerloop;
						}
					}
				}

				// Prints game info (hands, current player's turn, etc.)
				if(reprint){
					printHands();
					System.out.println(username + ": Printed Hands");

					for(int i = 0; i < outputList.size(); i++){
						if(i == turn){
							outputList.get(i).writeUTF("\nIt is currently your turn");
						} else {
							outputList.get(i).writeUTF("\nIt is currently " + userList.get(turn) + "'s turn");
						}
					}


					if(!startRound){
						String writeout = "Current hand to beat: " + discardPile.get(discardPile.size()-1).toString();
						writeAll(writeout);
					} else {
						writeAll(userList.get(turn) + " can play anything");
					}
					System.out.println(username + ": Printed Status");
					reprint = false;
				}

				try{
					//writeAll("Waiting on input from " + username);
					line = in.readUTF();
					//writeAll("Gotten input from " + username);

					//System.out.println(username + " has typed " + line);

					// Listing the players in the game (useful for checking turn order)
					if(line.equals("allusers")){
						String userOutput = "";
						userOutput += "Server: Listing all users...\n";
						userOutput += "------------------------------------------------\n";
						for(int i = 0; i < userList.size(); i++){
							userOutput += "Player " + (i+1) + ": " + userList.get(i) + "\t|\tSkipped: " + skipped.get(i) +"\n";
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
						String[] input = line.split(" ");
						//System.out.println(username + ": directly input " + line);
						//System.out.print(username + ": input ");
						for(String aaa : input){
							System.out.print(aaa);
						}
						System.out.println("");


						ArrayList<Integer> handIndex = new ArrayList<Integer>();
						for(String index : input){
							handIndex.add(Integer.parseInt(index));
						}

						// 99 is hardcoded to be a skip
						if(handIndex.get(0).equals(99)){
							writeAll(username + " skipped this round!");
							skipped.set(userIndex, true);
							updateTurn();
							continue;
						}


						for(int i = 0; i < handIndex.size(); i++){
							// Seeing if the user put in a valid card in their hand
							if(handIndex.get(i) < 0 || handIndex.get(i) > hands.get(userIndex).size()){
								out.writeUTF("That's not a valid input!");
								continue;	
							}
						}

						// Get the card(s) the player played
						ArrayList<Card> playedCard = new ArrayList<Card>();
						for(int i = 0; i < handIndex.size(); i++){
							playedCard.add(hands.get(turn).get(handIndex.get(i).intValue()));
						}
						System.out.println(username + ": attemped to play " + playedCard.toString());



						// In Hail/Thirteen, the player who starts must include the lowest card in the first hand played
						if(begin && !handIndex.contains(0)){
							out.writeUTF("You must play the lowest value card when starting the game!");
							continue;
						}

						// At the beginning of the round, anything can be played
						if(startRound){
							//deck.play(playedCard);

							// Checks if the played hand is a valid sequence, pair, or three/four of a kind
							if(!validHand(playedCard) && !checkChop(playedCard)){
								out.writeUTF("That isn't a sequence or duplicate!");
								continue;
							}

							discardPile.add(playedCard);
							writeAll("\033[H\033[2J");
							writeAll(username + " has played " + playedCard.toString());
							System.out.println(username + " has played " + playedCard.toString() + " in the first round");
							begin = false;
							startRound = false;

							for(int i = handIndex.size()-1; i >= 0; i--){
								System.out.println(username + ": Removed " + hands.get(turn).remove(handIndex.get(i).intValue()).toString());
							}

							updateTurn();
							reprint = true;
							continue;
						}

						// Checks if the played hand matches the number of cards required
						if(handIndex.size() != discardPile.get(0).size() && !checkChop(playedCard)){
							out.writeUTF("This sequence isn't the right size.");
							continue;
						}

						// Checks if the played hand is a valid sequence, pair, or three/four of a kind
						if(!validHand(playedCard) && !checkChop(playedCard)){
							out.writeUTF("That isn't a sequence or duplicate!");
							continue;
						}
						// if(handIndex.size() > 1){
						// 	boolean validSequence = true;
						// 	if(handIndex.size() < 3){
						// 		validSequence = false;
						// 	} else {
						// 		for(int i = 0; i < handIndex.size()-1; i++){
						// 			if(hands.get(userIndex).get(handIndex.get(i)).getValue()/10 - hands.get(userIndex).get(handIndex.get(i+1)).getValue()/10 != -1){
						// 				validSequence = false;
						// 				break;
						// 			}
						// 		}
						// 	}

						// 	boolean validOfAKind = true;
						// 	for(int i = 0; i < handIndex.size()-1; i++){
						// 		if(hands.get(userIndex).get(handIndex.get(i)).getValue()/10 != hands.get(userIndex).get(handIndex.get(i+1)).getValue()/10){
						// 			validSequence = false;
						// 			break;
						// 		}
						// 	}

						// 	if(!validSequence && !validOfAKind){
						// 		out.writeUTF("That isn't a sequence or duplicate!");
						// 		continue;
						// 	}
						// }

						if(discardPile.get(discardPile.size()-1).get(discardPile.get(discardPile.size()-1).size()-1).getValue()/10 == 13 && checkChop(playedCard)){
							discardPile.add(playedCard);
							writeAll("\033[H\033[2J");
							writeAll(username + " has played " + playedCard.toString());
							System.out.println(username + " has played " + playedCard.toString());
							for(int i = handIndex.size()-1; i >= 0; i--){
								System.out.println(username + ": Removed " + hands.get(turn).remove(handIndex.get(i).intValue()).toString());
							}
							updateTurn();
							continue;
						}

						// Chop checks
						// 4 of a kind beats a double sequence any time
						// If they're the same chop, then it depends on the highest number in the played hand
						if(checkChop(discardPile.get(discardPile.size()-1)) && checkChop(playedCard)){
							if(playedCard.size() == discardPile.get(discardPile.size()-1).size()){
								if(playedCard.get(playedCard.size()-1).getValue() < discardPile.get(discardPile.size()-1).get(discardPile.get(discardPile.size()-1).size()-1).getValue()){
									out.writeUTF("That chop doesn't beat the current one!");
									continue;
								}
								discardPile.add(playedCard);
								writeAll("\033[H\033[2J");
								writeAll(username + " has played " + playedCard.toString());
								System.out.println(username + " has played " + playedCard.toString());
								// begin = false;
								// startRound = false;
								for(int i = handIndex.size()-1; i >= 0; i--){
									System.out.println(username + ": Removed " + hands.get(turn).remove(handIndex.get(i).intValue()).toString());
								}
								updateTurn();
								continue;
							} else if (playedCard.size() < discardPile.get(discardPile.size()-1).size()){
								out.writeUTF("That card/collection doesn't beat the current one!");
								continue;
							} else if (playedCard.size() > discardPile.get(discardPile.size()-1).size()){
								if(playedCard.get(playedCard.size()-1).getValue() < discardPile.get(discardPile.size()-1).get(discardPile.get(discardPile.size()-1).size()-1).getValue()){
									out.writeUTF("That chop doesn't beat the current one!");
									continue;
								}
								discardPile.add(playedCard);
								writeAll("\033[H\033[2J");
								writeAll(username + " has played " + playedCard.toString());
								System.out.println(username + " has played " + playedCard.toString());
								// begin = false;
								// startRound = false;
								for(int i = handIndex.size()-1; i >= 0; i--){
									System.out.println(username + ": Removed " + hands.get(turn).remove(handIndex.get(i).intValue()).toString());
								}
								updateTurn();
								continue;
							} else {
								System.out.println("How the fuck did you get here");
								updateTurn();
							}
						}

						// Seeing if the card can actually beat the current card on top of the discard pile
						if(playedCard.get(playedCard.size()-1).getValue() < discardPile.get(discardPile.size()-1).get(discardPile.get(discardPile.size()-1).size()-1).getValue()){
							out.writeUTF("That card/collection doesn't beat the current one!");
							continue;
						}

						// Played card does beat the current card
						discardPile.add(playedCard);
						writeAll("\033[H\033[2J");
						writeAll(username + " has played " + playedCard.toString());
						System.out.println(username + " has played " + playedCard.toString());
						// begin = false;
						// startRound = false;
						for(int i = handIndex.size()-1; i >= 0; i--){
							System.out.println(username + ": Removed " + hands.get(turn).remove(handIndex.get(i).intValue()).toString());
						}
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

			out.writeUTF(winner + " has won the game!");

			//out.writeUTF("Server: Goodbye " + username);
		} catch (Exception e) {
			System.err.println("Error Line 220: " + e.getMessage());
		}
	}

	private boolean validHand(ArrayList<Card> playedCard){
		if(playedCard.size() > 1){
			boolean validSequence = true;
			if(playedCard.size() < 3){
				validSequence = false;
			} else {
				for(int i = 0; i < playedCard.size()-1; i++){
					if(playedCard.get(i).getValue()/10 - playedCard.get(i+1).getValue()/10 != -1){
						validSequence = false;
						break;
					}
				}
			}

			boolean validOfAKind = true;
			for(int i = 0; i < playedCard.size()-1; i++){
				if(playedCard.get(i).getValue()/10 != playedCard.get(i+1).getValue()/10){
					validOfAKind = false;
					break;
				}
			}

			if(!validSequence && !validOfAKind){
				return false;
			}
		}
		return true;
	}

	private boolean checkChop(ArrayList<Card> playedCard){
		if(playedCard.size() < 4){
			return false;
		}
		if(playedCard.size() == 4){
			for(int i = 0; i < playedCard.size()-1; i++){
				if(playedCard.get(i).getValue()/10 != playedCard.get(i).getValue()/10){
					return false;
				}
			}
			return true;
		}

		for(int i = 0; i < playedCard.size()-1; i++){
			if(i%2 == 0){
				if(playedCard.get(i).getValue()/10 != playedCard.get(i+1).getValue()/10){
					return false;
				}
			} else {
				if(playedCard.get(i).getValue()/10 - playedCard.get(i+1).getValue()/10 != -1){
					return false;
				}
			}
		}

		return true;

	}

	// Updates the turn after a player makes a move.
	// Also updates variables for starting a new round
	private void updateTurn(){
		turn = (turn+1) % players;
		while(skipped.get(turn)){
			turn = (turn+1) % players;
		}

		int everyone = 0;
		for(int i = 0; i < skipped.size(); i++){
			if(!skipped.get(i)){
				everyone++;
			}
		}

		if(everyone == 1){
			startRound = true;
			for(int i = 0; i < skipped.size(); i++){
				skipped.set(i, false);
			}
			startRound = true;
			discardPile.clear();
		} else {
			startRound = false;
		}
		begin = false;
		reprint = true;
	}


	// Sorts the hands
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

	// Prints the hands to everyone in the game
	private void printHands() throws IOException{
		//writeAll("\033[H\033[2J");
		writeAll("--------------------------");
		for(int i = 0; i < hands.size(); i++){
			String hand = "Hand: \n";
			for(int j = 0; j < hands.get(i).size(); j++){
				hand += "[" + j + "] " + hands.get(i).get(j).toString() + "\n";
			}
			hand += "[99] Skip this round\n";
			outputList.get(i).writeUTF(hand);
		}
		writeAll("--------------------------");
	}

	// Writes to everyone in the game
	private void writeAll(String str) throws IOException{
		for(int i = 0; i < outputList.size(); i++){
			outputList.get(i).writeUTF(str);
		}
	}
	
	// Getting usernames
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

	protected void changeBarrier (CyclicBarrier barrier){
		this.barrier = barrier;
	}






	private class StartThread implements Runnable{
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
					changeBarrier(new CyclicBarrier(maxPlayers));
					
				} catch (Exception e){
	
				}
			}
	
			try{
				out.writeUTF("Waiting on other players...");
			while(players < maxPlayers){ 
				System.out.print("");
			}
	
			} catch(Exception e) {
	
			}
		}
	
	}
	
	
} // End Client Thread

