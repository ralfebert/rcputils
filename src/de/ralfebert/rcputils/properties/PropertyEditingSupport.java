package de.ralfebert.rcputils.properties;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;

import de.ralfebert.rcputils.properties.internal.PropertyAccess;

/**
 * EditingSupport for JFace viewers that gets and sets the value using a bean
 * property (can be nested with ".")
 * 
 * @author Ralf Ebert <info@ralfebert.de>
 */
public class PropertyEditingSupport extends EditingSupport {

	private final PropertyAccess property;
	private final CellEditor cellEditor;

	public PropertyEditingSupport(ColumnViewer viewer, String propertyName) {
		super(viewer);
		this.property = new PropertyAccess(propertyName);
		this.cellEditor = null;
	}

	public PropertyEditingSupport(ColumnViewer viewer, String propertyName, CellEditor cellEditor) {
		super(viewer);
		this.property = new PropertyAccess(propertyName);
		this.cellEditor = cellEditor;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return cellEditor;
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected Object getValue(Object element) {
		return property.getValue(element);
	}

	@Override
	protected void setValue(Object element, Object value) {
		property.setValue(element, value);
		getViewer().refresh(element);
	}

}
