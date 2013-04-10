/**
 * 
 */
package cpsc433;

import static org.junit.Assert.*;

import java.text.ParseException;

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
		mySol.updateEnvironment(env); 
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
		Entity chuck = new Entity("Chuck"); 
		Entity jurgen = new Entity("Jurgen"); 
		Entity room0 = new Entity("000"); 
		Entity goodRoom = new Entity("C5113"); 
		assertEquals(false, (mySol.assign(chuck, room0))); 
		assertEquals(false, (mySol.assign(jurgen, room0)));
		assertEquals(false, (mySol.assign(chuck, goodRoom)));
	}
	
	/**
	 * If we make an assignment with people and rooms that do exist, 
	 * then we should get the value 'true' returned. Note that hard 
	 * constraints are not tested here. 
	 */
	@Test
	public void testGoodAssignment() {
		
		Entity jurgen = new Entity("Jurgen"); 
		Entity goodRoom = new Entity("C5113"); 
		
		String workstring = "assign-to(" + jurgen + ", " + goodRoom + ")"; 
		Predicate workPred;
		try {
			workPred = new Predicate(workstring);
			assertEquals(true, env.getRooms().contains(goodRoom)); 
			assertEquals(true, env.getPeople().contains(jurgen)); 
			assertEquals(true, mySol.assign(jurgen, goodRoom)); 
			assertEquals(true, mySol.getAssignments().contains(workPred)); 
		} catch (ParseException e) {
			fail("Work String was malformed"); 
		} 
	}
	
}
