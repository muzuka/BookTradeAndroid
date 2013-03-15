package cpsc433;

import java.util.ArrayList;
import java.util.TreeSet;

import cpsc433.Predicate.ParamType;


/**
 * SisyphusI environment. Holds all 'facts'. Uses the singleton design pattern. 
 * 
 * @author Rob Kremer, Todd Bennett, Sean Brown, Alex Madsen
 */
public class Environment extends PredicateReader implements SisyphusPredicates {
	
	private static Environment getEnv;
	private ArrayList<Predicate> facts;
	public Solution currentSolution;
	public boolean fixedAssignments; // TODO: What is this used for? 
	
	private Environment(String name) {
		super(name);
		this.facts = new ArrayList<Predicate>(); 
		this.currentSolution = null; 
		this.fixedAssignments = false; 
	}

	private Environment(Environment p) {
		super(p);
		this.facts = new ArrayList<Predicate>(); 
		this.currentSolution = null; 
		this.fixedAssignments = false; 
	}
	
	
	/**
	 * Reads SisyphusI predicates from a data file.
	 * 
	 * @param datafile
	 *            the file path to read data from
	 * @return the number of lines read from the file. -1 if there was an error.
	 */
	public int fromFile(String datafile) {
		return super.fromFile(datafile); 
		// FIXME: Can't we just delete this if we are using the PredicateReader? 
		// FIXME: or do we need to do some of our own pre/post processing? 
	}
	
	public static Environment get() {
		getEnv = new Environment("getEnv");
		return getEnv;
	}
	
	public static void reset() {
		// TODO: resetting the environment needs to clear all the facts 
	}
	
	public void a_person(String p) {
		
	}
	
	public boolean e_person(String p) {
		return false; 
		// TODO: implement me!
	}
	
	public void a_secretary(String p) {
		
	}
	
	public boolean e_secretary(String p) {
		return false; 
		// TODO: implement me!
	}
	
	public void a_researcher(String p) {
		
	}
	
	public boolean e_researcher(String p) {
		return false; 
		// TODO: implement me!
	}
	
	public void a_manager(String p) {
		
	}
	
	public boolean e_manager(String p) {
		return false; 
		// TODO: implement me!
	}
	
	public void a_smoker(String p) {
		
	}
	
	public boolean e_smoker(String p) {
		return false; 
		// TODO: implement me!
	}
	
	public void a_hacker(String p) {
		
	}
	
	public boolean e_hacker(String p) {
		return false; 
		// TODO: implement me!
	}
	
	public void a_in_group(String p, String grp) {
		
	}
	
	public boolean e_in_group(String p, String grp) {
		return false; 
		// TODO: implement me!
	}
	
	public void a_group(String p, String grp) {
		
	}
	
	public boolean e_group(String p, String grp) {
		return false; 
		// TODO: implement me!
	}
	
	public void a_in_project(String p, String prj) {
		
	}
	
	public boolean e_in_project(String p, String prj) {
		return false; 
		// TODO: implement me!
	}
	
	public void a_project(String p, String prj) {
		
	}
	
	public boolean e_project(String p, String prj) {
		return false; 
		// TODO: implement me!
	}
	
	public void a_heads_group(String p, String grp) {
		
	}
	
	public boolean e_heads_group(String p, String grp) {
		return false; 
		// TODO: implement me!
	}
	
	public void a_heads_project(String p, String prj) {
		
	}
	
	public boolean e_heads_project(String p, String prj) {
		return false; 
		// TODO: implement me!
	}
	
	public void a_works_with(String p, String p2) {
		
	}
	
	public boolean e_works_with(String p, String p2) {
		return false; 
		// TODO: implement me!
	}
	
	public void a_assign_to(String p, String room) throws Exception {
		
	}
	
	public boolean e_assign_to(String p, String room) {
		return false; 
		// TODO: implement me!
	}
	
	public void a_room(String r) {
		
	}
	
	public boolean e_room(String r) {
		return false; 
		// TODO: implement me!
	}
	
	public void a_close(String room, String room2) {
		
	}
	
	public boolean e_close(String room, String room2) {
		return false; 
		// TODO: implement me!
	}
	
	
	public void a_large_room(String r) {
		
	}
	
	public boolean e_large_room(String r) {
		return false; 
		// TODO: implement me!
	}
	
	public void a_medium_room(String r) {
		
	}
	
	public boolean e_medium_room(String r) {
		return false; 
		// TODO: implement me!
	}
	
	public void a_small_room(String r) {
		
	}
	
	public boolean e_small_room(String r) {
		return false; 
		// TODO: implement me!
	}
	
	public void a_group(String g) {
		
	}
	
	public boolean e_group(String g) {
		return false; 
		// TODO: implement me!
	}
	
	public void a_project(String p) {
		
	}
	
	public boolean e_project(String p) {
		return false; 
		// TODO: implement me!
	}
	
	public void a_large_project(String prj) {
		
	}
	
	public boolean e_large_project(String prj) {
		return false; 
		// TODO: implement me!
	}

	@Override
	public void a_works_with(String p, TreeSet<Pair<ParamType, Object>> p2s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean e_works_with(String p, TreeSet<Pair<ParamType, Object>> p2s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void a_close(String room, TreeSet<Pair<ParamType, Object>> set) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean e_close(String room, TreeSet<Pair<ParamType, Object>> set) {
		// TODO Auto-generated method stub
		return false;
	}

}
