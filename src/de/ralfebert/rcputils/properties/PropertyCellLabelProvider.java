package de.ralfebert.rcputils.properties;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;

import de.ralfebert.rcputils.properties.internal.PropertyValue;
import de.ralfebert.rcputils.tablebuilder.ICellFormatter;

/**
 * PropertyCellLabelProvider is a CellLabelProvider that gets cell labels using
 * a bean property (can be nested with ".")
 * 
 * @author Ralf Ebert <info@ralfebert.de>
 */
public class PropertyCellLabelProvider extends CellLabelProvider {

	private final IValue valueHandler;
	private final ICellFormatter valueFormatter;

	public PropertyCellLabelProvider(String propertyName) {
		this.valueHandler = new PropertyValue(propertyName);
		this.valueFormatter = null;
	}

	public PropertyCellLabelProvider(IValue valueHandler, ICellFormatter valueFormatter) {
		this.valueHandler = valueHandler;
		this.valueFormatter = valueFormatter;
	}

	@Override
	public void update(ViewerCell cell) {
		Object value = null;
		if (valueHandler != null) {
			value = valueHandler.getValue(cell.getElement());
			cell.setText(String.valueOf(value));
		}
		if (valueFormatter != null) {
			valueFormatter.formatCell(cell, value);
		}
	}

}
