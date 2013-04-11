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
	
	/**
	 * Tests Soft Constraint 1: 
	 * 		<ul> 
	 * 		<li>Group Heads should have a Large Office</li> 
	 * 		<li>Penalty: -40</li>
	 * 		</ul> 
	 */
	@Test
	public void testSoftConstraint1() {
		env.a_person("Steve"); 
		env.a_room("Bedroom"); 
		env.a_group("Warcraft Guild");
		env.a_heads_group("Steve", "Warcraft Guild"); 
		env.a_large_room("Bedroom"); 
		
		Entity steve = new Entity("Steve"); 
		Entity bedroom = new Entity("Bedroom"); 
		
		try {
			env.a_assign_to("Steve", "Bedroom");
			mySol.assign(steve, bedroom); 
		} catch (Exception e) {
			fail("The Good case did not work"); 
		} 
		assertEquals(0, mySol.getGoodness(env)); 
		
		env.a_person("Harry"); 
		env.a_room("Broom Closet");
		env.a_group("Hogwarts");
		env.a_heads_group("Harry", "Hogwarts");
		env.a_small_room("Broom Closet");
		try {
			env.a_assign_to("Harry", "Broom Closet");
		} catch (Exception e) {
			fail("The Bad Case did not work!");
		} 
		assertEquals(-40, mySol.getGoodness(env)); 
	}
	
	/**
	 * Tests Soft Constraint 2: 
	 * 		<ul> 
	 * 		<li>Group Heads should be close to all members of their group</li> 
	 * 		<li>Penalty: -2</li>
	 * 		</ul> 
	 */
	@Test
	public void testSoftConstraint2() {
		
		fail("not implemented yet"); 
	}
	
	/**
	 * Tests Soft Constraint 3: 
	 * 		<ul> 
	 * 		<li>Group Heads should be close to at least one secretary in the group</li> 
	 * 		<li>Penalty: -30</li>
	 * 		</ul> 
	 */
	@Test
	public void testSoftConstraint3() {
		fail("not implemented yet"); 
	}
	
	/**
	 * Tests Soft Constraint 4: 
	 * 		<ul> 
	 * 		<li>Secretaries should share offices with other secretaries</li> 
	 * 		<li>Penalty: -5</li>
	 * 		</ul> 
	 */
	@Test
	public void testSoftConstraint4() {
		fail("not implemented yet"); 
	}
	
	/**
	 * Tests Soft Constraint 5: 
	 * 		<ul> 
	 * 		<li>Managers should be close to at least one secretary of their group</li> 
	 * 		<li>Penalty: -20</li>
	 * 		</ul> 
	 */
	@Test
	public void testSoftConstraint5() {
		fail("not implemented yet"); 
	}
	
	/**
	 * Tests Soft Constraint 6: 
	 * 		<ul> 
	 * 		<li>Managers should be close to their Group's Head </li> 
	 * 		<li>Penalty: -20</li>
	 * 		</ul> 
	 */
	@Test
	public void testSoftConstraint6() {
		fail("not implemented yet"); 
	}
	
	/**
	 * Tests Soft Constraint 7: 
	 * 		<ul> 
	 * 		<li>Managers should be close to all members of their group</li> 
	 * 		<li>Penalty: -2</li>
	 * 		</ul> 
	 */
	@Test
	public void testSoftConstraint7() {
		fail("not implemented yet"); 
	}
	
	/**
	 * Tests Soft Constraint 8: 
	 * 		<ul> 
	 * 		<li>All heads of projects should be close to all members of their project</li> 
	 * 		<li>Penalty: -5</li>
	 * 		</ul> 
	 */
	@Test
	public void testSoftConstraint8() {
		fail("not implemented yet"); 
	}
	
	/**
	 * Tests Soft Constraint 9: 
	 * 		<ul> 
	 * 		<li>Heads of Large Projects should be close to at least one secretary in their group</li> 
	 * 		<li>Penalty: -10</li>
	 * 		</ul> 
	 */
	@Test
	public void testSoftConstraint9() {
		fail("not implemented yet"); 
	}
	
	/**
	 * Tests Soft Constraint 10: 
	 * 		<ul> 
	 * 		<li>Heads of Large Projects should be close to the head of their group</li> 
	 * 		<li>Penalty: -10</li>
	 * 		</ul> 
	 */
	@Test
	public void testSoftConstraint10() {
		fail("not implemented yet"); 
	}
	
	/**
	 * Tests Soft Constraint 11: 
	 * 		<ul> 
	 * 		<li>A Smoker shouldn't share an office with a non-smoker</li> 
	 * 		<li>Penalty: -50</li>
	 * 		</ul> 
	 */
	@Test
	public void testSoftConstraint11() {
		fail("not implemented yet"); 
	}
	
	/**
	 * Tests Soft Constraint 12: 
	 * 		<ul> 
	 * 		<li>Members of the same project should not share an office</li> 
	 * 		<li>Penalty: -7</li>
	 * 		</ul> 
	 */
	@Test
	public void testSoftConstraint12() {
		fail("not implemented yet"); 
	}
	
	/**
	 * Tests Soft Constraint 13: 
	 * 		<ul> 
	 * 		<li>if a non secretary hacker/non-hacker shares an office, then they should share with another hacker/non-hacker</li> 
	 * 		<li>Penalty: -2</li>
	 * 		</ul> 
	 */
	@Test
	public void testSoftConstraint13() {
		fail("not implemented yet"); 
	}
	
	/**
	 * Tests Soft Constraint 14: 
	 * 		<ul> 
	 * 		<li>people prefer to have their own offices</li> 
	 * 		<li>Penalty: -4</li>
	 * 		</ul> 
	 */
	@Test
	public void testSoftConstraint14() {
		fail("not implemented yet"); 
	}
	
	/**
	 * Tests Soft Constraint 15: 
	 * 		<ul> 
	 * 		<li>if two people share an office, they should work together</li> 
	 * 		<li>Penalty: -3</li>
	 * 		</ul> 
	 */
	@Test
	public void testSoftConstraint15() {
		fail("not implemented yet"); 
	}
	
	/**
	 * Tests Soft Constraint 16: 
	 * 		<ul> 
	 * 		<li>Two people shouldn't share a small room</li> 
	 * 		<li>Penalty: -25</li>
	 * 		</ul> 
	 */
	@Test
	public void testSoftConstraint16() {
		env.a_person("Vegeta"); 
		env.a_person("Nappa"); 
		env.a_room("Space Pod"); 
		env.a_small_room("Space Pod"); 
		
		try {
			env.a_assign_to("Vegeta", "Space Pod");
			env.a_assign_to("Nappa", "Space Pod");
			
			Entity vegeta = new Entity("Vegeta"); 
			Entity nappa = new Entity("Nappa"); 
			Entity spacepod = new Entity("Space Pod"); 
			
			mySol.assign(vegeta, spacepod); 
			mySol.assign(nappa, spacepod); 
			
			assertEquals(-25-4, mySol.getGoodness(env)); 
		} catch (Exception e) {
			if((!env.e_assign_to("Vegeta", "Space Pod")) && (!env.e_assign_to("Nappa", "Space Pod"))){
				fail("could not assign Saiyans to Space Pod"); 
			} else if (!env.e_assign_to("Vegeta", "Space Pod")) { 
				fail("could not assign Vegeta to Space Pod"); 
			} else if(!env.e_assign_to("Nappa", "Space Pod")) {
				fail("could not assign Nappa to Space Pod"); 
			} else {
				fail("something else blew up!"); 
			}
		}
	}
}
