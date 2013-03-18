package cpsc433;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PairTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Tests that the compareto function works for lessThan
	 * We want the compareTo function to return -1
	 */
	@Test
	public void testLessThanKey() {
		Pair<String, String> self = new Pair<String, String>("Aardvark", "Something");
		Pair<String, String> target = new Pair<String, String>("Cantaloupe", "Something");
		assertEquals(-1, self.compareTo(target)); 
	}
	
	/**
	 * Tests that the compareto function works for greaterThan
	 * We want the compareTo function to return 1
	 */
	@Test
	public void testGreaterThanKey() {
		Pair<String, String> self = new Pair<String, String>("Cantaloupe", "Something");
		Pair<String, String> target = new Pair<String, String>("Aardvark", "Something");
		assertEquals(1, self.compareTo(target)); 
	}
	
	/**
	 * Tests that the compareto function works for lessThan
	 * We want the compareTo function to return -1
	 */
	@Test
	public void testLessThanValue() {
		Pair<String, String> self = new Pair<String, String>("Something", "Aardvark");
		Pair<String, String> target = new Pair<String, String>("Something", "Cantaloupe");
		assertEquals(-1, self.compareTo(target)); 
	}
	
	/**
	 * Tests that the compareto function works for greaterThan
	 * We want the compareTo function to return 1
	 */
	@Test
	public void testGreaterThanValue() {
		Pair<String, String> self = new Pair<String, String>("Something", "Cantaloupe");
		Pair<String, String> target = new Pair<String, String>("Something", "Aardvark");
		assertEquals(1, self.compareTo(target)); 
	}
	
	/**
	 * Tests that the compareto function works for compareTo equals
	 * We want the compareTo function to return 0
	 */
	@Test
	public void testCompareToEquals() {
		Pair<String, String> self = new Pair<String, String>("Merlin", "Wizard");
		Pair<String, String> target = new Pair<String, String>("Merlin", "Wizard");
		assertEquals(0, self.compareTo(target)); 
	}
	
	/**
	 * Tests that the compareto function works if we don't have a good class
	 * we expect an exception in this case
	 */
	@Test(expected=ClassCastException.class)
	public void testCompareToBadClass() {
		Pair<String, String> self = new Pair<String, String>("Merlin", "Wizard");
		Integer target = new Integer(1);
		assertEquals(0, self.compareTo(target)); 
	}
	
}
