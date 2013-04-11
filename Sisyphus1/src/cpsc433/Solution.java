package cpsc433;

import java.util.ArrayList;
import java.io.*;

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
	
	public Solution(Solution s) {
		myEnv = Environment.get();
		assignments = new ArrayList<Predicate>(s.assignments);
		people = new ArrayList<Entity>(s.people);
		rooms = new ArrayList<Entity>(s.rooms);
		
		outfilename = new String(s.outfilename);
	}

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
	public int getGoodness() {
		// place in the soft constraints and their corresponding
		// penalties - requires knowledge of the environment... Hurm... 
		// seems like this was what the Singleton pattern was designed for... 
		// will this work well? - AM 
		int goodness = 0;
		ArrayList<Entity> groups = myEnv.getGroups();
		ArrayList<Entity> projects = myEnv.getProjects();
		ArrayList<Pair<Entity, Entity>> close = myEnv.getClose();
		
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
				if (myEnv.e_close(room, assignments.get(j).getStringParam(1))) {
					closeAssignments.add(assignments.get(j));
				}
				
				// if room matches, start testing
				if (assignments.get(j).getStringParam(1).equals(room) && j >= i+1) {
					
					String person2 = assignments.get(j).getStringParam(0);
					
					// Test 11: smoker vs. non-smoker
					if ((myEnv.e_smoker(person) && !myEnv.e_smoker(person2)) || (myEnv.e_smoker(person2) && !myEnv.e_smoker(person))) {
						goodness -= 50;
					}
					
					// Test 1: group heads need large offices.
					String group = myEnv.getGroup(new Entity(person)).getName();
					if((myEnv.e_heads_group(person, group) && !myEnv.e_large_room(room))) {
						goodness -= 40;
					}
					
					// Test 16: no sharing a small room.
					if (myEnv.e_small_room(room)) {
						goodness -= 25;
					}
					
					// Test 12: both cannot be in the same project
					for (int k = 0; k < projects.size(); k++) {
						if (!myEnv.e_in_project(person, projects.get(k).getName()) || !myEnv.e_in_project(person2, projects.get(k).getName())) {
							goodness -= 7;
						}
					}
					
					// Test 4: secretary shouldn't be with a non-secretary
					if ((myEnv.e_secretary(person) && !myEnv.e_secretary(person2)) || (!myEnv.e_secretary(person) && myEnv.e_secretary(person2))) {
						goodness -= 5;
					}
					
					// Test 14: if not alone
					goodness -= 4;
					
					// Test 15: both people should work together
					if (!myEnv.e_works_with(person, person2)) {
						goodness -= 3;
					}
					
					// Test 13: hackers should share rooms with hackers and non-hackers should share with non-hackers
					if ((myEnv.e_hacker(person) && myEnv.e_hacker(person2)) || (!myEnv.e_hacker(person) && !myEnv.e_hacker(person2))) {
						goodness -= 2;
					}
				}
			}
			
			ArrayList<Entity> closePeople = getPeople(closeAssignments);
			
			// for each group
			for (int j = 0; j < groups.size(); j++) {
				boolean isGroupHead = false;
				String group = groups.get(j).getName();

				// Test 2: group head is close to at least one secretary in group
				// if current person is head of the group
				if (myEnv.e_heads_group(person, group)) {
					// search close rooms
					for (int k = 0; k < closeAssignments.size(); k++) {
						String person2 = closeAssignments.get(k).getStringParam(0);
					
						// if secretary is in the same group
						if (myEnv.e_secretary(person2) && myEnv.e_in_group(person2, group)) {
							break;
						}
						else {
							goodness -= 30;
						}
					}
					// Test: are all members of group close
					if (!areMembersClose(myEnv, group, closePeople)) {
						goodness -= 2;
					}
				}
				// Test 5: manager is close to at least one secretary in group
				// if current person is a manager of the group
				else if (myEnv.e_manager(person) && myEnv.e_in_group(person, group)) {
				
					// search close rooms
					for (int k = 0; k < closeAssignments.size(); k++) {
						String person2 = closeAssignments.get(k).getStringParam(0);
					
						if (myEnv.e_heads_group(person2, group)) {
							isGroupHead = true;
						}
					
						// if secretary is in the same group
						if (myEnv.e_secretary(person2) && myEnv.e_in_group(person2, group)) {
							break;
						}
						else {
							goodness -= 20;
						}
					}
					// Test: is group head close to the manager
					if (!isGroupHead) {
						goodness -= 20;
					}
					// Test: are all members of group close
					if (!areMembersClose(myEnv, group, closePeople)) {
						goodness -= 2;
					}
				}
			}
			// for all projects
			for (int j = 0; j < projects.size(); j++) {
				// for all groups check for head
				for (int k = 0; k < groups.size(); k++) {
					boolean isGroupHead = false;
					String group = groups.get(k).getName();
					String project = projects.get(j).getName();
						// Test 9: large project head is close to at least one secretary in group
						// if current person is large project head
						if (myEnv.e_heads_project(person, project) && myEnv.e_large_project(project)) {
							// search close rooms
							for (int l = 0; l < closeAssignments.size(); l++) {
								String person2 = closeAssignments.get(l).getStringParam(0);
						
								if (myEnv.e_heads_group(person2, group)) {
									isGroupHead = true;
								}
						
								// if secretary is in the same group
								if (myEnv.e_secretary(person2) && myEnv.e_in_project(person2, project)) {
									break;
								}
								else {
									goodness -= 10;
								}
							}
							// test: is head of the group close to project head
							if (!isGroupHead) {
								goodness -= 10;
							}
					
							// Test: are all members of group close
							if (!areMembersClose(myEnv, group, closePeople)) {
								goodness -= 5;
							}
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
		return names; 
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
		ArrayList<Entity> names = getPeople(assignments);
		for (int i = 0; i < people.size(); i++) {
			// Test: All people should be assigned
			if(!names.contains(new Entity(people.get(i).getName()))) {
				return false;
			}
			try {
				// Test: people should only be assigned to one room
				for (int j = 0; j < names.size(); j++) {
					String person = names.get(j).getName();
					for (int k = j+1; k < names.size(); k++) {
						if (person.equals(names.get(k).getName())) {
							return false;
						}
					}
				}
			}
			catch(IndexOutOfBoundsException e) {
				e.printStackTrace();
			}
			// Test: no more than 2 people should be assigned to an office.
			// for all rooms
			for (int j = 0; j < rooms.size(); j++) {
				try {
					String room = rooms.get(j).getName();
					int rooms = 0;
			    
					// for each assignment
					for (int k = 0; k < assignments.size(); k++) {
						if (room.equals(assignments.get(k).getStringParam(1))) {
							rooms++;
						}
						if (rooms >= 3) {
							return false;
						}	
					}
				}
				catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
				}
			}
			// for all assignments
			for (int j = 0; j < assignments.size(); j++) {
				try {
					String room = assignments.get(j).getStringParam(1);
					String person = assignments.get(j).getName();
					String group = myEnv.getGroup(new Entity(person)).getName();
					// if a group head, manager, or 
					if (myEnv.e_heads_group(person, group) || myEnv.e_manager(person) || myEnv.e_heads_project(person, myEnv.getProject(new Entity(person)).getName())) {
						for (int k = j+1; k < assignments.size(); k++) {
							String person2 = assignments.get(k).getStringParam(0);
							String group2 = myEnv.getGroup(new Entity(person2)).getName();
						
							if(room.equals(assignments.get(k).getStringParam(1))) {
								if (myEnv.e_heads_group(person2, group) || myEnv.e_manager(person2) || myEnv.e_heads_project(person2, myEnv.getProject(new Entity(person2)).getName())) {
									return false;
								}
							}
						}
					}
				}
				catch(IndexOutOfBoundsException e) {
					e.printStackTrace();
				}
			}
		}
		return true; 
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
	
	public void writeFile() {
		PrintWriter writer = new PrintWriter(new File(outfilename));
		writer.println(assignments.toString());
		writer.close();
	}
	
	/**
	 * Prints out all current room assignments as well as the current goodness of 
	 * the solution. 
	 */
	// I'm thinking this should go here for ease of access -AM 
	public String toString(){
		String str;
		return "Implement me!"; 
	}

	public void updateEnvironment(Environment env) {
		this.myEnv = env; 
		this.people = myEnv.getPeople();
		this.rooms = myEnv.getRooms(); 
	}
}
