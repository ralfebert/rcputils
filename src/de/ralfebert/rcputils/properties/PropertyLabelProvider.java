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

import org.eclipse.jface.viewers.LabelProvider;

import de.ralfebert.rcputils.internal.RcpUtilsPlugin;

/**
 * PropertyLabelProvider is a LabelProvider that gets labels using a nested bean
 * property string like "company.country.name".
 * 
 * @author Ralf Ebert <info@ralfebert.de>
 */
@SuppressWarnings("unchecked")
public class PropertyLabelProvider extends LabelProvider {

	private final IValue valueHandler;
	private IValueFormatter valueFormatter;

	public PropertyLabelProvider(String propertyName) {
		this.valueHandler = new PropertyValue(propertyName);
	}

	public PropertyLabelProvider(IValue valueHandler, IValueFormatter valueFormatter) {
		this.valueHandler = valueHandler;
		this.valueFormatter = valueFormatter;
	}

	@Override
	public String getText(Object element) {
		try {
			Object rawValue = null;
			if (valueHandler != null) {
				rawValue = valueHandler.getValue(element);
				Object formattedValue = rawValue;
				if (valueFormatter != null) {
					formattedValue = valueFormatter.format(rawValue);
				}
				return String.valueOf(formattedValue);
			}
		} catch (Exception e) {
			RcpUtilsPlugin.logException(e);
		}
		return "";
	}
}
