package cpsc433;

public class Environment extends PredicateReader {
	private static Environment getEnv;
	
	public Environment(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public Environment(Environment p) {
		super(p);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * No idea what these public fields are for... What da faq? 
	 * Reimplement some stuffs, I suppose
	 */
	public Solution currentSolution;
	public boolean fixedAssignments;
	
	
	public int fromFile(String datafile) {
		return 0;
		// TODO Auto-generated method stub
		
	}
	public static Environment get() {
		getEnv = new Environment("getEnv");
		return getEnv;
	}
	public static void reset() {
		// TODO Auto-generated method stub
		
	}

}
