package cpsc433;

import java.util.TreeSet;

public class Room implements Comparable<Room> {

	public enum Size {
		small, medium, large
	}

	private String name;

	private TreeSet<Person> assigned_to = new TreeSet<Person>();
	private TreeSet<Room> close = new TreeSet<Room>();
	private Size size = null;

	public Room(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(Room o) {
		return name.compareTo(o.name);
	}

	public boolean equals(Object o) {
		if (o instanceof Room) {
			return name.equals(((Room) o).name);
		}
		return false;
	}
	
	public void addPerson(Person person) {
		assigned_to.add(person);
	}
	
	public void removePerson(Person person) {
		assigned_to.remove(person);
	}

	public void addClose(Room room) {
		close.add(room);
	}
	
	public boolean getClose(Room room) {
		return close.contains(room);
	}
	
	public void setSize(Size size) {
		this.size = size;
	}
	
	public Size getSize() {
		return size;
	}
	
	public TreeSet<Person> getPeople() {
		return assigned_to;
	}
	
	public String toString() {
		return name;
	}
}