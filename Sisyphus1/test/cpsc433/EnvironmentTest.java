/**
 * 
 */
package cpsc433;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cpsc433.Environment;

/**
 * @author akmadsen
 *
 */
public class EnvironmentTest {

	Environment myInstance; 
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		myInstance = Environment.get(); 
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		Environment.reset(); 
		myInstance = null; 
	}

	/**
	 * Need to test the toString method of the environment for
	 * normal predicates
	 */
	@Test
	public void testToStringNormal() {
		String name = "Jim Moriarte"; 
		String group = "Bad Guys"; 
		myInstance.a_person(name); 
		myInstance.a_group(group); 
		myInstance.a_in_group(name, group);
		assertEquals("person(Jim Moriarte)\ngroup(Bad Guys)\ngroup(Jim Moriarte, Bad Guys)\nin-group(Jim Moriarte, Bad Guys)\n", myInstance.toString()); 
	}
	
	/**
	 * Makes sure that the environment's singleton design pattern
	 * is enforced. We want to see that 
	 */
	@Test
	public void testSingleton() {
		String name = "Jim Moriarte"; 
		String group = "Bad Guys"; 
		String researcher = "Sherlock Holmes"; 
		
		myInstance.a_person(name); 
		myInstance.a_group(group); 
		
		Environment newInstance = Environment.get(); 
		assertEquals(true, myInstance.e_person(name));
		assertEquals(true, myInstance.e_group(group)); 
		
		assertEquals(true, newInstance.e_person(name));
		assertEquals(true, newInstance.e_group(group)); 
		
		newInstance.a_researcher(researcher); 
		assertEquals(true, myInstance.e_person(name));
		assertEquals(true, myInstance.e_group(group)); 
		assertEquals(true, myInstance.e_researcher(researcher)); 
		assertEquals(true, newInstance.e_person(name));
		assertEquals(true, newInstance.e_group(group)); 	
		assertEquals(true, newInstance.e_researcher(researcher)); 
	}
	
	/**
	 * Tests to make sure that a researcher is seen as a person.
	 */
	@Test
	public void testResearcherPeopleInference() {
		String researcher = "Sherlock Holmes"; 
		String noone = "Slender Man";
		
		myInstance.a_researcher(researcher); 
		
		assertEquals(true, myInstance.e_researcher(researcher)); 
		assertEquals(true, myInstance.e_person(researcher)); 
		assertEquals(false, myInstance.e_researcher(noone)); 
		assertEquals(false, myInstance.e_person(noone)); 
	}
	
	/**
	 * Tests to make sure that a hacker is seen as a person.
	 */
	@Test
	public void testHackerPeopleInference() {
		String hacker = "John Connor"; 
		String noone = "Slender Man";		
		
		myInstance.a_hacker(hacker); 
		
		assertEquals(true, myInstance.e_hacker(hacker)); 
		assertEquals(true, myInstance.e_person(hacker)); 
	}
	
	/**
	 * Tests to make sure that a hacker is seen as a person.
	 */
	@Test
	public void testInGroup() {
		String person = "Spiderman"; 
		String group = "Avengers"; 
		
		myInstance.a_person(person);
		myInstance.a_group(group);
		
		myInstance.a_in_group(person, group); 
		
		assertEquals(true, myInstance.e_in_group(person, group)); 
	}
	
	/**
	 * Test Multiple in group with a head
	 */
	@Test
	public void testMultipleInGroupWithHead() {
		String head = "Joker"; 
		String member = "Penguin";
		String group = "Villains"; 
		
		myInstance.a_person(head);
		myInstance.a_person(member); 
		myInstance.a_group(group);
		myInstance.a_in_group(head, group);
		myInstance.a_in_group(member, group); 
		myInstance.a_heads_group(head, group); 
		
		assertEquals(true, myInstance.e_heads_group(head, group)); 
		assertEquals(true, myInstance.e_in_group(head, group));
		assertEquals(true, myInstance.e_in_group(member, group)); 
	}
	
	/**
	 * Test Multiple in group with a head
	 */
	@Test
	public void testGetGroup() {
		String head = "Joker"; 
		String member = "Penguin";
		String group = "Villains"; 
		
		myInstance.a_person(head);
		myInstance.a_person(member); 
		myInstance.a_group(group);
		myInstance.a_in_group(head, group);
		myInstance.a_in_group(member, group); 
		myInstance.a_heads_group(head, group); 
		
		assertEquals(new Entity(head), myInstance.getGroup(new Entity(head)));
		assertEquals(new Entity(member), myInstance.getGroup(new Entity(member))); 
	}
	
	/**
	 * Tests to make sure that the Environment.reset() function 
	 * works properly. 
	 */
	@Test
	public void testReset() {
		String terminator = "Arnold Schwartzenagger"; 
		
		myInstance.a_manager(terminator);
		
		assertEquals(true, myInstance.e_manager(terminator)); 
		
		myInstance.reset(); 
		
		assertEquals(false, myInstance.e_manager(terminator)); 
	}
	
	

}
