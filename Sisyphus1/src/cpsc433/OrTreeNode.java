package cpsc433;

import java.util.Date;
import java.util.ArrayList;

/**
 * Class to represent the internal nodes of our or-tree 
 * based search. 
 * 
 * Each node will contain a current solution, as well as the 
 * current environment. The Environment will be used to provide 
 * all the predicates used to describe the current state of 
 * affairs within the problem (room numbers, persons, qualities of 
 * each, etc...). 
 * 
 * Each node will contain a function in order to find the next 
 * node of our problem search (fAltern). 
 *   
 * @author Todd Bennett, Sean Brown, Alex Madsen
 */
public class OrTreeNode {
	private Environment env;
	private Solution currentSol;
	private ArrayList<Pair<Entity, Entity>> assigned;
	private ArrayList<OrTreeNode> children;
	private Date apocalypse;
	private String outfilename;
	private int start_goodness;
	
	// These are not so much "necessary", but my algorithm is fairly
	// specialized. I would consider that a strong point.
	private ArrayList<Entity> groupHeads;
	private ArrayList<Entity> projectHeads;
	private ArrayList<Entity> managers;
	private ArrayList<Entity> people;
	private ArrayList<Entity> largeRooms;
	
	/**
	 * Creates a new OrTreeNode with the current assignments
	 * @param assigned The current assignments. Pass in 
	 * <b><code>null</code></b> to create a root node.
	 * @param endtimes A Date representing the time limit for the search.
	 * @param outfilename The name for the output solution file.
	 */
	public OrTreeNode(ArrayList<Pair<Entity, Entity>> assigned, Date endtimes, String outfilename) {
		if (assigned == null) {
			// Root node.
			this.assigned = new ArrayList<Pair<Entity, Entity>>();
		}
		else {
			// Child node.
			this.assigned = new ArrayList<Pair<Entity, Entity>>(assigned);
		}
		children = new ArrayList<OrTreeNode>();
		env = Environment.get();
		apocalypse = endtimes;
		
		// Keep this around so we can pass it on to the next guy...
		this.outfilename = outfilename;
		
		currentSol = new Solution(outfilename);
		for (Pair<Entity, Entity> p : this.assigned) {
			currentSol.assign(p.getKey(), p.getValue());
		}
		start_goodness = currentSol.getGoodness();
		
		groupHeads = env.getGroupHeads();
		projectHeads = env.getProjectHeads();
		managers = env.getManagers();
		people = env.getPeople();
		largeRooms = env.getLargeRooms();
	}
	
	/** 
	 * Checks whether the given person is assigned. 
	 * @param e Checks whether the person is currently assigned. 
	 * @return True if the person is assigned, false otherwise. 
	 */
	private boolean isAssigned(Entity e) {
		for (Pair<Entity, Entity> p : assigned) {
			if (p.getKey().compareTo(e) == 0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the number of persons assigned to the room. 
	 * @param e The room to be checked. 
	 * @return The number of persons assigned to a room. 
	 */
	private int numAssigned(Entity e) {
		int i = 0;
		for (Pair<Entity, Entity> p : assigned) {
			if (p.getValue().compareTo(e) == 0) {
				i++;
			}
		}
		return i;
	}
	
	/**
	 * Search for the optimal room assignment given the 
	 * state of the current environment. If Ctrl+C is pressed
	 * or the process is otherwise interrupted, the current 
	 * solution is returned. 
	 * @return A Solution, or null
	 */
	public Solution search() {
		Date testTime = new Date();
		if (testTime.after(apocalypse)) {
			return panic_mode();
		}
		Entity p = nextPersonToAssign();
		if (p == null) {
			return (currentSol.isSolved() ? currentSol : null);
		}
		ArrayList<Entity> possibleRooms = null;
		if (groupHeads.contains(p)) {
			possibleRooms = findPossibleRooms(p, 1, true);
			if (possibleRooms == null) {
				possibleRooms = findPossibleRooms(p, 1, false);
			}
		}
		else if (projectHeads.contains(p) || managers.contains(p)) {
			possibleRooms = findPossibleRooms(p, 1, false);
		}
		else {
			possibleRooms = findPossibleRooms(p, 2, false);
		}
		if (possibleRooms == null) {
			return null;
		}
		children.add(new OrTreeNode(assigned, apocalypse, outfilename));
		for (OrTreeNode c : children) {
			Solution csoln = c.search();
		}
		return currentSol;
	}
	
	private ArrayList<Entity> findPossibleRooms(Entity p, int max, boolean large) {
		ArrayList<Entity> possibleRooms = env.getRooms();
		for (Entity r : possibleRooms) {
			if (large) {
				if (!largeRooms.contains(r)) {
					possibleRooms.remove(r);
				}
			}
			if (max == 1) {
				if (numAssigned(r) > 0) {
					possibleRooms.remove(r);
				}
			}
			if (numAssigned(r) > 1) {
				possibleRooms.remove(r);
			}
		}
		return null;
	}

	private Solution panic_mode() {
		return currentSol;
	}

	/**
	 * Search for the optimal room assignment given the 
	 * state of the current environment. If Ctrl+C is pressed
	 * or the process is otherwise interrupted, the current 
	 * solution is returned. 
	 * @return the OrTreeNode with the optimal found solution. 
	 */
	// This isn't done. Don't complain just yet. - T
	private OrTreeNode search_h() {
		if (assign() == 0) {
			// We're done. This is a complete assignment.
			return this;
		}
		children.add(new OrTreeNode(assigned, apocalypse, outfilename));
		for (OrTreeNode c : children) {
			return c.search_h();
		}
		return null;
	}
	
	private Entity nextPersonToAssign() {
		for (Entity p : groupHeads) {
			if (!isAssigned(p)) {
				return p;
			}
		}
		for (Entity p : projectHeads) {
			if (!isAssigned(p)) {
				return p;
			}
		}
		for (Entity p : managers) {
			if (!isAssigned(p)) {
				return p;
			}
		}
		for (Entity p : people) {
			if (!isAssigned(p)) {
				return p;
			}
		}
		return null;
	}
	
	/**
	 * Assign a person to a room 
	 * TODO: should this be in the Solution/Involve the Solution? 
	 * @return 1 if the person is a group head , 0 otherwise 
	 * 			
	 */
	public int assign() {
		ArrayList<Entity> groupHeads = env.getGroupHeads();
		for (Entity p : groupHeads) {
			if (!isAssigned(p)) {
				assignGroupHead(p);
				return 1;
			}
		}
		return 0;
	}

	/**
	 * Don't quite know what this is doing... We'll figure it out. 
	 * @param p 
	 */
	// One per room, Should be a large room
	// These are assigned first
	private void assignGroupHead(Entity p) {
		ArrayList<Entity> largeRooms = env.getLargeRooms();
		for (Entity r : largeRooms) {
			if (numAssigned(r) == 0) {
				assigned.add(new Pair<Entity, Entity>(p, r));
				return;
			}
		}
	}
}
