/**
 * 
 */
package cpsc433;

/**
 * The base class for named things.  It implements <code>Comparable</code> so
 * that such objects may be compared using the <code>compareTo(Object)</code>
 * method (useful if putting them in sorted containers).
 * 
 * <p>Copyright: Copyright (c) 2003, Department of Computer Science, University 
 * of Calgary.  Permission to use, copy, modify, distribute and sell this 
 * software and its documentation for any purpose is hereby granted without 
 * fee, provided that the above copyright notice appear in all copies and that
 * both that copyright notice and this permission notice appear in supporting 
 * documentation.  The Department of Computer Science makes no representations
 * about the suitability of this software for any purpose.  It is provided
 * "as is" without express or implied warranty.</p>
 *
 * @author <a href="http://www.cpsc.ucalgary.ca/~kremer/">Rob Kremer</a>
 *
 */
public class Entity implements Comparable<Entity>{
	
	private String name = null;
	
	static public String FIELD_TERMINATOR = "\n",
						 OBJECT_TERMINATOR = "\n"; 

	/**
	 * Constructs a new object with name <code>name</code>.
	 * @param name the name of the new object.
	 */
	public Entity(String name) {
		this.name = name;
	}

	/**
	 * Constructs a new object with the same <code>name</code>
	 * as the parameter object.
	 * @param e another Entity.
	 */
	public Entity(Entity e) {
		this.name = (e!=null)?e.name:"unnamed";
	}

	/**
	 * @return the name of this object.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param newName the new name of this object.
	 * @return the old name of this object.
	 */
	public String setName(String newName) {
		String oldName = name;
		name = newName;
		return oldName;
	}

	/**
	 * If the parameter object is an <code>Entity</code>, 
	 * compares the name of this object with that of the parameter:
	 * <code>name.compareTo(((Entity)arg0).name)</code>.  Otherwise
	 * throws a <code>java.lang.ClassCastException</code> exception.
	 * @param arg0 another object ot compare to (hopefully, another <em>Entity</em>.
	 * @return -1 if this name is less than the param, 0 if equal, 1 if greater than.
	 */
	public int compareTo(Entity arg0) {
	    if (arg0 instanceof Entity) {
			return name.compareTo(((Entity)arg0).name);
		}
	    else throw new java.lang.ClassCastException();
	}

	/**
	 * If the parameter object is an <code>Entity</code>, 
	 * compares the name of this object with that of the parameter:
	 * <code>name.equals(((Entity)arg0).name)</code>.  Otherwise
	 * returns false.
	 * @param arg0 another object ot compare to (hopefully, another <em>Entity</em>.
	 * @return true if the argument is an <em>Entity</em> and the names are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object arg0) {
		return (arg0 instanceof Entity) ? name.equals(((Entity)arg0).name) : false;
		
	}
	
	
}
