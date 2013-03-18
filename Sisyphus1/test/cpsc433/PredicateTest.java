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
			assertEquals("hello()", monad.toString()); 
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
			assertEquals("dragon-man(Trogdor)", unary.toString()); 
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
			assertEquals("trogdor-was(man, dragon)", binary.toString()); 
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
			assertEquals("what-is(my, age, again)", ternary.toString()); 
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
	
	/**
	 * Make sure that the constructor works on bad input. We want a parse
	 * exception to be thrown in that case. The minus sign is tested here. 
	 * @throws ParseException 
	 */
	@Test(expected= ParseException.class)
	public void testConstructorBadString2() throws ParseException {
		Predicate bad2 = new Predicate("totally-a-number(-iLied)"); 
	}
	
	/**
	 * Test to make sure that the getArity function works properly. We want: 
	 * <ul>
	 * 	<li>test() = 0</li>
	 * 	<li>test(one) = 1</li>
	 *  <li>test(one, two) = 2</li>
	 *  <li>test(one, two, three) = 3</li>
	 * </ul>
	 */
	@Test
	public void testArity() throws ParseException {
		Predicate monad = new Predicate("hello()");
		Predicate unary = new Predicate("dragon-man(Trogdor)"); 
		Predicate binary = new Predicate("trogdor-was(man, dragon)"); 
		Predicate ternary = new Predicate("what-is(my, age, again)"); 
		
		assertEquals(0, monad.getArity()-1); // Parameter for Monads is the empty string... Fix?  
		assertEquals(1, unary.getArity()); 
		assertEquals(2, binary.getArity()); 
		assertEquals(3, ternary.getArity()); 
	}
	
}
