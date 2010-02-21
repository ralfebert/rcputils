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

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;

import de.ralfebert.rcputils.internal.RcpUtilsPlugin;
import de.ralfebert.rcputils.tables.ICellFormatter;

/**
 * PropertyCellLabelProvider is a CellLabelProvider that gets cell labels using
 * a nested bean property string like "company.country.name".
 * 
 * @author Ralf Ebert <info@ralfebert.de>
 */
@SuppressWarnings("unchecked")
public class PropertyCellLabelProvider extends CellLabelProvider {

	private final IValue valueHandler;
	private IValueFormatter valueFormatter;
	private final ICellFormatter cellFormatter;

	public PropertyCellLabelProvider(String propertyName) {
		this.valueHandler = new PropertyValue(propertyName);
		this.cellFormatter = null;
	}

	public PropertyCellLabelProvider(IValue valueHandler, IValueFormatter valueFormatter, ICellFormatter cellFormatter) {
		this.valueHandler = valueHandler;
		this.valueFormatter = valueFormatter;
		this.cellFormatter = cellFormatter;
	}

	@Override
	public void update(ViewerCell cell) {
		try {
			Object rawValue = null;
			if (valueHandler != null) {
				rawValue = valueHandler.getValue(cell.getElement());
				Object formattedValue = rawValue;
				if (valueFormatter != null) {
					formattedValue = valueFormatter.format(rawValue);
				}
				cell.setText(String.valueOf(formattedValue));
			}
			if (cellFormatter != null) {
				cellFormatter.formatCell(cell, rawValue);
			}
		} catch (Exception e) {
			RcpUtilsPlugin.logException(e);
		}
	}
}
