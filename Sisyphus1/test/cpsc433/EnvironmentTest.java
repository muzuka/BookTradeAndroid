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
		myInstance.a_person("Jim Moriarte"); 
		assertEquals("person(Jim Moriarte)\n", myInstance.toString()); 
	}

}
