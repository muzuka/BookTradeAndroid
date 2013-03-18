package cpsc433;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
	
	//testCopyConstructor(PredicateReader)
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
