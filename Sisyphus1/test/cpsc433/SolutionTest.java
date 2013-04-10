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
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		env = Environment.get(); 
		env.fromFile("test.txt"); 
		mySol = new Solution("test.txt.out"); 
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		env.reset(); 
		mySol = null; 
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
