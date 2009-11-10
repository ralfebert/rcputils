/*******************************************************************************
 * Copyright (c) 2008 Ralf Ebert
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Ralf Ebert - initial API and implementation
 *******************************************************************************/
package de.ralfebert.rcputils.types;

import java.util.Calendar;
import java.util.Date;

/**
 * Helper methods for dealing with java.util.Date
 */
public class DateHelper {

	/**
	 * Returns true if the given date equals or is after tomorrow (ignoring the
	 * time)
	 * 
	 * @return true if the given date is equals or after tomorrow
	 */
	public static boolean isEqualOrAfterTomorrow(Date d) {
		return (d != null && !d.before(getTomorrow()));
	}

	/**
	 * Returns the date representing today (by system time) without time
	 * 
	 * @return
	 */
	public static Date getToday() {
		Calendar c = Calendar.getInstance();
		setZeroTime(c);
		return c.getTime();
	}

	/**
	 * Returns the date representing tomorrow (by system time) without time
	 * 
	 * @return Date object representing tomorrow 00:00:00
	 */
	public static Date getTomorrow() {
		Calendar c = Calendar.getInstance();
		setZeroTime(c);
		c.add(Calendar.DATE, 1);
		return c.getTime();
	}

	/**
	 * Returns date without time (hour, minute, second, millisecond set to 0)
	 * 
	 * @param date
	 *            date
	 * @return date without time
	 */
	public static Date getDateWithoutTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		setZeroTime(c);
		return c.getTime();
	}

	/**
	 * Sets hour, minute, second, millisecond set to 0 for the given Calendar
	 * object
	 * 
	 * @param c
	 *            Calendar object
	 */
	private static void setZeroTime(Calendar c) {
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
	}

	/**
	 * Returns the latest date of the given dates
	 * 
	 * @param d1
	 * @param d2
	 * @return latest date of the given dates
	 */
	public static Date max(Date d1, Date d2) {
		if (d1 == null) {
			return d2;
		}
		if (d2 == null) {
			return d1;
		}
		return (d1.after(d2) ? d1 : d2);
	}

	/**
	 * Returns the earliest date of the given dates
	 * 
	 * @param d1
	 *            date
	 * @param d2
	 *            date
	 * @return earliest date of the given dates
	 */
	public static Date min(Date d1, Date d2) {
		if (d1 == null) {
			return d2;
		}
		if (d2 == null) {
			return d1;
		}
		return (d1.before(d2) ? d1 : d2);
	}

	/**
	 * Adds nDays to the given date and returns a new date
	 * 
	 * @param date
	 *            date
	 * @param nDays
	 *            number of days to add
	 * @return date added nDays
	 */
	public static Date addDays(Date date, int nDays) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, nDays);
		return c.getTime();
	}

	/**
	 * This method creates a new Date object with the given date
	 * 
	 * @param day
	 *            day of the month 1..31
	 * @param month
	 *            month of the year 1..12
	 * @param year
	 *            year for digits, e.g. 2002
	 * @return a Date based on input parameters (with time zeroed)
	 */
	public static Date getDateByDayMonthYear(int day, int month, int year) {

		if (day < 1 || day > 31) {
			throw new IllegalArgumentException("day is out of range 1..31:" + day);
		}
		if (month < 1 || month > 12) {
			throw new IllegalArgumentException("month is out of range 1..12:" + month);
		}

		Calendar c = Calendar.getInstance();
		// For Calendar 0 is January.
		c.set(year, month - 1, day);
		setZeroTime(c);
		return c.getTime();
	}

}