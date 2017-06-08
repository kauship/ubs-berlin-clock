/**
 * 
 */
package com.ubs.opsit.interviews;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author kaushik
 *
 */
public class BerlinClockTest {
	
	private TimeConverter berlinClock = new BerlinClock();
	private static final String NEW_LINE = System.getProperty("line.separator");

	@Rule
	public ExpectedException exepectedExpection = ExpectedException.none();
	
	@Test
	public void testNullInput(){
		exepectedExpection.expect(IllegalArgumentException.class);
		exepectedExpection.expectMessage("a non null input must be supplied!!!");
		berlinClock.convertTime(null);
	}
	
	@Test
	public void testEmptyInput(){
		exepectedExpection.expect(IllegalArgumentException.class);
		exepectedExpection.expectMessage("input should be of format: HH:MM:SS");
		berlinClock.convertTime("");
	}
	
	@Test
	public void testInvalidInputFormatByLength1(){
		exepectedExpection.expect(IllegalArgumentException.class);
		exepectedExpection.expectMessage("input should be of format: HH:MM:SS");
		berlinClock.convertTime("12:15:30:39");
	}
	
	@Test
	public void testInvalidInputFormatByLength2(){
		exepectedExpection.expect(IllegalArgumentException.class);
		exepectedExpection.expectMessage("input should be of format: HH:MM:SS");
		berlinClock.convertTime("12:15");
	}
	
	@Test
	public void testUnparseableInput(){
		exepectedExpection.expect(IllegalArgumentException.class);
		exepectedExpection.expectMessage("Input is not parsable, it should be of format: HH:MM:SS");
		berlinClock.convertTime("ab:12:50");
	}
	
	@Test
	public void testInvalidInputHours(){
		exepectedExpection.expect(IllegalArgumentException.class);
		exepectedExpection.expectMessage("HOURS arg should be in range of 0:24");
		berlinClock.convertTime("25:12:50");
	}
	
	@Test
	public void testInvalidInputMinutes(){
		exepectedExpection.expect(IllegalArgumentException.class);
		exepectedExpection.expectMessage("MINUTES arg should be in range of 0:60");
		berlinClock.convertTime("23:72:50");
	}
	
	@Test
	public void testInvalidInputSeconds(){
		exepectedExpection.expect(IllegalArgumentException.class);
		exepectedExpection.expectMessage("SECONDS arg should be in range of 0:60");
		berlinClock.convertTime("23:29:70");
	}
	
	@Test
	public void testConvertTimeWithStartOfDay(){
		String output = berlinClock.convertTime("00:00:00");
		String expectedOutput = "Y"+NEW_LINE+"OOOO"+NEW_LINE+"OOOO"+NEW_LINE+"OOOOOOOOOOO"+NEW_LINE+"OOOO";
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testConvertTimeInFirstHalf(){
		String output = berlinClock.convertTime("11:30:45");
		String expectedOutput = "O"+NEW_LINE+"RROO"+NEW_LINE+"ROOO"+NEW_LINE+"YYRYYROOOOO"+NEW_LINE+"OOOO";
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testConvertTimeInSecondHalf(){
		String output = berlinClock.convertTime("16:42:20");
		String expectedOutput = "Y"+NEW_LINE+"RRRO"+NEW_LINE+"ROOO"+NEW_LINE+"YYRYYRYYOOO"+NEW_LINE+"YYOO";
		assertEquals(expectedOutput, output);
	}
	
	@Test
	public void testConvertTimeWithEndOfDay(){
		String output = berlinClock.convertTime("23:59:59");
		String expectedOutput = "O"+NEW_LINE+"RRRR"+NEW_LINE+"RRRO"+NEW_LINE+"YYRYYRYYRYY"+NEW_LINE+"YYYY";
		assertEquals(expectedOutput, output);
	}	
	
}
