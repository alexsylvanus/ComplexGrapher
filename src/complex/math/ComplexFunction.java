/**
 * 
 */
package complex.math;

import java.util.*;

/**
 * @author alexs
 *
 */
enum charType {
	OPENPAREN,
	LETTER,
	NUMERICAL,
	OPERATOR,
	CLOSEDPAREN,
	ERROR
}
//Class Declaration
public class ComplexFunction {
	// Class members
	private String fctn_str;
	private Stack<String> fctn_stck;
	// Constant members
	public static final charType OPENPAREN = charType.OPENPAREN;
	public static final charType LETTER = charType.LETTER;
	public static final charType NUMERICAL = charType.NUMERICAL;
	public static final charType OPERATOR = charType.OPERATOR;
	public static final charType CLOSEDPAREN = charType.CLOSEDPAREN;
	public static final charType ERROR = charType.ERROR;
	
	// Class Constructor
	public ComplexFunction(String s) {
		fctn_str = s;
		fctn_stck = parseFunc(s);
	}
	
	// To string
	public String toString() {
		return fctn_str;
	}

	
	//**
	public static Stack<String> parseFunc(String s) {
		// Declare processing flags
		boolean num = false; // processing number
		boolean str = false; // processing string
		
		// Declare temporary variables
		char c = s.charAt(0);
		charType ct = labelChar(c);
		int order = 0;
		String str_temp = "";
		String op_temp = "";
		Stack<String> ops = new Stack<String>();
		Stack<String> parsed = new Stack<String>();
		
		// Strip Whitespace
		s = stripWhitespace(s);
		
		// Loop through string
		for (int i = 0; i<s.length(); i++) {
			// Create character analysis label
			c = s.charAt(i);
			ct = labelChar(c);
			
			// Label is used to reduce the options that are checked in the nested if statements
			character_analysis:
			switch(ct) {
			case OPENPAREN:
				// Open paren code goes here
				if (str) {  // function from context
					str = false;
					if (isUnary(str_temp)) {
						ops.push("(");
						ops.push(str_temp);
						str_temp = "(";
						break character_analysis;
					}
					else {
						System.out.print(str_temp);
						System.err.println(" is not a valid function preceding (. Invalid format");
						throw new NumberFormatException();
					}
				}
				if (num) { // if previously processing a number, an open parentheses will generate an error
					System.err.println("Number next to (. Invalid format");
					throw new NumberFormatException();
				}
				ops.push("(");
				str_temp = null;
				break character_analysis;
				
			
			case LETTER:
				// Letter code goes here
				if (str) {
					str_temp += c;
					break character_analysis;
				}
				if (num) {
					System.err.println("Not expecting number after letter. Invalid format.");
					throw new NumberFormatException();
				}
				str_temp = String.valueOf(c);
				num = false;
				str = true;
				break character_analysis;
				
				
			case NUMERICAL:
				// Numerical code goes here
				if (num) {
					str_temp += c;
					break character_analysis;
				}
				if (str) {
					System.err.println("Not expecting letter after number. Invalid format");
					throw new NumberFormatException();
				}
				str_temp = String.valueOf(c);
				str = false;
				num = true;
				break character_analysis;
				
				
			case OPERATOR:
				// Operator code goes here
				if (i==0 || (!str && !num && s.charAt(i-1)!=')')) {
					// The first char in the string or no num and str flags and the previous
					// char is not a closeparen
					if ( i>1 && labelChar(s.charAt(i-1))==OPERATOR && labelChar(s.charAt(i-2))==OPERATOR) {
						// If greater than the second index, the two prior indexes
						// are operators, throw exception
						System.err.println("Too many operators in a row. Invalid format");
						throw new NumberFormatException();
					}
					
					// Break if current char is a plus sign
					if (c=='+') {
						break character_analysis;
					}
					
					// Decide what to do with minus sign
					if (c=='-') {
						// Get the priority from ngtv, should be 3
						str_temp = "ngtv";
						order = priority(str_temp);
						
						// Check if operator stack is empty
						if (!ops.isEmpty()) {
							// Copy operator stack to parsed stack until empty, or if the priority is less
							while (!ops.isEmpty()) {
								op_temp = ops.pop();
								if (priority(op_temp)>=order) {
									parsed.push(op_temp);
								}
								else { // Break the loop if the priority of the temporary operator is greater
									ops.push(op_temp);
									break; // while loop
								}
							}
						}
						ops.push(str_temp);
						break character_analysis;
					}
					throw new NumberFormatException();
				}
				// If currently processing string
				if (str) {  // variable or constant from context
					if (isConstant(str_temp)) {
						parsed.push(str_temp);
					}
					else {
						throw new NumberFormatException();
					}
				}
				// Add currently processed number
				if (num) {
					parsed.push(str_temp);
				}
				
				// Reset str and num flags
				str = false;
				num = false;
				
				// Set the temp string equal to the current operator 
				str_temp = String.valueOf(c);
				
				// Update the operator stack according to priority
				order = priority(str_temp);
				if (!ops.isEmpty()) { // Check if stack is empty
					while (!ops.isEmpty()) { // While it's not empty, copy operators to parsed stack
						op_temp = ops.pop();
						if (priority(op_temp)>=order) {
							parsed.push(op_temp);
						}	
						else { // If the priority of the current operator is higher, stop copying
							ops.push(op_temp);
							break; // while loop
						}
					}
				}
				ops.push(str_temp);
				break character_analysis;
				
				
			case CLOSEDPAREN:
				// Error detected if string begins with closed parentheses 
				if (!str && !num && !(i>0 && s.charAt(i-1)==')')) {
					throw new NumberFormatException();
				}
				// If a number is being processed, push it to the parsed stack
				if (num) {
					parsed.push(str_temp);
				}
				
				// If a string is being processed, check that it's a valid string
				if (str) {  // variable or constant from context
					if (isConstant(str_temp)) {
						parsed.push(str_temp);
					}
				}
				
				// Reset number and string flags
				num = false;
				str = false;
				
				// Copy the operator list until a ( is detected.
				// This ensures that the math inside the parentheses is prioritized 
				while (!ops.isEmpty()) {
					op_temp = ops.pop();
					if (!op_temp.equals("(")) {
						parsed.push(op_temp);
					}
					else {
						break character_analysis; // Go back to beginning
					}
				}
				break;
					
					
			case ERROR:
				System.err.println("Error, Invalid character");
				throw new NumberFormatException();
			}
		}
		
		
		// Clean up after string loop finishes
		if (str) {  // variable or constant from context
			if (isConstant(str_temp)) {
				parsed.push(str_temp);
			}
			else {
				throw new NumberFormatException();
			}
		}
		
		// Add currently processed number
		if (num) {
			parsed.push(str_temp);
		}
		     
		// Clear the operator stack
		while (!ops.isEmpty()) {
			op_temp = ops.pop();
			if (!op_temp.equals("(")) { // Copy the operator if it is not an open parentheses
				parsed.push(op_temp);
			}
			else {
				throw new NumberFormatException();
			}
		}
		
		// Reverse the Stack
		Stack<String> reversed = new Stack<String>();
		while (!parsed.isEmpty()) {
			reversed.push(parsed.pop());
		}
		return reversed;
	}
	//*/
	
	public Complex compute(Complex z) {
		// Declare local variables
		String str_curr = "";
		Complex z1 = new Complex();
		Complex z2 = new Complex();
		
		// Declare Stacks for computation
		Stack<Complex> cmplxvals = new Stack<Complex>();
		Stack<?> localstack = new Stack<String>();
		localstack = (Stack<?>) fctn_stck.clone();
		// Define constants for substitution in expression
		Complex e = new Complex(Complex.e, 0);    // complex e
		Complex pi = new Complex(Complex.pi, 0);  // complex pi
		Complex i = new Complex(0, 1.0f);         // complex i
		
		// Loop while the stack is not empty
		while(!localstack.isEmpty()) {
			// Get the current string
			str_curr = (String)localstack.pop();
			
			// Check if constant, Unary Operator, or Binary Operator. Otherwise, it's a number
			if (isConstant(str_curr)) { // Case of a defined constant
				switch(str_curr) {
				case "z":
					cmplxvals.push(z);
					break;
				case "e":
					cmplxvals.push(e);
					break;
				case "pi":
					cmplxvals.push(pi);
					break;
				case "i":
					cmplxvals.push(i);
					break;
				}
			}
			else if (isUnary(str_curr)) { // Case of a unary function
				cmplxvals.push(computeUnary(cmplxvals.pop(), str_curr));
			}
			else if (isOperator(str_curr)) { // Case of an operator
				z2 = cmplxvals.pop();
				z1 = cmplxvals.pop();
				cmplxvals.push(computeBinary(z1, z2, str_curr));
			}
			else { // Case of a numerical value
				cmplxvals.push(new Complex(new Float(str_curr).floatValue(), 0));
			}
	    }
	    return cmplxvals.pop();
	}
	
	// Private functions
	private static boolean isWhitespace(char c) {
		return (c==' ' || c== '\n' || c=='\r' || c=='\t');
	}
	private static boolean isLowercase(char c) {
		return (c>='a' && c<='z');
	}
	private static boolean isUppercase(char c) {
		return (c>='A' && c<='Z');
	}
	private static boolean isLetter(char c) {
		return (isUppercase(c) || isLowercase(c));
	}
	private static boolean isNumerical(char c) {
		return ((c>='0' && c<='9') || c == '.');
	}
	private static boolean isOperator(char c) {
		return (c=='+' || c=='-' || c=='*' || c=='/' || c=='^');
	}
	private static boolean isOperator(String s) {
		switch(s) {
		case "+":
		case "-":
		case "*":
		case "/":
		case "^":
			return true;
		default:
			return false;
		}
	}
	private static boolean isConstant(String s) {
		switch(s) {
		case "z":
		case "e":
		case "pi":
		case "i":
			return true;
		default:
			return false;
		}
	}
	private static boolean isUnary(String s) {
		switch(s) {
		case "exp":
		case "sin":
		case "cos":
		case "tan":
		case "sinh":
		case "cosh":
		case "tanh":
		case "log":
		case "ln":
		case "sqrt":
		case "abs":
		case "ngtv":
		case "conj":
			return true;
		default:
			return false;
		}
	}
	
	private static String stripWhitespace(String s) {
		// Declare return variable
		String stripped = "";
		
		// Declare temporary character variable
		char c = 'a';
		
		// Loop through string, copying non whitespace characters
		for (int i = 0; i < s.length(); i++) {
			c = s.charAt(i);
			if (!isWhitespace(c)) {
				stripped += c;
			}
		}
		return stripped;
	}
	private static charType labelChar(char c) {
		// Declare return variable
		charType ret = charType.ERROR;
		
		// Classify
		if (c=='(') {
			ret = OPENPAREN;
		} else if (isLetter(c)) {
			ret = LETTER;
		} else if (isNumerical(c)) {
			ret = NUMERICAL;
		} else if (isOperator(c)) {
			ret = OPERATOR;
		} else if (c==')') {
			ret = CLOSEDPAREN;
		} else {
			ret = ERROR; // Error
		}
		
		// Return variable
		return ret;
	}
	
	private static Complex computeUnary(Complex z, String s) {
		Complex ret = new Complex();
		if (isUnary(s)) {
			switch(s) {
			case "exp": return z.exp();
			case "sin": return z.sin();
			case "cos": return z.cos();
			case "tan": return z.tan();
			case "sinh": return z.sinh();
			case "cosh": return z.cosh();
			case "tanh": return z.tanh();
			case "log": return z.log();
			case "ln": return z.ln();
			case "sqrt": return z.sqrt();
			case "abs": return z.abs();
			case "ngtv": return z.ngtv();
			case "conj": return z.conj();
			}
		}
		else {
			System.err.println("Function not recognized, returning 1+0i");
		}
		return ret;
	}
	
	private static Complex computeBinary(Complex z1, Complex z2, String s) {
		Complex ret = new Complex();
		if (isOperator(s)) {
			switch(s) {
			case "+": return Complex.Add(z1, z2);
			case "-": return Complex.Subtract(z1, z2);
			case "*": return Complex.Multiply(z1, z2);
			case "/": return Complex.Divide(z1, z2);
			case "^": return Complex.Pow(z1, z2);
			}
		}
		else {
			System.err.println("Operator not recognized, returning 1+0i");
		}
		return ret;
	}
	
	private static int priority(String s) {
		switch(s) {
		case "^": return 4;
		case "ngtv": return 3;
		case "*": case "/": return 2;
		case "+": case "-": return 1;
		default: return 0;
		}
	}
}