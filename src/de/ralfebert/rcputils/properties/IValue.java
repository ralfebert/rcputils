package de.ralfebert.rcputils.properties;

public interface IValue {

	public Object getValue(Object element);

	public void setValue(Object element, Object value);

}
