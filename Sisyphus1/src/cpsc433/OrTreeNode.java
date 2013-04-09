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
	private ArrayList<Pair<Entity, Entity>> assigned;
	private ArrayList<OrTreeNode> children;
	
	// Give this a person
	private boolean isAssigned(Entity e) {
		for (Pair<Entity, Entity> p : assigned) {
			if (p.getKey().compareTo(e) == 0) {
				return true;
			}
		}
		return false;
	}
	
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
	
	public OrTreeNode(ArrayList<Pair<Entity, Entity>> assigned) {
		if (assigned == null) {
			this.assigned = new ArrayList<Pair<Entity, Entity>>();
		}
		else {
			this.assigned = new ArrayList<Pair<Entity, Entity>>(assigned);
		}
		children = new ArrayList<OrTreeNode>();
		env = Environment.get();
	}
	
	// This isn't done. Don't complain just yet. - T
	public OrTreeNode search() {
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
