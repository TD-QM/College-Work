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
		
		while(!root.searchLimitReached() && iterator.hasNext())
			children.add(iterator.next());
		// Choose one of the children at random.
		
		
		
		
		return children.get(random.nextInt(children.size()));
		
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
					} else {
						materialScore = 0;
					}
					
					if() {
						
					}
					
				}
			}
		}
		
		
		evaluation += myMaterialScore - oppMaterialScore;
		return evaluation;
	}
}
