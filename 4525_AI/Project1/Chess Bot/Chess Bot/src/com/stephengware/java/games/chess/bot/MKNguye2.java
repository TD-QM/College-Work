package com.stephengware.java.games.chess.bot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.stephengware.java.games.chess.bot.Bot;
import com.stephengware.java.games.chess.state.Bishop;
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
public class MKNguye2 extends Bot {

	/** A random number generator */
	private final Random random;
	
	/**
	 * Constructs a new chess bot named "My Chess Bot" and whose random  number
	 * generator (see {@link java.util.Random}) begins with a seed of 0.
	 */
	public MKNguye2() {
		super("My Chess Bot");
		this.random = new Random(0);
	}

	@Override
	protected State chooseMove(State root) {
		// This list will hold all the children nodes of the root.
		ArrayList<State> children = new ArrayList<>();
		// Generate all the children nodes of the root (that is, all the
		// possible next states of the game.  Make sure that we do not exceed
		// the number of GameTree nodes that we are allowed to generate.
		Iterator<State> iterator = root.next().iterator();
		double bestEval = -9999.0;
		
		if(root.turn == 0 && root.player == Player.WHITE) {
			return root.next(new Pawn(Player.WHITE, 4, 1), new Pawn(Player.WHITE, 4, 3));
		} else if (root.turn == 1 && root.player == Player.WHITE) {
			return root.next(new Knight(Player.WHITE, 1, 0), new Knight(Player.WHITE, 2, 2));
		} else if(root.turn == 0 && root.player == Player.BLACK) {
			return root.next(new Pawn(Player.BLACK, 2, 6), new Pawn(Player.BLACK, 2, 5));
		} else if (root.turn == 1 && root.player == Player.BLACK) {
			return root.next(new Pawn(Player.BLACK, 3, 6), new Pawn(Player.BLACK, 3, 4));
		}
		
		
		while(!root.searchLimitReached() && iterator.hasNext()) {
			
			State testState = iterator.next();
			double testEval = eval(testState);
			
			if(testEval > bestEval) {
				bestEval = testEval;
				children.clear();
				children.add(testState);
			} else if (testEval == bestEval) {
				children.add(testState);
			}
			
		}
		// Choose one of the children at random.
		
		
		
		
		return children.get(random.nextInt(children.size()));
		
	}
	
	protected ArrayList<State> minimax(ArrayList<State> testedState, int ply) {
		if (ply == 1) {
			return max(minimax(testedState, ply+1));
		} else if (ply == 2) {
			return min(testedState);
		}
		return;
	}
	
	protected ArrayList<State> max(ArrayList<State> stateList) {
		ArrayList<State> maxStates = new ArrayList<State>();
		double max = eval(stateList.get(0));
		
		for(int i = 0; i < stateList.size(); i++) {
			if(eval(stateList.get(i)) > max) {
				maxStates.clear();
				maxStates.add(stateList.get(i));
				max = eval(stateList.get(i));
			} else if (eval(stateList.get(i)) == max){
				maxStates.add(stateList.get(i));
			}
		}
		
		return maxStates;
	}
	
	protected ArrayList<State> min(ArrayList<State> stateList) {
		ArrayList<State> minStates = new ArrayList<State>();
		double min = eval(stateList.get(0));
		
		for(int i = 0; i < stateList.size(); i++) {
			if(eval(stateList.get(i)) < min) {
				minStates.clear();
				minStates.add(stateList.get(i));
				min = eval(stateList.get(i));
			} else if (eval(stateList.get(i)) == min){
				minStates.add(stateList.get(i));
			}
		}
		
		return minStates;
	}
	
	
	protected double eval(State testedState) {
		double evaluation = 0.0;
		double myMaterialScore = 0.0;
		double oppMaterialScore = 0.0;
		
		
		
		for(int row = 0; row < 8; row++) {
			for(int col = 0; col < 8; col++) {
				if(testedState.board.pieceAt(row, col)) {
					
					Piece testedPiece = testedState.board.getPieceAt(row, col);
					
					int materialScore = 0;
					
					if(testedPiece instanceof Queen) {
						materialScore = 10;
					} else if(testedPiece instanceof Rook) {
						materialScore = 5;
					} else if(testedPiece instanceof Knight) {
						materialScore = 3;
					} else if(testedPiece instanceof Bishop) {
						materialScore = 3;
					} else if(testedPiece instanceof Pawn) {
						materialScore = 1;
					}
					
					if(testedPiece.player == testedState.player) {
						oppMaterialScore += materialScore;
					} else {
						myMaterialScore += materialScore;
					}
				}
			}
		}
		
		
		evaluation += myMaterialScore - oppMaterialScore;
		return evaluation;
	}
}
