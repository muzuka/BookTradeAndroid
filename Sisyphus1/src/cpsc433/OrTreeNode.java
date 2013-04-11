package cpsc433;

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
	
	/**
	 * Creates a new OrTreeNode with the current assignments
	 * @param assigned The current assignments. Pass in 
	 * <b><code>null</code></b> to create a root node. 
	 */
	public OrTreeNode(ArrayList<Pair<Entity, Entity>> assigned) {
		if (assigned == null) {
			this.assigned = new ArrayList<Pair<Entity, Entity>>();
		}
		else {
			this.assigned = new ArrayList<Pair<Entity, Entity>>(assigned);
		}
		children = new ArrayList<OrTreeNode>();
		env = Environment.get();
		
		// this is NOT to spec! We need to change this up... 
		// should we pass this as a parameter? or allow this to be set up...? - AM 
		currentSol = new Solution("outfile.txt"); 
		
		for (Pair<Entity, Entity> p : assigned) {
			currentSol.assign(p.getKey(), p.getValue());
		}
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
	// Give this a room
	private int numAssigned(Entity e) {
		int i = 0;
		for (Pair<Entity, Entity> p : assigned) {
			if (p.getValue().compareTo(e) == 0) {
				i++;
			}
		}
		return i;
	}
	
	public Solution search() {
		if (assign() == 0) {
			return currentSol;
		}
		children.add(new OrTreeNode(assigned));
		for (OrTreeNode c : children) {
			Solution csoln = c.search();
		}
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
		children.add(new OrTreeNode(assigned));
		for (OrTreeNode c : children) {
			return c.search();
		}
		return null;
	}
	
	/**
	 * Assign a person to a room 
	 * TODO: should this be in the Solution/Involve the Solution? 
	 * @return 1 if the person is a group head 
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