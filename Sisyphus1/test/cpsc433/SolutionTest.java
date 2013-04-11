/**
 * 
 */
package cpsc433;

import static org.junit.Assert.*;

import java.text.ParseException;

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
		mySol = new Solution("test.txt.out"); 
		//mySol.updateEnvironment(env); 
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

	/**
	 * Test the goodness function for the empty assignment. It should
	 * return 0 (assuming that no one in the test environment has been
	 * assigned to a room) 
	 */
	@Test
	public void testEmptyGoodness() {
		assertEquals(0, mySol.getGoodness()); 
	}

	/**
	 * If either the person or the room does not exist, 
	 * then we should get false. 
	 */
	@Test
	public void testNonexistentAssignment() {
		env.fromFile("test.txt"); 
		Entity chuck = new Entity("Chuck"); 
		Entity jurgen = new Entity("Jurgen"); 
		Entity room0 = new Entity("000"); 
		Entity goodRoom = new Entity("C5113"); 
		assertEquals(false, (mySol.assign(chuck, room0))); 
		assertEquals(false, (mySol.assign(jurgen, room0)));
		assertEquals(false, (mySol.assign(chuck, goodRoom)));
	}
	
	/**
	 * If we make an assignment with people and rooms that do exist, 
	 * then we should get the value 'true' returned. Note that hard 
	 * constraints are not tested here. 
	 */
	@Test
	public void testGoodAssignment() {
		env.fromFile("test.txt"); 
		
		Entity jurgen = new Entity("Jurgen"); 
		Entity goodRoom = new Entity("C5113"); 
		
		if(!env.e_person("Jurgen")){
			fail("Jurgen is not present within the environment"); 
		} 
		
		if(!env.e_room("C5113")) { 
			fail("C5113 is not present within the environment"); 
		}
		
		String workstring = "assign-to(" + jurgen + ", " + goodRoom + ")"; 
		Predicate workPred;
		try {
			workPred = new Predicate(workstring);
			assertEquals(true, mySol.assign(jurgen, goodRoom)); 
			assertEquals(true, mySol.getAssignments().contains(workPred)); 
		} catch (ParseException e) {
			fail("Work String was malformed"); 
		} 
	}
	
	/**
	 * Tests Soft Constraint 1: 
	 * 		<ul> 
	 * 		<li>Group Heads should have a Large Office</li> 
	 * 		<li>Penalty: -40</li>
	 * 		</ul> 
	 */
	@Test
	public void testSoftConstraint1() {
		// Good Case
		env.a_person("Steve"); 
		env.a_room("Bedroom");
		env.a_group("Warcraft Guild");
		env.a_in_group("Steve", "Warcraft Guild"); 
		env.a_heads_group("Steve", "Warcraft Guild"); 
		env.a_large_room("Bedroom"); 
		
		Entity steve = new Entity("Steve");
		Entity bedroom = new Entity("Bedroom");		
		
		assertEquals(true, mySol.assign(steve, bedroom)); 
		assertEquals(0, mySol.getNumberViolations(0));
				
		// Bad Case 
		env.a_person("Harry"); 
		env.a_room("Broom Closet");
		env.a_group("Hogwarts");
		env.a_in_group("Harry", "Hogwarts"); 
		env.a_heads_group("Harry", "Hogwarts");
		env.a_small_room("Broom Closet");

		Entity harry = new Entity("Harry"); 
		Entity closet = new Entity("Broom Closet"); 
		
		assertEquals(true, mySol.assign(harry, closet));
		assertEquals(1, mySol.getNumberViolations(0)); 
		
		assertEquals(-40, mySol.getPenalty(0)); 
	}
	
	/**
	 * Tests Soft Constraint 2: 
	 * 		<ul> 
	 * 		<li>Group Heads should be close to all members of their group</li> 
	 * 		<li>Penalty: -2</li>
	 * 		</ul> 
	 */
	@Test
	public void testSoftConstraint2() {
		env.a_person("Joker");
		env.a_person("Penguin"); 
		
		env.a_group("Villains");
		env.a_in_group("Joker", "Villains"); 
		env.a_in_group("Penguin", "Villains"); 
		env.a_heads_group("Joker", "Villains"); 
		
		env.a_room("Arkham Asylum"); 
		env.a_room("Iceberg Lounge"); 
		
		Entity joker = new Entity("Joker"); 
		Entity penguin = new Entity("Penguin"); 
		Entity arkham = new Entity("Arkham Asylum"); 
		Entity lounge = new Entity("Iceberg Lounge"); 
		
		mySol.assign(joker, arkham); 
		mySol.assign(penguin, lounge); 
		
		assertEquals(1, mySol.getNumberViolations(1));
		assertEquals(-2, mySol.getPenalty(1)); 
	}
	
	/**
	 * Tests Soft Constraint 3: 
	 * 		<ul> 
	 * 		<li>Group Heads should be close to at least one secretary in the group</li> 
	 * 		<li>Penalty: -30</li>
	 * 		</ul> 
	 */
	@Test
	public void testSoftConstraint3() {
		env.a_person("Joker");
		env.a_secretary("Penguin"); 
		
		env.a_group("Villains");
		env.a_in_group("Joker", "Villains"); 
		env.a_in_group("Penguin", "Villains"); 
		env.a_heads_group("Joker", "Villains"); 
		
		env.a_room("Arkham Asylum"); 
		env.a_room("Iceberg Lounge"); 
		
		Entity joker = new Entity("Joker"); 
		Entity penguin = new Entity("Penguin"); 
		Entity arkham = new Entity("Arkham Asylum"); 
		Entity lounge = new Entity("Iceberg Lounge"); 
		
		mySol.assign(joker, arkham); 
		mySol.assign(penguin, lounge); 
		
		assertEquals(-30, mySol.getGoodness()); 
	}
	
	/**
	 * Tests Soft Constraint 4: 
	 * 		<ul> 
	 * 		<li>Secretaries should share offices with other secretaries</li> 
	 * 		<li>Penalty: -5</li>
	 * 		</ul> 
	 */
	@Test
	public void testSoftConstraint4() {
		env.a_person("Joker");
		env.a_secretary("Penguin"); 
		
		env.a_group("Villains");
		env.a_in_group("Joker", "Villains"); 
		env.a_in_group("Penguin", "Villains"); 
		
		env.a_room("Arkham Asylum"); 
		
		Entity joker = new Entity("Joker"); 
		Entity penguin = new Entity("Penguin"); 
		Entity arkham = new Entity("Arkham Asylum"); 
		
		mySol.assign(joker, arkham); 
		mySol.assign(penguin, arkham); 
		
		assertEquals(-5, mySol.getGoodness()); 
	}
	
	/**
	 * Tests Soft Constraint 5: 
	 * 		<ul> 
	 * 		<li>Managers should be close to at least one secretary of their group</li> 
	 * 		<li>Penalty: -20</li>
	 * 		</ul> 
	 */
	@Test
	public void testSoftConstraint5() {
		env.a_manager("Joker");
		env.a_secretary("Penguin"); 
		
		env.a_group("Villains");
		env.a_in_group("Joker", "Villains"); 
		env.a_in_group("Penguin", "Villains"); 
		
		env.a_room("Arkham Asylum"); 
		env.a_room("Iceberg Lounge"); 
		
		Entity joker = new Entity("Joker"); 
		Entity penguin = new Entity("Penguin"); 
		Entity arkham = new Entity("Arkham Asylum"); 
		Entity lounge = new Entity("Iceberg Lounge"); 
		
		mySol.assign(joker, arkham); 
		mySol.assign(penguin, lounge); 
		
		assertEquals(-20, mySol.getGoodness()); 
	}
	
	/**
	 * Tests Soft Constraint 6: 
	 * 		<ul> 
	 * 		<li>Managers should be close to their Group's Head </li> 
	 * 		<li>Penalty: -20</li>
	 * 		</ul> 
	 */
	@Test
	public void testSoftConstraint6() {
		env.a_person("Joker");
		env.a_manager("Penguin"); 
		
		env.a_group("Villains");
		env.a_in_group("Joker", "Villains"); 
		env.a_in_group("Penguin", "Villains"); 
		env.a_heads_group("Joker", "Villains"); 
		
		env.a_room("Arkham Asylum"); 
		env.a_room("Iceberg Lounge"); 
		
		Entity joker = new Entity("Joker"); 
		Entity penguin = new Entity("Penguin"); 
		Entity arkham = new Entity("Arkham Asylum"); 
		Entity lounge = new Entity("Iceberg Lounge"); 
		
		mySol.assign(joker, arkham); 
		mySol.assign(penguin, lounge); 
		
		assertEquals(-20, mySol.getGoodness()); 
	}
	
	/**
	 * Tests Soft Constraint 7: 
	 * 		<ul> 
	 * 		<li>Managers should be close to all members of their group</li> 
	 * 		<li>Penalty: -2</li>
	 * 		</ul> 
	 */
	@Test
	public void testSoftConstraint7() {
		env.a_manager("Joker");
		env.a_person("Penguin"); 
		
		env.a_group("Villains");
		env.a_in_group("Joker", "Villains"); 
		env.a_in_group("Penguin", "Villains"); 
		
		env.a_room("Arkham Asylum"); 
		env.a_room("Iceberg Lounge"); 
		
		Entity joker = new Entity("Joker"); 
		Entity penguin = new Entity("Penguin"); 
		Entity arkham = new Entity("Arkham Asylum"); 
		Entity lounge = new Entity("Iceberg Lounge"); 
		
		mySol.assign(joker, arkham); 
		mySol.assign(penguin, lounge); 
		
		assertEquals(-2, mySol.getGoodness()); 
	}
	
	/**
	 * Tests Soft Constraint 8: 
	 * 		<ul> 
	 * 		<li>All heads of projects should be close to all members of their project</li> 
	 * 		<li>Penalty: -5</li>
	 * 		</ul> 
	 */
	@Test
	public void testSoftConstraint8() {
		env.a_person("Joker");
		env.a_person("Penguin"); 
		
		env.a_project("Conquer Gotham");
		env.a_in_project("Joker", "Conquer Gotham"); 
		env.a_in_project("Penguin", "Conquer Gotham"); 
		env.a_heads_project("Joker", "Conquer Gotham"); 
		
		env.a_room("Arkham Asylum"); 
		env.a_room("Iceberg Lounge"); 
		
		Entity joker = new Entity("Joker"); 
		Entity penguin = new Entity("Penguin"); 
		Entity arkham = new Entity("Arkham Asylum"); 
		Entity lounge = new Entity("Iceberg Lounge"); 
		
		mySol.assign(joker, arkham); 
		mySol.assign(penguin, lounge); 
		
		assertEquals(-5, mySol.getGoodness()); 
	}
	
	/**
	 * Tests Soft Constraint 9: 
	 * 		<ul> 
	 * 		<li>Heads of Large Projects should be close to at least one secretary in their group</li> 
	 * 		<li>Penalty: -10</li>
	 * 		</ul> 
	 */
	@Test
	public void testSoftConstraint9() {
		env.a_person("Master Chief");
		env.a_person("Cortana");
		env.a_large_project("Destroy the Halo");
		env.a_group("The Good Guys"); 
		env.a_secretary("Cortana"); 
		
		env.a_in_group("Master Chief", "The Good Guys"); 
		env.a_in_group("Cortana", "The Good guys"); 
		
		env.a_heads_project("Master Chief", "Destroy the Halo"); 
		env.a_heads_group("Cortana", "The Good Guys"); 
		
		// Note that these two rooms are NOT at all close
		env.a_room("The Halo");
		env.a_room("The Autumn"); 
		
		Entity chief = new Entity("Master Chief"); 
		Entity cortana = new Entity("Cortana"); 
		Entity halo = new Entity("The Halo"); 
		Entity autumn = new Entity("The Autumn");
		
		mySol.assign(chief, halo); 
		mySol.assign(cortana, autumn); 
		
		assertEquals(-10, mySol.getGoodness()); 
	}
	
	/**
	 * Tests Soft Constraint 10: 
	 * 		<ul> 
	 * 		<li>Heads of Large Projects should be close to the head of their group</li> 
	 * 		<li>Penalty: -10</li>
	 * 		</ul> 
	 */
	@Test
	public void testSoftConstraint10() {
		env.a_person("Master Chief");
		env.a_person("Cortana");
		env.a_large_project("Destroy the Halo");
		env.a_group("The Good Guys"); 
		
		env.a_in_group("Master Chief", "The Good Guys"); 
		env.a_in_group("Cortana", "The Good guys"); 
		
		env.a_heads_project("Master Chief", "Destroy the Halo"); 
		env.a_heads_group("Cortana", "The Good Guys"); 
		
		// Note that these two rooms are NOT at all close
		env.a_room("The Halo");
		env.a_room("The Autumn"); 
		
		Entity chief = new Entity("Master Chief"); 
		Entity cortana = new Entity("Cortana"); 
		Entity halo = new Entity("The Halo"); 
		Entity autumn = new Entity("The Autumn");
		
		mySol.assign(chief, halo); 
		mySol.assign(cortana, autumn); 
		
		assertEquals(-10, mySol.getGoodness()); 
	}
	
	/**
	 * Tests Soft Constraint 11: 
	 * 		<ul> 
	 * 		<li>A Smoker shouldn't share an office with a non-smoker</li> 
	 * 		<li>Penalty: -50</li>
	 * 		</ul> 
	 */
	@Test
	public void testSoftConstraint11() {
		env.a_person("Snake");
		env.a_person("Raiden");
		env.a_smoker("Snake"); 
		env.a_room("Metal Gear Rex");
		
		Entity snake = new Entity("Snake"); 
		Entity raiden = new Entity("Raiden"); 
		Entity rex = new Entity("Metal Gear Rex"); 
		
		mySol.assign(snake, rex);
		mySol.assign(raiden, rex); 
		
		assertEquals(-50, mySol.getGoodness());  
	}
	
	/**
	 * Tests Soft Constraint 12: 
	 * 		<ul> 
	 * 		<li>Members of the same project should not share an office</li> 
	 * 		<li>Penalty: -7</li>
	 * 		</ul> 
	 */
	@Test
	public void testSoftConstraint12() {
		env.a_person("Vegeta");
		env.a_person("Nappa");
		env.a_project("Destroy Earth");
		env.a_project("Find Dragonballs"); 
		env.a_room("Namek"); 
		
		env.a_in_project("Vegeta", "Find Dragonballs");
		env.a_in_project("Nappa", "Destroy Earth"); 
		
		Entity vegeta = new Entity("Vegeta"); 
		Entity nappa = new Entity("Nappa"); 
		Entity namek = new Entity("Namek"); 
		
		mySol.assign(vegeta, namek); 
		mySol.assign(nappa, namek); 
		
		assertEquals(-7, mySol.getGoodness()); 
		
	}
	
	/**
	 * Tests Soft Constraint 13: 
	 * 		<ul> 
	 * 		<li>if a non secretary hacker/non-hacker shares an office, then they should share with another hacker/non-hacker</li> 
	 * 		<li>Penalty: -2</li>
	 * 		</ul> 
	 */
	@Test
	public void testSoftConstraint13() {
		
		env.a_person("Link");
		env.a_person("Zelda"); 
		env.a_person("Water Temple");
		
		Entity link = new Entity("Link"); 
		Entity zelda = new Entity("Zelda"); 
		Entity temple = new Entity("Water Temple"); 
		
		mySol.assign(zelda, temple); 
		mySol.assign(link, temple); 
		
		assertEquals(0, mySol.getGoodness()); 
		
		
		env.a_hacker("Spike Spiegel");
		env.a_hacker("Jet Black"); 
		env.a_room("The Bebop"); 
		
		Entity spike = new Entity("Spike Spiegel"); 
		Entity jet = new Entity("Jet Black"); 
		Entity bebop = new Entity("The Bebop"); 
		
		assertEquals(0, mySol.getGoodness()); 
		
		
		env.a_person("John Wayne");
		env.a_hacker("Bill Gates");
		env.a_room("A Bar"); 
		
		Entity wayne = new Entity("John Wayne"); 
		Entity gates = new Entity("Bill Gates"); 
		Entity saloon = new Entity("A Bar"); 
		
		mySol.assign(wayne, saloon); 
		mySol.assign(gates, saloon);
		
		assertEquals(-2, mySol.getGoodness()); 
	}
	
	/**
	 * Tests Soft Constraint 14: 
	 * 		<ul> 
	 * 		<li>people prefer to have their own offices</li> 
	 * 		<li>Penalty: -4</li>
	 * 		</ul> 
	 */
	@Test
	public void testSoftConstraint14() {		
		env.a_person("Dr. Alan Grant"); 
		env.a_person("Velociraptors"); 
		env.a_room("Jurassic Park"); 
		
		Entity grant = new Entity("Dr. Alan Grant"); 
		Entity raptors = new Entity("Velociraptors"); 
		Entity jp = new Entity("Jurassic Park"); 
		
		mySol.assign(grant, jp);
		
		assertEquals(0, mySol.getGoodness()); 
		
		mySol.assign(raptors, jp); 
		
		assertEquals(-4, mySol.getGoodness()); 
	}
	
	/**
	 * Tests Soft Constraint 15: 
	 * 		<ul> 
	 * 		<li>if two people share an office, they should work together</li> 
	 * 		<li>Penalty: -3</li>
	 * 		</ul> 
	 */
	@Test
	public void testSoftConstraint15() {
		env.a_person("Goku"); 
		env.a_person("Krillin"); 
		env.a_person("Nappa"); 
		env.a_room("Space Pod"); 
		env.a_works_with("Goku", "Krillin");
		
		Entity goku = new Entity("Goku"); 
		Entity nappa = new Entity("Nappa"); 
		Entity room = new Entity("Space Pod"); 
		
		mySol.assign(goku, room);
		mySol.assign(nappa, room); 
		
		assertEquals(-3, mySol.getGoodness()); 
	}
	
	/**
	 * Tests Soft Constraint 16: 
	 * 		<ul> 
	 * 		<li>Two people shouldn't share a small room</li> 
	 * 		<li>Penalty: -25</li>
	 * 		</ul> 
	 */
	@Test
	public void testSoftConstraint16() {
		env.a_person("Vegeta"); 
		env.a_person("Nappa"); 
		env.a_room("Space Pod"); 
		env.a_small_room("Space Pod"); 
		
		Entity vegeta = new Entity("Vegeta"); 
		Entity nappa = new Entity("Nappa"); 
		Entity spacepod = new Entity("Space Pod"); 
			
		mySol.assign(vegeta, spacepod); 
		mySol.assign(nappa, spacepod); 
			
		// additional -4 b/c they have to share an office!
		assertEquals(-25-4, mySol.getGoodness()); 
	}
}
