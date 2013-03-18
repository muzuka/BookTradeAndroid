package cpsc433;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test Suite for the Predicate class 
 * @author Todd Bennet, Sean Brown, Alex Madsen
 */
public class PredicateTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Make sure that the constructor works on good input
	 */
	@Test
	public void testConstructor() {
		try{ 
			Predicate monad = new Predicate("hello()"); 
		} catch (ParseException pe){ 
			fail("Parse Exception on Atomic Input"); 
		}
	}
	
	/**
	 * Make sure that the constructor works on good Unary predicates
	 */
	@Test
	public void testConstructorUnary() {
		try{ 
			Predicate unary = new Predicate("dragon-man(Trogdor)"); 
			// test to make sure the params took!!!
		} catch (ParseException pe){ 
			fail("Parse Exception on Unary Input"); 
		}
	}
	
	/**
	 * Make sure that the constructor works on good Binary predicates
	 */
	@Test
	public void testConstructorBinary() {
		try{ 
			Predicate binary = new Predicate("trogdor-was(man, dragon)"); 
			// test to make sure the params took!!!
		} catch (ParseException pe){ 
			fail("Parse Exception on Binary Input"); 
		}
	}
	
	/**
	 * Make sure that the constructor works on good Ternary predicates
	 */
	@Test
	public void testConstructorTernary() {
		try{ 
			Predicate ternary = new Predicate("what-is(my, age, again)"); 
			// test to make sure the params took!!!
		} catch (ParseException pe){ 
			fail("Parse Exception on Ternary Input"); 
		}
	}
	
	/**
	 * Make sure that the constructor works on bad input. We want a parse
	 * exception to be thrown in that case. 
	 * @throws ParseException 
	 */
	@Test(expected= ParseException.class)
	public void testConstructorBadString() throws ParseException {
		Predicate mypred = new Predicate("Do they speak English in What?"); 
	}
	
}
