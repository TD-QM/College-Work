package com.stephengware.java.games.chess.bot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.stephengware.java.games.chess.bot.Bot;
import com.stephengware.java.games.chess.state.Bishop;
import com.stephengware.java.games.chess.state.Board;
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
 * 			Also don't want doubled panws (a pawn in front of another pawn)
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
 * 		Implement a "salvage move"
 * 			Salvage move happens when you're in an entire lost position, but in order to salvage as many points as possible, you go for draws
 * 			This would be done either through draws by repetition or through stalemating
 * 		Implement a way to look at "pins"
 * 			Pins are when pieces are effectively locked at their current position because they're guarding a more precious piece (king, queen, even rooks)
 * 		Implement a colored square strength/weakness for the bishops
 * 			This is usually determined by how your pawn structure is. If most pawns are on dark squares, then you're dark squared bishop isn't as strong since it's easily blocked by the pawns.
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
		
		Object[] minimaxReturn = minimax(root, root, 4);
		System.out.println("Turn #" + root.turn + ": " + (double)minimaxReturn[1]);
		if(minimaxReturn[1] != null) {
			return (State)minimaxReturn[0];
		} else {
			return root.next().iterator().next();
		}
			
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

	
	
	
	
	
	
	
	
	
	
	protected Object[] minimax(State testedState, State root, int ply) {
		Object[] returnVal = max(testedState, root, ply, -999999, 999999);
		return returnVal;
	}

	
	// Returns both the max child state and the the max eval value of a given set of states
	// Note the returning of an Object array. This is because java doesn't support returning multiple values
	// 		and we need both the State (for minimax) and the score (for min/max recursion)
	protected Object[] max(State testedState, State root, int ply, double a, double b) {
		Object[] returnVal = new Object[2];
		
//		if(limit > 499000) {
//			return returnVal;
//		}

		if (ply == 0 || testedState.over) {
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
			
			if(best > b) {
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

		if (ply == 0 || testedState.over) {
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
			
			if(best < a) {
				returnVal[0] = bestState;
				returnVal[1] = best;
				return returnVal;
			}
			
			if(b < best) {
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
					evaluation += guardScore(testedState, testedPiece, root);
					// End Guarding Calc
					
					
					// Piece Attack Calc
					evaluation += atkScore(testedState, testedPiece, root);
					// End Attack Calc
					
					
					// Check Calc
					if(testedPiece instanceof King && testedState.check) {
						if(testedPiece.player == root.player && testedState.player == root.player) {
							evaluation -= .1;
						} else if(testedPiece.player == root.player && testedState.player != root.player) {
							evaluation += .1;
						}
					}
					// End Check Calc
					
					
				}
			}
		}
		
		// Checkmate Calc
		if(testedState.over && testedState.check && testedState.movesUntilDraw > 10) {
			if(testedState.player == root.player) {
				evaluation -= 999;
			} else if(testedState.player != root.player) {
				evaluation += 999 - testedState.turn;
			}
		}
		// End Checkmate Calc
		
		
		
		return evaluation;
	}
	
	
	private double materialScore(State testedState, Piece testedPiece, State root) {
		double materialScore = 0;
		double evaluation = 0.0;
		if (testedPiece instanceof Queen) {
			materialScore = 15;
		} else if (testedPiece instanceof Rook) {
			materialScore = 7.5;
		} else if (testedPiece instanceof Knight) {
			materialScore = 3;
		} else if (testedPiece instanceof Bishop) {
			materialScore = 3;
		} else if (testedPiece instanceof Pawn) {
			materialScore = .9;
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
				developmentScore = 5;
			} else if (testedPiece instanceof Rook) {
				developmentScore = 2.5;
			} else if (testedPiece instanceof Knight) {
				developmentScore = 1.5;
			} else if (testedPiece instanceof Bishop) {
				developmentScore = 1.5;
			} else if (testedPiece instanceof Pawn) {
				developmentScore = .5;
			}
		}
		if (testedPiece.player == root.player) {
			evaluation += developmentScore;
		} else {
			evaluation -= developmentScore;
		}
		
		return evaluation;
	}
	
	
	private double guardScore(State testedState, Piece testedPiece, State root) {
		double guardScore = 0.0;
		double evaluation = 0.0;
		boolean me = (testedState.player == root.player);
		if (testedPiece instanceof Queen) {
			for(int i = testedPiece.file-1; i >= 0; i--) { // Left Row
				if(testedState.board.pieceAt(i, testedPiece.rank) && testedState.board.getPieceAt(i, testedPiece.rank).player == testedState.player) {
					guardScore += guardPieceCalc(testedPiece, testedState.board.getPieceAt(i, testedPiece.rank));
					break;
				}
			}
			for(int i = testedPiece.file+1; i <= 7; i++) { // Right Row
				if(testedState.board.pieceAt(i, testedPiece.rank) && testedState.board.getPieceAt(i, testedPiece.rank).player == testedState.player) {
					guardScore += guardPieceCalc(testedPiece, testedState.board.getPieceAt(i, testedPiece.rank));
					break;
				}
			}
			for(int i = testedPiece.rank-1; i >= 0; i--) { // Top Col
				if(testedState.board.pieceAt(testedPiece.file, i) && testedState.board.getPieceAt(testedPiece.file, i).player == testedState.player) {
					guardScore += guardPieceCalc(testedPiece, testedState.board.getPieceAt(testedPiece.file, i));
					break;
				}
			}
			for(int i = testedPiece.rank+1; i <= 7; i++) { // Bot Col
				if(testedState.board.pieceAt(testedPiece.file, i) && testedState.board.getPieceAt(testedPiece.file, i).player == testedState.player) {
					guardScore += guardPieceCalc(testedPiece, testedState.board.getPieceAt(testedPiece.file, i));
					break;
				}
			}
			
			for(int i = testedPiece.file-1; i >= 0; i--) {
				if(testedState.board.pieceAt(i, i) && testedState.board.getPieceAt(i, i).player == testedState.player) {
					guardScore += guardPieceCalc(testedPiece, testedState.board.getPieceAt(i, i));
					break;
				}
			}
			for(int i = testedPiece.file+1; i <= 7; i++) {
				if(testedState.board.pieceAt(i, i) && testedState.board.getPieceAt(i, i).player == testedState.player) {
					guardScore += guardPieceCalc(testedPiece, testedState.board.getPieceAt(i, i));
					break;
				}
			}
			for(int i = testedPiece.file-1; i >= 0; i--) {
				if(testedState.board.pieceAt(i, 8-i) && testedState.board.getPieceAt(i, 8-i).player == testedState.player) {
					guardScore += guardPieceCalc(testedPiece, testedState.board.getPieceAt(i, 8-i));
					break;
				}
			}
			for(int i = testedPiece.file+1; i <= 7; i++) {
				if(testedState.board.pieceAt(i, 8-i) && testedState.board.getPieceAt(i, 8-i).player == testedState.player) {
					guardScore += guardPieceCalc(testedPiece, testedState.board.getPieceAt(i, 8-i));
					break;
				}
			}
		} else if (testedPiece instanceof Rook) {
			for(int i = testedPiece.file-1; i >= 0; i--) {
				if(testedState.board.pieceAt(i, i) && testedState.board.getPieceAt(i, i).player == testedState.player) {
					guardScore += guardPieceCalc(testedPiece, testedState.board.getPieceAt(i, i));
					break;
				}
			}
			for(int i = testedPiece.file+1; i <= 7; i++) {
				if(testedState.board.pieceAt(i, i) && testedState.board.getPieceAt(i, i).player == testedState.player) {
					guardScore += guardPieceCalc(testedPiece, testedState.board.getPieceAt(i, i));
					break;
				}
			}
			for(int i = testedPiece.file-1; i >= 0; i--) {
				if(testedState.board.pieceAt(i, 8-i) && testedState.board.getPieceAt(i, 8-i).player == testedState.player) {
					guardScore += guardPieceCalc(testedPiece, testedState.board.getPieceAt(i, 8-i));
					break;
				}
			}
			for(int i = testedPiece.file+1; i <= 7; i++) {
				if(testedState.board.pieceAt(i, 8-i) && testedState.board.getPieceAt(i, 8-i).player == testedState.player) {
					guardScore += guardPieceCalc(testedPiece, testedState.board.getPieceAt(i, 8-i));
					break;
				}
			}
		} else if (testedPiece instanceof Knight) {
			if(testedState.board.pieceAt(testedPiece.file+1, testedPiece.rank+2) && testedState.board.getPieceAt(testedPiece.file+1, testedPiece.rank+2).player == testedState.player) {
				guardScore += guardPieceCalc(testedPiece, testedState.board.getPieceAt(testedPiece.file+1, testedPiece.rank+2));
			}
			if(testedState.board.pieceAt(testedPiece.file+1, testedPiece.rank-2) && testedState.board.getPieceAt(testedPiece.file+1, testedPiece.rank-2).player == testedState.player) {
				guardScore += guardPieceCalc(testedPiece, testedState.board.getPieceAt(testedPiece.file+1, testedPiece.rank-2));
			}
			if(testedState.board.pieceAt(testedPiece.file+2, testedPiece.rank+1) && testedState.board.getPieceAt(testedPiece.file+2, testedPiece.rank+1).player == testedState.player) {
				guardScore += guardPieceCalc(testedPiece, testedState.board.getPieceAt(testedPiece.file+2, testedPiece.rank+1));
			}
			if(testedState.board.pieceAt(testedPiece.file+2, testedPiece.rank-1) && testedState.board.getPieceAt(testedPiece.file+2, testedPiece.rank-1).player == testedState.player) {
				guardScore += guardPieceCalc(testedPiece, testedState.board.getPieceAt(testedPiece.file+2, testedPiece.rank-1));
			}
			if(testedState.board.pieceAt(testedPiece.file-1, testedPiece.rank+2) && testedState.board.getPieceAt(testedPiece.file-1, testedPiece.rank+2).player == testedState.player) {
				guardScore += guardPieceCalc(testedPiece, testedState.board.getPieceAt(testedPiece.file-1, testedPiece.rank+2));
			}
			if(testedState.board.pieceAt(testedPiece.file-1, testedPiece.rank-2) && testedState.board.getPieceAt(testedPiece.file-1, testedPiece.rank-2).player == testedState.player) {
				guardScore += guardPieceCalc(testedPiece, testedState.board.getPieceAt(testedPiece.file-1, testedPiece.rank-2));
			}
			if(testedState.board.pieceAt(testedPiece.file-2, testedPiece.rank+1) && testedState.board.getPieceAt(testedPiece.file-2, testedPiece.rank+1).player == testedState.player) {
				guardScore += guardPieceCalc(testedPiece, testedState.board.getPieceAt(testedPiece.file-2, testedPiece.rank+1));
			}
			if(testedState.board.pieceAt(testedPiece.file-2, testedPiece.rank-1) && testedState.board.getPieceAt(testedPiece.file-2, testedPiece.rank-1).player == testedState.player) {
				guardScore += guardPieceCalc(testedPiece, testedState.board.getPieceAt(testedPiece.file-2, testedPiece.rank-1));
			}
		} else if (testedPiece instanceof Bishop) {
			for(int i = testedPiece.file-1; i >= 0; i--) {
				if(testedState.board.pieceAt(i, i) && testedState.board.getPieceAt(i, i).player == testedState.player) {
					guardScore += guardPieceCalc(testedPiece, testedState.board.getPieceAt(i, i));
					break;
				}
			}
			for(int i = testedPiece.file+1; i <= 7; i++) {
				if(testedState.board.pieceAt(i, i) && testedState.board.getPieceAt(i, i).player == testedState.player) {
					guardScore += guardPieceCalc(testedPiece, testedState.board.getPieceAt(i, i));
					break;
				}
			}
			for(int i = testedPiece.file-1; i >= 0; i--) {
				if(testedState.board.pieceAt(i, 8-i) && testedState.board.getPieceAt(i, 8-i).player == testedState.player) {
					guardScore += guardPieceCalc(testedPiece, testedState.board.getPieceAt(i, 8-i));
					break;
				}
			}
			for(int i = testedPiece.file+1; i <= 7; i++) {
				if(testedState.board.pieceAt(i, 8-i) && testedState.board.getPieceAt(i, 8-i).player == testedState.player) {
					guardScore += guardPieceCalc(testedPiece, testedState.board.getPieceAt(i, 8-i));
					break;
				}
			}
		} else if (testedPiece instanceof Pawn) {
			if(testedState.board.pieceAt(testedPiece.file-1, testedPiece.rank+1) && testedState.board.getPieceAt(testedPiece.file-1, testedPiece.rank+1).player == testedState.player) {
				guardScore += guardPieceCalc(testedPiece, testedState.board.getPieceAt(testedPiece.file-1, testedPiece.rank+1));
			}
			if(testedState.board.pieceAt(testedPiece.file+1, testedPiece.rank+1) && testedState.board.getPieceAt(testedPiece.file+1, testedPiece.rank+1).player == testedState.player) {
				guardScore += guardPieceCalc(testedPiece, testedState.board.getPieceAt(testedPiece.file+1, testedPiece.rank+1));
			}
		}
		
		if (testedPiece.player == root.player) {
			evaluation += guardScore;
		} else {
			evaluation -= guardScore;
		}
		
		return evaluation;
	}
	
	private double guardPieceCalc(Piece guard, Piece guarded) {
		if(guard instanceof King) {
			if(guarded instanceof Queen) {
				return .005;
			} else if(guarded instanceof Rook) {
				return .005;
			} else if(guarded instanceof Knight) {
				return .005;
			} else if(guarded instanceof Bishop) {
				return .005;
			} else { // Pawn
				return .005;
			}
		} else if(guard instanceof Queen) {
			if(guarded instanceof Queen) {
				return .1;
			} else if(guarded instanceof Rook) {
				return .04;
			} else if(guarded instanceof Knight) {
				return .04;
			} else if(guarded instanceof Bishop) {
				return .04;
			} else { // Pawn
				return .005;
			}
		} else if(guard instanceof Rook) {
			if(guarded instanceof Queen) {
				return .1;
			} else if(guarded instanceof Rook) {
				return .08;
			} else if(guarded instanceof Knight) {
				return .05;
			} else if(guarded instanceof Bishop) {
				return .05;
			} else { // Pawn
				return .005;
			}
		} else if(guard instanceof Knight) {
			if(guarded instanceof Queen) {
				return .1;
			} else if(guarded instanceof Rook) {
				return .06;
			} else if(guarded instanceof Knight) {
				return .06;
			} else if(guarded instanceof Bishop) {
				return .06;
			} else { // Pawn
				return .01;
			}
		} else if(guard instanceof Bishop) {
			if(guarded instanceof Queen) {
				return .1;
			} else if(guarded instanceof Rook) {
				return .04;
			} else if(guarded instanceof Knight) {
				return .04;
			} else if(guarded instanceof Bishop) {
				return .001;
			} else { // Pawn
				return .0005;
			}
		} else { // Pawn
			if(guarded instanceof Queen) {
				return .0001;
			} else if(guarded instanceof Rook) {
				return .025;
			} else if(guarded instanceof Knight) {
				return .05;
			} else if(guarded instanceof Bishop) {
				return .05;
			} else { // Pawn
				return .1;
			}
		}
	}
	
	
	
	
	/*
	 * NOT FINISHED
	 */
	private double atkScore(State testedState, Piece testedPiece, State root) {
		double atkScore = 0.0;
		double evaluation = 0.0;
		boolean me = (testedState.player == root.player);
		if (testedPiece instanceof Queen) {
			for(int i = testedPiece.file-1; i >= 0; i--) { // Left Row
				if(testedState.board.pieceAt(i, testedPiece.rank) && testedState.board.getPieceAt(i, testedPiece.rank).player != testedState.player) {
					atkScore += atkPieceCalc(testedPiece, testedState.board.getPieceAt(i, testedPiece.rank));
					break;
				}
			}
			for(int i = testedPiece.file+1; i <= 7; i++) { // Right Row
				if(testedState.board.pieceAt(i, testedPiece.rank) && testedState.board.getPieceAt(i, testedPiece.rank).player != testedState.player) {
					atkScore += atkPieceCalc(testedPiece, testedState.board.getPieceAt(i, testedPiece.rank));
					break;
				}
			}
			for(int i = testedPiece.rank-1; i >= 0; i--) { // Top Col
				if(testedState.board.pieceAt(testedPiece.file, i) && testedState.board.getPieceAt(testedPiece.file, i).player != testedState.player) {
					atkScore += atkPieceCalc(testedPiece, testedState.board.getPieceAt(testedPiece.file, i));
					break;
				}
			}
			for(int i = testedPiece.rank+1; i <= 7; i++) { // Bot Col
				if(testedState.board.pieceAt(testedPiece.file, i) && testedState.board.getPieceAt(testedPiece.file, i).player != testedState.player) {
					atkScore += atkPieceCalc(testedPiece, testedState.board.getPieceAt(testedPiece.file, i));
					break;
				}
			}
			
			for(int i = testedPiece.file-1; i >= 0; i--) {
				if(testedState.board.pieceAt(i, i) && testedState.board.getPieceAt(i, i).player != testedState.player) {
					atkScore += atkPieceCalc(testedPiece, testedState.board.getPieceAt(i, i));
					break;
				}
			}
			for(int i = testedPiece.file+1; i <= 7; i++) {
				if(testedState.board.pieceAt(i, i) && testedState.board.getPieceAt(i, i).player == testedState.player) {
					atkScore += atkPieceCalc(testedPiece, testedState.board.getPieceAt(i, i));
					break;
				}
			}
			for(int i = testedPiece.file-1; i >= 0; i--) {
				if(testedState.board.pieceAt(i, 8-i) && testedState.board.getPieceAt(i, 8-i).player == testedState.player) {
					atkScore += atkPieceCalc(testedPiece, testedState.board.getPieceAt(i, 8-i));
					break;
				}
			}
			for(int i = testedPiece.file+1; i <= 7; i++) {
				if(testedState.board.pieceAt(i, 8-i) && testedState.board.getPieceAt(i, 8-i).player == testedState.player) {
					atkScore += atkPieceCalc(testedPiece, testedState.board.getPieceAt(i, 8-i));
					break;
				}
			}
		} else if (testedPiece instanceof Rook) {
			for(int i = testedPiece.file-1; i >= 0; i--) {
				if(testedState.board.pieceAt(i, i) && testedState.board.getPieceAt(i, i).player == testedState.player) {
					atkScore += atkPieceCalc(testedPiece, testedState.board.getPieceAt(i, i));
					break;
				}
			}
			for(int i = testedPiece.file+1; i <= 7; i++) {
				if(testedState.board.pieceAt(i, i) && testedState.board.getPieceAt(i, i).player == testedState.player) {
					atkScore += atkPieceCalc(testedPiece, testedState.board.getPieceAt(i, i));
					break;
				}
			}
			for(int i = testedPiece.file-1; i >= 0; i--) {
				if(testedState.board.pieceAt(i, 8-i) && testedState.board.getPieceAt(i, 8-i).player == testedState.player) {
					atkScore += atkPieceCalc(testedPiece, testedState.board.getPieceAt(i, 8-i));
					break;
				}
			}
			for(int i = testedPiece.file+1; i <= 7; i++) {
				if(testedState.board.pieceAt(i, 8-i) && testedState.board.getPieceAt(i, 8-i).player == testedState.player) {
					atkScore += atkPieceCalc(testedPiece, testedState.board.getPieceAt(i, 8-i));
					break;
				}
			}
		} else if (testedPiece instanceof Knight) {
			if(testedState.board.pieceAt(testedPiece.file+1, testedPiece.rank+2) && testedState.board.getPieceAt(testedPiece.file+1, testedPiece.rank+2).player == testedState.player) {
				atkScore += atkPieceCalc(testedPiece, testedState.board.getPieceAt(testedPiece.file+1, testedPiece.rank+2));
			}
			if(testedState.board.pieceAt(testedPiece.file+1, testedPiece.rank-2) && testedState.board.getPieceAt(testedPiece.file+1, testedPiece.rank-2).player == testedState.player) {
				atkScore += atkPieceCalc(testedPiece, testedState.board.getPieceAt(testedPiece.file+1, testedPiece.rank-2));
			}
			if(testedState.board.pieceAt(testedPiece.file+2, testedPiece.rank+1) && testedState.board.getPieceAt(testedPiece.file+2, testedPiece.rank+1).player == testedState.player) {
				atkScore += atkPieceCalc(testedPiece, testedState.board.getPieceAt(testedPiece.file+2, testedPiece.rank+1));
			}
			if(testedState.board.pieceAt(testedPiece.file+2, testedPiece.rank-1) && testedState.board.getPieceAt(testedPiece.file+2, testedPiece.rank-1).player == testedState.player) {
				atkScore += atkPieceCalc(testedPiece, testedState.board.getPieceAt(testedPiece.file+2, testedPiece.rank-1));
			}
			if(testedState.board.pieceAt(testedPiece.file-1, testedPiece.rank+2) && testedState.board.getPieceAt(testedPiece.file-1, testedPiece.rank+2).player == testedState.player) {
				atkScore += atkPieceCalc(testedPiece, testedState.board.getPieceAt(testedPiece.file-1, testedPiece.rank+2));
			}
			if(testedState.board.pieceAt(testedPiece.file-1, testedPiece.rank-2) && testedState.board.getPieceAt(testedPiece.file-1, testedPiece.rank-2).player == testedState.player) {
				atkScore += atkPieceCalc(testedPiece, testedState.board.getPieceAt(testedPiece.file-1, testedPiece.rank-2));
			}
			if(testedState.board.pieceAt(testedPiece.file-2, testedPiece.rank+1) && testedState.board.getPieceAt(testedPiece.file-2, testedPiece.rank+1).player == testedState.player) {
				atkScore += atkPieceCalc(testedPiece, testedState.board.getPieceAt(testedPiece.file-2, testedPiece.rank+1));
			}
			if(testedState.board.pieceAt(testedPiece.file-2, testedPiece.rank-1) && testedState.board.getPieceAt(testedPiece.file-2, testedPiece.rank-1).player == testedState.player) {
				atkScore += atkPieceCalc(testedPiece, testedState.board.getPieceAt(testedPiece.file-2, testedPiece.rank-1));
			}
		} else if (testedPiece instanceof Bishop) {
			for(int i = testedPiece.file-1; i >= 0; i--) {
				if(testedState.board.pieceAt(i, i) && testedState.board.getPieceAt(i, i).player == testedState.player) {
					atkScore += atkPieceCalc(testedPiece, testedState.board.getPieceAt(i, i));
					break;
				}
			}
			for(int i = testedPiece.file+1; i <= 7; i++) {
				if(testedState.board.pieceAt(i, i) && testedState.board.getPieceAt(i, i).player == testedState.player) {
					atkScore += atkPieceCalc(testedPiece, testedState.board.getPieceAt(i, i));
					break;
				}
			}
			for(int i = testedPiece.file-1; i >= 0; i--) {
				if(testedState.board.pieceAt(i, 8-i) && testedState.board.getPieceAt(i, 8-i).player == testedState.player) {
					atkScore += atkPieceCalc(testedPiece, testedState.board.getPieceAt(i, 8-i));
					break;
				}
			}
			for(int i = testedPiece.file+1; i <= 7; i++) {
				if(testedState.board.pieceAt(i, 8-i) && testedState.board.getPieceAt(i, 8-i).player == testedState.player) {
					atkScore += atkPieceCalc(testedPiece, testedState.board.getPieceAt(i, 8-i));
					break;
				}
			}
		} else if (testedPiece instanceof Pawn) {
			if(testedState.board.pieceAt(testedPiece.file-1, testedPiece.rank+1) && testedState.board.getPieceAt(testedPiece.file-1, testedPiece.rank+1).player == testedState.player) {
				atkScore += atkPieceCalc(testedPiece, testedState.board.getPieceAt(testedPiece.file-1, testedPiece.rank+1));
			}
			if(testedState.board.pieceAt(testedPiece.file+1, testedPiece.rank+1) && testedState.board.getPieceAt(testedPiece.file+1, testedPiece.rank+1).player == testedState.player) {
				atkScore += atkPieceCalc(testedPiece, testedState.board.getPieceAt(testedPiece.file+1, testedPiece.rank+1));
			}
		}
		
		if (testedPiece.player == root.player) {
			evaluation += atkScore;
		} else {
			evaluation -= atkScore;
		}
		
		return evaluation;
	}
	
	private double atkPieceCalc(Piece atk, Piece atked) {
		if(atk instanceof King) {
			if(atked instanceof Queen) {
				return .005;
			} else if(atked instanceof Rook) {
				return .01;
			} else if(atked instanceof Knight) {
				return .005;
			} else if(atked instanceof Bishop) {
				return .005;
			} else { // Pawn
				return .00025;
			}
		} else if(atk instanceof Queen) {
			if(atked instanceof Queen) {
				return .02;
			} else if(atked instanceof Rook) {
				return .001;
			} else if(atked instanceof Knight) {
				return .04;
			} else if(atked instanceof Bishop) {
				return .001;
			} else { // Pawn
				return .01;
			}
		} else if(atk instanceof Rook) {
			if(atked instanceof Queen) {
				return .02;
			} else if(atked instanceof Rook) {
				return .02;
			} else if(atked instanceof Knight) {
				return .04;
			} else if(atked instanceof Bishop) {
				return .04;
			} else { // Pawn
				return .01;
			}
		} else if(atk instanceof Knight) {
			if(atked instanceof Queen) {
				return .1;
			} else if(atked instanceof Rook) {
				return .04;
			} else if(atked instanceof Knight) {
				return .02;
			} else if(atked instanceof Bishop) {
				return .04;
			} else { // Pawn
				return .01;
			}
		} else if(atk instanceof Bishop) {
			if(atked instanceof Queen) {
				return .02;
			} else if(atked instanceof Rook) {
				return .04;
			} else if(atked instanceof Knight) {
				return .04;
			} else if(atked instanceof Bishop) {
				return .01;
			} else { // Pawn
				return .0005;
			}
		} else { // Pawn
			if(atked instanceof Queen) {
				return .05;
			} else if(atked instanceof Rook) {
				return .05;
			} else if(atked instanceof Knight) {
				return .04;
			} else if(atked instanceof Bishop) {
				return .03;
			} else { // Pawn
				return .03;
			}
		}
	}
	
	
	
	
	
	
	
	private double boardControl(State testedState, Piece testedPiece, State root) {
		double spaceControl = 0.0;
		double evaluation = 0.0;
		if (testedPiece instanceof Queen) {
			for(int i = testedPiece.file-1; i >= 0; i--) { // Left Row
				spaceControl += .005;
				if(testedState.board.pieceAt(i, testedPiece.rank)) {
					break;
				}
			}
			for(int i = testedPiece.file+1; i <= 7; i++) { // Right Row
				spaceControl += .005;
				if(testedState.board.pieceAt(i, testedPiece.rank)) {
					break;
				}
			}
			for(int i = testedPiece.rank-1; i >= 0; i--) { // Top Col
				spaceControl += .005;
				if(testedState.board.pieceAt(testedPiece.file, i)) {
					break;
				}
			}
			for(int i = testedPiece.rank+1; i <= 7; i++) { // Bot Col
				spaceControl += .005;
				if(testedState.board.pieceAt(testedPiece.file, i)) {
					break;
				}
			}
			
			for(int i = testedPiece.file-1; i >= 0; i--) {
				spaceControl += .005;
				if(testedState.board.pieceAt(i, i)) {
					break;
				}
			}
			for(int i = testedPiece.file+1; i <= 7; i++) {
				spaceControl += .005;
				if(testedState.board.pieceAt(i, i)) {
					break;
				}
			}
			for(int i = testedPiece.file-1; i >= 0; i--) {
				spaceControl += .005;
				if(testedState.board.pieceAt(i, 8-i)) {
					break;
				}
			}
			for(int i = testedPiece.file+1; i <= 7; i++) {
				spaceControl += .005;
				if(testedState.board.pieceAt(i, 8-i)) {
					break;
				}
			}
		} else if (testedPiece instanceof Rook) {
			for(int i = testedPiece.file-1; i >= 0; i--) {
				spaceControl += .009;
				if(testedState.board.pieceAt(i, testedPiece.rank)) {
					break;
				}
			}
			for(int i = testedPiece.file+1; i <= 7; i++) {
				spaceControl += .009;
				if(testedState.board.pieceAt(i, testedPiece.rank)) {
					break;
				}
			}
			for(int i = testedPiece.rank-1; i >= 0; i--) {
				spaceControl += .009;
				if(testedState.board.pieceAt(testedPiece.file, i)) {
					break;
				}
			}
			for(int i = testedPiece.rank+1; i <= 7; i++) {
				spaceControl += .009;
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
				spaceControl += .008;
				if(testedState.board.pieceAt(i, i)) {
					break;
				}
			}
			for(int i = testedPiece.file+1; i <= 7; i++) {
				spaceControl += .008;
				if(testedState.board.pieceAt(i, i)) {
					break;
				}
			}
			for(int i = testedPiece.file-1; i >= 0; i--) {
				spaceControl += .008;
				if(testedState.board.pieceAt(i, 8-i)) {
					break;
				}
			}
			for(int i = testedPiece.file+1; i <= 7; i++) {
				spaceControl += .008;
				if(testedState.board.pieceAt(i, 8-i)) {
					break;
				}
			}
		} else if (testedPiece instanceof King) {
			if(Board.isValid(testedPiece.rank+1, testedPiece.file-1) && testedState.board.pieceAt(testedPiece.rank+1, testedPiece.file-1)){
				spaceControl -= .002;
			}
			if(Board.isValid(testedPiece.rank+1, testedPiece.file) && testedState.board.pieceAt(testedPiece.rank+1, testedPiece.file)){
				spaceControl -= .002;
			}
			if(Board.isValid(testedPiece.rank+1, testedPiece.file+1) && testedState.board.pieceAt(testedPiece.rank+1, testedPiece.file+1)){
				spaceControl -= .002;
			}
			if(Board.isValid(testedPiece.rank, testedPiece.file-1) && testedState.board.pieceAt(testedPiece.rank, testedPiece.file-1)){
				spaceControl -= .002;
			}
			if(Board.isValid(testedPiece.rank, testedPiece.file+1) && testedState.board.pieceAt(testedPiece.rank, testedPiece.file+1)){
				spaceControl -= .002;
			}
			if(Board.isValid(testedPiece.rank-1, testedPiece.file-1) && testedState.board.pieceAt(testedPiece.rank-1, testedPiece.file-1)){
				spaceControl -= .002;
			}
			if(Board.isValid(testedPiece.rank-1, testedPiece.file) && testedState.board.pieceAt(testedPiece.rank-1, testedPiece.file)){
				spaceControl -= .002;
			}
			if(Board.isValid(testedPiece.rank-1, testedPiece.file+1) && testedState.board.pieceAt(testedPiece.rank-1, testedPiece.file+1)){
				spaceControl -= .002;
			}
		}
		
		if (testedPiece.player == root.player) {
			evaluation += spaceControl;
		} else {
			evaluation -= spaceControl;
		}
		
		return evaluation;
	}

	
}
