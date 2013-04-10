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
 */
public class SolutionTest {
		Environment env; 
		Solution mySol; 
	
	/**
	 * Set up the environment where the solution will be living
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		env = Environment.get(); 
		env.fromFile("test.txt"); 
		mySol = new Solution("test.txt.out"); 
	}

	/**
	 * destroy that  environment to be made afresh 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		Environment.reset(); 
		mySol = null; 
	}

	/**
	 * Test the goodness function for the empty assignment. It should
	 * return 0 (assuming that no one in the test environment has been
	 * assigned to a room) 
	 */
	@Test
	public void testEmptyGoodness() {
		assertEquals(0, mySol.getGoodness(env)); 
	}

	/**
	 * If either the person or the room does not exist, 
	 * then we should get false. 
	 */
	@Test
	public void testNonexistentAssignment() {
		assert(!(mySol.assign(new Entity("Chuck"), new Entity("000")))); 
		assert(!(mySol.assign(new Entity("Jurgen"), new Entity("000"))));
		assert(!(mySol.assign(new Entity("Chuck"), new Entity("C5113"))));
	}
}
