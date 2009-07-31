package de.ralfebert.rcputils.properties;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;

import de.ralfebert.rcputils.properties.internal.PropertyValue;

/**
 * EditingSupport for JFace viewers that gets and sets the value using a bean
 * property (can be nested with ".")
 * 
 * @author Ralf Ebert <info@ralfebert.de>
 */
public class PropertyEditingSupport extends EditingSupport {

	private final CellEditor cellEditor;
	private final IValue valueHandler;

	public PropertyEditingSupport(ColumnViewer viewer, String propertyName, CellEditor cellEditor) {
		this(viewer, new PropertyValue(propertyName), cellEditor);
	}

	public PropertyEditingSupport(ColumnViewer viewer, IValue valueHandler, CellEditor cellEditor) {
		super(viewer);
		this.valueHandler = valueHandler;
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
		return valueHandler.getValue(element);
	}

	@Override
	protected void setValue(Object element, Object value) {
		valueHandler.setValue(element, value);
		getViewer().refresh(element);
	}

}
