package cpsc433;

import java.util.TreeSet;

public class Person implements Comparable<Person> {

	private String name;

	private boolean secretary = false;
	private boolean manager = false;
	private boolean researcher = false;
	private boolean smoker = false;
	private boolean hacker = false;
	private Group group = null;
	private boolean heads_group = false;
	private Project project = null;
	private boolean heads_project = false;
	private TreeSet<Person> works_with = new TreeSet<Person>();
	private Room assigned_to = null;

	public Person(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(Person o) {
		return name.compareTo(o.name);
	}

	public boolean equals(Object o) {
		if (o instanceof Person) {
			return name.equals(((Person) o).name);
		}
		return false;
	}

	public String getName() {
		return name;
	}

	public void setSecretary(boolean secretary) {
		this.secretary = secretary;
	}

	public boolean getSecretary() {
		return secretary;
	}

	public void setResearcher(boolean researcher) {
		this.researcher = researcher;
	}

	public boolean getResearcher() {
		return researcher;
	}

	public void setManager(boolean manager) {
		this.manager = manager;
	}

	public boolean getManager() {
		return manager;
	}

	public void setSmoker(boolean smoker) {
		this.smoker = smoker;
	}

	public boolean getSmoker() {
		return smoker;
	}

	public void setHacker(boolean hacker) {
		this.hacker = hacker;
	}

	public boolean getHacker() {
		return hacker;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Group getGroup() {
		return group;
	}
	
	public void setProject(Project project) {
		this.project = project;
	}

	public Project getProject() {
		return project;
	}
	
	public void setHeadsGroup(boolean heads_group) {
		this.heads_group = heads_group;
	}
	
	public boolean getHeadsGroup() {
		return heads_group;
	}
	
	public void setHeadsProject(boolean heads_project) {
		this.heads_project = heads_project;
	}
	
	public boolean getHeadsProject() {
		return heads_project;
	}
	
	public void addWorksWith(Person person) {
		works_with.add(person);
	}
	
	public boolean getWorksWith(Person person) {
		return works_with.contains(person);
	}
	
	public void setAssignedTo(Room room) {
		assigned_to = room;
	}
	
	public Room getAssignedTo() {
		return assigned_to;
	}
	
	public String toString() {
		return name;
	}
}