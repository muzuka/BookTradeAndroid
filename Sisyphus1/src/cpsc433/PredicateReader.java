/**
 * 
 */
package cpsc433;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * 
 * <p>Copyright: Copyright (c) 2003, Department of Computer Science, University 
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
public class PredicateReader extends Entity {

	/**
	 * Used by subclass predicate interpreters ("e_*()" and 
	 * "a_*()" methods) to give access to the currently being
	 * read predicate through the currentPredicate() method.
	 * Mostly for error reporting.
	 */
	private Predicate currentPred;
	
	/**
	 * Global to keep track of line numbers when reading files for
	 * error reporting.
	 */
	private int lineNumber = 0;
	
	/**
	 * Constructor thad does nothing except for recording this PredicateReader's
	 * name.
	 * @param name The name of this PredicateReading
	 */
	public PredicateReader(String name) {
		super(name);
	}
	
	/**
	 * Copy constructor.
	 * @param p a PredicateReader to copy.
	 */
	public PredicateReader(PredicateReader p) {
		super(p);
	}
	
	/**
	 * Read predicates from file <code>fileName</code>, one per line.
	 * Note the line may contain comments ("//" to end of line).  Calls
	 * {@link #fromStream(BufferedReader)}.
	 * @param fileName The file to open and read.
	 * @return the number of lines read from the file, -1 if something went wrong.
	 */
	public int fromFile(String fileName) {
		int ret = 0;
		BufferedReader stream = null;
		System.out.println("reading file "+fileName+"...");
		try {
			stream = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
			ret = fromStream(stream);
		}
		catch (FileNotFoundException ex) {
			error("Can't open file " + fileName);
			return -1;
		}
		try {
			if (stream!=null) stream.close();
		}
		catch (IOException ex) {
			error("Can't close file " + fileName);
		}
		System.out.println("... read "+ret+" lines from file "+fileName);
		return ret;
	}

	/**
	 * Read predicates from file <code>stream</code>, one per line.
	 * Note the line may contain comments ("//" to end of line). 
	 * Calls {@link #assert_(String)} once per line.
	 * @param stream
	 * @return the number of lines read from the file, -1 if something when wrong
	 */
	public int fromStream(BufferedReader stream) {
	    String line = "";
	    lineNumber = 0;
		while (line != null) {
			try {
				line = stream.readLine();
				lineNumber++;
			} catch (IOException ex) {
				error(formatLineNumber()+ex.toString());
				line = null;
				lineNumber = 0;
				return -1;
			}
			assert_(line);
		}
		int ret = lineNumber;
		lineNumber=0;
		return ret;
	}

	/**
	 * If {@link #lineNumber} is 0, returns the empty string, otherwise
	 * returns "on line <lineNumber>: ".  Used to display error output. 
	 * @return an appropriate String.
	 */
	private String formatLineNumber() {
		return (lineNumber==0)?"":"on line "+lineNumber+": ";
	}
	
	/**
	 * Creates a predicate based on the text in <code>line</code>.
	 * That is, strips comments and calls the string 
	 * <code>Predicate</code> constructor.
	 * @param line the text line to make a Predicate out of.
	 * @return a <code>Predicate</code> or null, depending on if
	 * there is a predicate in <code>line</code>.
	 */
	private Predicate makePredicate(String line) {
		if (line!=null) {
			line = stripComments(line);
			Predicate p = null;
			try {
				p = new Predicate(line);
			} catch (java.text.ParseException e){
				//the error was already reported to System.out in the Predicate constructor.
				//error(formatLineNumber()+line+"\n    "+e.toString());
			}
		    if (p!=null && p.getName()!=null) {
		        //System.out.println(p.toString());
		        return p;
		    }
		}
		return null;
	}
	
	/**
	 * Attempts to assert a predicate, created from <code>line</code>
	 * using {@link #makePredicate(String)}.  If the line is empty,
	 * just contains an comment, or the predicate is is malformed,
	 * not attempt is made to call {@link #assert_(Predicate)}.
	 * @param line the line to make a predicate from.
	 */
	public void assert_(String line) {
		if (line!=null) {
			line = line.trim();
			if (line.length()>0) {
				Predicate p = makePredicate(line);
				if (p!=null && p.getName()!=null) {
					//System.out.println(p.toString());
					assert_(p);
				}
			}
		}
	}
	
	/**
	 * Attempts to evaluate a predicate, created from <code>line</code>
	 * using {@link #makePredicate(String)}.  If the line is empty,
	 * just contains an comment, or the predicate is is malformed,
	 * not attempt is made to call {@link #eval(Predicate)}, and false
	 * is returned.
	 * @param line the line to make a predicate from.
	 * @return false if there's problems (above) or there is no such predicate; 
	 * the return value of the predicate interpreter method otherwise.
	 */
	public boolean eval(String line) {
		if (line!=null) {
			line = line.trim();
			if (line.length()>0) {
				Predicate p = makePredicate(line);
				if (p!=null && p.getName()!=null) {
					//System.out.println(p.toString());
					return eval(p);
				}
			}
		}
		return false;
	}

	/**
	 * Attempts to assert the predicate by searching for and executing
	 * a method named "a_[PredicateName]" (where [PredicateName] is the
	 * name of the Predicate object <code>pred</code>) and that has
	 * a parameter type list that matches the predicate's parameter type
	 * list. 
	 * @param pred a predicate object to assert.
	 */
	public void assert_(Predicate pred) {
		assert_or_eval(true,pred);
	}
		
	/**
	 * Attempts to evalutate the predicate by searching for and executing
	 * a method named "a_[PredicateName]" (where [PredicateName] is the
	 * name of the Predicate object <code>pred</code>) and that has
	 * a parameter type list that matches the predicate's parameter type
	 * list. 
	 * @param pred a predicate object to evaluate.
	 * @return the value returned by the method described above; false if
	 * something when wrong.
	 */
	public boolean eval(Predicate pred) {
		return assert_or_eval(false,pred);
	}
		
	/**
	 * Attempts to evalutate or assert the predicate by searching for and executing
	 * a method named "a_[PredicateName]" (where [PredicateName] is the
	 * name of the Predicate object <code>pred</code>) and that has
	 * a parameter type list that matches the predicate's parameter type
	 * list. 
	 * @param isAssert true if this is to be interpreted as an assertion,
	 * false if it's to be treated as an evaluation.
	 * @param pred a predicate object to assert or evaluate.
	 * @return the value returned by the method described above; false if
	 * something when wrong.
	 */
	private boolean assert_or_eval(boolean isAssert, Predicate pred) {
		currentPred = pred;
		String predName = pred.getName();
		if (predName==null || predName.length()==0) return false;
		String methodName = (isAssert?"a_":"e_")+predName.replaceAll("-","_");
		
		int arity = pred.getArity();
		Class<?>[] paramTypes = new Class[arity];
		Object[] paramValues = new Object[arity];
		for (int i=0; i<arity; i++) {
			switch ((Predicate.ParamType)pred.getParamType(i)) {
			case STRING: 
				paramTypes[i]  = String.class;
			    paramValues[i] = pred.getStringParam(i);
			    break;
			case SET:
				paramTypes[i]  = TreeSet.class;
				paramValues[i] = pred.getSetParam(i);
				break;
			case LONG:
				paramTypes[i]  = Long.class;
				paramValues[i] = pred.getLongParam(i);
				break;
			default: 
				error((isAssert?"!":"")+pred.toString()+": Unrecognized argument type argument "+i+" in Predicate");
				return false;
			}
		}
		
		try {
			Method m = this.getClass().getMethod(methodName,paramTypes);
			Object ret = m.invoke(this,paramValues);
			if (ret instanceof Boolean)	return ((Boolean) ret).booleanValue();
		}
		catch (InvocationTargetException e) {
			error(formatLineNumber()+(isAssert?"!":"")+pred.toString()+": InvocationTargetException ("+e.getCause()+")");
			e.getTargetException().printStackTrace();
		}
		catch (Exception e) {
			error(formatLineNumber()+(isAssert?"!":"")+pred.toString()+": predicate not found or failed ("+e.toString()+")");
		}
		
		return false;
	}


	/** the help string for the {@link #a_read(String)} predicate interpreter. */
	public static String h_read = "Read in predicates and assert them from filename <id>";
	/**
	 * Predicate interpreter for predicate "read(filename)".  Calls
	 * {@link #fromFile(String)} to assert the predicates in the file.
	 * @param filename the file to read
	 */
	public void a_read(String filename) {
		fromFile(filename);
	}
	
	/** the help string for the {@link #a_help(String)} predicate interpreter. */
	public static String h_help = "Returns help about all predicates readable by this object";
	/**
	 * Predicate interpreter for predicate "help()".  Uses the reflect facility
	 * to find all the "a_*" and "e_*" methods in the class of <code>this</code>
	 * and list them to <code>System.out</code>.  Also searches for corresponding
	 * "h_*" static strings as an embellished explanation of the predicates.
	 * @param s ignored.
	 */
	public void a_help(String s) {
		final int a=1, e=2, both=3;
		TreeMap<String,Integer> names = new TreeMap<String,Integer>(); 
		for (Method m : getClass().getMethods()) {
			String fullName = m.getName();
			if (fullName.length()>2) {
				int flags=0;
				String prefix = fullName.substring(0,2);
				String name = fullName.substring(2);
				if (s==null || s.length()==0 || s.equals(name)) {
					if      (prefix.equals("a_")) flags += a;
					else if (prefix.equals("e_")) flags += e;
					else continue;
					StringBuffer buf = new StringBuffer();
					buf.append(name.replaceAll("_","-")+"(");
					Class<?>[] params = m.getParameterTypes();
					boolean first = true;
					for (Class<?> c : params) {
						if (!first) buf.append(", ");
						String ptype="unknown";
						if      (c.equals(String .class)) ptype = "id";
						else if (c.equals(TreeSet.class)) ptype = "set";
						else if (c.equals(Long   .class)) ptype = "integer";
						buf.append('<').append(ptype).append('>');
						first = false;
					}
					buf.append(")");
					try {
						Field field = getClass().getField("h_"+name);
						Object help = field.get(null);
						if (help instanceof String)
							buf.append(" //").append((String)help);
					} catch (Exception e1) {}
					String sig = new String(buf);
					if (names.containsKey(sig)) names.put(sig,names.get(sig)|flags);
					else names.put(sig,flags);
				}
			}
		}
		System.out.println("Query using unqualified predicates, assert using \"!\" prefixing predicates");
		System.out.println("(prefixes: \"!\" means assert only, \"\" means query only, \"[!]\" means either query or assert)");
		System.out.println("Understood predicates:");
		for (String sig : names.keySet()) {
			StringBuffer buf = new StringBuffer();
			int flags = names.get(sig);
			buf.append((flags&both)==both?"  [":"   ")
			   .append((flags&a   )!=0   ?"!":" ")
			   .append((flags&both)==both?"]":" ")
			   .append(sig);
			System.out.println(buf.toString());
		}
	}
	
	/**
	 * Strips comments from a string, eg: "small(x)//check" becomes
	 * "small(x)".
	 * @param line the line to strip
	 * @return the stripped line
	 */
	protected String stripComments(String line) {
		int i = line.indexOf("//");
		return i==-1?line:line.substring(0,i);
	}

	/**
	 * Checks a unary predicate for a type matching the type parameter. 
	 * @param pred a predicate to check.
	 * @param type the proposed type of parameter 1.
	 * @return true if <code>pred</code> is a unary predicate that matches the type.
	 */
	protected static String checkTypes(Predicate pred, Predicate.ParamType type) {
		if (pred.getArity()!=1) return formatError(pred,"expected 1 argument");
		else if (pred.getParamType(0)!=type) formatError(pred,"argument 1 should be type "+type);	
		return null;
	}

	/**
	 * Checks a binary predicate for a type matching the type parameters. 
	 * @param pred a predicate to check.
	 * @param type1 the proposed type of parameter 1.
	 * @param type2 the proposed type of parameter 2.
	 * @return true if <code>pred</code> is a binary predicate that matches the types.
	 */
	protected static String checkTypes(Predicate pred, Predicate.ParamType type1, Predicate.ParamType type2) {
		if (pred.getArity()!=2) return formatError(pred,"expected 2 arguments");
		else if (pred.getParamType(0)!=type1) return formatError(pred,"argument 1 should be type "+type1);	
		else if (pred.getParamType(1)!=type2) return formatError(pred,"argument 2 should be type "+type2);	
		return null;
	}

	/**
	 * Checks a trinary predicate for a type matching the type parameters. 
	 * @param pred a predicate to check.
	 * @param type1 the proposed type of parameter 1.
	 * @param type2 the proposed type of parameter 2.
	 * @param type3 the proposed type of parameter 3.
	 * @return true if <code>pred</code> is a trinary predicate that matches the types.
	 */
	protected static String checkTypes(Predicate pred, Predicate.ParamType type1, Predicate.ParamType type2, Predicate.ParamType type3) {
		if (pred.getArity()!=3) return formatError(pred,"expected 3 arguments");
		else if (pred.getParamType(0)!=type1) return formatError(pred,"argument 1 should be type "+type1);	
		else if (pred.getParamType(1)!=type2) return formatError(pred,"argument 2 should be type "+type2);	
		else if (pred.getParamType(2)!=type3) return formatError(pred,"argument 3 should be type "+type3);	
		return null;
	}

	/**
	 * Utility method to format and error message.
	 * @param p a <code>Predicate</code> to be used in formatting the message.
	 * @param exp an explanation string to be  used in formatting the message.
	 * @return the formatted error message.
	 */
	protected static String formatError(Predicate p, String exp) {
		return "Malformed predicate \""+p.toString()+"\": "+exp;
	}

	/**
	 * Utility method to format and channel error messages to System.out.
	 * @param msg the message to write to System.out.
	 */
	public static void error(String msg) {
		System.out.println(">>>>>> "+msg);
	}
	
	/**
	 * Utility method to allow other classes to access the predicate being
	 * processed or just processed.  Primarily for error reporting purposes.
	 * @return the current or last-processed predicate.
	 */
	protected final Predicate currentPredicate() {
		return currentPred;
	}
}
