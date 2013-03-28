package cpsc433;

import java.util.ArrayList;

public class Solution {
	private ArrayList<Predicate> assignments;
	
	public enum Verbosity {
		SUMMARY;
	}
	public static Verbosity verbosity;

	public Solution(String outfilename) {
		this.assignments = new ArrayList<Predicate>(); 
	}

	public int getGoodness() {
		// TODO Auto-generated method stub
		
		// place in the soft constraints and their corresponding
		// penalties - requires knowledge of the environment... Hurm... 
		// seems like this was what the Singleton pattern was designed for... 
		// will this work well? - AM 
		return 0;
	}

	public boolean isComplete() {
		// TODO Auto-generated method stub
		
		// if all of the persons are assigned, then the solution
		// is complete
		return false;
	}

	public boolean isSolved() {
		// TODO Auto-generated method stub
		
		// how is this different from isComplete() for Otrees? 
		return false;
	}

}
