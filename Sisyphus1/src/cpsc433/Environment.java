package cpsc433;

import java.util.ArrayList;
import java.util.TreeSet;

import cpsc433.Predicate.ParamType;

public class Environment extends PredicateReader implements SisyphusPredicates {
	
	private static Environment getEnv;
	private ArrayList<Predicate> facts; // FIXME: problem with instantiating list - it's an interface
	public Solution currentSolution;
	public boolean fixedAssignments; // TODO: What is this used for? 
		
	private Environment(String name) {
		super(name);
		facts = new ArrayList<Predicate>(); 
		// TODO Auto-generated constructor stub
	}

	private Environment(Environment p) {
		super(p);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * No idea what these public fields are for... What da faq? 
	 * Reimplement some stuffs, I suppose
	 */
	
	
	public int fromFile(String datafile) {
		return 0;
		// TODO Auto-generated method stub
		
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
		
	}
	
	public void a_secretary(String p) {
		
	}
	
	public boolean e_secretary(String p) {
		
	}
	
	public void a_researcher(String p) {
		
	}
	
	public boolean e_researcher(String p) {
		
	}
	
	public void a_manager(String p) {
		
	}
	
	public boolean e_manager(String p) {
		
	}
	
	public void a_smoker(String p) {
		
	}
	
	public boolean e_smoker(String p) {
		
	}
	
	public void a_hacker(String p) {
		
	}
	
	public boolean e_hacker(String p) {
		
	}
	
	public void a_in_group(String p, String grp) {
		
	}
	
	public boolean e_in_group(String p, String grp) {
		
	}
	
	public void a_group(String p, String grp) {
		
	}
	
	public boolean e_group(String p, String grp) {
		
	}
	
	public void a_in_project(String p, String prj) {
		
	}
	
	public boolean e_in_project(String p, String prj) {
		
	}
	
	public void a_project(String p, String prj) {
		
	}
	
	public boolean e_project(String p, String prj) {
		
	}
	
	public void a_heads_group(String p, String grp) {
		
	}
	
	public boolean e_heads_group(String p, String grp) {
		
	}
	
	public void a_heads_project(String p, String prj) {
		
	}
	
	public boolean e_heads_project(String p, String prj) {
		
	}
	
	public void a_works_with(String p, TreeSet<Pair<ParamType,Object>> p2s) {
		
	}
	
	public boolean e_works_with(String p, TreeSet<Pair<ParamType,Object>> p2s) {
		
	}
	
	public void a_works_with(String p, String p2) {
		
	}
	
	public boolean e_works_with(String p, String p2) {
		
	}
	
	public void a_assign_to(String p, String room) throws Exception {
		
	}
	
	public boolean e_assign_to(String p, String room) {
		
	}
	
	public void a_room(String r) {
		
	}
	
	public boolean e_room(String r) {
		
	}
	
	public void a_close(String room, String room2) {
		
	}
	
	public boolean e_close(String room, String room2) {
		
	}
	
	public void a_close(String room, TreeSet<Pair<ParamType,Object>> set) {
		
	}
	
	public boolean e_close(String room, TreeSet<Pair<ParamType,Object>> set) {
		
	}
	
	public void a_large_room(String r) {
		
	}
	
	public boolean e_large_room(String r) {
		
	}
	
	public void a_medium_room(String r) {
		
	}
	
	public boolean e_medium_room(String r) {
		
	}
	
	public void a_small_room(String r) {
		
	}
	
	public boolean e_small_room(String r) {
		
	}
	
	public void a_group(String g) {
		
	}
	
	public boolean e_group(String g) {
		
	}
	
	public void a_project(String p) {
		
	}
	
	public boolean e_project(String p) {
		
	}
	
	public void a_large_project(String prj) {
		
	}
	
	public boolean e_large_project(String prj) {
		
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
