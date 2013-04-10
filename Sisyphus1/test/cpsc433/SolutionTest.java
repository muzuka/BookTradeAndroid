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

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
