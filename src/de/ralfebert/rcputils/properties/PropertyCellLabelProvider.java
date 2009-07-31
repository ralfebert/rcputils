package de.ralfebert.rcputils.properties;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;

import de.ralfebert.rcputils.properties.internal.PropertyAccess;

/**
 * PropertyCellLabelProvider is a CellLabelProvider that gets cell labels using
 * a bean property (can be nested with ".")
 * 
 * @author Ralf Ebert <info@ralfebert.de>
 */
public class PropertyCellLabelProvider extends CellLabelProvider {

	private final PropertyAccess property;

	public PropertyCellLabelProvider(String propertyName) {
		property = new PropertyAccess(propertyName);
	}

	@Override
	public void update(ViewerCell cell) {
		cell.setText(String.valueOf(property.getValue(cell.getElement())));
	}

}
