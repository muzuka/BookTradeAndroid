package cpsc433;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test Suite for the PredicateReader class 
 * @author Todd Bennet, Sean Brown, Alex Madsen
 */
public class PredicateReaderTest {

	PredicateReader myReader; 
	
	@Before
	public void setUp() throws Exception {
		myReader = new PredicateReader("testReader"); 
	}

	@After
	public void tearDown() throws Exception {
		myReader = null; 
	}

	/**
	 * Just makes sure that the name of the PR is properly set
	 * after being constructed on name alone... But why does 
	 * a PR need a name? That seems weird to me. 
	 */
	@Test
	public void testStringConstructor() {
		assertEquals("testReader", myReader.getName());
	}
	
	/**
	 * Tests to make sure that the copy constructor works for 
	 * the predicate reader. 
	 */
	@Test
	public void testCopyConstructor() {
		PredicateReader duplicate = new PredicateReader(myReader); 
		assert(myReader.equals(duplicate)); 
	}
	
	//testFromFile(String)
	//testFromStream(BufferedReader)
	//testAssert_(String)
	//testEval(String)
	//testAssert_(Predicate)
	//testEval(Predicate)
	//testA_read(String)
	//testA_help(String)
	//testError(String)
}
