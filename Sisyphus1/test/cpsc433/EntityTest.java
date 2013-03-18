/**
 * 
 */
package cpsc433;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author akmadsen
 *
 */
public class EntityTest {

	Entity mything; 
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		mything = new Entity("testItem"); 
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		mything = null; 
	}

	/**
	 * Tests to make sure that the copy constructor copies the name
	 * of the entity that is passed. There is only one entity. 
	 */
	@Test
	public void testCopy() {
		Entity test_entity = new Entity(mything); 
		assert(test_entity.getName().equals(mything.getName())); 
	}

	/**
	 * Tests that the compareto function works for lessThan
	 * We want the compareTo function to return -1
	 */
	@Test
	public void testLessThan() {
		Entity self = new Entity("Anteater"); 
		Entity target = new Entity("Zebra"); 
		assertEquals(-1, self.compareTo(target)); 
	}
	
	/**
	 * Tests that the compareto function works for greaterThan
	 * We want the compareTo function to return 1
	 */
	@Test
	public void testGreaterThan() {
		Entity self = new Entity("Zebra"); 
		Entity target = new Entity("Anteater"); 
		assertEquals(1, self.compareTo(target)); 
	}
	
	/**
	 * Tests that the compareto function works for compareTo equals
	 * We want the compareTo function to return 0
	 */
	@Test
	public void testCompareToEquals() {
		Entity self = new Entity("Bob"); 
		Entity target = new Entity("Bob"); 
		assertEquals(0, self.compareTo(target)); 
	}
		
	/**
	 * Tests that the equals function works
	 * We want the equals function to return true if equals
	 */
	@Test
	public void testEquals() {
		Entity self = new Entity("Bob"); 
		Entity target = new Entity("Bob"); 
		assert(self.equals(target)); 
	}
	
	/**
	 * Tests that the equals function works
	 * We want the equals function to return false if not equals
	 */
	@Test
	public void testNotEquals() {
		Entity self = new Entity("Bob"); 
		Entity target = new Entity("Samantha"); 
		if(self.equals(target)){
			fail("Unequal Targets returned as equal"); 
		}
	}
	
	/**
	 * Tests that the equals function works
	 * We want the equals function to return false if not equals
	 */
	@Test
	public void testNotEqualsNotEntity() {
		Entity self = new Entity("Bob"); 
		Integer target = new Integer(22); 
		if(self.equals(target)){
			fail("Unequal Targets returned as equal"); 
		}
	}

	/**
	 * Tests that the equals function works
	 * We want the equals function to return false if not equals
	 */
	@Test
	public void testToString() {
		Entity self = new Entity("Rob Kremer"); 
		assertEquals("Rob Kremer", self.toString()); 
	}
}