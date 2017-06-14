/***************************************************************************
 * Copyright (C) Accenture
 * 
 * The reproduction, transmission or use of this document or its contents is not permitted without
 * prior express written consent of Accenture. Offenders will be liable for damages. All rights,
 * including but not limited to rights created by patent grant or registration of a utility model or
 * design, are reserved.
 * 
 * Accenture reserves the right to modify technical specifications and features.
 * 
 * Technical specifications and features are binding only insofar as they are specifically and
 * expressly agreed upon in a written contract.
 * 
 **************************************************************************/

package com.acn.avs.push.messaging.generator;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * This class generates random data for specific data types.
 *
 * @author singh.saurabh
 * @version 1.0
 * @date June 3, 2014
 */

public class RandomData {


	/** chars. */
	private char[] chars = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
			'x', 'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
			'!', '#', '$', '%', ' ' };

	/** numbers. */
	private char[] numbers = { '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	/** hexNumbers. */
	private char[] hexNumbers = { '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'A', 'B', 'C', 'D', 'E', 'F' };

	/* private ListOrderedSet charListSet = new ListOrderedSet(); */

	/**
	 * Creates a new RandomData object.
	 */
	public RandomData() {

		/*try {
			buildCharList();
		} catch (IOException e) {
			LOG.info("[RandomData] Error during building char list :"
					+ e.getMessage());
		}*/
	}

	/**
 * Generates a random BigDecimal.
 *
 * @param precision the precision
 * @param scale the scale
 * @return BigDecimal
 */
	public BigDecimal randomBigDecimal(int precision, int scale) {

		BigDecimal rand = new BigDecimal(Math.random());
		rand = rand.movePointRight(precision);
		rand = rand.divideToIntegralValue(new BigDecimal(1));

		return rand.movePointLeft(scale);

	}

	/**
 * Generates a random Long.
 *
 * @param precision the precision
 * @return Long
 */
	public Long randomLong(int precision) {
		Long value;

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < precision; i++) {
			sb.append(getRandomNumber());
		}

		value = new Long(sb.toString());

		return value;
	}

	/**
 * Generates a random Boolean value.
 *
 * @return Boolean
 */
	public Boolean randomBoolean() {

		if (Math.random() > 0.5) {
			return true;
		} else {
			return false;
		}

	}

	/**
 * Generates a random Long Boolean value.
 *
 * @return Boolean
 */
	public Long randomLongBoolean() {

		if (Math.random() > 0.5) {
			return 1L;
		} else {
			return 0L;
		}

	}

	/**
 * Generates a random String.
 *
 * @param len the len
 * @return String
 */
	public String randomString(int len) {
		String value;

		// do {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < len; i++) {
			sb.append(getRandomChar());
		}

		value = sb.toString();

		return value;
	}

	/**
 * Returns a random char from char[].
 *
 * @return char
 */
	private char getRandomChar() {

		int pos = (int) (Math.random() * (double) chars.length);
		return chars[pos];

	}

	/**
 * Returns a random number from numbers[].
 *
 * @return char
 */
	private char getRandomNumber() {

		int pos = (int) (Math.random() * (double) numbers.length);
		return numbers[pos];

	}

	/**
 * Generates a random Hexadecimal number.
 *
 * @return char
 */
	private char getRandomHexadecimalNumber() {

		int pos = (int) (Math.random() * (double) hexNumbers.length);
		return hexNumbers[pos];

	}

	/**
 * Generates a random Character.
 *
 * @return Character
 */
	/*
	 * public Character randomChar() {
	 * 
	 * if (charListSet.size() == LIST_SET_SIZE) { charListSet.remove(0); }
	 * 
	 * Character value;
	 * 
	 * do { value = getRandomChar(); } while (charListSet.contains(value));
	 * 
	 * charListSet.add(value);
	 * 
	 * return value; }
	 */

	/**
	 * Generates a random Calender
	 * 
	 * @return Calendar
	 */
	public Calendar randomCalendar() {
		Calendar c = Calendar.getInstance();

		c.set((int) ((Math.random() * 20) + 2000), (int) (Math.random() * 12),
				(int) (Math.random() * 29), (int) (Math.random() * 24),
				(int) (Math.random() * 60), (int) (Math.random() * 60));

		return c;
	}

	/**
 * randomly generates a double value.
 *
 * @param precision the precision
 * @param scale the scale
 * @return Double
 */
	public Double randomDouble(int precision, int scale) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < (precision - scale); i++) {
			sb.append(getRandomNumber());
		}

		sb.append(".");

		for (int i = 0; i < scale; i++) {
			sb.append(getRandomNumber());
		}

		return new Double(sb.toString());
	}

	/**
 * Generates a random hexadecimal string.
 *
 * @param length - returned string length
 * @return String
 */
	public String randomHexadecimal(int length) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < length; i++) {
			sb.append(getRandomHexadecimalNumber());
		}

		return sb.toString();

	}

	/**
 * Generates a random Integer.
 *
 * @param args the arguments
 */
	public static void main(String[] args) {

	}

	/**
 * Generates a random Integer.
 *
 * @param precision the precision
 * @return Integer
 */
	public Integer randomInteger(int precision) {

		Integer value;
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < precision; i++) {
			sb.append(getRandomNumber());
		}

		value = new Integer(sb.toString());
		return value;
	}

} // end class RandomData
