package cpsc433;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

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

	/**
	 * Makes sure that the fromFile performs up to spec. Measures 
	 * the number of lines read by the file. 
	 */
	@Test
	public void testFromFile() {
		String testfile = "test.txt"; 
		assertEquals(119, myReader.fromFile(testfile)); 
	}
	
	/**
	 * Makes sure that the fromStream performs up to spec. Measures the number
	 * of lines read by the file.
	 */
	@Test
	public void testFromStream() {
		String testfile = "test.txt"; 
		
		int ret = 0;
		BufferedReader stream = null;
		//System.out.println("reading file " + fileName + "...");
		try {
			stream = new BufferedReader(new InputStreamReader(
					new FileInputStream(testfile)));
			assertEquals(119, myReader.fromStream(stream)); 
		} catch (FileNotFoundException ex) {
			fail("Could not find file " + testfile); 
		}
		try {
			if (stream != null)
				stream.close();
		} catch (IOException ex) {
			fail("Could not close the file " + testfile); 
		}
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
