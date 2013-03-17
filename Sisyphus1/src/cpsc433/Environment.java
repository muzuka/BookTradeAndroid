package cpsc433;

import java.text.ParseException;
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
	public Solution currentSolution;
	public boolean fixedAssignments; // TODO: What is this used for? 

	// lists of the known 'nouns' within our system  
	private ArrayList<Entity> 	people; 
	private ArrayList<Entity> 	groups; 
	private ArrayList<Entity> 	projects;
	private ArrayList<Entity> 	rooms; 

	// list of the unary relations that are true within our system 
	private ArrayList<Predicate> facts;
	private ArrayList<Entity> smokers; 
	private ArrayList<Entity> secretaries; 
	private ArrayList<Entity> managers;
	private ArrayList<Entity> researchers; 
	private ArrayList<Entity> hackers; 
	private ArrayList<Entity> large_rooms; 
	private ArrayList<Entity> medium_rooms; 
	private ArrayList<Entity> small_rooms; 
	private ArrayList<Pair<Entity, Entity>> works_with; 
	private ArrayList<Pair<Entity, Entity>> assigned_to; // Format: <Person, Room> 
	private ArrayList<Pair<Entity, Entity>> in_group; // Format: <Person, Group> 
	private ArrayList<Pair<Entity, Entity>> in_project; // Format: <Person, Project> NEW
 	// FIXME: these 'lists' seem to be filling in for what a predicate should do 

	private Environment(String name) {
		super(name);
		this.facts = new ArrayList<Predicate>(); 
		this.smokers = new ArrayList<Entity>(); 
		this.secretaries = new ArrayList<Entity>(); 
		this.managers = new ArrayList<Entity>();
		this.researchers = new ArrayList<Entity>(); 
		this.hackers = new ArrayList<Entity>(); 
		this.large_rooms = new ArrayList<Entity>(); 
		this.medium_rooms = new ArrayList<Entity>(); 
		this.small_rooms = new ArrayList<Entity>(); 
		this.works_with = new ArrayList<Pair<Entity, Entity>>(); 
		this.assigned_to  = new ArrayList<Pair<Entity, Entity>>(); // Format: <Person, Room> 
		this.in_group  = new ArrayList<Pair<Entity, Entity>>(); // Format: <Person, Group> 
		this.in_project  = new ArrayList<Pair<Entity, Entity>>();
		this.currentSolution = null; 
		this.fixedAssignments = false; 
	}

	private Environment(Environment p) {
		super(p);
		this.facts = new ArrayList<Predicate>(); 
		this.smokers = new ArrayList<Entity>(); 
		this.secretaries = new ArrayList<Entity>(); 
		this.managers = new ArrayList<Entity>();
		this.researchers = new ArrayList<Entity>(); 
		this.hackers = new ArrayList<Entity>(); 
		this.large_rooms = new ArrayList<Entity>(); 
		this.medium_rooms = new ArrayList<Entity>(); 
		this.small_rooms = new ArrayList<Entity>(); 
		this.works_with = new ArrayList<Pair<Entity, Entity>>(); 
		this.assigned_to  = new ArrayList<Pair<Entity, Entity>>(); // Format: <Person, Room> 
		this.in_group  = new ArrayList<Pair<Entity, Entity>>(); // Format: <Person, Group> 
		this.in_project  = new ArrayList<Pair<Entity, Entity>>();
		this.currentSolution = null; 
		this.fixedAssignments = false; 
	}

	public static Environment get() {
		getEnv = new Environment("getEnv");
		return getEnv;
	}

	public static void reset() {
		// TODO: resetting the environment needs to clear all the facts 
	}

	public void a_person(String p) {
		try {
			if (!facts.contains(new Predicate("person(" + p + ")")) && !people.contains(new Entity(p))) {
				facts.add(new Predicate("person(" + p + ")"));
				people.add(new Entity(p));
			}
			else {
				if (facts.contains(new Predicate("person(" + p + ")"))) {
					System.out.println("This person already exists in the facts list.");
				}
				if(people.contains(new Entity(p))) {
					System.out.println("This person already exists in the people list.");
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean e_person(String p) {
		try {
			return (facts.contains(new Predicate("person(" + p + ")")) && people.contains(new Entity(p)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO: implement me!
		return false;
	}

	public void a_secretary(String p) {
		try {
			if (!facts.contains(new Predicate("secretary(" + p + ")")) && !secretaries.contains(new Entity(p))) {
				if (!people.contains(new Entity(p))) {
					people.add(new Entity(p));
				}
				facts.add(new Predicate("secretary(" + p + ")"));
				secretaries.add(new Entity(p));
			}
			else {
				if (facts.contains(new Predicate("secretary(" + p + ")"))) {
					System.out.println("This person already exists in the facts list.");
				}
				if(secretaries.contains(new Entity(p))) {
					System.out.println("This person already exists in the secretaries list.");
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean e_secretary(String p) {
		try {
			return (facts.contains(new Predicate("secretary(" + p + ")")) && people.contains(new Entity(p)) && secretaries.contains(new Entity(p)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO: implement me!
		return false;
	}

	public void a_researcher(String p) {
		try {
			if (!facts.contains(new Predicate("researcher(" + p + ")")) && !researchers.contains(new Entity(p))) {
				if (!people.contains(new Entity(p))) {
					people.add(new Entity(p));
				}
				facts.add(new Predicate("researcher(" + p + ")"));
				researchers.add(new Entity(p));
			}
			else {
				if (facts.contains(new Predicate("researcher(" + p + ")"))) {
					System.out.println("This person already exists in the facts list.");
				}
				if(researchers.contains(new Entity(p))) {
					System.out.println("This person already exists in the researchers list.");
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean e_researcher(String p) {
		try {
			return (facts.contains(new Predicate("researcher(" + p + ")")) && people.contains(new Entity(p)) && researchers.contains(new Entity(p)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO: implement me!
		return false;
	}

	public void a_manager(String p) {
		try {
			if (!facts.contains(new Predicate("manager(" + p + ")")) && !managers.contains(new Entity(p))) {
				if (!people.contains(new Entity(p))) {
					people.add(new Entity(p));
				}
				facts.add(new Predicate("manager(" + p + ")"));
				managers.add(new Entity(p));
			}
			else {
				if (facts.contains(new Predicate("manager(" + p + ")"))) {
					System.out.println("This person already exists in the facts list.");
				}
				if(managers.contains(new Entity(p))) {
					System.out.println("This person already exists in the managers list.");
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean e_manager(String p) {
		try {
			return (facts.contains(new Predicate("manager(" + p + ")")) && people.contains(new Entity(p)) && managers.contains(new Entity(p)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO: implement me!
		return false;
	}

	public void a_smoker(String p) {
		try {
			if (!facts.contains(new Predicate("smoker(" + p + ")")) && !smokers.contains(new Entity(p))) {
				if (!people.contains(new Entity(p))) {
					people.add(new Entity(p));
				}
				facts.add(new Predicate("smoker(" + p + ")"));
				smokers.add(new Entity(p));
			}
			else {
				if (facts.contains(new Predicate("smoker(" + p + ")"))) {
					System.out.println("This person already exists in the facts list.");
				}
				if(smokers.contains(new Entity(p))) {
					System.out.println("This person already exists in the smokers list.");
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean e_smoker(String p) {
		try {
			return (facts.contains(new Predicate("smoker(" + p + ")")) && people.contains(new Entity(p)) && smokers.contains(new Entity(p)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO: implement me!
		return false;
	}

	public void a_hacker(String p) {
		try {
			if (!facts.contains(new Predicate("hacker(" + p + ")")) && !hackers.contains(new Entity(p))) {
				if (!people.contains(new Entity(p))) {
					people.add(new Entity(p));
				}
				facts.add(new Predicate("hacker(" + p + ")"));
				hackers.add(new Entity(p));
			}
			else {
				if (facts.contains(new Predicate("hacker(" + p + ")"))) {
					System.out.println("This person already exists in the facts list.");
				}
				if(hackers.contains(new Entity(p))) {
					System.out.println("This person already exists in the hackers list.");
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean e_hacker(String p) {
		try {
			return (facts.contains(new Predicate("hacker(" + p + ")")) && people.contains(new Entity(p)) && hackers.contains(new Entity(p)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO: implement me!
		return false;
	}

	public void a_in_group(String p, String grp) {
		try {
			if (!facts.contains(new Predicate("in-group(" + p + "," + grp + ")")) && !in_group.contains(new Pair<Entity, Entity>(new Entity(p), new Entity(grp)))) {
				if (!people.contains(new Entity(p))) {
					people.add(new Entity(p));
				}
				if (!groups.contains(new Entity(grp))) {
					groups.add(new Entity(grp));
				}
				facts.add(new Predicate("in-group(" + p + "," + grp + ")"));
				in_group.add(new Pair<Entity, Entity>(new Entity(p), new Entity(grp)));
			}
			else {
				if (facts.contains(new Predicate("in-group(" + p + "," + grp + ")"))) {
					System.out.println("This pairing already exists in the facts list.");
				}
				if (in_group.contains(new Pair<Entity, Entity>(new Entity(p), new Entity(grp)))) {
					System.out.println("This pairing already exists in the in-group list.");
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean e_in_group(String p, String grp) {
		try {
			return (facts.contains(new Predicate("in-group(" + p + "," + grp + ")")) && in_group.contains(new Pair<Entity, Entity>(new Entity(p), new Entity(grp))));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO: implement me!
		return false;
	}

	public void a_group(String p, String grp) {
		try {
			if (!facts.contains(new Predicate("group(" + p + "," + grp + ")")) && !in_group.contains(new Pair<Entity, Entity>(new Entity(p), new Entity(grp)))) {
				if (!people.contains(new Entity(p))) {
					people.add(new Entity(p));
				}
				if (!groups.contains(new Entity(grp))) {
					groups.add(new Entity(grp));
				}
				facts.add(new Predicate("group(" + p + "," + grp + ")"));
				in_group.add(new Pair<Entity, Entity>(new Entity(p), new Entity(grp)));
			}
			else {
				if (facts.contains(new Predicate("group(" + p + "," + grp + ")"))) {
					System.out.println("This pairing already exists in the facts list.");
				}
				if (in_group.contains(new Pair<Entity, Entity>(new Entity(p), new Entity(grp)))) {
					System.out.println("This pairing already exists in the in-group list.");
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean e_group(String p, String grp) {
		try {
			return (facts.contains(new Predicate("group(" + p + "," + grp + ")")) && in_group.contains(new Pair<Entity, Entity>(new Entity(p), new Entity(grp))));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO: implement me!
		return false;
	}

	public void a_in_project(String p, String prj) {
		try {
			if (!facts.contains(new Predicate("in-project(" + p + "," + prj + ")")) && !in_project.contains(new Pair<Entity, Entity>(new Entity(p), new Entity(prj)))) {
				if (!people.contains(new Entity(p))) {
					people.add(new Entity(p));
				}
				if (!projects.contains(new Entity(prj))) {
					projects.add(new Entity(prj));
				}
				facts.add(new Predicate("in-project(" + p + "," + prj + ")"));
				in_project.add(new Pair<Entity, Entity>(new Entity(p), new Entity(prj)));
			}
			else {
				if (facts.contains(new Predicate("in-project(" + p + "," + prj + ")"))) {
					System.out.println("This pairing already exists in the facts list.");
				}
				if (in_project.contains(new Pair<Entity, Entity>(new Entity(p), new Entity(prj)))) {
					System.out.println("This pairing already exists in the in-project list");
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean e_in_project(String p, String prj) {
		try {
			return facts.contains(new Predicate("in-project(" + p + "," + prj + ")"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		// TODO: implement me!
		return false;
	}

	public void a_project(String p, String prj) {
		try {
			if (!facts.contains(new Predicate("project(" + p + "," + prj + ")")) && !in_project.contains(new Pair<Entity, Entity>(new Entity(p), new Entity(prj)))) {
				if (!people.contains(new Entity(p))) {
					people.add(new Entity(p));
				}
				if (!projects.contains(new Entity(prj))) {
					projects.add(new Entity(prj));
				}
				facts.add(new Predicate("project(" + p + "," + prj + ")"));
				in_project.add(new Pair<Entity, Entity>(new Entity(p), new Entity(prj)));
			}
			else {
				if (facts.contains(new Predicate("project(" + p + "," + prj + ")"))) {
					System.out.println("This pairing already exists in the facts list.");
				}
				if (in_project.contains(new Pair<Entity, Entity>(new Entity(p), new Entity(prj)))) {
					System.out.println("This pairing already exists in the in-project list.");
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean e_project(String p, String prj) {
		try {
			return facts.contains(new Predicate("project(" + p + "," + prj + ")"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		// TODO: implement me!
		return false;
	}

	public void a_heads_group(String p, String grp) {
		try {
			if (!facts.contains(new Predicate("heads-group(" + p + "," + grp + ")"))) {
				if (!people.contains(new Entity(p))) {
					people.add(new Entity(p));
				}
				if (!groups.contains(new Entity(grp))) {
					groups.add(new Entity(grp));
				}
				facts.add(new Predicate("heads-group(" + p + "," + grp + ")"));
			}
			else {
				if (facts.contains(new Predicate("heads-group(" + p + "," + grp + ")"))) {
					System.out.println("The group head already exists in the facts list.");
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean e_heads_group(String p, String grp) {
		try {
			return facts.contains(new Predicate("heads-group(" + p + "," + grp + ")"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		// TODO: implement me!
		return false;
	}

	public void a_heads_project(String p, String prj) {
		try {
			if (!facts.contains(new Predicate("heads-project(" + p + "," + prj + ")"))) {
				if (!people.contains(new Entity(p))) {
					people.add(new Entity(p));
				}
				if (!projects.contains(new Entity(prj))) {
					projects.add(new Entity(p));
				}
				facts.add(new Predicate("heads-project(" + p + "," + prj + ")"));
			}
			else {
				if (facts.contains(new Predicate("heads-project(" + p + "," + prj + ")"))) {
					System.out.println("The project head already exists in the facts list.");
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean e_heads_project(String p, String prj) {
		try {
			return facts.contains(new Predicate("heads-project(" + p + "," + prj + ")"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		// TODO: implement me!
		return false;
	}

	public void a_works_with(String p, String p2) {
		try {
			if (!facts.contains(new Predicate("works-with(" + p + "," + p2 + ")")) && !works_with.contains(new Pair<Entity, Entity>(new Entity(p), new Entity(p2)))) {
				if (!people.contains(new Entity(p))) {
					people.add(new Entity(p));
				}
				if (!people.contains(new Entity(p2))) {
					people.add(new Entity(p2));
				}
				facts.add(new Predicate("works-with(" + p + "," + p2 + ")"));
				works_with.add(new Pair<Entity, Entity>(new Entity(p), new Entity(p2)));
			}
			else {
				if (!facts.contains(new Predicate("works-with(" + p + "," + p2 + ")"))) {
					System.out.println("This pairing already exists in the facts list.");
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean e_works_with(String p, String p2) {
		try {
			return (facts.contains(new Predicate("works-with(" + p + "," + p2 + ")")) && works_with.contains(new Pair<Entity, Entity>(new Entity(p), new Entity(p2))));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		// TODO: implement me!
		return false;
	}

	// How to use exception?
	// Need to distinguish room
	public void a_assign_to(String p, String room) throws Exception {
		if (!facts.contains(new Predicate("assign-to(" + p + "," + room + ")")) && !assigned_to.contains(new Pair<Entity, Entity>(new Entity(p), new Entity(room)))) {
			if (!people.contains(new Entity(p))) {
				people.add(new Entity(p));
			}
			if (!rooms.contains(new Entity(room))) {
				rooms.add(new Entity(room));
			}
			facts.add(new Predicate("assign-to(" + p + "," + room + ")"));
			assigned_to.add(new Pair<Entity, Entity>(new Entity(p), new Entity(room)));
		}
		else {
			if (facts.contains(new Predicate("assign-to(" + p + "," + room + ")"))) {
				System.out.println("The assignment already exists in the facts list.");
			}
			if (assigned_to.contains(new Pair<Entity, Entity>(new Entity(p), new Entity(room)))) {
				System.out.println("The assignment already exists in the assigned-to list.");
			}
		}
	}

	public boolean e_assign_to(String p, String room) {
		try {
			return (facts.contains(new Predicate("assign-to(" + p + "," + room + ")")) && assigned_to.contains(new Pair<Entity, Entity>(new Entity(p), new Entity(room))));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		// TODO: implement me!
		return false;
	}

	public void a_room(String r) {
		try {
			if (!facts.contains(new Predicate("room(" + r + ")")) && !rooms.contains(new Entity(r))) {
				rooms.add(new Entity(r));
				facts.add(new Predicate("room(" + r + ")"));
			}
			else {
				if (facts.contains(new Predicate("room(" + r + ")"))) {
					System.out.println("This room already exists in the facts list.");
				}
				if (rooms.contains(new Entity(r))) {
					System.out.println("This room already exists in the rooms list.");
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean e_room(String r) {
		try {
			return (facts.contains(new Predicate("room(" + r + ")")) && rooms.contains(new Entity(r)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		// TODO: implement me!
		return false;
	}

	public void a_close(String room, String room2) {
		try {
			if (!facts.contains(new Predicate("close(" + room + "," + room2 + ")"))) {
				if (!rooms.contains(new Entity(room))) {
					rooms.add(new Entity(room));
				}
				if (!rooms.contains(new Entity(room2))) {
					rooms.add(new Entity(room2));
				}
				facts.add(new Predicate("close(" + room + "," + room2 + ")"));
			}
			else {
				if (facts.contains(new Predicate("close(" + room + "," + room2 + ")"))) {
					System.out.println("This close assertion already exists in the facts list.");
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean e_close(String room, String room2) {
		try {
			return facts.contains(new Predicate("close(" + room + "," + room2 + ")"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		// TODO: implement me!
		return false;
	}


	public void a_large_room(String r) {
		try {
			if (!facts.contains(new Predicate("large-room(" + r + ")")) && !large_rooms.contains(new Entity(r))) {
				facts.add(new Predicate("large-room(" + r + ")"));
				large_rooms.add(new Entity(r));
			}
			else {
				if (facts.contains(new Predicate("large-room(" + r + ")"))) {
					System.out.println("This large room already exists in the facts list.");
				}
				if (large_rooms.contains(new Entity(r))) {
					System.out.println("This large room already exists in the large_rooms list.");
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean e_large_room(String r) {
		try {
			return (facts.contains(new Predicate("large-room(" + r + ")")) && large_rooms.contains(new Entity(r)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		// TODO: implement me!
		return false;
	}

	public void a_medium_room(String r) {
		try {
			if (!facts.contains(new Predicate("medium-room(" + r + ")")) && !medium_rooms.contains(new Entity(r))) {
				facts.add(new Predicate("medium-room(" + r + ")"));
				medium_rooms.add(new Entity(r));
			}
			else {
				if (facts.contains(new Predicate("medium-room(" + r + ")"))) {
					System.out.println("This medium room already exists in the facts list.");
				}
				if (medium_rooms.contains(new Entity(r))) {
					System.out.println("This medium room already exists in the large_rooms list.");
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean e_medium_room(String r) {
		try {
			return (facts.contains(new Predicate("medium-room(" + r + ")")) && medium_rooms.contains(new Entity(r)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		// TODO: implement me!
		return false;
	}

	public void a_small_room(String r) {
		try {
			if (!facts.contains(new Predicate("small-room(" + r + ")")) && !small_rooms.contains(new Entity(r))) {
				facts.add(new Predicate("small-room(" + r + ")"));
				small_rooms.add(new Entity(r));
			}
			else {
				if (facts.contains(new Predicate("small-room(" + r + ")"))) {
					System.out.println("This small room already exists in the facts list.");
				}
				if (small_rooms.contains(new Entity(r))) {
					System.out.println("This small room already exists in the large_rooms list.");
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean e_small_room(String r) {
		try {
			return (facts.contains(new Predicate("small-room(" + r + ")")) && small_rooms.contains(new Entity(r)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		// TODO: implement me!
		return false;
	}

	public void a_group(String g) {
		try {
			if (!facts.contains(new Predicate("group(" + g + ")")) && !groups.contains(new Entity(g))) {
				facts.add(new Predicate("group(" + g + ")"));
				groups.add(new Entity(g));
			}
			else {
				if (facts.contains(new Predicate("group(" + g + ")"))) {
					System.out.println("This group is already in the facts list.");
				}
				if (groups.contains(new Entity(g))) {
					System.out.println("This group is already in the groups list.");
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean e_group(String g) {
		try {
			return (facts.contains(new Predicate("group(" + g + ")")) && groups.contains(new Entity(g)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		// TODO: implement me!
		return false;
	}

	public void a_project(String p) {
		try {
			if (!facts.contains(new Predicate("project(" + p + ")")) && !projects.contains(new Entity(p))) {
				facts.add(new Predicate("project(" + p + ")"));
				projects.add(new Entity(p));
			}
			else {
				if (facts.contains(new Predicate("project(" + p + ")"))) {
					System.out.println("This group is already in the facts list.");
				}
				if (projects.contains(new Entity(p))) {
					System.out.println("This group is already in the groups list.");
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean e_project(String p) {
		try {
			return (facts.contains(new Predicate("project(" + p + ")")) && projects.contains(new Entity(p)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		// TODO: implement me!
		return false;
	}

	// Note: add large-project list maybe?
	public void a_large_project(String prj) {
		try {
			if (!facts.contains(new Predicate("large-project(" + prj + ")")) && !projects.contains(new Entity(prj))) {
				facts.add(new Predicate("large-project(" + prj + ")"));
				projects.add(new Entity(prj));
			}
			else {
				if (facts.contains(new Predicate("large-project(" + prj + ")"))) {
					System.out.println("This group is already in the facts list.");
				}
				if (projects.contains(new Entity(prj))) {
					System.out.println("This group is already in the groups list.");
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean e_large_project(String prj) {
		try {
			return (facts.contains(new Predicate("large-project(" + prj + ")")) && projects.contains(new Entity(prj)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		// TODO: implement me!
		return false;
	}

	// I don't like TreeSets
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
