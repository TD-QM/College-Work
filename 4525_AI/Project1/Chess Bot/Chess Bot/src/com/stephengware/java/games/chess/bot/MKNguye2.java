package com.stephengware.java.games.chess.bot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.stephengware.java.games.chess.bot.Bot;
import com.stephengware.java.games.chess.state.Bishop;
import com.stephengware.java.games.chess.state.King;
import com.stephengware.java.games.chess.state.Knight;
import com.stephengware.java.games.chess.state.Pawn;
import com.stephengware.java.games.chess.state.Piece;
import com.stephengware.java.games.chess.state.Player;
import com.stephengware.java.games.chess.state.Queen;
import com.stephengware.java.games.chess.state.Rook;
import com.stephengware.java.games.chess.state.State;

/**
 * A chess bot which selects its next move at random.
 * 
 * @author Stephen G. Ware
 */

/*
 * TO DO:
 * 		Implement the "guard score" that represents how many pieces a piece is guarding
 * 			Guard score increases depending on who is defending is whom is being defended
 * 		Implement the "pawn structure score" that represents how good your pawn structure is
 * 			Want chains of pawns covering each other instead of being side-to-side
 * 			May be implemented in the guard score
 * 		Implement the "king safety score" that represents how safe the king is
 * 			Maybe look for checks? Could be implemented in conjunction with the check score and the space control score
 * 		Implement the "attack score" that represents how well you can attack enemy pieces
 * 			Look at attackers and defenders of the square to see if you can take while maintaining a good material score (among also keeping other factors in mind)
 * 		
 * More Difficult TO DO:
 * 		Implement "in-between moves"
 * 			In-betweens are moves that are done in-between the main moves that you want to make. These are usually facilitated to better your position during an attack.
 * 			Can happen through checks or more prevalent threats (Ex: attack on a queen)
 */
public class MKNguye2 extends Bot {

	/** A random number generator */
	private final Random random;

	/**
	 * Constructs a new chess bot named "My Chess Bot" and whose random number
	 * generator (see {@link java.util.Random}) begins with a seed of 0.
	 */
	public MKNguye2() {
		super("My Chess Bot");
		this.random = new Random(0);
	}

	@Override
	protected State chooseMove(State root) {
		// This list will hold all the children nodes of the root.
		//ArrayList<State> children = new ArrayList<>();
		// Generate all the children nodes of the root (that is, all the
		// possible next states of the game. Make sure that we do not exceed
		// the number of GameTree nodes that we are allowed to generate.
		//Iterator<State> iterator = root.next().iterator();
		
		if (root.turn == 0 && root.player == Player.WHITE) {
			return root.next(new Pawn(Player.WHITE, 4, 1), new Pawn(Player.WHITE, 4, 3));
		} else if (root.turn == 1 && root.player == Player.WHITE) {
			return root.next(new Knight(Player.WHITE, 1, 0), new Knight(Player.WHITE, 2, 2));
		} else if (root.turn == 0 && root.player == Player.BLACK) {
			return root.next(new Pawn(Player.BLACK, 3, 6), new Pawn(Player.BLACK, 3, 4));
		}
//		} else if (root.turn == 0 && root.player == Player.BLACK) {
//			return root.next(new Pawn(Player.BLACK, 2, 6), new Pawn(Player.BLACK, 2, 5));
//		} else if (root.turn == 1 && root.player == Player.BLACK) {
//			return root.next(new Pawn(Player.BLACK, 3, 6), new Pawn(Player.BLACK, 3, 4));
//		}
		
		State move = minimax(root, root, 3);
		//System.out.println("move.toString(): " + move.toString());
		return move;

//		while(!root.searchLimitReached() && iterator.hasNext()) {
//			
//			State testState = iterator.next();
//			double testEval = eval(testState);
//			
//			if(testEval > bestEval) {
//				bestEval = testEval;
//				children.clear();
//				children.add(testState);
//			} else if (testEval == bestEval) {
//				children.add(testState);
//			}
//			
//		}
		// Choose one of the children at random.
		// return children.get(random.nextInt(children.size()));

	}

	
	
	
	
	
	
	
	
	
	
	protected State minimax(State testedState, State root, int ply) {
		State best = (State) max(testedState, root, ply, -999999, 999999)[0];
		return best;
	}

	
	// Returns both the max child state and the the max eval value of a given set of states
	// Note the returning of an Object array. This is because java doesn't support returning multiple values
	// 		and we need both the State (for minimax) and the score (for min/max recursion)
	protected Object[] max(State testedState, State root, int ply, double a, double b) {
		Object[] returnVal = new Object[2];

		if (ply == 0) {
			returnVal[0] = testedState;
			returnVal[1] = eval(testedState, root);
			return returnVal;
		}

		State bestState = null;
		double best = -9999;

		Iterator<State> iterator = testedState.next().iterator();
		while (iterator.hasNext()) {
			State child = iterator.next();
			double childVal = (Double) min(child, root, ply-1, a, b)[1];
			if (childVal > best) {
				bestState = child;
				best = childVal;
			}
			
			if(best >= b) {
				returnVal[0] = bestState;
				returnVal[1] = best;
				return returnVal; 
			}
			
			if(a >= best) {
				a = a; 
			} else {
				a = best;
			}
			
		}

		returnVal[0] = bestState;
		returnVal[1] = best;
		return returnVal;

	}

	// Returns both the min child state and the min eval value of a given set of states
	protected Object[] min(State testedState, State root, int ply, double a, double b) {
		Object[] returnVal = new Object[2];

		if (ply == 0) {
			returnVal[0] = testedState;
			returnVal[1] = eval(testedState, root);
			return returnVal;
		}

		State bestState = null;
		double best = 9999;

		Iterator<State> iterator = testedState.next().iterator();
		while (iterator.hasNext()) {
			State child = iterator.next();
			double childVal = (Double) max(child, root, ply - 1, a, b)[1];
			if (childVal < best) {
				bestState = child;
				best = childVal;
			}
			
			if(best <= a) {
				returnVal[0] = bestState;
				returnVal[1] = best;
				return returnVal;
			}
			
			if(b <= best) {
				b = b; 
			} else {
				b = best;
			}
			
		}

		returnVal[0] = bestState;
		returnVal[1] = best;
		return returnVal;

	}

	
	
	// Returns an evaluation score for a given state
	//		Root is passed as an argument to get the bot's player color
	protected double eval(State testedState, State root) {
		double evaluation = 0.0;

		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (testedState.board.pieceAt(row, col)) {

					Piece testedPiece = testedState.board.getPieceAt(row, col);

					// Material Score Calc
					evaluation += materialScore(testedState, testedPiece, root);
					// End Material Score Calc
					
					
					// Development Calc
					evaluation += developmentScore(testedState, testedPiece, root);
					// End Development Calc
					
					
					// Doubled Pawns Calc
					if(testedPiece instanceof Pawn) {
						if(testedState.board.pieceAt(row, col, root.player)) {
							
						}
					}
					// End Doubled Pawns Calc
					
					
					// Castling Calc (King Safety)
					// End King Safety Calc
					
					
					// Board/Space Control Calc
					evaluation += boardControl(testedState, testedPiece, root);
					// End Board Control Calc
					
					
					// Piece Guarding Calc
					// End Guarding Calc
					
					
					// Piece Attack Calc
					// End Attack Calc
					
					
					// Check Calc
					if(testedPiece instanceof King && testedState.check) {
						if(testedPiece.player == root.player && testedState.player == root.player) {
							evaluation -= 2;
						} else if(testedPiece.player == root.player && testedState.player != root.player) {
							evaluation += 2;
						}
					}
					// End Check Calc
					
					
					// Checkmate Calc
					if(testedPiece instanceof King && testedState.over) {
						if(testedPiece.player == root.player && testedState.player == root.player) {
							evaluation = -999999;
							break;
						} else if(testedPiece.player == root.player && testedState.player != root.player) {
							evaluation = 999999;
							break;
						}
					}
					// End Checkmate Calc
					
					
				}
			}
		}

		return evaluation;
	}
	
	
	private double materialScore(State testedState, Piece testedPiece, State root) {
		double materialScore = 0;
		double evaluation = 0.0;
		if (testedPiece instanceof Queen) {
			materialScore = 10;
		} else if (testedPiece instanceof Rook) {
			materialScore = 5;
		} else if (testedPiece instanceof Knight) {
			materialScore = 3;
		} else if (testedPiece instanceof Bishop) {
			materialScore = 3;
		} else if (testedPiece instanceof Pawn) {
			materialScore = 1;
		}
		if (testedPiece.player == root.player) {
			evaluation += materialScore;
		} else {
			evaluation -= materialScore;
		}
		return evaluation;
	}
	
	private double developmentScore(State testedState, Piece testedPiece, State root) {
		double developmentScore = 0.0;
		double evaluation = 0.0;
		if(testedState.board.hasMoved(testedPiece)) {
			if (testedPiece instanceof Queen) {
				developmentScore = 15;
			} else if (testedPiece instanceof Rook) {
				developmentScore = 8;
			} else if (testedPiece instanceof Knight) {
				developmentScore = 10;
			} else if (testedPiece instanceof Bishop) {
				developmentScore = 10;
			} else if (testedPiece instanceof Pawn) {
				developmentScore = 3;
			}
		}
		if (testedPiece.player == root.player) {
			evaluation += developmentScore;
		}
		return evaluation;
	}
	
	
	private double guardScore(State testedState, Piece testedPiece, State root) {
		double spaceControl = 0.0;
		double evaluation = 0.0;
		boolean me = (testedState.player == root.player);
		if (testedPiece instanceof Queen) {
			for(int i = testedPiece.file-1; i >= 0; i--) { // Left Row
				spaceControl += .01;
				if(testedState.board.pieceAt(i, testedPiece.rank) && testedState.board.getPieceAt(i, testedPiece.rank).player == testedState.player) {
					break;
				}
			}
			for(int i = testedPiece.file+1; i <= 7; i++) { // Right Row
				spaceControl += .01;
				if(testedState.board.pieceAt(i, testedPiece.rank)) {
					break;
				}
			}
			for(int i = testedPiece.rank-1; i >= 0; i--) { // Top Col
				spaceControl += .01;
				if(testedState.board.pieceAt(testedPiece.file, i)) {
					break;
				}
			}
			for(int i = testedPiece.rank+1; i <= 7; i++) { // Bot Col
				spaceControl += .01;
				if(testedState.board.pieceAt(testedPiece.file, i)) {
					break;
				}
			}
			
			for(int i = testedPiece.file-1; i >= 0; i--) {
				spaceControl += .01;
				if(testedState.board.pieceAt(i, i)) {
					break;
				}
			}
			for(int i = testedPiece.file+1; i <= 7; i++) {
				spaceControl += .01;
				if(testedState.board.pieceAt(i, i)) {
					break;
				}
			}
			for(int i = testedPiece.file-1; i >= 0; i--) {
				spaceControl += .01;
				if(testedState.board.pieceAt(i, 8-i)) {
					break;
				}
			}
			for(int i = testedPiece.file+1; i <= 7; i++) {
				spaceControl += .01;
				if(testedState.board.pieceAt(i, 8-i)) {
					break;
				}
			}
		} else if (testedPiece instanceof Rook) {
			for(int i = testedPiece.file-1; i >= 0; i--) {
				spaceControl += .01;
				if(testedState.board.pieceAt(i, testedPiece.rank)) {
					break;
				}
			}
			for(int i = testedPiece.file+1; i <= 7; i++) {
				spaceControl += .01;
				if(testedState.board.pieceAt(i, testedPiece.rank)) {
					break;
				}
			}
			for(int i = testedPiece.rank-1; i >= 0; i--) {
				spaceControl += .01;
				if(testedState.board.pieceAt(testedPiece.file, i)) {
					break;
				}
			}
			for(int i = testedPiece.rank+1; i <= 7; i++) {
				spaceControl += .01;
				if(testedState.board.pieceAt(testedPiece.file, i)) {
					break;
				}
			}
		} else if (testedPiece instanceof Knight) {
			if(testedState.board.pieceAt(testedPiece.file+1, testedPiece.rank+2)) {
				spaceControl += .01;
			}
			if(testedState.board.pieceAt(testedPiece.file+1, testedPiece.rank-2)) {
				spaceControl += .01;
			}
			if(testedState.board.pieceAt(testedPiece.file+2, testedPiece.rank+1)) {
				spaceControl += .01;
			}
			if(testedState.board.pieceAt(testedPiece.file+2, testedPiece.rank-1)) {
				spaceControl += .01;
			}
			if(testedState.board.pieceAt(testedPiece.file-1, testedPiece.rank+2)) {
				spaceControl += .01;
			}
			if(testedState.board.pieceAt(testedPiece.file-1, testedPiece.rank-2)) {
				spaceControl += .01;
			}
			if(testedState.board.pieceAt(testedPiece.file-2, testedPiece.rank+1)) {
				spaceControl += .01;
			}
			if(testedState.board.pieceAt(testedPiece.file-2, testedPiece.rank-1)) {
				spaceControl += .01;
			}
		} else if (testedPiece instanceof Bishop) {
			for(int i = testedPiece.file-1; i >= 0; i--) {
				spaceControl += .01;
				if(testedState.board.pieceAt(i, i)) {
					break;
				}
			}
			for(int i = testedPiece.file+1; i <= 7; i++) {
				spaceControl += .01;
				if(testedState.board.pieceAt(i, i)) {
					break;
				}
			}
			for(int i = testedPiece.file-1; i >= 0; i--) {
				spaceControl += .01;
				if(testedState.board.pieceAt(i, 8-i)) {
					break;
				}
			}
			for(int i = testedPiece.file+1; i <= 7; i++) {
				spaceControl += .01;
				if(testedState.board.pieceAt(i, 8-i)) {
					break;
				}
			}
		}
	}
	
	private double boardControl(State testedState, Piece testedPiece, State root) {
		double spaceControl = 0.0;
		double evaluation = 0.0;
		if (testedPiece instanceof Queen) {
			for(int i = testedPiece.file-1; i >= 0; i--) { // Left Row
				spaceControl += .01;
				if(testedState.board.pieceAt(i, testedPiece.rank)) {
					break;
				}
			}
			for(int i = testedPiece.file+1; i <= 7; i++) { // Right Row
				spaceControl += .01;
				if(testedState.board.pieceAt(i, testedPiece.rank)) {
					break;
				}
			}
			for(int i = testedPiece.rank-1; i >= 0; i--) { // Top Col
				spaceControl += .01;
				if(testedState.board.pieceAt(testedPiece.file, i)) {
					break;
				}
			}
			for(int i = testedPiece.rank+1; i <= 7; i++) { // Bot Col
				spaceControl += .01;
				if(testedState.board.pieceAt(testedPiece.file, i)) {
					break;
				}
			}
			
			for(int i = testedPiece.file-1; i >= 0; i--) {
				spaceControl += .01;
				if(testedState.board.pieceAt(i, i)) {
					break;
				}
			}
			for(int i = testedPiece.file+1; i <= 7; i++) {
				spaceControl += .01;
				if(testedState.board.pieceAt(i, i)) {
					break;
				}
			}
			for(int i = testedPiece.file-1; i >= 0; i--) {
				spaceControl += .01;
				if(testedState.board.pieceAt(i, 8-i)) {
					break;
				}
			}
			for(int i = testedPiece.file+1; i <= 7; i++) {
				spaceControl += .01;
				if(testedState.board.pieceAt(i, 8-i)) {
					break;
				}
			}
		} else if (testedPiece instanceof Rook) {
			for(int i = testedPiece.file-1; i >= 0; i--) {
				spaceControl += .01;
				if(testedState.board.pieceAt(i, testedPiece.rank)) {
					break;
				}
			}
			for(int i = testedPiece.file+1; i <= 7; i++) {
				spaceControl += .01;
				if(testedState.board.pieceAt(i, testedPiece.rank)) {
					break;
				}
			}
			for(int i = testedPiece.rank-1; i >= 0; i--) {
				spaceControl += .01;
				if(testedState.board.pieceAt(testedPiece.file, i)) {
					break;
				}
			}
			for(int i = testedPiece.rank+1; i <= 7; i++) {
				spaceControl += .01;
				if(testedState.board.pieceAt(testedPiece.file, i)) {
					break;
				}
			}
		} else if (testedPiece instanceof Knight) {
			if(testedState.board.pieceAt(testedPiece.file+1, testedPiece.rank+2)) {
				spaceControl += .01;
			}
			if(testedState.board.pieceAt(testedPiece.file+1, testedPiece.rank-2)) {
				spaceControl += .01;
			}
			if(testedState.board.pieceAt(testedPiece.file+2, testedPiece.rank+1)) {
				spaceControl += .01;
			}
			if(testedState.board.pieceAt(testedPiece.file+2, testedPiece.rank-1)) {
				spaceControl += .01;
			}
			if(testedState.board.pieceAt(testedPiece.file-1, testedPiece.rank+2)) {
				spaceControl += .01;
			}
			if(testedState.board.pieceAt(testedPiece.file-1, testedPiece.rank-2)) {
				spaceControl += .01;
			}
			if(testedState.board.pieceAt(testedPiece.file-2, testedPiece.rank+1)) {
				spaceControl += .01;
			}
			if(testedState.board.pieceAt(testedPiece.file-2, testedPiece.rank-1)) {
				spaceControl += .01;
			}
		} else if (testedPiece instanceof Bishop) {
			for(int i = testedPiece.file-1; i >= 0; i--) {
				spaceControl += .01;
				if(testedState.board.pieceAt(i, i)) {
					break;
				}
			}
			for(int i = testedPiece.file+1; i <= 7; i++) {
				spaceControl += .01;
				if(testedState.board.pieceAt(i, i)) {
					break;
				}
			}
			for(int i = testedPiece.file-1; i >= 0; i--) {
				spaceControl += .01;
				if(testedState.board.pieceAt(i, 8-i)) {
					break;
				}
			}
			for(int i = testedPiece.file+1; i <= 7; i++) {
				spaceControl += .01;
				if(testedState.board.pieceAt(i, 8-i)) {
					break;
				}
			}
		}
		
		if (testedPiece.player == root.player) {
			evaluation += spaceControl;
//		} else {
//			evaluation -= spaceControl;
		}
		
		return evaluation;
	}
}
