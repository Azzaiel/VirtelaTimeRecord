package net.virtela.TimeRecord;

import java.util.regex.Pattern;

public class PatternMatcherConsoleTest {
	
	private final static Pattern pattern =  Pattern.compile("^(0[0-9]||1[0-2])/([0-2][0-9]||3[0-1])/([0-9][0-9])?[0-9][0-9]$");

	public static void main(String[] args) {
		isPatternMatcherWorking("01/01/1900", true);
		isPatternMatcherWorking("12/31/2018", true);
		isPatternMatcherWorking("04/31/2020", true);
		isPatternMatcherWorking("31/12/2018", false);
		isPatternMatcherWorking("31/12/18", false);
		isPatternMatcherWorking("-1/22/2018", false);
		
	}
	
	private static void isPatternMatcherWorking(String value, boolean expectedResult) {
		boolean result = pattern.matcher(value).matches();
		
		if (result == expectedResult) {
			System.out.println("Input: " + value + ", expexted result is " + expectedResult + ": Good!!");
		} else {
			System.out.println("Input: " + value + ", expexted result is " + expectedResult + ": Failed!!");
		}
		
	}

}
