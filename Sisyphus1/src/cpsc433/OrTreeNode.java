package cpsc433;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

/**
 * Class to represent the internal nodes of our or-tree based search.
 * 
 * Each node will contain a current solution, as well as the current
 * environment. The Environment will be used to provide all the predicates used
 * to describe the current state of affairs within the problem (room numbers,
 * persons, qualities of each, etc...).
 * 
 * Each node will contain a function in order to find the next node of our
 * problem search (fAltern).
 * 
 * @author Todd Bennett, Sean Brown, Alex Madsen
 */
public class OrTreeNode {
	private Environment env;
	private Solution currentSol;
	private ArrayList<OrTreeNode> children;
	private Date apocalypse;
	private Random magic8ball;

	/**
	 * Creates a new OrTreeNode with the current assignments
	 * 
	 * @param assigned
	 *            The current assignments. Pass in <b><code>null</code></b> to
	 *            create a root node.
	 * @param endtimes
	 *            A Date representing the time limit for the search.
	 * @param outfilename
	 *            The name for the output solution file.
	 */
	public OrTreeNode(Date endtimes, Solution currentSol) {
		children = new ArrayList<OrTreeNode>();
		this.currentSol = currentSol;
		env = Environment.get();
		apocalypse = endtimes;
		magic8ball = new Random();
	}

	/**
	 * Search for the optimal room assignment given the state of the current
	 * environment. If Ctrl+C is pressed or the process is otherwise
	 * interrupted, the current solution is returned.
	 * 
	 * @return A Solution, or null
	 */
	public Solution search() {
		Date testTime = new Date();
		if ((apocalypse.getTime() - testTime.getTime()) > 1000000) {
			return panic_mode();
		}
		Person p = nextPersonToAssign();
		if (p == null) {
			if (currentSol.isComplete()) {
				return currentSol;
			}
			return null;
		}
		for (Room r : env.getRooms()) {
			if (currentSol.assign(p, r)) {
				OrTreeNode child = new OrTreeNode(apocalypse, new Solution(
						currentSol));
				Solution childSol = child.search();
				if (childSol != null) {
					return childSol;
				} else {
					return currentSol;
				}
			}
		}

		return null;
	}

	private TreeSet<Room> findPossibleRooms(int max, boolean large) {
		TreeSet<Room> possibleRooms = new TreeSet<Room>(env.getRooms());
		Collection<Room> realPossibleRooms = env.getRooms();
		for (Room r : realPossibleRooms) {
			if (large) {
				if (r.getSize() != Room.Size.large) {
					possibleRooms.remove(r);
				}
			}
			if (max == 1) {
				if (currentSol.numAssigned(r) > 0) {
					possibleRooms.remove(r);
				}
			}
			if (currentSol.numAssigned(r) > 1) {
				possibleRooms.remove(r);
			}
		}
		return possibleRooms;
	}

	private Solution panic_mode() {
		for (Person p : env.getPeople()) {
			if (currentSol.isAssigned(p)) {
				continue;
			}
			for (Room r : env.getRooms()) {
				if (currentSol.assign(p, r)) {
					OrTreeNode child = new OrTreeNode(apocalypse, new Solution(
							currentSol));
					Solution childSol = child.search();
					if (childSol != null) {
						return childSol;
					} else {
						return currentSol;
					}
				}
			}
		}
		return null;
	}

	private Person nextPersonToAssign() {
		for (Person p : env.getPeople()) {
			if (p.getHeadsGroup() || p.getHeadsProject() || p.getManager()) {
				if (!currentSol.isAssigned(p)) {
					return p;
				}
			}
		}
		for (Person p : env.getPeople()) {
			if (!currentSol.isAssigned(p)) {
				return p;
			}
		}
		return null;
	}

	// For testing purposes
	public static void main(String[] args) {
		Environment env = Environment.get();
		env.a_heads_group("Francis", "Vatican");
		env.a_person("Kim Jong-un");
		env.a_person("A-Mad");
		env.a_smoker("NastySmoker");
		env.a_smoker("NastierSmoker");
		env.a_smoker("SnoopDogg");
		env.a_secretary("Heather");
		env.a_group("Heather", "Vatican");
		env.a_large_room("GrandCanyon");
		env.a_medium_room("InsideJabuJabusBelly");
		env.a_large_room("OuterSpace");
		env.a_small_room("ST084");
		env.a_medium_room("HyruleCastle");
		env.a_medium_room("TheCrimsonRoom");
		env.a_medium_room("Barrels O Fun");
		env.a_large_room("bigroom1");
		env.a_large_room("bigroom2");
		env.a_large_room("bigroom3");
		env.a_large_room("bigroom4");
		Solution currentSol = new Solution(
				"C:\\Users\\Todd\\Desktop\\output.txt");
		OrTreeNode root = new OrTreeNode(new Date(), currentSol);
		Solution s = root.search();
		System.out.println(s.getGoodness());
		s.writeFile();
	}
}
