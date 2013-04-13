package cpsc433;

import java.util.TreeSet;

public class Group implements Comparable<Group> {

	private String name;

	private TreeSet<Person> in_group = new TreeSet<Person>();
	private TreeSet<Person> heads_group = new TreeSet<Person>();

	public Group(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(Group o) {
		return name.compareTo(o.name);
	}

	public boolean equals(Object o) {
		if (o instanceof Group) {
			return name.equals(((Group) o).name);
		}
		return false;
	}
	
	public String getName() {
		return name;
	}
	
	public void addPerson(Person person) {
		in_group.add(person);
	}
	
	public void addHead(Person person) {
		in_group.add(person);
		heads_group.add(person);
	}
	
	public void removePerson(Person person) {
		in_group.remove(person);
		heads_group.remove(person);
	}

	public TreeSet<Person> getGroupMembers() {
		return in_group;
	}
	
	public TreeSet<Person> getGroupHeads() {
		return heads_group;
	}
}