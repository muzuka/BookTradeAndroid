package cpsc433;

import java.io.FileWriter;

public class SisyphusI {

	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Wrong number of args to SisyphusI!");
			System.exit(-1);
		}
		try {
			FileWriter outfilewriter = new FileWriter(args[0] + ".out");
			Environment env = Environment.get();
			env.fromFile(args[0]);
			outfilewriter.write(env.toString());
			Solution mySol = env.search(); 
			outfilewriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
