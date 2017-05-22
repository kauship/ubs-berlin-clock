/**
 * 
 */
package com.ubs.opsit.interviews;

import java.util.EnumMap;

/**
 * @author kaushik
 *
 */
public class BerlinClock implements TimeConverter {

	private static final char RED = 'R';
	private static final char YELLOW = 'Y';
	private static final char OFF = 'O';
	private static final String NEW_LINE = System.getProperty("line.separator");

	enum TimeUnit {
		HOURS, MINUTES, SECONDS
	}

	@Override
	public String convertTime(String aTime) {
		EnumMap<TimeUnit, Integer> timeInputMap = parseAndValidateInput(aTime);
		return buildClock(timeInputMap);
	}

	/**
	 * Parse the given input which is expected in the format HH:MM:SS if the
	 * input is not in this format then exception is thrown
	 * 
	 * @param aTime
	 * @return an enum map with hours, minutes and seconds
	 * @throws IllegalArgumentException
	 *             if the input in not in the correct format
	 */
	private EnumMap<TimeUnit, Integer> parseAndValidateInput(String aTime) {
		if (aTime == null)
			throw new IllegalArgumentException("a non null input must be supplied!!!");
		String[] inputArr = aTime.split(":");
		if (inputArr.length != 3) {
			throw new IllegalArgumentException("input should be of format: HH:MM:SS");
		}
		EnumMap<TimeUnit, Integer> inputMap = new EnumMap<>(TimeUnit.class);
		try {
			inputMap.put(TimeUnit.HOURS, Integer.parseInt(inputArr[0]));
			inputMap.put(TimeUnit.MINUTES, Integer.parseInt(inputArr[1]));
			inputMap.put(TimeUnit.SECONDS, Integer.parseInt(inputArr[2]));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Input is not parsable, it should be of format: HH:MM:SS");
		}
		return inputMap;
	}

	/**
	 * Build the berlin clock with given inputs
	 * 
	 * @param timeInputMap
	 * @return
	 */
	private String buildClock(EnumMap<TimeUnit, Integer> timeInputMap) {
		StringBuilder outputBuilder = new StringBuilder();
		outputBuilder.append(secondsIndicator(timeInputMap.get(TimeUnit.SECONDS))).append(NEW_LINE)
				.append(hoursFirstRow(timeInputMap.get(TimeUnit.HOURS))).append(NEW_LINE)
				.append(hoursSecondRow(timeInputMap.get(TimeUnit.HOURS))).append(NEW_LINE)
				.append(minutesFirstRow(timeInputMap.get(TimeUnit.MINUTES))).append(NEW_LINE)
				.append(minutesSecondRow(timeInputMap.get(TimeUnit.MINUTES)));

		return outputBuilder.toString();
	}

	/**
	 * Return the top row of minutes in which lit lamps are yellow except the
	 * quarter lamps which are red when lit
	 * 
	 * @param minutes
	 * @return
	 */
	private String minutesFirstRow(int minutes) {
		int minutesLit = minutes / 5;
		int minutesUnlit = 11 - minutesLit;
		String minutesFirstRow = buildMinutesRow(minutesLit, minutesUnlit);
		return updateQuarterLamps(minutesFirstRow);
	}

	/**
	 * Update the quarter lamps in the minutes top row. If they are lit then
	 * they are converted to RED
	 * 
	 * @param minutesFirstRow
	 * @return
	 */
	private String updateQuarterLamps(String minutesFirstRow) {
		StringBuilder minutesFirstRowBuilder = new StringBuilder(minutesFirstRow);
		for (int i = 1; i <= minutesFirstRow.length(); i++) {
			if (i % 3 == 0 && YELLOW == minutesFirstRow.charAt(i - 1)) {
				minutesFirstRowBuilder.setCharAt(i - 1, RED);
			}
		}
		return minutesFirstRowBuilder.toString();

	}

	/**
	 * Creates the minutes bottom row which is have total four lamps which are
	 * yellow when lit
	 * 
	 * @param minutes
	 * @return
	 */
	private String minutesSecondRow(int minutes) {
		int minutesLit = minutes % 5;
		int minutesUnlit = 4 - minutesLit;
		return buildMinutesRow(minutesLit, minutesUnlit);
	}

	/**
	 * Creates hours top row. lamps are RED when lit
	 * 
	 * @param hours
	 * @return
	 */
	private String hoursFirstRow(int hours) {
		int hoursLit = hours / 5;
		int hoursUnlit = 4 - hoursLit;
		return buildHrRow(hoursLit, hoursUnlit);
	}

	/**
	 * Creates hours bottom row. lamps are RED when lit
	 * 
	 * @param hours
	 * @return
	 */
	private String hoursSecondRow(int hours) {
		int hoursLit = hours % 5;
		int hoursUnlit = 4 - hoursLit;
		return buildHrRow(hoursLit, hoursUnlit);
	}

	/**
	 * Build a minutes row based upon given lit and unlit number if lamps
	 * 
	 * @param minutesLit
	 * @param minutesUnlit
	 * @return
	 */
	private String buildMinutesRow(int minutesLit, int minutesUnlit) {
		StringBuilder minutesRowBuilder = new StringBuilder();
		minutesRowBuilder.append(buildLampRow(minutesLit, YELLOW)).append(buildLampRow(minutesUnlit, OFF));
		return minutesRowBuilder.toString();
	}

	/**
	 * Build/create and hour row based upon given number of lit and unlit hours
	 * lamps
	 * 
	 * @param hoursLit
	 * @param hoursUnlit
	 * @return
	 */
	private String buildHrRow(int hoursLit, int hoursUnlit) {
		StringBuilder hrRowBuilder = new StringBuilder();
		hrRowBuilder.append(buildLampRow(hoursLit, RED)).append(buildLampRow(hoursUnlit, OFF));
		return hrRowBuilder.toString();
	}

	private String buildLampRow(int count, char lampSymbol) {
		StringBuilder lampRowBuilder = new StringBuilder();
		for (int i = 0; i < count; i++) {
			lampRowBuilder.append(lampSymbol);
		}
		return lampRowBuilder.toString();
	}

	/**
	 * Returns a seconds indicator, in case of even it is yellow and off
	 * otherwise
	 * 
	 * @param seconds
	 * @return
	 */
	private char secondsIndicator(int seconds) {
		return seconds % 2 == 0 ? YELLOW : OFF;
	}

}
