package cpsc433;

public class Environment extends PredicateReader {
	public Environment(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public Environment(Environment p) {
		super(p);
		// TODO Auto-generated constructor stub
	}
	public Solution currentSolution;
	public boolean fixedAssignments;
	public int fromFile(String datafile) {
		return 0;
		// TODO Auto-generated method stub
		
	}
	public static Environment get() {
		// TODO Auto-generated method stub
		return null;
	}
	public static void reset() {
		// TODO Auto-generated method stub
		
	}

}
