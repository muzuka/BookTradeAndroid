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
	
	private boolean isAssigned(Entity e) {
		for (Pair<Entity, Entity> p : assigned) {
			if (p.getKey().compareTo(e) == 0) {
				return true;
			}
		}
		return false;
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
	
	public void assign() {
		ArrayList<Entity> managers = env.getManagers();
		for (Entity m : managers) {
			if (!isAssigned(m)) {
				assign(m);
				return;
			}
		}
	}
	
	// This is private because there's careful rules about how it ought to be
	// called. It makes the assumption that p has not already been assigned.
	// This better be true! Check before it ever gets here. assign(void) is
	// the external interface, it looks for an appropriate person to assign
	// next.
	private void assign(Entity p) {
		
	}
}
