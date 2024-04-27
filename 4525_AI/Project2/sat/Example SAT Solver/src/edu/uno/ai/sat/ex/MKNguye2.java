package edu.uno.ai.sat.ex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

import edu.uno.ai.logic.Atom;
import edu.uno.ai.logic.Disjunction;
import edu.uno.ai.sat.Assignment;
import edu.uno.ai.sat.Clause;
import edu.uno.ai.sat.Literal;
import edu.uno.ai.sat.Problem;
import edu.uno.ai.sat.Solver;
import edu.uno.ai.sat.Value;
import edu.uno.ai.sat.Variable;
import edu.uno.ai.util.ImmutableArray;

/**
 * 
 * @author Krispy Kreme (Minh-Hieu Kristopher Nguyen) (MKNguye2@uno.edu)
 */

/*
 * TODO:
 * 	The main issue that's coming up is efficiency. DPLL in my current state isn't enough to solve it quickly. 
 * 		I could restart to do WalkSAT, but the sunken cost fallacy says otherwise
 * 	The book mentions component analysis as a way to increase efficiency since it'll only be dealing with the same values and won't have to backtrack
 * 		as far if it makes a mistake since there aren't that many outside influences. Issues arise with how to actually determine components
 * 	The book also mentions a degree heuristic to try to help guide the program into making the correct guess when it's in backtracking mode
 * 		[*] This actually works! The number of solved problems increased from 19 to 21. Might improve on it in the future.
 * 	There's also mention of "Conflict Clause Learning," which seems to be similar to unification. Might try it out
 * 		[*] I don't know if I implemented it correctly or if it just doesn't work. I set it to unify essentially everything in the original problem.
 * 		[*] I could go back and try to unify on the extra clauses added, but that might get out of hand. There's also the idea of getting an
 * 		[*]    empty clause this way to see if the problem is even satisfiable. Worth a look.
 * 		[*] This approach also opens up the idea of implementing my own assignment, problem, clause, and literal values (the last two are already
 * 		[*]    implemented) as a way to bypass the main constraint of the project.
 * [**] Went to Office Hours, was given one piece of advice, and immediately went from 21 to 41 problems completed. I commented out the 
 * 		[**] Conflict Clause Learning and unification stuff, but it did absolutely nothing. Praise be our lord and savior Dr. Benjamin Samuel.
 */
public class MKNguye2 extends Solver {

	private final Random random = new Random(0);
	
	/**
	 * Constructs a new random SAT solver. You should change the string below
	 * from "random" to your ID. You should also change the name of this class.
	 * In Eclipse, you can do that easily by right-clicking on this file
	 * (RandomAgent.java) in the Package Explorer and choosing Refactor > Rename.
	 */
	public MKNguye2() {
		super("My bot :D");
	}

	@Override
	public boolean solve(Assignment assignment) {
		
		// If the problem has no variables, it is trivially true or false.
		if(assignment.problem.variables.size() == 0) {
			return assignment.getValue() == Value.TRUE;
		} else if(assignment.getValue() == Value.TRUE) {
			return true;
		} else if(assignment.getValue() == Value.FALSE) {
			return false;
		} else if(assignment.countFalseClauses() >= 1) {
			return false;
		} else {
//			try {
				return solve2(assignment, resolve(assignment));
//			} catch (Exception e) {
//				return false;
//			}
		}
	}
	
	
	// Attempts to slim down the search space by introducing new clauses via resolution. Unfortunately, this doesn't do anything, 
	// and 2 hours of my life have been wasted.
	// It doesn't impact the performance of the algorithm negatively though (in terms of calling setValue), so I kept it in
	private ArrayList<myClause> resolve(Assignment assignment) {
		ArrayList<myClause> arr = new ArrayList<myClause>();
		
		// oh my god what is happening
		for(Variable var : assignment.problem.variables) { 		// Loops through each variable
			for(int i = 0; i < var.literals.size()-1; i++) {		// Loops through each literal that the variable appears in 
				for(int j = i+1; j < var.literals.size(); j++) {	// Loops through each *other* literal that the variable appears in (after that one)
					if(var.literals.get(i).valence != var.literals.get(j).valence) {	// Checks the valence of the literals
						myClause clause = new myClause(assignment);						// If the valences don't match, then unify the two clauses
						for(Literal lit : var.literals.get(i).clause.literals) {
							if(lit.variable != var) {
								clause.addLiteral(lit); // addLiteral takes care of duplicates on its own
							}
						}
						for(Literal lit : var.literals.get(j).clause.literals) {
							if(lit.variable != var) {
								clause.addLiteral(lit);
							}
						}
						arr.add(clause);
					}
				}
			}
		}
		
		return arr;
	}
	
	
	
	// My creativity is ASTOUNDING. I was going to name it "solve", but the main function is already called that. oops
	private final boolean solve2(Assignment assignment, ArrayList<myClause> resoClauses) {
		
		// Basic checks
		if(assignment.getValue() == Value.TRUE) {
			return true;
		} else if(assignment.getValue() == Value.FALSE) {
			return false;
		} else if(assignment.countFalseClauses() >= 1) {
			return false;
		}
		
//		for(myClause clau : resoClauses) {
//			if(clau.getValue() == Value.FALSE) {
//				return false;
//			}
//		}
		
		Object[] pureSymb = pureSymbol(assignment); // Object[Existence (boolean), Valence, Variable]
		Object[] unitClau = unitClause(assignment); // Object[Existence (boolean), Literal]
		
		// Recursion wooooo
		if( (boolean)pureSymb[0] ) {
			if( (boolean)pureSymb[1] ) {
				return tryValue(assignment, (Variable)pureSymb[2], Value.TRUE, resoClauses);
			} else {
				return tryValue(assignment, (Variable)pureSymb[2], Value.FALSE, resoClauses);
			}
		}
		
		if( (boolean)unitClau[0] ) {
			if(((Literal)unitClau[1]).valence) {
				return tryValue(assignment, ((Literal)unitClau[1]).variable, Value.TRUE, resoClauses);
			} else {
				return tryValue(assignment, ((Literal)unitClau[1]).variable, Value.FALSE, resoClauses);
			}
		}
		
		
		
		// This sh*t right here is too inefficient
		for(Variable var: assignment.problem.variables) {
			
			// Attempts to limit the search space by not checking variables that only appear in true clauses
			boolean proceed = false;
			for(Literal lit : var.literals) {
				if(assignment.getValue(lit.clause) == Value.UNKNOWN) {
					proceed = true;
				}
			}
			
			
			if(proceed && assignment.getValue(var) == Value.UNKNOWN) {
				// Attempts at a degree heuristic
				int tcount = 0;
				int fcount = 0;
				for(Literal lit : var.literals) {
					if(assignment.getValue(lit.clause) != Value.TRUE) {
						if(lit.valence) {
							tcount++;
						} else {
							fcount++;
						}
					}
				}
				if(tcount >= fcount) {
					return tryValMain(assignment, var, Value.TRUE, resoClauses);
				} else {
					return tryValMain(assignment, var, Value.FALSE, resoClauses);
				}
			}
		}
		return false;
	}
	
	// Unit clauses
	private Object[] unitClause(Assignment assignment) {
		Object[] returnVal = new Object[2];
		returnVal[0] = false;
		HashMap<Literal, Integer> litCount = new HashMap<Literal, Integer>();
		
		for(Clause clause : assignment.problem.clauses) {
			if(assignment.getValue(clause) == Value.UNKNOWN && assignment.countUnknownLiterals(clause) == 1) {
				for(Literal lit : clause.literals) {
					if(assignment.getValue(lit) == Value.UNKNOWN) {
						returnVal[0] = true;
						litCount.putIfAbsent(lit, 0);
						litCount.replace(lit, litCount.get(lit)+1);
					}
				}
			}
		}
		
		int max = 0;
		returnVal[1] = null;
		for(Literal lit : litCount.keySet()) {
			if(litCount.get(lit) > max) {
				returnVal[1] = lit;
			}
		}
		
			
		return returnVal;
	}
	// Pure symbols
	// IN PROGRESS: Adding a heuristic to the pure symbol
	private Object[] pureSymbol(Assignment assignment) {
		Object[] returnVal = new Object[3];
		returnVal[0] = false;
		boolean pure;
		boolean valence = true;
		HashMap<Literal, Integer> litCount = new HashMap<Literal, Integer>();
		
		for(Variable var : assignment.problem.variables) {
			if(assignment.getValue(var) == Value.UNKNOWN) {
				pure = true;
				for(Literal lit : var.literals) {
					if(assignment.getValue(lit.clause) == Value.UNKNOWN) {
						valence = lit.valence;
						break;
					}
				}
				for(Literal lit : var.literals) {
					if(assignment.getValue(lit.clause) == Value.UNKNOWN && lit.valence != valence) {
						pure = false;
						break;
					}
				}
				if(pure) {
					returnVal[0] = true;
					returnVal[1] = valence;
					returnVal[2] = var;
					return returnVal;
				}
			}
		}
		
		int max = 0;
		returnVal[1] = null;
		for(Literal lit : litCount.keySet()) {
			if(litCount.get(lit) > max) {
				returnVal[1] = lit;
			}
		}
		
		return returnVal;
	}
	
	
	
	// Copy/pasted code from the document
	// Many thanks to our lord and savior Benjamin Samuel (assuming you wrote it)
	private boolean tryValue(Assignment a, Variable var, Value val, ArrayList<myClause> resoClauses){
		//Back up the variable’s current value.
		Value backup = a.getValue(var);
		//Set the variable to the given value.
		a.setValue(var, val);
		//Try to solve the problem with this new information.
		if(solve2(a, resoClauses)){
			return true;
		}
		//If we failed to solve the problem, return the variable to its previous value.
		else{
			a.setValue(var, backup);
			return false;
		}
	}
	
	
	// Should theoretically cut down the calls of setValue by reducing the number of times a variable is set back to UNKNOWN
	// (I don't know if this actually works)
	private boolean tryValMain(Assignment a, Variable var, Value val, ArrayList<myClause> resoClauses){
		a.setValue(var, val);
		if(solve2(a, resoClauses)) {
			return true;
		} else {
			if(val == Value.TRUE) {
				a.setValue(var, Value.FALSE);
			} else {
				a.setValue(var, Value.TRUE);
			}
			if(solve2(a, resoClauses)) {
				return true;
			}
		}
		a.setValue(var, Value.UNKNOWN);
		return false;
	}
	
	
	
	/***********************************************************************************************************************/ 
	/* [!] WARNING: Beyond this checkpoint lies the ramblings of a madman with too much free time. Caution is advised. [!] */
	/***********************************************************************************************************************/
	
	
	// Why can't we make our own clauses?????????
	/**
	 * Constructs a custom clause for the sake of adding new clauses via resolution
	 * 
	 * @author MKNguye2@uno.edu
	 */
	private class myClause{
		public ArrayList<myLiteral> litArr;
		private Assignment assignment;
		
		// Constructor
		myClause(Assignment assignment){
			litArr = new ArrayList<myLiteral>();
			this.assignment = assignment;
		}
		
		// Oh boy, I actually have to explain things since I don't think anyone in their right mind would even attempt this.
		// This method just adds a literal to the clause. They're stored in an ArrayList.
		// If the literal already exists in the clause, it doesn't add it since repeats aren't helpful
		public boolean addLiteral(Literal lit) {
			myLiteral temp = convert(lit);
			for(int i = 0; i < litArr.size(); i++) {
				if(litArr.get(i).equals(temp)) {
					return false;
				}
			}
			litArr.add(convert(lit));
			return true;
		}
		
		// This method returns the value of the entire clause
		public Value getValue() {
			for(myLiteral lit : litArr) {
				if(lit.getValue() == Value.UNKNOWN) {
					return Value.UNKNOWN;
				} else if(lit.getValue() == Value.TRUE) {
					return Value.TRUE;
				}
			}
			return Value.FALSE;
		}
		
		// This method converts from a normal literal to the custom literal that I made (See below)
		private myLiteral convert(Literal lit) {
			return new myLiteral(lit.valence, lit.variable, assignment);
		}
		
	}
	
	
	
	
	// AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
	/**
	 * Constructs a custom literal for the sake of adding new literals to (custom-made) clauses 
	 * 
	 * @author MKNguye2@uno.edu
	 */
	private class myLiteral{
		public boolean valence;
		public Variable variable;
		private Assignment assignment;
		
		// Construction. Building. Architecture. Civil Engineering. Some other word that's closely related.
		myLiteral(boolean valence, Variable variable, Assignment assignment){
			this.valence = valence;
			this.variable = variable;
			this.assignment = assignment;
		}
		
		// This method returns the value of the literal using the value of the variable as a reference point.
		public Value getValue() {
			if(assignment.getValue(variable) == Value.UNKNOWN){
				return Value.UNKNOWN;
			} else if(assignment.getValue(variable) == Value.TRUE && valence) {
				return Value.TRUE;
			} else if(assignment.getValue(variable) == Value.FALSE && !valence) {
				return Value.TRUE;
			} else {
				return Value.FALSE;
			}
		}
		
		// This method checks if a literal is equal to another literal
		public boolean equals(myLiteral myLit2) {
			if(valence == myLit2.valence && variable.equals(myLit2.variable)) {
				return true;
			} else {
				return false;
			}
		}

	}
	
	
}
