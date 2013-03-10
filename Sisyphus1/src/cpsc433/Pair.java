/**
 * 
 */
package cpsc433;

import java.util.Map;

/**
 * Implements a simple key/value pair which supports the {@link java.lang.Comparable}
 * contract and the {@link java.util.Map.Entry} contract.
 * 
 * <p>Copyright: Copyright (c) 2005-2006, Department of Computer Science, University 
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
public class Pair<K, V> implements Map.Entry<K,V>, Comparable {

	private K key;
	private V value;
	
	/**
	 * Member constructor.
	 * @param key the key
	 * @param value the value that coorosponds the the key
	 */
	public Pair(K key, V value) {
		super();
		this.key = key;
		this.value = value;
	}

	/**
	 * Returns the key of the pair.
	 * @return the key in the pair.
	 * @see java.util.Map.Entry#getKey()
	 */
	public K getKey() {
		return key;
	}

	/**
	 * Returns the value part of the pair.
	 * @return the value part of the pair.
	 * @see java.util.Map.Entry#getValue()
	 */
	public V getValue() {
		return value;
	}

	/**
	 * Sets the value to <code>value</code> and returns the old value. 
	 * @return the old value before this action took place
	 * @see java.util.Map.Entry#setValue(Object)
	 */
	public V setValue(V value) {
		V old = this.value;
		this.value = value;
		return old;
	}

    /**
     * Implements the {@link Comparable#compareTo(Object)} contract for
     * two Pair objects by first attempting to compare the keys, and if
     * they are equal, comparing the values.  If the argument is not a
     * Pair object, then throw {@link java.lang.ClassCastException}
     * @param arg0 the object to compare to
     * @return -1 if this&lt;arg0; 0 if this=arg0; +1 if this&gt;arg0
     */
    public int compareTo(Object arg0) {
        if (arg0 instanceof Pair) {
        	Pair other = (Pair) arg0;
    		int ret = compare(key,other.key);
    		if (ret!=0) return ret;
    		else return compare(value,other.value);
    	}
        else throw new java.lang.ClassCastException();
    }
    
    /**
     * Attempts to compare to objects: if the first is {@link Comparable} then
     * call it's {@link Comparable#compareTo(Object)} method, call 
     * {@link Comparable#compareTo(Object)} on it's 
     * {@link String#compareTo(java.lang.String)} with tthe other object's
     * <code>toString()</code> version. 
     * @param x the object to compare with y
     * @param y the object to be campared
     * @return return -1 if x&lt;y; 0 if x=y; +1 if x&gt;y
     */
    private int compare(Object x, Object y) {
    	if (x instanceof Comparable) {
			Comparable x1 =  (Comparable)x;
			return x1.compareTo(y);
		} else 
			return x.toString().compareTo(y.toString());
   }
}
