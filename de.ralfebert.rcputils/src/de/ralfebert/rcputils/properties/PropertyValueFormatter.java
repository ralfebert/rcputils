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
package de.ralfebert.rcputils.properties;

import de.ralfebert.rcputils.internal.RcpUtilsPlugin;

/**
 * PropertyValueFormatter describes a conversion from a object to some property
 * of the object.
 * 
 * @author Ralf Ebert <info@ralfebert.de>
 */
@SuppressWarnings("unchecked")
public class PropertyValueFormatter implements IValueFormatter {

	private final IValue valueHandler;

	public PropertyValueFormatter(String propertyName) {
		this.valueHandler = new PropertyValue(propertyName);
	}

	public Object format(Object obj) {
		try {
			return valueHandler.getValue(obj);
		} catch (Exception e) {
			RcpUtilsPlugin.logException(e);
			return null;
		}
	}

	public Object parse(Object obj) {
		throw new UnsupportedOperationException();
	}
}
