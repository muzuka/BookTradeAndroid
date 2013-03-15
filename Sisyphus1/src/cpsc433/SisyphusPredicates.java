/**
 * 
 */
package cpsc433;

import java.util.TreeSet;

import cpsc433.Predicate.ParamType;

/**
 * This interface should be <code>implement</code>ed by any class that reads
 * Sisyphus I predicates. The implementing class should also <code>extend</code>
 * the class {@link cpsc433.PredicateReader}.
 * <p>
 * The method declarations here form the stubs you will need to implement all
 * the predicates for the assignments. The static String definitions here don't
 * need to be overridden: they will automatically work with
 * {@link cpsc433.PredicateReader} to yield help information when the predicate
 * "!help()" is interpreted.
 * <p>
 * For more information on the semantics of these predicates, see the CPSC 433
 * W06 assignment specification.
 * 
 * <p>
 * Copyright: Copyright (c) 2005-2006, Department of Computer Science,
 * University of Calgary. Permission to use, copy, modify, distribute and sell
 * this software and its documentation for any purpose is hereby granted without
 * fee, provided that the above copyright notice appear in all copies and that
 * both that copyright notice and this permission notice appear in supporting
 * documentation. The Department of Computer Science makes no representations
 * about the suitability of this software for any purpose. It is provided
 * "as is" without express or implied warranty.
 * </p>
 * 
 * @author <a href="http://www.cpsc.ucalgary.ca/~kremer/">Rob Kremer</a>
 * 
 */
public interface SisyphusPredicates {
	public static String h_person = "query or assert <id> is a person";

	public void a_person(String p);

	public boolean e_person(String p);

		
	
	
	public static String h_secretary = "query or assert person <id> is a secretary";

	public void a_secretary(String p);

	public boolean e_secretary(String p);

	
	
	
	public static String h_researcher = "query or assert person <id> is a researcher";

	public void a_researcher(String p);

	public boolean e_researcher(String p);

	
	
	
	
	
	public static String h_manager = "query or assert person <id> is a manager";

	public void a_manager(String p);

	public boolean e_manager(String p);

	
	
	
	
	
	
	public static String h_smoker = "query or assert person <id> is a smoker";

	public void a_smoker(String p);

	public boolean e_smoker(String p);

	
	
	
	
	public static String h_hacker = "query or assert person <id> is a hacker";

	public void a_hacker(String p);

	public boolean e_hacker(String p);

	
	
	
	
	public static String h_in_group = "(syn: group) query or assert person <id1> is a member of group <id2>";

	public void a_in_group(String p, String grp);

	public boolean e_in_group(String p, String grp);
	
	public void a_group(String p, String grp);

	public boolean e_group(String p, String grp);

	
	
	
	public static String h_in_project = "(syn: project) query or assert person <id1> is a member of project <id2>";

	public void a_in_project(String p, String prj);

	public boolean e_in_project(String p, String prj);

	public void a_project(String p, String prj);

	public boolean e_project(String p, String prj);

	
	
	
	
	public static String h_heads_group = "query or assert person <id1> heads group <id2>";

	public void a_heads_group(String p, String grp);

	public boolean e_heads_group(String p, String grp);

	
	
	
	public static String h_heads_project = "query or assert person <id1> heads project <id2>";

	public void a_heads_project(String p, String prj);

	public boolean e_heads_project(String p, String prj);

	
	
	
	
	
	public static String h_works_with = "query or assert person <id> works with [the person <id2>/all the people in <set>] (reflexive)";

	public void a_works_with(String p, TreeSet<Pair<ParamType, Object>> p2s);

	public boolean e_works_with(String p, TreeSet<Pair<ParamType, Object>> p2s);

	public void a_works_with(String p, String p2);

	public boolean e_works_with(String p, String p2);

	
	
	
	
	public static String h_assign_to = "query or assert person <id1> is assigned to <id2>";

	public void a_assign_to(String p, String room) throws Exception;

	public boolean e_assign_to(String p, String room);

	
	
	
	
	
	// ROOMS
	public static String h_room = "query or assert <id> is a room";

	public void a_room(String r);

	public boolean e_room(String r);

	
	
	
	
	
	public static String h_close = "query or assert room <id> is close to [the room <id2>/all the rooms in <set>] (reflexive)";

	public void a_close(String room, String room2);

	public boolean e_close(String room, String room2);

	public void a_close(String room, TreeSet<Pair<ParamType, Object>> set);

	public boolean e_close(String room, TreeSet<Pair<ParamType, Object>> set);

	
	
	
	
	public static String h_large_room = "query or assert <id> is a large-sized room";

	public void a_large_room(String r);

	public boolean e_large_room(String r);

	
	
	
	
	
	public static String h_medium_room = "query or assert <id> is a medium-sized room";

	public void a_medium_room(String r);

	public boolean e_medium_room(String r);

	
	
	
	
	
	public static String h_small_room = "query or assert <id> is a small-sized room";

	public void a_small_room(String r);

	public boolean e_small_room(String r);

	
	
	
	
	// GROUPS
	public static String h_group = "query or assert <id> is a group";

	public void a_group(String g);

	public boolean e_group(String g);

	
	
	
	
	
	
	
	// PROJECTS
	public static String h_project = "query or assert <id> is a project";

	public void a_project(String p);

	public boolean e_project(String p);

	
	
	
	
	
	
	public static String h_large_project = "query or assert <id> is a large project";

	public void a_large_project(String prj);

	public boolean e_large_project(String prj);

}
