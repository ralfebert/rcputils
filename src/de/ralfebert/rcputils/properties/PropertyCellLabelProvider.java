package de.ralfebert.rcputils.properties;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;

import de.ralfebert.rcputils.builder.table.ICellFormatter;
import de.ralfebert.rcputils.internal.RcpUtilsPlugin;
import de.ralfebert.rcputils.properties.internal.PropertyValue;

/**
 * PropertyCellLabelProvider is a CellLabelProvider that gets cell labels using
 * a bean property (can be nested with ".")
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
