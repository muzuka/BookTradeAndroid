/**
 * 
 */
package cpsc433;

import java.util.Vector;
import java.util.Iterator;
import java.util.TreeSet;
//import casa.util.CASAUtil;
import java.text.ParseException;

/**
 * Represents a predicate of the form <em>predicate-name ( param , ... )</em>
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
public class Predicate {

/**
 * Represents the types of the parameters to the predicate.
 */
enum ParamType { STRING, SET, LONG, UNDEFINED }

/**
 * The name of the predicate.
 */
protected String name = null;

/**
 * The parameters (represented by type x value) in order.
 */
protected Vector<Pair<ParamType,Object>> params = new Vector<Pair<ParamType,Object>>();

/**
 * Constructs a predicate from <code>s</code>.  If <code>s</code> is malformed,
 * throws a ParseException exception.  Describes errors by printing to System.out.
 * @param s a <code>String</code> from which to parse the predicate data.
 * @throws ParseException thrown if the string is malformed.
 */
Predicate(String s) throws ParseException { 
    int openParen = scanFor(s,0,"(");
	if (openParen == -1) { //assume a 0-ary predicate (which defaults to 1 unary with an empty string)
		openParen = s.length();
		s = s+"()";
	}
	Int cur = new Int(openParen);
	if (openParen<0) return;
	try {
		name = s.substring(0,openParen).trim();
		params = scanList(s,cur,')',',');
	} catch (Exception e) {
		error(s);
		StringBuffer buf = new StringBuffer();
		for (int i=cur.value; i>=0; i--) buf.append(' ');
		error(buf+"^-----parse error: "+e.toString());
		throw new ParseException(e.toString()+" at column "+cur,0);
	}
}

private class Int {
	int value;
	public Int(int i) {value=i;}
	public int getValue() {return value;}
	public Int setValue(int value) {this.value = value; return this;}
	@Override
	public String toString() {return Integer.toString(value);}
}

/**
 * Scans a parameter list or a list of set elements and returns a vector of pairs 
 * (type x value).  
 * @param list the input String.
 * @param curInt the index at which to start scanning the string.
 * @param terminator the character that will terminate the input.
 * @param separator the character that separates the input elements.
 * @return a vector of (type x value) representing the list elements.
 * @throws ParseException if the input <code>list</code> is malformed.
 */
private Vector<Pair<ParamType,Object>> scanList(String list, Int curInt, char terminator, char separator) 
		throws ParseException {
	Vector<Pair<ParamType,Object>> ret = new Vector<Pair<ParamType,Object>>();
	int cur = curInt.getValue();
	int paramEnd;
	String delim = new String(new char[]{separator,terminator});
	while ((paramEnd = scanFor(list,++cur,delim))!=-1) {
		//String param = list.substring(cur+1,paramEnd).trim();
		ParamType t = ParamType.UNDEFINED;
		Object obj = null;
		while (Character.isSpaceChar(list.charAt(cur))) cur++;
		switch (list.charAt(cur)) {
			case '{':
				obj = new TreeSet<Pair<ParamType,Object>>(scanList(list,curInt.setValue(cur),'}',','));
				cur = curInt.getValue();
				t = ParamType.SET;
				// paramEnd might have been wrong if there was more than one element in the set
				paramEnd = scanFor(list,cur,delim);  
				break;
			case '"':
				obj = fromQuotedString(list,cur);
				t = ParamType.STRING;
				// paramEnd might have been wrong if there were escape characters in the string
				paramEnd = scanFor(list,scanFor(list,cur,"\"")+1,delim);  
				break;
			case '0': case '1': case '2': case '3': case '4': case '5': case '6': case '7': case '8': case '9': case '-':
				paramEnd = scanFor(list,cur,delim);  
				obj = new Long(list.substring(cur,paramEnd));
				t = ParamType.LONG;
				break;
			default:
				char c = list.charAt(cur);
				if (Character.isJavaIdentifierStart(c) || c==terminator || c==separator) {
					obj = list.substring(cur,paramEnd);
					t = ParamType.STRING;
				} else throw new ParseException("unrecognized token",cur);
		}
		ret.add(new Pair<ParamType,Object>(t,obj));
		cur = paramEnd;
		if (list.charAt(paramEnd)==terminator) break;
	}
	if (paramEnd==-1) throw new ParseException("no closing terminator '"+terminator+"' found",cur);
	curInt.setValue(cur);
	return ret;
}

/**
 * @return the name of the predicate
 */
public String getName() {return name;}

/**
 * Returns the number of paramteres.
 * @return the number of parameters of this predicate.  Note that an
 * empty predcate (eg. "hot()" still has arity of 1 (a empty string parameter).
 */
public int getArity() {return params.size();}

/**
 * Returns the <code>ParamType</code> of the parameter at <code>index</code>.
 * Note that <code>index</code> should be less than the value returned
 * by <code>getArity()</code>.  The first parameter is at index 0.
 * @param index an index into the vector of parameters. 
 * @return a <code>ParamType</code> representing the type of the parameter.
 */
public ParamType getParamType(int index){
	if (index < params.size()) {
		return params.get(index).getKey();
	} else return ParamType.UNDEFINED;
}

/**
 * Returns the value of a string parameter at index <code>index</code>.
 * If the index is out of range or the parameter is not of a string
 * type, returns <code>null</code>. 
 * @param index the index of the parameter to evaluate
 * @return a <code>String</code> representing the value of parameter at the index, or null.
 */
public String getStringParam(int index) {
	if (index < params.size()) {
		Pair<ParamType,Object> p =  params.get(index);
		if (p.getKey()== ParamType.STRING) {
			return (String)p.getValue();
		} else return null;
	} else return null;
}

/**
 * Returns the value of a set parameter at index <code>index</code>
 * as a <code>TreeSet&lt;Pair&lt;ParamType,Object&lt;&lt;</code>.  
 * Thus, nested sets are allowed.
 * If the index is out of range or the parameter is not of a set
 * type, returns <code>null</code>. 
 * @param index the index of the parameter to evaluate
 * @return a <code>TreeSet</code> representing the value of parameter at the index, or null.
 */
@SuppressWarnings("unchecked")
public TreeSet<Pair<ParamType,Object>> getSetParam(int index) {
	if (index < params.size()) {
		Pair<ParamType,Object> p =  params.get(index);
		if (p.getKey() == ParamType.SET) {
			return (TreeSet<Pair<ParamType,Object>>)p.getValue();
		} else return null;
	} else return null;
}

/**
 * Returns the value of a long integer parameter at index <code>index</code>.
 * If the index is out of range or the parameter is not of a Long
 * type, returns <code>null</code>. 
 * @param index the index of the parameter to evaluate
 * @return a <code>Long</code> representing the value of parameter at the index, or null.
 */
public Long getLongParam(int index) {
	if (index < params.size()) {
		Pair<ParamType,Object> p =  params.get(index);
		if (p.getKey() == ParamType.LONG) {
			return (Long)p.getValue();
		} else return null;
	} else return null;
}


/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	StringBuffer s = new StringBuffer();
	s.append(name).append('(').append(toString(params)).append(')');
	return s.toString();
}

@SuppressWarnings("unchecked")
private String toString(Vector<Pair<ParamType,Object>> v) {
	StringBuffer s = new StringBuffer();
	//for (Pair p : v) {
	for (Iterator<Pair<ParamType,Object>> i = v.iterator(); i.hasNext();) {
		Pair<ParamType,Object> p = i.next();
		switch (p.getKey()) {
			case STRING:
				s.append((String)p.getValue());
				break;
			case SET:
				s.append('{').append(toString(new Vector<Pair<ParamType,Object>>((TreeSet<Pair<ParamType,Object>>)p.getValue()))).append('}');
				break;
			case LONG:
				s.append(((Long)p.getValue()));
				break;
			case UNDEFINED:
				s.append("UNDEFINED!");
		}
		if (i.hasNext()) s.append(", ");
	}
	return s.toString();
}

/**
 * Scans for the first occurrance of any of the charcters in <em>chars</em>
 * starting at <em>startAt</em>, and returns the index (from the beginning
 * of string, not <em>startAt</em>).  It will <b>not</b> pay attention to
 * the any target charater that is preceded by a backslash.  Thus, scanFor()
 * can be used in conjunction with escape() and unescape() to guarentee that
 * an unknown string can be parsed.  For example, you need to deliniate an
 * arbitraty string for future readin, but there may be further text after
 * it.  You can't just surround your string with {} because your string may
 * contain } characters.  Save your string, s, as <code>s="{"+escape(s,"{}")+"}"
 * </code> you can safely use scanFor(s,1,"{}") to find the original terminating
 * } deliminator.  The expression:
 * <pre>
 * unescape(s.substring(scanFor(s,0,"{")+1,scanFor(s,scanFor(s,0,"{"),"}")),"{}")
 * </pre>
 * successfully retrieves the original string, no matter what was in the
 * origial string.
 * @param in
 * @param startAt
 * @param chars
 * @return the index of the first occurance of a char in <em>chars</em> or
 *         -1 if none of the chars in <em>chars</em> are in <em>in</em>
 * @see casa.util.CASAUtil#unescape(String,String)
 * @see casa.util.CASAUtil#escape(String,String)
 */
private static int scanFor(String in, int startAt, String chars) {
  int pos = startAt, end = in.length();
  for (pos=startAt; pos<end; pos++) {
    char c = in.charAt(pos);
    if (c=='\\') pos++;
    else if (chars.indexOf(in.charAt(pos)) >= 0) break;
  }
  if (pos==end) return -1;
  return pos;
}

/**
 * Given a String (as generated from {@link casa.util.CASAUtil#toQuotedString(String)})
 * returns a String without the enclosing quotes and with an escaped double-quotes
 * restored (unescaped).  Extra chars after the closing quote are ignored.
 * @param s the quoted string to de-quote
 * @param startAt the position is <em>s</em> to start at
 * @return an unquoted, unescaped string
 * @throws ParseException if <em>s</em> can't be interpreted as a quoted string (eg: first non-white-space isn't '"') starting at <em>startAt</em>
 * @see casa.util.CASAUtil#unescape(String, String)
 */
private static String fromQuotedString(String s, int startAt) throws ParseException {
  int pos = startAt, mark;
  while (Character.isWhitespace(s.charAt(pos))) pos++; //read whitespace
  if (s.charAt(pos) != '\"') throw new ParseException("Expected '\"'",pos);
  mark = ++pos;
  pos = scanFor(s,pos,"\"");
  if (pos < 0) throw new ParseException("Expected '\"'",s.length());
  return unescape(s.substring(mark,pos),"\"");
}

/**
 * Removes any backslash characters that had been added to <em>in</em> by
 * a previous call to escape() with the same <em>escChars</em>.
 * For example,
 * <code>
 *  casautil.unescape("\(\(\)\)","()")  ==>  "(())"
 * </code>
 * unescape() always unescapes the backslash character, so
 * <code>unescape(x,"")</code> is exactly the same as <code>unescape(x,"\\")</code>.
 * <br>Note that escape() and unescape() reverse each others actions (if you start
 * with escape).  However escape applied a second time will re-encode the
 * string (if there were some escaped charaters).  This can be undone by two
 * calls to unescape.  Unescape applied to a string that has not been escaped
 * may have unpredictable results.
 * <pre>
 * <b>code</b>                      <b>value of s</b>
 * s = "(())";               (())
 * s =   escape(s,"()");     \(\(\)\)
 * s = unescape(s,"()");     (())
 * s =   escape(s,"()");     \(\(\)\)
 * s =   escape(s,"()");     \\(\\(\\)\\)
 * s = unescape(s,"()");     \(\(\)\)
 * s = unescape(s,"()");     (())
 * </pre>
 * @param in
 * @param escChars
 * @return the <code>in</code> string unescaped
 * @see casa.util.CASAUtil#escape(String,String)
 * @see casa.util.CASAUtil#scanFor(String,int,String)
 */
private static String unescape(String in, String escChars) {
  if (in == null) return null;
  if (in.length() == 0) return ""; //can't be any escapes in 1-char String, and we need to guarentee one pass through the loop
  escChars = escChars.concat("\\");
  StringBuffer out = new StringBuffer(in.length());
  char c=' ', next=in.charAt(0); //fix it so the last line will do the right thing if we don't enter the loop
  int end = in.length()-1;
  int i;
  for (i=0; i<end; i++) {
    c = in.charAt(i);
    if (c == '\\') {
      next = in.charAt(i+1);
      if (escChars.indexOf(next) < 0) out.append(c);
      else {out.append(next); i++;}
    }
    else {
      out.append(c);
    }
  }
  /*
  if (c == '\\') {
    if (escChars.indexOf(next) < 0) out.append(next);
  }
  else out.append(in.charAt(i));
  */
  if (i==end)
    out.append(in.charAt(i));
  return out.toString().trim();
}

/**
 * Utility method to format and channel error messages to System.out.
 * @param msg the message to write to System.out.
 */
public static void error(String msg) {
	System.out.println(">>>>>> "+msg);
}

}

