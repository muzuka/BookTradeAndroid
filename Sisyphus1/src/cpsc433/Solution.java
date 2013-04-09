package cpsc433;

import java.util.ArrayList;

public class Solution {
	
	//FIXME: What use is an enum with only one value? Da fuq? 
	public enum Verbosity {
		SUMMARY;
	}
	
	public static Verbosity verbosity;
	private ArrayList<Predicate> assignments;
	private ArrayList<Predicate> people; 
	private ArrayList<Predicate> rooms; 

	/**
	 * Used to define the output file of the 
	 * @param outfilename
	 */
	public Solution(String outfilename) {
		this.assignments = new ArrayList<Predicate>(); 
	}

	/**
	 * Generate the goodness value as per the soft constraint definitions
	 * of the Sisyphus I problem. 
	 * 
	 * @return the goodness value of the current solution
	 */
	public int getGoodness() {
		// TODO Auto-generated method stub
		
		// place in the soft constraints and their corresponding
		// penalties - requires knowledge of the environment... Hurm... 
		// seems like this was what the Singleton pattern was designed for... 
		// will this work well? - AM 
		// yeah it'll work fine. Environment really is a singleton, just
		// have the Solution grab the Environment in the constructor I'd say.
		// - T
		return 0;
	}

	/**
	 * Returns a boolean value as per whether the solution is complete 
	 * @return true if the solution is complete, false otherwise 
	 */
	public boolean isComplete() {
		// TODO Auto-generated method stub
		
		// if all of the persons are assigned, then the solution
		// is complete
		return false;
	}

	/**
	 * Returns a boolean value as per whether the solution is solved
	 * @return true if the solution is solved, false otherwise 
	 */
	public boolean isSolved() {
		// TODO Auto-generated method stub
		
		// how is this different from isComplete() for Otrees? 
		return false;
	}

}
