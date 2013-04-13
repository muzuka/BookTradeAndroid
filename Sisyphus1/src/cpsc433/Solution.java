package cpsc433;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;
import java.io.*;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public class Solution {

	// START LEGACY CODE
	public enum Verbosity {
		SUMMARY;
	}

	public static Verbosity verbosity;
	// END LEGACY CODE

	private Environment env;
	private String outfilename;
	private TreeMap<Person, Room> assignments;

	public Solution(Solution s) {
		outfilename = new String(s.outfilename);
		env = Environment.get();
		assignments = new TreeMap<Person, Room>(s.assignments);
	}

	/**
	 * Used to define the output file of the
	 * 
	 * @param outfilename
	 */
	public Solution(String outfilename) {
		this.outfilename = outfilename;
		env = Environment.get();
		assignments = new TreeMap<Person, Room>();
	}

	/**
	 * Generate the goodness value as per the soft constraint definitions of the
	 * Sisyphus I problem.
	 * 
	 * @return the goodness value of the current solution
	 */
	public int getGoodness() {
		int goodness = 0;

		for (Person p : env.getPeople()) {
			Room r = p.getAssignedTo();
			if (r == null) {
				r = assignments.get(p);
			}
			if (r != null) {
				Group g = p.getGroup();
				Project pr = p.getProject();
				if (p.getHeadsGroup()) {
					// Constraint 1: Group heads should have a large office
					if (r.getSize() != Room.Size.large) {
						goodness -= 40;
					}

					// Constraint 2: Group heads should be close to all members
					// of
					// their group
					// Constraint 3: Group heads should be located close to at
					// least one secretary in the group
					boolean hasSecretary = false;
					for (Person p2 : g.getGroupMembers()) {
						Room r2 = p2.getAssignedTo();
						if (r2 == null) {
							r2 = assignments.get(p2);
						}
						if (r2 != null) {
							if (!r.getClose(r2)) {
								goodness -= 2;
							} else if (p2.getSecretary()) {
								hasSecretary = true;
							}
						}
					}
					if (!hasSecretary) {
						goodness -= 30;
					}
				}
				if (p.getSecretary()) {
					// Constraint 4: Secretaries should share offices with other
					// secretaries
					boolean hasSecretary = false;
					for (Person p2 : r.getPeople()) {
						if (!p.equals(p2) && p2.getSecretary()) {
							hasSecretary = true;
						}
					}
					if (!hasSecretary) {
						goodness -= 5;
					}
				}
				if (g != null && p.getManager()) {
					// Constraint 5: Managers should be close to at least one
					// secretary in their group
					// Constraint 6: Managers should be close to their group's
					// head(s)
					// Constraint 7: Managers should be close to all members of
					// their group
					boolean hasSecretary = false;
					for (Person p2 : g.getGroupMembers()) {
						Room r2 = p2.getAssignedTo();
						if (r2 == null) {
							r2 = assignments.get(p2);
						}
						if (r2 != null) {
							if (!r.getClose(r2)) {
								goodness -= 2;
							} else if (p2.getSecretary()) {
								hasSecretary = true;
							}
						}
					}
					if (!hasSecretary) {
						goodness -= 20;
					}
					for (Person p2 : g.getGroupHeads()) {
						Room r2 = p2.getAssignedTo();
						if (r2 == null) {
							r2 = assignments.get(p2);
						}
						if (r2 != null && !r.getClose(r2)) {
							goodness -= 20;
						}
					}
				}
				if (p.getHeadsProject()) {
					// Constraint 8: The heads of projects should be close to
					// all members of their project
					for (Person p2 : pr.getProjectMembers()) {
						Room r2 = p2.getAssignedTo();
						if (r2 == null) {
							r2 = assignments.get(p2);
						}
						if (r2 != null && !r.getClose(r2)) {
							goodness -= 5;
						}
					}
					if (g != null && pr.getLargeProject()) {
						// Constraint 9: The heads of large projects should be
						// close to at least one secretary in their group
						boolean hasSecretary = false;
						for (Person p2 : g.getGroupMembers()) {
							Room r2 = p2.getAssignedTo();
							if (r2 == null) {
								r2 = assignments.get(p2);
							}
							if (r2 != null && p2.getSecretary()
									&& r.getClose(r2)) {
								hasSecretary = true;
							}
						}
						if (!hasSecretary) {
							goodness -= 10;
						}

						// Constraint 10: The heads of large projects should be
						// close to the head of their group
						for (Person p2 : g.getGroupHeads()) {
							Room r2 = p2.getAssignedTo();
							if (r2 == null) {
								r2 = assignments.get(p2);
							}
							if (r2 != null && !r.getClose(r2)) {
								goodness -= 10;
							}
						}
					}
				}
				for (Person p2 : r.getPeople()) {
					if (!p.equals(p2)) {
						// Constraint 11: A smoker shouldn't share an office
						// with a non-smoker
						if (p.getSmoker() && !p2.getSmoker()) {
							goodness -= 50;
						}

						// Constraint 12: Members of the same project should
						// not share an office
						if (pr != null && pr.getProjectMembers().contains(p2)) {
							goodness -= 7;
						}

						// Constraint 13: If a non-secretary hacker/non-hacker
						// shares an office, then he/she should share with
						// another hacker/non-hacker
						if (!p.getSecretary() && !p2.getSecretary()) {
							if (p.getHacker()) {
								if (!p2.getHacker()) {
									goodness -= 2;
								}
							} else {
								if (p2.getHacker()) {
									goodness -= 2;
								}
							}
						}

						// Constraint 14: People prefer to have their own
						// offices
						goodness -= 4;

						// Constraint 15: If two people share an office, they
						// should work together
						if (!p.getWorksWith(p2)) {
							goodness -= 3;
						}

						// Constraint 16: Two people shouldn't share a small
						// room
						if (r.getSize() == Room.Size.small) {
							goodness -= 25;
						}
					}
				}
			}
		}
		return goodness;
	}

	/**
	 * Returns a boolean value as to whether all persons within the solution
	 * have been assigned.
	 * 
	 * @return true if the solution is complete, false otherwise
	 */
	public boolean isComplete() {
		for (Person p : env.getPeople()) {
			Room r = p.getAssignedTo();
			if (r == null) {
				r = assignments.get(p);
				if (r == null) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Returns a boolean value as per whether the solution is solved
	 * 
	 * @return true if the solution is solved, false otherwise
	 */
	public boolean isSolved() {
		return !violatesHardConstraints() && isComplete();
	}

	public String getOutfileName() {
		return outfilename;
	}

	/**
	 * Returns true if the solution violates hard constraints, false otherwise.
	 * 
	 * @return true if the solution violates any hard constraints, false
	 *         otherwise.
	 */
	public boolean violatesHardConstraints() {
		return false;
	}

	public void writeFile() {
		PrintWriter writer;
		try {
			writer = new PrintWriter(new File(outfilename));
			for (Person p : env.getPeople()) {
				Room r = p.getAssignedTo();
				if (r == null) {
					r = assignments.get(p);
				}
				if (r != null) {
					writer.println("assign-to(" + p + ", " + r + ")");
				}
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Prints out all current room assignments as well as the current goodness
	 * of the solution.
	 */
	public String toString() {
		String str = "";
		for (Person p : env.getPeople()) {
			Room r = p.getAssignedTo();
			if (r != null) {
				str.concat("assign-to(" + p + ", " + r + ")\n");
			}
		}
		return str;
	}

	public boolean assign(Person p, Room r) {
		if (p.getAssignedTo() != null) {
			return false;
		}
		int numinr = numAssigned(r);
		if (numinr > ((p.getHeadsGroup() || p.getHeadsProject() || p
				.getManager()) ? 0 : 1)) {
			return false;
		}
		assignments.put(p, r);
		return true;
	}

	public int numAssigned(Room r) {
		int numinr = 0;
		for (Person p2 : r.getPeople()) {
			if (p2.getHeadsGroup() || p2.getManager() || p2.getHeadsProject()) {
				numinr++;
			}
			numinr++;
		}
		for (Entry<Person, Room> e : assignments.entrySet()) {
			if (e.getValue().equals(r)) {
				Person p2 = e.getKey();
				if (p2.getHeadsGroup() || p2.getManager()
						|| p2.getHeadsProject()) {
					numinr++;
				}
				numinr++;
			}
		}
		return numinr;
	}

	public boolean isAssigned(Person p) {
		if (p.getAssignedTo() != null) {
			return true;
		}
		if (assignments.containsKey(p)) {
			return true;
		}
		return false;
	}
}
