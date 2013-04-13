package cpsc433;

import java.util.Collection;
import java.util.TreeMap;
import java.util.TreeSet;

import cpsc433.Predicate.ParamType;

/**
 * SisyphusI environment. Holds all 'facts'. Uses the singleton design pattern.
 * 
 * @author Rob Kremer, Todd Bennett, Sean Brown, Alex Madsen
 */
public class Environment extends PredicateReader implements SisyphusPredicates {
	private static Environment getEnv = new Environment("getEnv");

	private TreeMap<String, Person> people;
	private TreeMap<String, Group> groups;
	private TreeMap<String, Project> projects;
	private TreeMap<String, Room> rooms;

	// These don't do anything. They have to exist because of Kremer's code.
	public boolean fixedAssignments;
	public Solution currentSolution;

	private Environment(String name) {
		super(name);

		people = new TreeMap<String, Person>();
		groups = new TreeMap<String, Group>();
		projects = new TreeMap<String, Project>();
		rooms = new TreeMap<String, Room>();
	}

	private Environment(Environment p) {
		super(p);

		this.people = new TreeMap<String, Person>(p.people);
		this.groups = new TreeMap<String, Group>(p.groups);
		this.projects = new TreeMap<String, Project>(p.projects);
		this.rooms = new TreeMap<String, Room>(p.rooms);
	}

	public static Environment get() {
		return getEnv;
	}

	public static void reset() {
		getEnv = new Environment("getEnv");
	}
	
	public Collection<Person> getPeople() {
		return people.values();
	}
	
	public Collection<Room> getRooms() {
		return rooms.values();
	}
	
	// Don't bother looking past here.

	public void a_person(String p) {
		if (!people.containsKey(p)) {
			people.put(p, new Person(p));
		}
	}

	public boolean e_person(String p) {
		return people.containsKey(p);
	}

	public void a_secretary(String p) {
		Person person = people.get(p);
		if (person == null) {
			person = new Person(p);
			people.put(p, person);
		}
		person.setSecretary(true);
	}

	public boolean e_secretary(String p) {
		Person person = people.get(p);
		if (person == null) {
			return false;
		}
		return person.getSecretary();
	}

	public void a_researcher(String p) {
		Person person = people.get(p);
		if (person == null) {
			person = new Person(p);
			people.put(p, person);
		}
		person.setResearcher(true);
	}

	public boolean e_researcher(String p) {
		Person person = people.get(p);
		if (person == null) {
			return false;
		}
		return person.getResearcher();
	}

	public void a_manager(String p) {
		Person person = people.get(p);
		if (person == null) {
			person = new Person(p);
			people.put(p, person);
		}
		person.setManager(true);
	}

	public boolean e_manager(String p) {
		Person person = people.get(p);
		if (person == null) {
			return false;
		}
		return person.getManager();
	}

	public void a_smoker(String p) {
		Person person = people.get(p);
		if (person == null) {
			person = new Person(p);
			people.put(p, person);
		}
		person.setSmoker(true);
	}

	public boolean e_smoker(String p) {
		Person person = people.get(p);
		if (person == null) {
			return false;
		}
		return person.getSmoker();
	}

	public void a_hacker(String p) {
		Person person = people.get(p);
		if (person == null) {
			person = new Person(p);
			people.put(p, person);
		}
		person.setHacker(true);
	}

	public boolean e_hacker(String p) {
		Person person = people.get(p);
		if (person == null) {
			return false;
		}
		return person.getHacker();
	}

	public void a_in_group(String p, String grp) {
		Person person = people.get(p);
		if (person == null) {
			person = new Person(p);
			people.put(p, person);
		}

		Group group = groups.get(grp);
		if (group == null) {
			group = new Group(grp);
			groups.put(grp, group);
		}

		Group oldGroup = person.getGroup();
		if (oldGroup != null && !group.equals(oldGroup)) {
			oldGroup.removePerson(person);
			person.setHeadsGroup(false);
		}

		person.setGroup(group);
		group.addPerson(person);
	}

	public boolean e_in_group(String p, String grp) {
		Person person = people.get(p);
		if (person == null) {
			return false;
		}
		Group group = person.getGroup();
		if (group == null) {
			return false;
		}
		return group.getName().equals(grp);
	}

	public void a_group(String p, String grp) {
		a_in_group(p, grp);
	}

	public boolean e_group(String p, String grp) {
		return e_in_group(p, grp);
	}

	public void a_in_project(String p, String prj) {
		Person person = people.get(p);
		if (person == null) {
			person = new Person(p);
			people.put(p, person);
		}

		Project project = projects.get(prj);
		if (project == null) {
			project = new Project(prj);
			projects.put(prj, project);
		}

		Project oldProject = person.getProject();
		if (oldProject != null && !project.equals(oldProject)) {
			oldProject.removePerson(person);
			person.setHeadsProject(false);
		}

		person.setProject(project);
		project.addPerson(person);
	}

	public boolean e_in_project(String p, String prj) {
		Person person = people.get(p);
		if (person == null) {
			return false;
		}
		Project project = person.getProject();
		if (project == null) {
			return false;
		}
		return project.getName().equals(prj);
	}

	public void a_project(String p, String prj) {
		a_in_project(p, prj);
	}

	public boolean e_project(String p, String prj) {
		return e_in_project(p, prj);
	}

	public void a_heads_group(String p, String grp) {
		Person person = people.get(p);
		if (person == null) {
			person = new Person(p);
			people.put(p, person);
		}

		Group group = groups.get(grp);
		if (group == null) {
			group = new Group(grp);
			groups.put(grp, group);
		}

		Group oldGroup = person.getGroup();
		if (oldGroup != null && !group.equals(oldGroup)) {
			oldGroup.removePerson(person);
		}

		person.setGroup(group);
		person.setHeadsGroup(true);
		group.addHead(person);
	}

	public boolean e_heads_group(String p, String grp) {
		Person person = people.get(p);
		if (person == null) {
			return false;
		}
		Group group = person.getGroup();
		if (group == null) {
			return false;
		}
		if (group.getName().equals(grp)) {
			return person.getHeadsGroup();
		}
		return false;
	}

	public void a_heads_project(String p, String prj) {
		Person person = people.get(p);
		if (person == null) {
			person = new Person(p);
			people.put(p, person);
		}

		Project project = projects.get(prj);
		if (project == null) {
			project = new Project(prj);
			projects.put(prj, project);
		}

		Project oldProject = person.getProject();
		if (oldProject != null && !project.equals(oldProject)) {
			oldProject.removePerson(person);
		}

		person.setProject(project);
		person.setHeadsProject(true);
		project.addHead(person);
	}

	public boolean e_heads_project(String p, String prj) {
		Person person = people.get(p);
		if (person == null) {
			return false;
		}
		Project project = person.getProject();
		if (project == null) {
			return false;
		}
		if (project.getName().equals(prj)) {
			return person.getHeadsProject();
		}
		return false;
	}

	public void a_works_with(String p, String p2) {
		Person a = people.get(p);
		if (a == null) {
			a = new Person(p);
			people.put(p, a);
		}
		Person b = people.get(p2);
		if (b == null) {
			b = new Person(p2);
			people.put(p2, b);
		}
		a.addWorksWith(b);
		b.addWorksWith(a);
	}

	public boolean e_works_with(String p, String p2) {
		Person a = people.get(p);
		if (a == null) {
			return false;
		}
		Person b = people.get(p2);
		if (b == null) {
			return false;
		}
		return a.getWorksWith(b);
	}

	public void a_assign_to(String p, String r) throws Exception {
		Person person = people.get(p);
		if (person == null) {
			person = new Person(p);
			people.put(p, person);
		}
		Room room = rooms.get(r);
		if (room == null) {
			room = new Room(r);
			rooms.put(r, room);
		}
		Room oldRoom = person.getAssignedTo();
		if (oldRoom != null && !room.equals(oldRoom)) {
			oldRoom.removePerson(person);
		}
		person.setAssignedTo(room);
		room.addPerson(person);
	}

	public boolean e_assign_to(String p, String r) {
		Person person = people.get(p);
		if (person == null) {
			return false;
		}
		Room room = rooms.get(r);
		if (room == null) {
			return false;
		}
		return person.getAssignedTo().equals(room);
	}

	public void a_room(String r) {
		if (!rooms.containsKey(r)) {
			rooms.put(r, new Room(r));
		}
	}

	public boolean e_room(String r) {
		return rooms.containsKey(r);
	}

	public void a_close(String room, String room2) {
		Room a = rooms.get(room);
		if (a == null) {
			a = new Room(room);
			rooms.put(room, a);
		}
		Room b = rooms.get(room2);
		if (b == null) {
			b = new Room(room2);
			rooms.put(room2, b);
		}
		a.addClose(b);
		b.addClose(a);
	}

	public boolean e_close(String room, String room2) {
		Room a = rooms.get(room);
		if (a == null) {
			return false;
		}
		Room b = rooms.get(room2);
		if (b == null) {
			return false;
		}
		return a.getClose(b);
	}

	public void a_large_room(String r) {
		Room room = rooms.get(r);
		if (room == null) {
			room = new Room(r);
			rooms.put(r, room);
		}
		room.setSize(Room.Size.large);
	}

	public boolean e_large_room(String r) {
		Room room = rooms.get(r);
		if (room == null) {
			return false;
		}
		return (room.getSize().equals(Room.Size.large));
	}

	public void a_medium_room(String r) {
		Room room = rooms.get(r);
		if (room == null) {
			room = new Room(r);
			rooms.put(r, room);
		}
		room.setSize(Room.Size.medium);
	}

	public boolean e_medium_room(String r) {
		Room room = rooms.get(r);
		if (room == null) {
			return false;
		}
		return (room.getSize().equals(Room.Size.medium));
	}

	public void a_small_room(String r) {
		Room room = rooms.get(r);
		if (room == null) {
			room = new Room(r);
			rooms.put(r, room);
		}
		room.setSize(Room.Size.small);
	}

	public boolean e_small_room(String r) {
		Room room = rooms.get(r);
		if (room == null) {
			return false;
		}
		return (room.getSize().equals(Room.Size.small));
	}

	public void a_group(String g) {
		if (!groups.containsKey(g)) {
			groups.put(g, new Group(g));
		}
	}

	public boolean e_group(String g) {
		return groups.containsKey(g);
	}

	public void a_project(String p) {
		if (!projects.containsKey(p)) {
			projects.put(p, new Project(p));
		}
	}

	public boolean e_project(String p) {
		return projects.containsKey(p);
	}

	public void a_large_project(String prj) {
		Project project = projects.get(prj);
		if (project == null) {
			project = new Project(prj);
			projects.put(prj, project);
		}
		project.setLargeProject(true);
	}

	public boolean e_large_project(String prj) {
		Project project = projects.get(prj);
		if (project == null) {
			return false;
		}
		return project.getLargeProject();
	}

	public void a_works_with(String p, TreeSet<Pair<ParamType, Object>> p2s) {
		for (Pair<ParamType, Object> pair : p2s) {
			a_works_with(p, (String) pair.getValue());
		}
	}

	public boolean e_works_with(String p, TreeSet<Pair<ParamType, Object>> p2s) {
		for (Pair<ParamType, Object> pair : p2s) {
			if (!e_works_with(p, (String) pair.getValue())) {
				return false;
			}
		}
		return true;
	}

	public void a_close(String room, TreeSet<Pair<ParamType, Object>> set) {
		for (Pair<ParamType, Object> pair : set) {
			a_close(room, (String) pair.getValue());
		}
	}

	public boolean e_close(String room, TreeSet<Pair<ParamType, Object>> set) {
		for (Pair<ParamType, Object> pair : set) {
			if (!e_works_with(room, (String) pair.getValue())) {
				return false;
			}
		}
		return true;
	}
}