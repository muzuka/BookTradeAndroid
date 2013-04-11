package cpsc433;

import java.util.Date;

public class SisyphusI {
	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Wrong number of args to SisyphusI!");
			System.exit(-1);
		}
		
		Date now = new Date();
		Date endtimes = new Date(now.getTime() + Long.parseLong(args[1]));
		
		Environment env = Environment.get();
		env.fromFile(args[0]);
		
		OrTreeNode root = new OrTreeNode(null, endtimes, args[0] + ".out");
		root.search();
	}
}
