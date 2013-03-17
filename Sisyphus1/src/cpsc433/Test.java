/**
 * 
 */
package cpsc433;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * main() for Testing a program in the CPSC 433 W06 assignment.  The main() 
 * method in the sisyphusI.Test class must comply to the following syntax:
 * <pre>
 *     java -classpath sisyphus.jar sisyphusI.SisyphusI filename maxtime
 * </pre>
 * where:
 * <ul>
 * <li> <em>sisyphus</em> is the name of the program's jar file.
 * <li> <em>filename</em> is the name of an problem specification input 
 * 			file of predicates as documented in the assignment spec and
 * 			specificed by the {@link cpsc433.SisyphusPredicates} interface.
 * <li> <em>maxtime</em> is and integer time in seconds specifying the time limit
 * 			for which the program is not allowed to exceed.
 * </ul>
 * 
 * If the program finds a solution, it MUST output a solution to a file with
 * the same name (including extension) suffixed with ".out".  ie: if the 
 * input filename is "test.txt", the output filename must be "test.txt.out".
 * It the program finds a solution and successfully writes out the solution
 * to the output file, it must exit with an exit status of 0.
 * <p>
 * If the program fails, it should exit with a status as follows:
 * <ul>
 * <li> <em>-1</em>: failed to read the input file 
 * <li> <em>-2</em>: no solution found 
 * <li> <em>-3</em>: failed to write the output file 
 * </ul>
 *  
 * If the program is interrupted (eg: ^C) it will nevertheless write out
 * the best solution so far to the output file.
 * <p>
 * 
 * <p>Copyright: Copyright (c) 2005-2006, Department of Computer Science, University 
 * of Calgary.  Permission to use, copy, modify, distribute and sell this 
 * software and its documentation for any purpose is hereby granted without 
 * fee, provided that the above copyright notice appear in all copies and that
 * both that copyright notice and this permission notice appear in supporting 
 * documentation.  The Department of Computer Science makes no representations
 * about the suitability of this software for any purpose.  It is provided
 * "as is" without express or implied warranty.</p>
 *
 * @author <a href="http://www.cpsc.ucalgary.ca/~kremer/">Rob Kremer</a>
 *
 */
public class Test {
	
	/**
	 * Exit status values that the program should return.
	 */
	static final int 	SUCCESS				=0, 
						FAILED_READ			=-1, 
						NO_SOLUTION_FOUND	=-2, 
						WRITE_FAILED		=-3;
	
	/**
	 * The default for the max time command line parameter. 
	 */
	static final int	DEFAULT_MAX_TIME	=30000;
	
	static PrintStream traceFile;

	final static int	S_SUCCESS						=  0,
						S_PROCESS_FORCEBLY_TERMINATED	=  1,
						S_NO_OUTPUT						= -1,
						S_UNPARSEABLE_OUTPUT			= -2,
						S_NO_OUTPUT_FORCED				= -3,
						S_UNPARSEABLE_OUTPUT_FORCED		= -4,
						S_BAD_PROGRAM_FILENAME			= -5,
						S_CLASSS_NOT_FOUND				= -6,
						S_CANNOT_OPEN_JAR				= -7,
						S_DATAFILE_MISSING				= -8,
						S_UNEXPECTED_EXCEPTION			= -9;

	static long max(long a, long b) {return a>b?a:b;}
	
	/**
	 * Searches for a solution
	 * @param runtime the length of time (in seconds) the program is allowed
	 * to search for a solution.
	 * @return true for success, false for failure.
	 */
	static int search(String searchPrg, String datafile, long runtime) {
		File prg = new File(searchPrg);
		String searchPackage = "cpsc433";
		String searchClass = "SisyphusI";
		if (!prg.exists()) {
			println("program file \""+searchPrg+"\" does not exist.");
			return S_BAD_PROGRAM_FILENAME;
		}
		else {
			try {
				ZipFile zip = new ZipFile(prg);
				ZipEntry entry = zip.getEntry(searchPackage+"/"+searchClass+".class");
				if (entry==null) {
					println("Couldn't find class "+searchPackage+"/"+searchClass+", trying sisyphusI/SisyphusI...");
					searchPackage = "sisyphusI";
					entry = zip.getEntry(searchPackage+"/"+searchClass+".class");
				  if (entry==null) {
					  println("class "+searchPackage+"/"+searchClass+".class not found in \""+searchPrg+"\".");
					  return S_CLASSS_NOT_FOUND;
				  }
				}
			}
			catch (Exception ex) {
				println("cannot open jar file \""+searchPrg+"\": "+ex.toString());
				return S_CANNOT_OPEN_JAR;
			}
		}
		if (!new File(datafile).exists()) {
			println("data file \""+datafile+"\" does not exist.");
			return S_DATAFILE_MISSING;
		}
		Process proc;
		String  command = "java -classpath "+ searchPrg +" "+searchPackage+"."+searchClass+" "+ datafile +" "+ runtime;
		synchronized (command) {
			try {
				println("Executing: " + command);
				
				//proc = Runtime.getRuntime().exec(command);
				ProcessBuilder pb = new ProcessBuilder("java", "-classpath", searchPrg, 
						searchPackage+"."+searchClass, datafile, String.valueOf(runtime));
				pb.redirectErrorStream(true);
				proc = pb.start();
				InputStream instream = proc.getInputStream();

				long start = System.currentTimeMillis();

				long limit = start + max(runtime+2000, new Double(runtime*1.3).longValue());
				byte[] buf = new byte[1000];
				while (System.currentTimeMillis() < limit) {
					int avail = instream.available();
					if (avail > 0) {
						int count = instream.read(buf,0,1000);
						write(buf,0,count);
					} else {
						synchronized (Runtime.getRuntime()) {
							Runtime.getRuntime().wait(1000);
						}
					}
					try { // check to see if the process is still running -- if it's stopped, break;
						proc.exitValue();
						break;
					} catch (IllegalThreadStateException ex2) {}
				}
				
				try {
					println("Subprocess Exit code = " + proc.exitValue() + " at " + (System.currentTimeMillis()-start) +"ms");
					return S_SUCCESS;
				}
				catch (IllegalThreadStateException ex2) {
					println("Process failed to terminate, forcably terminated" + " at " + (System.currentTimeMillis()-start) +"ms");
					proc.destroy();
					return S_PROCESS_FORCEBLY_TERMINATED;
				}
				
			} catch (Throwable ex) {
				System.err.println(ex);
				ex.printStackTrace();
				return S_UNEXPECTED_EXCEPTION;
			}
		}
	}
	
	/**
	 * The main method that reads the command line arguments and executes
	 * the search algorithm to find a solution, which it writes out.  Iff 
	 * there are no arguments on the command line, it goes into "command
	 * mode" as a convenience.
	 * @param args the command line arguments.
	 */
	public static void main(String[] args) {

		try {
			traceFile = new PrintStream(new FileOutputStream("trace.out"));
			traceFile.print("Trace sisyphusI.Test");
			for (String s: args) traceFile.print(" "+s);
			traceFile.println("\n"+new java.util.Date());
		}
		catch (Exception ex) {traceFile = null;}

		if (args.length>=2) {
			long runtime = DEFAULT_MAX_TIME;
			if (args.length<3) {
				printSynopsis();
				println("No run time given; assuming run time of "+runtime+" seconds");
			}
			else {
				runtime = new Long(args[2]).longValue();
			}

			evaluate(args[0],args[1],runtime);
		}
		else if (args.length==1) {
			evaluate(args[0]);
		}
		else { // go into "command mode" if there's nothing on the command line
			PredicateReader env = Environment.get();
			printSynopsis();
			commandMode(env);
		}

		if (traceFile!=null) {
			traceFile.println(new java.util.Date());
			traceFile.close();
		}
	}

	protected static int evaluate(String searchPrg, String datafile, long runtime) {
	    String outfilename = makeOutfilename(datafile);
	    int ret = search(searchPrg, datafile, runtime); 
	    if (ret >= 0) {
			if (!new File(outfilename).exists()) {
				println("search program did not generate expected output file \""+outfilename+"\".");
				return (ret==S_PROCESS_FORCEBLY_TERMINATED)?S_NO_OUTPUT_FORCED:S_NO_OUTPUT;
			}
			else {
				Environment env = Environment.get();
				Solution.verbosity = Solution.Verbosity.SUMMARY;
				env.fixedAssignments = true;
				env.fromFile(datafile);
				env.fixedAssignments = false;
				env.currentSolution = new Solution(outfilename);
				if (env.currentSolution!=null) {
					println("****Search found solution:");
					println(env.currentSolution.toString());
					// println(env.currentSolution.getName()+":
					// isSolved() -> "+env.currentSolution.isSolved());
					// println(env.currentSolution.getName()+":
					// getGoodness() -> "+env.currentSolution.getGoodness());
					return ret;
				}
				return (ret==S_PROCESS_FORCEBLY_TERMINATED)?S_UNPARSEABLE_OUTPUT_FORCED:S_UNPARSEABLE_OUTPUT;
			}
	    }
	    else {
	    	println("No solution found.");
	    	return ret;
	    }

	}

	static class TestInst {
		public TestInst(String d, long t, boolean completable, boolean solvable) {
			datafile=d; timelimit=t; this.completable=completable; this.solvable=solvable;}
		public String datafile;
		public long timelimit;
		public boolean completable;
		public boolean solvable;
		public int result;
		public long time;
		public boolean complete;
		public boolean solved;
		public int utility;
	}
	
	protected static void evaluate(String searchPrg) {
		TestInst[] tests = {
							new TestInst("sisyphus.txt"			,10000,	true,  true ),
							new TestInst("small.txt"			,10000,	true,  true ),
							new TestInst("big.txt"				,20000,	true,  true ),
							new TestInst("huge.txt"				,60000,	true,  true ),
							new TestInst("hugest.txt"				,60000,	true,  true ),
							new TestInst("huge2.txt"			, 1000,	true,  true ),
							new TestInst("tooManyPeople.txt"	,10000,	false, false),
							new TestInst("tooManyRooms.txt"		,10000,	true,  true ),
							new TestInst("empty.txt"			,10000,	true,  true ),
							new TestInst("badInput.txt"			,10000,	true,  true ),
							new TestInst("pre-assigned.txt"			,10000,	true,  true ),
							};
		for (int i=0,max=tests.length; i<max; i++) {
			long start = System.currentTimeMillis();
			Environment.reset();
			println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n"
					          +">>>>>>>>>>>>>>>>> test "+i+": "+tests[i].datafile+" ("+tests[i].timelimit/1000+"s) >>>>>>>>>>>>>>>>>\n"
			                  +">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			tests[i].result = evaluate(searchPrg,tests[i].datafile,tests[i].timelimit);
			println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n"
			          		  +"<<<<<<<<<<<<<<<<< test "+i+": "+tests[i].datafile+" ("+tests[i].timelimit/1000+"s) <<<<<<<<<<<<<<<<<\n"
			          		  +"<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n\n\n");
			if (tests[i].result>=0) {
				Environment env = Environment.get();
				tests[i].complete = env.currentSolution.isComplete();
				tests[i].solved = env.currentSolution.isSolved();
				tests[i].utility = env.currentSolution.getGoodness();
			}
			tests[i].time = System.currentTimeMillis()-start;
		}
		println("-------------------------------------SUMMARY------------------------------------------------");
		println("Test                          Status      Complete   Stat  Solved   Stat  Util   Limit Time");
		println("----------------------------- ----------- ---------- ----- -------- ----- ------ ----- -----");
			for (int i=0,max=tests.length; i<max; i++) {
				TestInst t = tests[i];
				String res = status2string(t.result);
				int secLimit = new Long(t.timelimit/1000).intValue();
				int secs = new Long(t.time/1000).intValue();
				println(t.datafile									+pad(t.datafile,30)+
						res											+pad(res,t.result<0?50:12)+
						(t.result<0?"":
						(t.complete               ?"  "   :"in")	+"complete "+
						(t.complete==t.completable?" OK  ":"wrong")	+" "+
						(t.solved                 ?"  "   :"un")	+"solved "+
						(t.solved==t.solvable     ?" OK  ":"wrong")	+" "+
						pad(String.valueOf(t.utility),6)			+t.utility)+
						pad(String.valueOf(secLimit),6)				+secLimit+ 
						pad(String.valueOf(secs),6)					+secs 
						);
			}
		println("--------------------------------------------------------------------------------------------");
	}

	static String status2string(int s) {
		switch (s) {
		case S_SUCCESS:						return "success";
		case S_PROCESS_FORCEBLY_TERMINATED:	return "FORCED TERM";
		case S_NO_OUTPUT:					return "no ouput file found";
		case S_UNPARSEABLE_OUTPUT:			return "unparseable output";
		case S_NO_OUTPUT_FORCED:			return "no ouput file found (forced termination)";
		case S_UNPARSEABLE_OUTPUT_FORCED:	return "unparseable output (forced termination)";
		case S_BAD_PROGRAM_FILENAME:		return "bad search program filename";
		case S_CLASSS_NOT_FOUND:			return "class not found in jar";
		case S_CANNOT_OPEN_JAR:				return "can't open jar";
		case S_DATAFILE_MISSING:			return "datafile missing";
		case S_UNEXPECTED_EXCEPTION:		return "unexpected exception during exec";
		default:							return "unknown status";
		}
	}

	protected static String pad(String s, int len) {
		StringBuffer b = new StringBuffer();
		for (int i=len-s.length(); i>0; i--) {
			b.append(' ');
		}
		return b.toString();
	}
	
	
	/**
	 * Implment "command mode": repeatedly read lines of predictes
	 * from {@link System#in} and either assert them (if the line starts
	 * with a "!") or evaluate them (and return "true" or "false" to
	 * {@link System#out}. 
	 * @param env the environment that can interpret the predicates.
	 */
	public static void commandMode(PredicateReader env) {
		final int maxBuf = 200;
		byte[] buf = new byte[maxBuf];
		int length;
		try {
			print("\nSisyphus I: query using predicates, assert using \"!\" prefixing predicates;\n !exit() to quit; !help() for help.\n\n> ");
			while ((length=System.in.read(buf))!=-1) {
				String s = new String(buf,0,length);
				s = s.trim();
				if (s.equals("exit")) break;
				if (s.equals("?")||s.equals("help")) {
					s = "!help()";
					println("> !help()");
				}
				if (s.length()>0) {
					if (s.charAt(0)=='!') {
						env.assert_(s.substring(1));
					}
					else { 
						print(" --> "+env.eval(s));
					}
				}
				print("\n> ");
			}
		} catch (Exception e) {
			println("exiting: "+e.toString());
		}
	}
	
	/**
	 * Utility method the create an output filename from an input filename.
	 * @param in A String representing the input filename
	 * @return an appropriate output filename (which is the input filename + ".out")
	 */
	static String makeOutfilename(String in) {
		return in+".out";
	}
	
	/**
	 * Utility method to print out a synopsis of the command line.
	 */
	static void printSynopsis() {
    	println("Synopsis: Sisyphus <search-prg> [<env-file> [<maxTimeInMilliseconds:default="+DEFAULT_MAX_TIME+">]]");
	}
	
	static void println(String s) {
		System.out.println(s);
		traceFile.println(s);
	}

	static void print(String s) {
		System.out.print(s);
		traceFile.print(s);
	}

	static void write(byte[] s, int offset, int count) throws Exception {
		System.out.write(s, offset, count);
		traceFile.write(s, offset, count);;
	}
}
