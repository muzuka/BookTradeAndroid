package cpsc433;

import java.util.ArrayList;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public class Solution {
	
	// START LEGACY CODE 
	public enum Verbosity {
		SUMMARY;
	}
	
	public static Verbosity verbosity;
	// END LEGACY CODE 
	
	private Environment myEnv; 
	private ArrayList<Predicate> assignments;
	private ArrayList<Entity> people; 
	private ArrayList<Entity> rooms; 

	private String outfilename; 

	/**
	 * Used to define the output file of the 
	 * @param outfilename
	 */
	public Solution(String outfilename) {
		this.outfilename = outfilename; 
		this.assignments = new ArrayList<Predicate>(); 
		this.myEnv = Environment.get(); 
		people = myEnv.getPeople(); 
		rooms = myEnv.getRooms(); 
	}
	
	/**
	 * Constructor used to parse out information
	 * from the environment 
	 */
	
	public Solution(Environment env){
		
	}

	/**
	 * Generate the goodness value as per the soft constraint definitions
	 * of the Sisyphus I problem. 
	 * 
	 * @return the goodness value of the current solution
	 */
	public int getGoodness(Environment env) {
		// place in the soft constraints and their corresponding
		// penalties - requires knowledge of the environment... Hurm... 
		// seems like this was what the Singleton pattern was designed for... 
		// will this work well? - AM 
		int goodness = 0;
		ArrayList<Entity> groups = env.getGroups();
		ArrayList<Entity> projects = env.getProjects();
		ArrayList<Pair<Entity, Entity>> close = env.getClose();
		
		// search assignments
		for (int i = 0; i < assignments.size(); i++) {
			String room = assignments.get(i).getStringParam(1);
			String person = assignments.get(i).getStringParam(0);
			ArrayList<Predicate> closeAssignments = new ArrayList<Predicate>();

			
			// Test room sharing rules
			// for each assignment, search for matching room
			for (int j = 0; j < assignments.size(); j++) {
				// multi-tasking ftw!
				// find close rooms too.
				if (env.e_close(room, assignments.get(j).getStringParam(1))) {
					closeAssignments.add(assignments.get(j));
				}
				
				// if room matches, start testing
				if (assignments.get(j).getStringParam(1).equals(room) && j >= i+1) {
					
					String person2 = assignments.get(j).getStringParam(0);
					
					// Test 11: smoker vs. non-smoker
					if ((env.e_smoker(person) && !env.e_smoker(person2)) || (env.e_smoker(person2) && !env.e_smoker(person))) {
						goodness -= 50;
					}
					
					// Test 1: group heads need large offices.
					for (int k = 0; k < groups.size(); k++) {
						if((env.e_heads_group(person, groups.get(k).getName()) && !env.e_large_room(room)) || (env.e_heads_group(person2, groups.get(k).getName()) && !env.e_large_room(room))) {
							goodness -= 40;
						}
					}
					
					// Test 16: no sharing a small room.
					if (env.e_small_room(room)) {
						goodness -= 25;
					}
					
					// Test 12: both cannot be in the same project
					for (int k = 0; k < projects.size(); k++) {
						if (!env.e_in_project(person, projects.get(k).getName()) || !env.e_in_project(person2, projects.get(k).getName())) {
							goodness -= 7;
						}
					}
					
					// Test 4: secretary shouldn't be with a non-secretary
					if ((env.e_secretary(person) && !env.e_secretary(person2)) || (!env.e_secretary(person) && env.e_secretary(person2))) {
						goodness -= 5;
					}
					
					// Test 14: if not alone
					goodness -= 4;
					
					// Test 15: both people should work together
					if (!env.e_works_with(person, person2)) {
						goodness -= 3;
					}
					
					// Test 13: hackers should share rooms with hackers and non-hackers should share with non-hackers
					if ((env.e_hacker(person) && env.e_hacker(person2)) || (!env.e_hacker(person) && !env.e_hacker(person2))) {
						goodness -= 2;
					}
				}
			}
			
			ArrayList<Entity> closePeople = getPeople(closeAssignments);
			
			// for each group
			for (int j = 0, j2 = 0; j < groups.size() || j2 < projects.size(); j++, j2++) {
				String project = projects.get(j2).getName();
				String group = groups.get(j).getName();
				// Test 2: group head is close to at least one secretary in group
				// if current person is head of the group
				if (env.e_heads_group(person, group)) {
					// search close rooms
					for (int k = 0; k < closeAssignments.size(); k++) {
						String person2 = closeAssignments.get(k).getStringParam(0);
						
						// if secretary is in the same group
						if (env.e_secretary(person2) && env.e_in_group(person2, group)) {
							break;
						}
						else {
							goodness -= 30;
						}
					}
					// Test: are all members of group close
					if (!areMembersClose(env, group, closePeople)) {
						goodness -= 2;
					}
				}
				// Test 5: manager is close to at least one secretary in group
				// if current person is a manager of the group
				else if (env.e_manager(person) && env.e_in_group(person, group)) {
					// search close rooms
					for (int k = 0; k < closeAssignments.size(); k++) {
						String person2 = closeAssignments.get(k).getStringParam(0);
						
						// if secretary is in the same group
						if (env.e_secretary(person2) && env.e_in_group(person2, group)) {
							break;
						}
						else {
							goodness -= 20;
						}
					}
					// Test: are all members of group close
					if (!areMembersClose(env, group, closePeople)) {
						goodness -= 2;
					}
				}
				// Test 9: large project head is close to at least one secretary in group
				// if current person is large project head
				else if (env.e_heads_project(person, project) && env.e_large_project(project)) {
					// search close rooms
					for (int k = 0; k < closeAssignments.size(); k++) {
						String person2 = closeAssignments.get(k).getStringParam(0);
						
						// if secretary is in the same group
						if (env.e_secretary(person2) && env.e_in_project(person2, project)) {
							break;
						}
						else {
							goodness -= 10;
						}
					}
					// Test: are all members of group close
					if (!areMembersClose(env, group, closePeople)) {
						goodness -= 5;
					}
				}
			}
		}
		
		return goodness;
	}
	
	// Extracts first arguements of a list of predicates
	// for getting the names of people in the close assignments
	public ArrayList<Entity> getPeople(ArrayList<Predicate> closeAssignings) {
		ArrayList<Entity> names = new ArrayList<Entity>();
		for (int i = 0; i < closeAssignings.size(); i++) {
			names.add(new Entity(closeAssignings.get(i).getStringParam(0)));
		}
	}
	
	// tests for all members of a group being close to room
	public boolean areMembersClose(Environment env, String groupName, ArrayList<Entity> closeAssignings) {
		ArrayList<Entity> people = env.getPeople();
		for (int i = 0; i < people.size(); i++) {
			if (env.e_in_group(people.get(i).getName(), groupName)) {
				if (!closeAssignings.contains(new Entity(people.get(i).getName()))) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Returns a boolean value as to whether all persons within
	 * the solution have been assigned. 
	 * @return true if the solution is complete, false otherwise 
	 */
	public boolean isComplete() {
		// TODO Auto-generated method stub
		// if (all persons are assigned) 
		//		return true
		// else
		//		return false
		
		return false;
	}

	/**
	 * Returns a boolean value as per whether the solution is solved
	 * @return true if the solution is solved, false otherwise 
	 */
	public boolean isSolved() {
		// TODO Auto-generated method stub
		// returns true if the solution has all persons assigned and 
		// no hard constraints are violated. 
		// returns false otherwise. 
		return false;
	}
	
	public String getOutfileName(){
		return outfilename; 
	}
	
	public ArrayList<Predicate> getAssignments(){
		return assignments; 
	}
	
	/**
	 * Returns true if the solution violates hard constraints, false otherwise. 
	 * @return true if the solution violates any hard constraints, false otherwise. 
	 */
	public boolean violatesHardConstraints(){
		//TODO: I'm a stub!
		return false; 
	}
	
	/**
	 * Assign a person to a room within the Solution class 
	 * @param person The person to be assigned to the room
	 * @param room The room to have the person assigned. 
	 */
	public boolean assign(Entity person, Entity room){
		String workstring = "assign-to(" + person + ", " + room + ")"; 
		try{
			if(people.contains(person) && rooms.contains(room)){
				Predicate assignment = new Predicate(workstring); 
				assignments.add(assignment);
				return true; 
			} else { 
				return false; 
			}
		} catch (java.text.ParseException pe) {
			System.err.println("ERROR: Could not parse " + workstring); 
			return false; 
		}
	}
	
	/**
	 * Prints out all current room assignments as well as the current goodness of 
	 * the solution. 
	 */
	// I'm thinking this should go here for ease of access -AM 
	public String toString(){
		return "Implement me!"; 
	}
}
