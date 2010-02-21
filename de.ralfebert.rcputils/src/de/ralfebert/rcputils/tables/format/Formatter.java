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
package de.ralfebert.rcputils.tables.format;

import java.text.DateFormat;
import java.text.NumberFormat;

import org.eclipse.core.databinding.conversion.IConverter;

import de.ralfebert.rcputils.properties.IValueFormatter;

/**
 * Factory for default value formatters for commonly-used types.
 * 
 * @author Ralf Ebert <info@ralfebert.de>
 */
public class Formatter {

	/**
	 * Returns a formatter for String to double/Double by NumberFormat
	 */
	public static StringValueFormatter forDouble(NumberFormat numberFormat) {
		return new StringValueFormatter(numberFormat) {
			@Override
			public Object parse(String str) {
				return ((Number) super.parse(str)).doubleValue();
			}
		};
	}

	/**
	 * Returns a formatter for String to long/Long by NumberFormat
	 */
	public static StringValueFormatter forLong(NumberFormat numberFormat) {
		return new StringValueFormatter(numberFormat) {
			@Override
			public Object parse(String str) {
				return ((Number) super.parse(str)).longValue();
			}
		};
	}

	/**
	 * Returns a formatter for String to float/Float by NumberFormat
	 */
	public static StringValueFormatter forFloat(NumberFormat numberFormat) {
		return new StringValueFormatter(numberFormat) {
			@Override
			public Object parse(String str) {
				return ((Number) super.parse(str)).floatValue();
			}
		};
	}

	/**
	 * Returns a formatter for String to int/Integer by NumberFormat
	 */
	public static StringValueFormatter forInt(NumberFormat numberFormat) {
		numberFormat.setParseIntegerOnly(true);
		return new StringValueFormatter(numberFormat);
	}

	/**
	 * Returns a formatter for String to Date by DateFormat.
	 */
	public static StringValueFormatter forDate(DateFormat dateFormat) {
		return new StringValueFormatter(dateFormat);
	}

	/**
	 * Returns a value formatter by two existing data binding IConverterts, on
	 * for each direction.
	 */
	public static IValueFormatter<Object, Object> fromConverters(final IConverter format, final IConverter parse) {
		return new IValueFormatter<Object, Object>() {

			public Object format(Object obj) {
				return format.convert(obj);
			}

			public Object parse(Object obj) {
				return parse.convert(obj);
			}
		};
	}

	/**
	 * Returns a formatter for string to int/Integer.
	 */
	public static IValueFormatter<Integer, String> forInt() {
		return new IValueFormatter<Integer, String>() {

			public String format(Integer obj) {
				return Integer.toString(obj);
			}

			public Integer parse(String obj) {
				return Integer.parseInt(obj);
			}

		};
	}

}