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

}
