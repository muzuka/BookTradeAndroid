package cpsc433;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class SisyphusI {
	
	public static void main(String[] args) {
		if (args.length != 2) {
			System.exit(-1);
		}
		String infilepath = args[0];
		File infile = new File(infilepath);
		String outfilepath = infile.getPath() + ".out";
		File outfile = new File(outfilepath);
		try {
			FileReader infilereader = new FileReader(infile);
			FileWriter outfilewriter = new FileWriter(outfile);
			outfilewriter.write("Hello, world!");
			infilereader.close();
			outfilewriter.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
