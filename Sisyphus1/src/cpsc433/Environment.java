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

	private static Environment getEnv = new Environment("getEnv");
	public Solution currentSolution;
	public boolean fixedAssignments; // TODO: What is this used for? 

	// lists of the known 'nouns' within our system  
	//TODO: this needs to be changed from Entity to Predicate for a nicer toString() 
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
	private ArrayList<Entity> large_projects;
	private ArrayList<Pair<Entity, Entity>> works_with; 
	private ArrayList<Pair<Entity, Entity>> assigned_to; // Format: <Person, Room> 
	private ArrayList<Pair<Entity, Entity>> in_group; // Format: <Person, Group> 
	private ArrayList<Pair<Entity, Entity>> in_project; // Format: <Person, Project> NEW
	private ArrayList<Pair<Entity, Entity>> heads_group;
	private ArrayList<Pair<Entity, Entity>> heads_project;
	private ArrayList<Pair<Entity, Entity>> close;
 	// FIXME: these 'lists' seem to be filling in for what a predicate should do 

	private Environment(String name) {
		super(name);
		
		this.facts = new ArrayList<Predicate>(); 
		this.people = new ArrayList<Entity>(); 
		this.groups = new ArrayList<Entity>(); 
		this.projects = new ArrayList<Entity>(); 
		this.rooms = new ArrayList<Entity>(); 
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
		this.heads_group = new ArrayList<Pair<Entity, Entity>>();
		this.heads_project = new ArrayList<Pair<Entity, Entity>>();
		this.large_projects = new ArrayList<Entity>();
		this.close = new ArrayList<Pair<Entity, Entity>>();
		
		this.currentSolution = null; 
		this.fixedAssignments = false; 
	}

	private Environment(Environment p) {
		super(p);
		
		// FIXME: Imperfection - Does not properly copy the object
		this.facts = p.facts; 
		this.people = p.people; 
		this.groups = p.groups; 
		this.projects = p.projects; 
		this.rooms = p.rooms; 
		this.smokers = p.smokers; 
		this.secretaries = p.secretaries; 
		this.managers = p.managers;
		this.researchers = p.researchers; 
		this.hackers = p.hackers; 
		this.large_rooms = p.large_rooms; 
		this.medium_rooms = p.medium_rooms; 
		this.small_rooms = p.small_rooms; 
		this.works_with = p.works_with; 
		this.assigned_to  = p.assigned_to; // Format: <Person, Room> 
		this.in_group  = p.in_group; // Format: <Person, Group> 
		this.in_project  = p.in_project;
		this.heads_group = p.heads_group;
		this.heads_project = p.heads_project;
		this.large_projects = p.large_projects;
		this.close = p.close;
		this.currentSolution = p.currentSolution; 
		this.fixedAssignments = p.fixedAssignments; 
	}

	public static Environment get() {
		return getEnv;
	}

	public static void reset() {
		// FIXME: Imperfection - Does not properly copy the object

	}

	public void a_person(String p) {
		try {
			if (!facts.contains(new Predicate("person(" + p + ")")) && !people.contains(new Entity(p))) {
				facts.add(new Predicate("person(" + p + ")"));
				people.add(new Entity(p));
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
				a_person(p);
				facts.add(new Predicate("secretary(" + p + ")"));
				secretaries.add(new Entity(p));
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
				a_person(p);
				facts.add(new Predicate("researcher(" + p + ")"));
				researchers.add(new Entity(p));
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
				a_person(p);
				facts.add(new Predicate("manager(" + p + ")"));
				managers.add(new Entity(p));
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
				a_person(p);
				facts.add(new Predicate("smoker(" + p + ")"));
				smokers.add(new Entity(p));
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
				a_person(p);
				facts.add(new Predicate("hacker(" + p + ")"));
				hackers.add(new Entity(p));
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
				a_person(p);
				a_group(grp);
				facts.add(new Predicate("group(" + p + "," + grp + ")"));
				facts.add(new Predicate("in-group(" + p + "," + grp + ")"));
				in_group.add(new Pair<Entity, Entity>(new Entity(p), new Entity(grp)));
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
				a_person(p);
				a_group(grp);
				facts.add(new Predicate("group(" + p + "," + grp + ")"));
				facts.add(new Predicate("in-group(" + p + "," + grp + ")"));
				in_group.add(new Pair<Entity, Entity>(new Entity(p), new Entity(grp)));
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
				a_person(p);
				a_project(prj);
				facts.add(new Predicate("project(" + p + "," + prj + ")"));
				facts.add(new Predicate("in-project(" + p + "," + prj + ")"));
				in_project.add(new Pair<Entity, Entity>(new Entity(p), new Entity(prj)));
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
				a_person(p);
				a_project(prj);
				facts.add(new Predicate("project(" + p + "," + prj + ")"));
				facts.add(new Predicate("in-project(" + p + "," + prj + ")"));
				in_project.add(new Pair<Entity, Entity>(new Entity(p), new Entity(prj)));
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
				a_group(p, grp);
				heads_group.add(new Pair<Entity, Entity>(new Entity(p), new Entity(grp)));
				facts.add(new Predicate("heads-group(" + p + "," + grp + ")"));
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
				a_project(p, prj);
				heads_project.add(new Pair<Entity, Entity>(new Entity(p), new Entity(prj)));
				facts.add(new Predicate("heads-project(" + p + "," + prj + ")"));
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
				a_person(p);
				a_person(p2);			
				facts.add(new Predicate("works-with(" + p + "," + p2 + ")"));
				works_with.add(new Pair<Entity, Entity>(new Entity(p), new Entity(p2)));
				a_works_with(p2, p);
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
	// The exception is for violation of hard constraints I think... not yet. - T
	public void a_assign_to(String p, String room) throws Exception {
		try {
			if (!facts.contains(new Predicate("assign-to(" + p + "," + room + ")")) && !assigned_to.contains(new Pair<Entity, Entity>(new Entity(p), new Entity(room)))) {
				a_person(p);
				a_room(room);
				facts.add(new Predicate("assign-to(" + p + "," + room + ")"));
				assigned_to.add(new Pair<Entity, Entity>(new Entity(p), new Entity(room)));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
				a_room(room);
				a_room(room2);
				facts.add(new Predicate("close(" + room + "," + room2 + ")"));
				close.add(new Pair<Entity, Entity>(new Entity(room), new Entity(room2)));
				a_close(room2, room);
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
				a_room(r);
				facts.add(new Predicate("large-room(" + r + ")"));
				large_rooms.add(new Entity(r));
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
				a_room(r);
				facts.add(new Predicate("medium-room(" + r + ")"));
				medium_rooms.add(new Entity(r));
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
				a_room(r);
				facts.add(new Predicate("small-room(" + r + ")"));
				small_rooms.add(new Entity(r));
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
	// Added -T
	public void a_large_project(String prj) {
		try {
			if (!facts.contains(new Predicate("large-project(" + prj + ")")) && !large_projects.contains(new Entity(prj))) {
				a_project(prj);
				facts.add(new Predicate("large-project(" + prj + ")"));
				large_projects.add(new Entity(prj));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean e_large_project(String prj) {
		try {
			return (facts.contains(new Predicate("large-project(" + prj + ")")) && large_projects.contains(new Entity(prj)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		// TODO: implement me!
		return false;
	}

	// I don't like TreeSets
	public void a_works_with(String p, TreeSet<Pair<ParamType, Object>> p2s) {
		
		// using the ArrayList
		for (Pair<ParamType, Object> pair : p2s) {
			a_works_with(p, (String) pair.getValue());
		}
		
		// using predicates
		StringBuffer s = new StringBuffer(); 
		s.append("works-with(" + p + ", {");
		for (Pair<ParamType, Object> pair : p2s) {
			s.append((String) pair.getValue());
			s.append(", "); 
		}
		if(!p2s.isEmpty()){
			s.delete(s.length()-2, s.length()-1); 
		}
		s.append("})"); 
		try{ 
			if(!facts.contains(new Predicate(s.toString()))){
				facts.add(new Predicate(s.toString())); 
			}
		}
		catch(ParseException pe){
			System.out.println("ERROR: we lost a predicate!"); 
		}
	}

	public boolean e_works_with(String p, TreeSet<Pair<ParamType, Object>> p2s) {
		// using the arraylist 
		/*
		for (Pair<ParamType, Object> pair : p2s) {
			if (!e_works_with(p, (String) pair.getValue())) {
				return false;
			}
		}
		return true;
		*/
		
		// using predicates
		StringBuffer s = new StringBuffer(); 
		s.append("works-with(" + p + ", {");
		for (Pair<ParamType, Object> pair : p2s) {
			s.append((String) pair.getValue());
			s.append(", "); 
		}
		if(!p2s.isEmpty()){
			s.delete(s.length()-2, s.length()-1); 
		}
		s.append("})"); 
		
		boolean ret = false; 
		try{ 
			if(!facts.contains(new Predicate(s.toString()))){
				ret = true;
			} else { 
				ret = false; 
			}
		}
		catch(ParseException pe){
			System.out.println("ERROR: we lost a predicate!"); 
			ret = false; 
		}
		return ret; 
	}

	public void a_close(String room, TreeSet<Pair<ParamType, Object>> set) {
		// using arraylist 
		for (Pair<ParamType, Object> pair : set) {
			a_close(room, (String) pair.getValue());
		}
		
		// using predicates
		StringBuffer s = new StringBuffer(); 
		s.append("close(" + room + ", {");
		for (Pair<ParamType, Object> pair : set) {
			s.append((String) pair.getValue()); 
			s.append(", "); 
		}
		if(!set.isEmpty()){
			s.delete(s.length()-2, s.length()-1); 
		}
		s.append("})"); 
		try{ 
			if(!facts.contains(new Predicate(s.toString()))){
				facts.add(new Predicate(s.toString())); 
			}
		}
		catch(ParseException pe){
			System.out.println("ERROR: we lost a predicate!"); 
		}
	}

	public boolean e_close(String room, TreeSet<Pair<ParamType, Object>> set) {
		/*
		for (Pair<ParamType, Object> pair : set) {
			if (!e_close(room, (String) pair.getValue())) {
				return false;
			}
		}
		return true;
		*/

		// using predicates
		StringBuffer s = new StringBuffer(); 
		s.append("close" + room + ", {");
		for (Pair<ParamType, Object> pair : set) {
			s.append((String) pair.getValue());
			s.append(", "); 
		}
		if(!set.isEmpty()){
			s.delete(s.length()-2, s.length()-1); 
		}
		s.append("})"); 
		
		boolean ret = false; 
		try{ 
			if(!facts.contains(new Predicate(s.toString()))){
				ret = true;
			} else { 
				ret = false; 
			}
		}
		catch(ParseException pe){
			System.out.println("ERROR: we lost a predicate!"); 
			ret = false; 
		}
		return ret; 
	}
	
	public String toString() {
		StringBuffer s = new StringBuffer();
		for(int i = 0; i < facts.size(); i++){
			s.append(facts.get(i).toString() + "\n"); 
		}
		
		/*
		for (int i = 0; i < people.size(); i++) {
			s.append("person(" + people.get(i).getName() + ")\n");
		}
		for (int i = 0; i < smokers.size(); i++) {
			s.append("smoker(" + smokers.get(i).getName() + ")\n");
		}
		for (int i = 0; i < hackers.size(); i++) {
			s.append("hacker(" + hackers.get(i).getName() + ")\n");
		}
		for (int i = 0; i < secretaries.size(); i++) {
			s.append("secretary(" + secretaries.get(i).getName() + ")\n");
		}
		for (int i = 0; i < researchers.size(); i++) {
			s.append("researcher(" + researchers.get(i).getName() + ")\n");
		}
		for (int i = 0; i < managers.size(); i++) {
			s.append("manager(" + managers.get(i).getName() + ")\n");
		}
		for (int i = 0; i < works_with.size(); i++) {
			s.append("works-with(" + works_with.get(i).getKey() + ", " + works_with.get(i).getValue() + ")\n");
		}
		for (int i = 0; i < in_group.size(); i++) {
			s.append("in-group(" + in_group.get(i).getKey() + ", " + in_group.get(i).getValue() + ")\n");
		}
		for (int i = 0; i < heads_group.size(); i++) {
			s.append("heads-group(" + heads_group.get(i).getKey() + ", " + heads_group.get(i).getValue() + ")\n");
		}
		for (int i = 0; i < in_project.size(); i++) {
			s.append("in-project(" + in_project.get(i).getKey() + ", " + in_project.get(i).getValue() + ")\n");
		}
		for (int i = 0; i < heads_project.size(); i++) {
			s.append("heads-project(" + heads_project.get(i).getKey() + ", " + heads_project.get(i).getValue() + ")\n");
		}
		for (int i = 0; i < rooms.size(); i++) {
			s.append("room(" + rooms.get(i).getName() + ")\n");
		}
		for (int i = 0; i < large_rooms.size(); i++) {
			s.append("large-room(" + large_rooms.get(i).getName() + ")\n");
		}
		for (int i = 0; i < medium_rooms.size(); i++) {
			s.append("medium-room(" + medium_rooms.get(i).getName() + ")\n");
		}
		for (int i = 0; i < small_rooms.size(); i++) {
			s.append("small-room(" + small_rooms.get(i).getName() + ")\n");
		}
		for (int i = 0; i < close.size(); i++) {
			s.append("close(" + close.get(i).getKey() + ", " + close.get(i).getValue() + ")\n");
		}
		for (int i = 0; i < assigned_to.size(); i++) {
			s.append("assigned-to(" + assigned_to.get(i).getKey() + ", " + assigned_to.get(i).getValue() + ")\n");
		}
		for (int i = 0; i < projects.size(); i++) {
			s.append("project(" + projects.get(i).getName() + ")\n");
		}
		for (int i = 0; i < large_projects.size(); i++) {
			s.append("large-project(" + large_projects.get(i).getName() + ")\n");
		}
		for (int i = 0; i < groups.size(); i++) {
			facts.
			s.append("group(" + groups.get(i).getName() + ")\n");
		}
		*/
		return s.toString();
	}

	public ArrayList<Entity> getGroups() {
		return groups;
	}
	
	public ArrayList<Pair<Entity, Entity>> getClose() {
		return close;
	}
	
	public ArrayList<Entity> getProjects() {
		return projects;
	}
	
	public ArrayList<Entity> getGroupHeads() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Entity> getLargeRooms() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ArrayList<Entity> getPeople() {
		return people; 
	}
	
	public ArrayList<Entity> getRooms() {
		return rooms; 
	}
	
}