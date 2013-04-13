package cpsc433;

import java.util.TreeSet;

public class Project implements Comparable<Project> {

	private String name;

	private TreeSet<Person> in_project = new TreeSet<Person>();
	private TreeSet<Person> heads_project = new TreeSet<Person>();
	private boolean large_project = false;

	public Project(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(Project o) {
		return name.compareTo(o.name);
	}

	public boolean equals(Object o) {
		if (o instanceof Project) {
			return name.equals(((Project) o).name);
		}
		return false;
	}
	
	public String getName() {
		return name;
	}
	
	public void addPerson(Person person) {
		in_project.add(person);
	}
	
	public void addHead(Person person) {
		in_project.add(person);
		heads_project.add(person);
	}
	
	public void removePerson(Person person) {
		in_project.remove(person);
		heads_project.remove(person);
	}
	
	public void setLargeProject(boolean large_project) {
		this.large_project = large_project;
	}
	
	public boolean getLargeProject() {
		return large_project;
	}
	
	public TreeSet<Person> getProjectMembers() {
		return in_project;
	}

}